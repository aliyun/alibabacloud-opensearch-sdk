package com.aliyun.opensearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.*;
import java.util.function.Consumer;

import com.aliyun.opensearch.auth.Authentication;
import com.aliyun.opensearch.auth.OpenSearchAuthentication;
import com.aliyun.opensearch.auth.credential.Credentials;
import com.aliyun.opensearch.auth.credential.provider.CredentialsProvider;
import com.aliyun.opensearch.client.ErrorResult;
import com.aliyun.opensearch.client.OpenSearchResponse;
import com.aliyun.opensearch.client.OpenSearchResponseConsumer;
import com.aliyun.opensearch.client.ServerSentEvent;
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
import org.apache.http.Header;
import org.apache.http.HttpResponse;
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

    private HttpClientManager httpClientManager;

    private CredentialsProvider credentialsProvider;

    public OpenSearchClient(OpenSearch opensearch) {
        this(opensearch, new HttpClientManager());
    }

    public OpenSearchClient(OpenSearch opensearch, HttpClientManager httpClientManager) {
        this(opensearch, null, httpClientManager);

        if (opensearch.isSetSecurityToken()) {
            this.authentication = new OpenSearchAuthentication(this.host, opensearch.getAccessKey(),
                opensearch.getSecret(), opensearch.getSecurityToken());
        } else {
            this.authentication = new OpenSearchAuthentication(this.host, opensearch.getAccessKey(),
                opensearch.getSecret());
        }
        this.credentialsProvider = ((OpenSearchAuthentication)authentication).getCredentialsProvider();
    }

    public OpenSearchClient(OpenSearch opensearch, CredentialsProvider credentialsProvider) {
        this(opensearch, credentialsProvider, new HttpClientManager());
    }

    public OpenSearchClient(OpenSearch opensearch, CredentialsProvider credentialsProvider, HttpClientManager httpClientManager) {
        this.credentialsProvider = credentialsProvider;
        this.httpClientManager = httpClientManager;

        this.host = opensearch.getHost();
        if (host == null || host.length() == 0) {
            throw new IllegalArgumentException("host not speicifed.");
        }

        this.host = Utils.normalize(this.host);

        this.authentication = new OpenSearchAuthentication(this.host, credentialsProvider);

        if (opensearch.isSetGzip() || opensearch.isGzip()) { // user set OR default(true)
            this.httpClientManager.enableGzip();
        }

        if (opensearch.getTimeout() > 0) {
            this.httpClientManager.setTimeout(opensearch.getTimeout());
            timeout = opensearch.getTimeout();
        }

        if (opensearch.isSetConnectTimeout() && opensearch.getConnectTimeout() > 0) {
            this.httpClientManager.setConnectTimeout(opensearch.getConnectTimeout());
        }

        if (opensearch.isSetExpired() && opensearch.isExpired()) {
            expired = opensearch.isExpired();
        }
    }

    public HttpClientManager getHttpClientManager() {
        return this.httpClientManager;
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

    public HttpResponse callForHttpResponse(String path, Map<String, String> params, String method) throws OpenSearchClientException {
        return authAndCallForHttpResponse(path, params, method);
    }

    public void callForServerSentEvents(String path, Map<String, String> params, String method,
                                        Consumer<ServerSentEvent<String>> eventConsumer) throws OpenSearchClientException {
        callForHttpResultEvents(path, params, method, e ->
            eventConsumer.accept(ServerSentEvent.builder(e.data().getResult())
                .id(e.id())
                .comment(e.comment())
                .event(e.event())
                .retry(e.retry())
                .build()
            ));
    }

    public void callForOpenSearchResultEvents(String path, Map<String, String> params, String method,
                                        OpenSearchResponseConsumer<ServerSentEvent<OpenSearchResult>> eventConsumer)
        throws OpenSearchException, OpenSearchClientException {
        callForHttpResultEvents(path, params, method, e ->
            eventConsumer.accept(ServerSentEvent.builder(fromHttpResult(e.data()))
                .id(e.id())
                .comment(e.comment())
                .event(e.event())
                .retry(e.retry())
                .build()
            ));
    }

    public void callForHttpResultEvents(String path, Map<String, String> params, String method,
                                        OpenSearchResponseConsumer<ServerSentEvent<HttpResult>> eventConsumer) throws OpenSearchClientException {
        HttpResponse httpResponse = authAndCallForHttpResponse(path, params, method);

        try {
            InputStream responseBodyStream = httpResponse.getEntity()
                .getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(responseBodyStream, ENCODE_UTF8));
            StringLineConsumer stringLineConsumer = new StringLineConsumer(e -> {
                HttpResult httpResult = new HttpResult(httpResponse.getStatusLine().getStatusCode(),
                    httpResponse.getStatusLine().getReasonPhrase(), e.data(), null, null,
                    Arrays.asList(httpResponse.getAllHeaders()));
                eventConsumer.accept(ServerSentEvent.builder(httpResult)
                    .id(e.id())
                    .comment(e.comment())
                    .event(e.event())
                    .retry(e.retry())
                    .build()
                );
            });
            String line;
            while ((line = br.readLine()) != null) {
                stringLineConsumer.accept(line);
            }
            // Trigger potential last event
            stringLineConsumer.accept("");

            br.close();

            // 请求成功
            getHttpClientManager().getClientTracer().success(httpResponse, "");
        } catch (Throwable e) {
            log.debug(String.format("failed to request [%s]", path), e);
            throw new OpenSearchClientException(e);
        }
    }

    public OpenSearchResult callAndDecodeResult(String path, Map<String, String> params, String method)
        throws OpenSearchException, OpenSearchClientException {
        HttpResult result = authAndCall(path, params, method);
        return fromHttpResult(result);
    }

    protected HttpResult authAndCall(String path, Map<String, String> params, String method)
        throws OpenSearchClientException {
        String request_path = buildRequestPath(path);
        String url = buildUrl(request_path);
        Map<String, String> headers = buildRequestHeaders(params, method, request_path, url);

        try {
            return this.doRequest(url, headers, params, method, false);
        } catch (Throwable e) {
            log.debug(String.format("failed to request [%s]", url), e);
            throw new OpenSearchClientException(e);
        }
    }

    protected HttpResponse authAndCallForHttpResponse(String path, Map<String, String> params, String method)
        throws OpenSearchClientException {
        String request_path = buildRequestPath(path);
        String url = buildUrl(request_path);
        Map<String, String> headers = buildRequestHeaders(params, method, request_path, url);

        try {
            return doRequestForHttpResponse(url, headers, params, method, false);
        } catch (Throwable e) {
            log.debug(String.format("failed to request [%s]", url), e);
            throw new OpenSearchClientException(e);
        }
    }

    private Map<String, String> buildRequestHeaders(Map<String, String> params, String method, String request_path, String url) throws OpenSearchClientException {
        Map<String, String> headers;

        long expiredTime = 0L;
        if (expired == true && timeout > 0) {
            expiredTime = System.currentTimeMillis() + timeout;
        }

        try {
            Credentials credentials = credentialsProvider.getCredentials();
            TreeMap<String, String> opensearchHeaders = createOpenSearchHeaders(expiredTime, credentials);
            TreeMap<String, Object> signParameters = this.authentication.createSignParameters(method, request_path,
                opensearchHeaders, params);
            String signature = this.authentication.createAliyunSign(signParameters, credentials);
            headers = this.authentication.createHttpHeaders(opensearchHeaders, signParameters, signature, credentials);
        } catch (Throwable authException) {
            log.debug(String.format("failed to authentication while request to [%s]", url), authException);
            throw new OpenSearchClientException(authException.getMessage(), authException);
        }
        return headers;
    }

    private String buildUrl(String request_path) {
        return this.host + request_path;
    }

    private String buildRequestPath(String path) {
        String uri = this.baseURI;
        return uri + path;
    }

    protected TreeMap<String, String> createOpenSearchHeaders(long expiredTime, Credentials credentials) {
        return this.authentication.createOpenSearchHeaders(expiredTime, credentials);
    }

    protected HttpResult doRequest(String url, Map<String, String> headers, Map<String,
        String> requestParams, String method, boolean isPB) throws IOException {
        HttpResult httpResult = new HttpResult();
        url = url + Utils.getHTTPParamsAsUrlStr(method, requestParams);
        if (method.equals(METHOD_POST)) {
            httpResult = this.httpClientManager.doPost(url, headers, requestParams.get(POST_BODY_PARAM_KEY), ENCODE_UTF8);
        } else if (method.equals(METHOD_GET)) {
            httpResult = this.httpClientManager.doGet(url, headers, ENCODE_UTF8, isPB);
        } else if (method.equals(METHOD_DELETE)) {
            httpResult = this.httpClientManager.doDelete(url, headers, ENCODE_UTF8);
        } else if (method.equals(METHOD_PATCH)) {
            httpResult = this.httpClientManager.doPatch(url, headers, requestParams.get(POST_BODY_PARAM_KEY), ENCODE_UTF8);
        } else if (method.equals(METHOD_PUT)) {
            httpResult = this.httpClientManager.doPut(url, headers, requestParams.get(POST_BODY_PARAM_KEY), ENCODE_UTF8);
        }
        return httpResult;
    }

    protected HttpResponse doRequestForHttpResponse(String url, Map<String, String> headers, Map<String,
        String> requestParams, String method, boolean isPB) throws IOException {
        HttpResponse httpResponse;
        if (method.equals(METHOD_POST)) {
            httpResponse = this.httpClientManager.doPostForHttpResponse(url, headers, requestParams.get(POST_BODY_PARAM_KEY), ENCODE_UTF8);
        } else if (method.equals(METHOD_GET)) {
            url = url + Utils.getHTTPParamsAsUrlStr(method, requestParams);
            httpResponse = this.httpClientManager.doGetForHttpResponse(url, headers, ENCODE_UTF8, isPB);
        } else if (method.equals(METHOD_DELETE)) {
            httpResponse = this.httpClientManager.doDeleteForHttpResponse(url, headers, ENCODE_UTF8);
        } else if (method.equals(METHOD_PATCH)) {
            httpResponse = this.httpClientManager.doPatchForHttpResponse(url, headers, requestParams.get(POST_BODY_PARAM_KEY), ENCODE_UTF8);
        } else if (method.equals(METHOD_PUT)) {
            httpResponse = this.httpClientManager.doPutForHttpResponse(url, headers, requestParams.get(POST_BODY_PARAM_KEY), ENCODE_UTF8);
        } else {
            throw new IllegalArgumentException();
        }
        return httpResponse;
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
            openSearchException.setRequestId(getRequestId(openSearchResponse, httpResult));
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
        traceInfo.setRequestId(getRequestId(openSearchResponse, httpResult));
        traceInfo.setTracer(openSearchResponse.getTracer());
        openSearchResult.setTraceInfo(traceInfo);
        openSearchResult.setResult(openSearchResponse.getResultString());
        return openSearchResult;
    }

    private static String getRequestId(OpenSearchResponse openSearchResponse, HttpResult httpResult) {
        if (StringUtils.isEmpty(openSearchResponse.getRequest_id())) {
            return findRequestIdHeaderValue(httpResult.getResponseHeaders());
        }

        return openSearchResponse.getRequest_id();
    }

    private static String findRequestIdHeaderValue(List<Header> headers) {
        return headers.stream()
            .filter(header -> "Request-Id".equalsIgnoreCase(header.getName()))
            .findAny()
            .map(Header::getValue)
            .orElse(null);
    }


    /**
     * 文本行消费器
     */
    private static class StringLineConsumer implements OpenSearchResponseConsumer<String> {
        private OpenSearchResponseConsumer<ServerSentEvent<String>> eventConsumer;
        private List<String> lineList = new ArrayList<String>(4);
        private ServerSentEventConverter serverSentEventConverter = new ServerSentEventConverter();

        /**
         * 构造函数
         *
         * <p>当隶属于同一个文本行的事件收集齐备时，将会触发一次{@link ServerSentEvent<String>}消费器的调用。</p>
         *
         * @param eventConsumer {@link ServerSentEvent<String>}的消费器
         */
        public StringLineConsumer(OpenSearchResponseConsumer<ServerSentEvent<String>> eventConsumer) {
            this.eventConsumer = eventConsumer;
        }

        /**
         * 接收一个文本行（不包含回车和换行符号）
         *
         * @param s the input argument 空文本行代表事件结束
         */
        @Override
        public void accept(String s) throws OpenSearchException, OpenSearchClientException {
            if ("".equals(s)) {
                if (lineList.size() > 0) {
                    ServerSentEvent<String> serverSentEvent = serverSentEventConverter.convert(lineList);
                    eventConsumer.accept(serverSentEvent);
                    lineList.clear();
                }
            } else {
                lineList.add(s);
            }
        }
    }

    /**
     * 将多行文本转换为{@link ServerSentEvent<String>}实例
     *
     * <p>代码改编自{@link org.springframework.http.codec.ServerSentEventHttpMessageReader#buildEvent()}</p>
     */
    private static class ServerSentEventConverter {
        public ServerSentEvent<String> convert(List<String> lines) {
            ServerSentEvent.Builder<String> sseBuilder = ServerSentEvent.builder();
            StringBuilder data = null;
            StringBuilder comment = null;

            for (String line : lines) {
                if (line.startsWith("data:")) {
                    int length = line.length();
                    if (length > 5) {
                        int index = (line.charAt(5) != ' ' ? 5 : 6);
                        if (length > index) {
                            data = (data != null ? data : new StringBuilder());
                            data.append(line, index, line.length());
                            data.append('\n');
                        }
                    }
                } else {
                    if (line.startsWith("id:")) {
                        sseBuilder.id(line.substring(3).trim());
                    }
                    else if (line.startsWith("event:")) {
                        sseBuilder.event(line.substring(6).trim());
                    }
                    else if (line.startsWith("retry:")) {
                        sseBuilder.retry(Duration.ofMillis(Long.parseLong(line.substring(6).trim())));
                    }
                    else if (line.startsWith(":")) {
                        comment = (comment != null ? comment : new StringBuilder());
                        comment.append(line.substring(1).trim()).append('\n');
                    }
                }
            }

            String decodedData = (data != null ? decodeData(data) : null);

            if (comment != null) {
                sseBuilder.comment(comment.substring(0, comment.length() - 1));
            }
            if (decodedData != null) {
                sseBuilder.data(decodedData);
            }
            return sseBuilder.build();
        }

        private String decodeData(StringBuilder data) {
            return data.substring(0, data.length() - 1);
        }
    }

}
