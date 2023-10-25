package com.aliyun.opensearch;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.aliyun.opensearch.auth.Authentication;
import com.aliyun.opensearch.auth.OpenSearchAuthentication;
import com.aliyun.opensearch.client.ErrorResult;
import com.aliyun.opensearch.client.OpenSearchResponse;
import com.aliyun.opensearch.sdk.generated.OpenSearch;
import com.aliyun.opensearch.sdk.generated.OpenSearchService;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchClientException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchResult;
import com.aliyun.opensearch.sdk.generated.commons.TraceInfo;
import com.aliyun.opensearch.util.HttpClientManager;
import com.aliyun.opensearch.util.HttpResult;
import com.aliyun.opensearch.util.JsonUtilWrapper;
import com.aliyun.opensearch.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenSearchClient implements OpenSearchService.Iface {
    private static final Logger log = LoggerFactory.getLogger(OpenSearchClient.class);

    /**
     * GET请求。
     */
    public static final String METHOD_GET = HttpGet.METHOD_NAME;
    /**
     * 指定默认的请求方式；默认为GET.
     */
    public static final String DEFAULT_METHOD = METHOD_GET;

    /**
     * POST请求。
     */
    public static final String METHOD_POST = HttpPost.METHOD_NAME;

    /**
     * PUT请求。
     */
    public static final String METHOD_PUT = HttpPut.METHOD_NAME;

    /**
     * PATCH请求。
     */
    public static final String METHOD_PATCH = HttpPatch.METHOD_NAME;

    /**
     * PATCH请求。
     */
    public static final String METHOD_DELETE = HttpDelete.METHOD_NAME;

    /**
     * The Constant POST_BODY_PARAM_KEY.
     */
    public static final String POST_BODY_PARAM_KEY = "_POST_BODY";

    public static final String ENCODE_UTF8 = "utf-8";
    /**
     * 当前API的版本号。
     */
    private static final String version = "v3";

    /**
     * 请求的domain地址。
     */
    private String host;
    private String baseURI = "/" + version + "/openapi";

    private static final int ERROR_INTERNAL = 1000;

    private int timeout = 0;

    private boolean expired = false;

    /**
     * The authentication.
     */
    private Authentication authentication;

    public OpenSearchClient(OpenSearch opensearch) {
        this.host = opensearch.getHost();
        if (host == null || host.length() == 0) {
            throw new IllegalArgumentException("host not speicifed.");
        }

        this.host = Utils.normalize(this.host);

        if (opensearch.isSetSecurityToken()) {
            this.authentication = new OpenSearchAuthentication(this.host, opensearch.getAccessKey(),
                opensearch.getSecret(), opensearch.getSecurityToken());
        } else {
            this.authentication = new OpenSearchAuthentication(this.host, opensearch.getAccessKey(),
                opensearch.getSecret());
        }

        if (opensearch.isSetGzip() || opensearch.isGzip()) { // user set OR default(true)
            HttpClientManager.enableGzip();
        }

        if (opensearch.getTimeout() > 0) {
            HttpClientManager.setTimeout(opensearch.getTimeout());
            timeout = opensearch.getTimeout();
        }

        if (opensearch.isSetConnectTimeout() && opensearch.getConnectTimeout() > 0) {
            HttpClientManager.setConnectTimeout(opensearch.getConnectTimeout());
        }

        if (opensearch.isSetExpired() && opensearch.isExpired()) {
            expired = opensearch.isExpired();
        }
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    @Override
    public String call(String path, Map<String, String> params, String method) throws OpenSearchClientException {
        return authAndCall(path, params, method).getResult();
    }

    public OpenSearchResult callAndDecodeResult(String path, Map<String, String> params, String method)
        throws OpenSearchException, OpenSearchClientException {
        HttpResult result = authAndCall(path, params, method);
        return fromHttpResult(result);
    }

    protected HttpResult authAndCall(String path, Map<String, String> params, String method)
        throws OpenSearchClientException {
        String uri = this.baseURI;
        String request_path = uri + path;
        String url = this.host + request_path;
        Map<String, String> headers;

        long expiredTime = 0L;
        if (expired == true && timeout > 0) {
            expiredTime = System.currentTimeMillis() + timeout;
        }

        try {
            TreeMap<String, String> opensearchHeaders = this.authentication.createOpenSearchHeaders(expiredTime);
            TreeMap<String, Object> signParameters = this.authentication.createSignParameters(method, request_path,
                opensearchHeaders, params);
            String signature = this.authentication.createAliyunSign(signParameters);
            headers = this.authentication.createHttpHeaders(opensearchHeaders, signParameters, signature);
        } catch (Throwable authException) {
            log.debug(String.format("failed to authentication while request to [%s]", url), authException);
            throw new OpenSearchClientException(authException.getMessage(), authException);
        }

        try {
            return this.doRequest(url, headers, params, method, false);
        } catch (Throwable e) {
            log.debug(String.format("failed to request [%s]", url), e);
            throw new OpenSearchClientException(e);
        }
    }

    protected HttpResult doRequest(String url, Map<String, String> headers, Map<String,
        String> requestParams, String method, boolean isPB) throws IOException {
        HttpResult httpResult = new HttpResult();
        url = url + Utils.getHTTPParamsAsUrlStr(method, requestParams);
        if (method.equals(METHOD_POST)) {
            httpResult = HttpClientManager.doPost(url, headers, requestParams.get(POST_BODY_PARAM_KEY), ENCODE_UTF8);
        } else if (method.equals(METHOD_GET)) {
            httpResult = HttpClientManager.doGet(url, headers, ENCODE_UTF8, isPB);
        } else if (method.equals(METHOD_DELETE)) {
            httpResult = HttpClientManager.doDelete(url, headers, ENCODE_UTF8);
        } else if (method.equals(METHOD_PATCH)) {
            httpResult = HttpClientManager.doPatch(url, headers, requestParams.get(POST_BODY_PARAM_KEY), ENCODE_UTF8);
        } else if (method.equals(METHOD_PUT)) {
            httpResult = HttpClientManager.doPut(url, headers, requestParams.get(POST_BODY_PARAM_KEY), ENCODE_UTF8);
        }
        return httpResult;
    }

    protected static OpenSearchResult fromHttpResult(HttpResult httpResult)
        throws OpenSearchException, OpenSearchClientException {
        OpenSearchResponse openSearchResponse;
        if (httpResult.getCode() >= HttpStatus.SC_BAD_REQUEST) {
            OpenSearchException openSearchException = new OpenSearchException();
            String resultString = httpResult.getResult();
            if (StringUtils.isEmpty(resultString)) {
                openSearchException.setCode(ERROR_INTERNAL);
                openSearchException.setMessage(httpResult.getReason());
                throw openSearchException;
            }
            try {
                openSearchResponse = JsonUtilWrapper.fromJson(httpResult.getResult());
            } catch (JSONException e) {
                openSearchException.setCode(ERROR_INTERNAL);
                openSearchException.setMessage(httpResult.toString());
                throw openSearchException;
            }
            openSearchException.setRequestId(openSearchResponse.getRequest_id());
            List<ErrorResult> errorResults = openSearchResponse.getErrors();
            if (errorResults == null || errorResults.size() == 0) {
                openSearchException.setCode(ERROR_INTERNAL);
                openSearchException.setMessage(httpResult.toString());
                throw openSearchException;
            }
            ErrorResult errorResult = errorResults.get(0);
            openSearchException.setCode(errorResult.getCode());
            openSearchException.setMessage(errorResult.getMessage());
            throw openSearchException;
        }
        try {
            openSearchResponse = JsonUtilWrapper.fromJson(httpResult.getResult());
        } catch (JSONException e) {
            throw new OpenSearchClientException(String.format("parse result failed: %s", httpResult.toString()), e);
        }
        OpenSearchResult openSearchResult = new OpenSearchResult();
        TraceInfo traceInfo = new TraceInfo();
        traceInfo.setRequestId(openSearchResponse.getRequest_id());
        traceInfo.setTracer(openSearchResponse.getTracer());
        openSearchResult.setTraceInfo(traceInfo);
        openSearchResult.setResult(openSearchResponse.getResultString());
        return openSearchResult;
    }
}
