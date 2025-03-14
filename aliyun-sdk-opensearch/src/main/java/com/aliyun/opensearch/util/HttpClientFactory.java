/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.aliyun.opensearch.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.opensearch.sdk.generated.OpenSearchConstants;
import com.aliyun.opensearch.tracer.ClientTracer;
import com.aliyun.opensearch.tracer.DefaultClientTracer;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class HttpClientFactory {
    private static final Logger LOG = LoggerFactory.getLogger(HttpClientFactory.class);

    private static int CLEAN_INIT_DELAY = 5;
    private static int clean_check_interval = 200;
    private int maxConnections = 50;
    // 默认请求超时时间：10秒
    private int timeout = (10 * 1000);
    // 默认连接超时时间：5秒
    private int connectTimeout = (5 * 1000);
    private boolean gzip = false;

    // sdk version
    private String version = "v" + OpenSearchConstants.VERSION;

    private HttpClient httpClient;
    private HttpParams params;
    private ScheduledExecutorService scheduledExeService;
    public PoolingClientConnectionManager connectionManager;

    /**
     * OpenSearchClient 请求监控
     */
    private ClientTracer clientTracer = new DefaultClientTracer();

    /**
     * 设置 OpenSearchClient 请求监控
     */
    public void setClientTracer(ClientTracer clientTracer) {
        this.clientTracer = clientTracer;
    }

    public ClientTracer getClientTracer() {
        return this.clientTracer;
    }

    public HttpClientFactory(int timeout, int connectTimeout, int connections) {
        this(timeout, connectTimeout, connections, CLEAN_INIT_DELAY, clean_check_interval);
    }

    public HttpClientFactory(int timeout, int connectTimeout, int connections, int cleanIdleConnInitDelay,
                             int cleanIdleConnInterval) {
        if (timeout <= 0) {
            timeout = this.timeout;
        }

        if (connectTimeout <= 0) {
            connectTimeout = this.connectTimeout;
        }

        if (connections > 0 && maxConnections != connections) {
            maxConnections = connections;
        }

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));

        connectionManager = new PoolingClientConnectionManager(schemeRegistry);
        connectionManager.setMaxTotal(maxConnections);
        connectionManager.setDefaultMaxPerRoute(maxConnections);

        scheduledExeService = Executors.newScheduledThreadPool(1,
            new DaemonThreadFactory("Http-client-ConenctionPool-Monitor"));
        scheduledExeService.scheduleAtFixedRate(new IdleConnectionMonitor(connectionManager), CLEAN_INIT_DELAY,
            clean_check_interval, TimeUnit.MILLISECONDS);

        this.httpClient = new DefaultHttpClient(connectionManager);

        this.params = httpClient.getParams();

        HttpConnectionParams.setSoTimeout(params, timeout);
        HttpConnectionParams.setConnectionTimeout(params, connectTimeout);
        HttpConnectionParams.setTcpNoDelay(params, Boolean.TRUE);
        HttpConnectionParams.setStaleCheckingEnabled(params, Boolean.FALSE);
    }

    public int getMaxConnections() {
        return this.maxConnections;
    }

    /**
     * 设置gzip传输方式
     */
    public void enableGzip() {
        this.gzip = true;
    }

    /**
     * 设置超时时间
     *
     * @param timeout 请求超时时间
     */
    public void setTimeOut(int timeout) {
        if (timeout > 0 && timeout != this.timeout) {
            this.timeout = timeout;
            HttpConnectionParams.setSoTimeout(params, this.timeout);
        }
    }

    /**
     * 设置连接超时时间
     *
     * @param connectTimeout 连接超时时间
     */
    public void setConnectTimeout(int connectTimeout) {
        if (connectTimeout > 0 && connectTimeout != this.connectTimeout) {
            this.connectTimeout = connectTimeout;
            HttpConnectionParams.setConnectionTimeout(params, this.connectTimeout);
        }
    }

    /**
     * 通过POST的方法向服务器发送请求。
     *
     * @param reqURL   请求的url地址。
     * @param headers  请求的headers。
     * @param body     请求的body。
     * @param encoding 请求的编码方式。
     * @return 返回response结果。
     * @throws IOException  IOException
     * @throws ClientProtocolException  ClientProtocolException
     */
    public HttpResult doPost(String reqURL, Map<String, String> headers, String body, String encoding)
        throws IOException {
        return doPost(reqURL, headers, encoding, buildHttpPost(reqURL, body, encoding));
    }

    private HttpPost buildHttpPost(String reqURL, String body, String encoding) {
        HttpPost httpPost = new HttpPost(reqURL);
        if (!StringUtils.isEmpty(body)) {
            httpPost.setEntity(new StringEntity(body, encoding));
        }
        return httpPost;
    }

    public HttpResponse doPostForHttpResponse(String reqURL, Map<String, String> headers, String body, String encoding)
        throws IOException {
        return doPostForHttpResponse(reqURL, headers, encoding, buildHttpPost(reqURL, body, encoding));
    }

    public HttpResult doPut(String reqURL, Map<String, String> headers, String body, String encoding)
        throws IOException {
        return doPost(reqURL, headers, encoding, buildHttpPut(reqURL, body, encoding));
    }

    private HttpPut buildHttpPut(String reqURL, String body, String encoding) {
        HttpPut httpPut = new HttpPut(reqURL);
        if (!StringUtils.isEmpty(body)) {
            httpPut.setEntity(new StringEntity(body, encoding));
        }
        return httpPut;
    }
    public HttpResponse doPutForHttpResponse(String reqURL, Map<String, String> headers, String body, String encoding)
        throws IOException {
        return doPostForHttpResponse(reqURL, headers, encoding, buildHttpPut(reqURL, body, encoding));
    }

    public HttpResult doPatch(String reqURL, Map<String, String> headers, String body, String encoding)
        throws IOException {
        return doPost(reqURL, headers, encoding, buildHttpPatch(reqURL, body, encoding));
    }

    private HttpPatch buildHttpPatch(String reqURL, String body, String encoding) {
        HttpPatch httpPatch = new HttpPatch(reqURL);
        if (!StringUtils.isEmpty(body)) {
            httpPatch.setEntity(new StringEntity(body, encoding));
        }
        return httpPatch;
    }

    public HttpResponse doPatchForHttpResponse(String reqURL, Map<String, String> headers, String body, String encoding)
        throws IOException {
        return doPostForHttpResponse(reqURL, headers, encoding, buildHttpPatch(reqURL, body, encoding));
    }

    public HttpResult doDelete(String reqURL, Map<String, String> headers, String encoding) throws IOException {
        return doPost(reqURL, headers, encoding, buildHttpDelete(reqURL));
    }

    private HttpDelete buildHttpDelete(String reqURL) {
        HttpDelete httpDelete = new HttpDelete(reqURL);
        return httpDelete;
    }

    public HttpResponse doDeleteForHttpResponse(String reqURL, Map<String, String> headers, String encoding) throws IOException {
        return doPostForHttpResponse(reqURL, headers, encoding, buildHttpDelete(reqURL));
    }

    private <T extends HttpRequestBase> HttpResult doPost(String reqURL, Map<String, String> headers,
                                                          String encoding,
                                                          T httpRequest) throws IOException {
        HttpResponse response = doPostForHttpResponse(reqURL, headers, encoding, httpRequest);

        String result = "";
        try {
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                try {
                    result = getResponseContent(entity, encoding);
                } finally {
                    HttpClientUtils.closeQuietly(response);
                }
            }
        } catch (RuntimeException e) {
            // 请求失败
            this.clientTracer.fail();
            throw e;
        } catch (IOException e) {
            // 请求失败
            this.clientTracer.fail();
            throw e;
        }

        // 请求成功
        this.clientTracer.success(response, result);

        StatusLine statusLine = response.getStatusLine();
        List<Header> requestHeaders = Lists.newArrayList(httpRequest.getAllHeaders());
        List<Header> responseHeaders = Lists.newArrayList(response.getAllHeaders());
        return new HttpResult(statusLine.getStatusCode(), statusLine.getReasonPhrase(), result, httpRequest.getURI(),
            requestHeaders, responseHeaders);
    }

    /**
     * 请求并获取HttpResponse对象
     * <p>注意：执行过程中出现异常会调用{@link ClientTracer#fail()}方法。
     * 正常返回不会调用{@link ClientTracer#success(HttpResponse, String)}。
     * 调用者需要通过{@link #getClientTracer()}方法获取{@link ClientTracer}实例完成上述调用。</p>
     */
    private <T extends HttpRequestBase> HttpResponse doPostForHttpResponse(String reqURL, Map<String, String> headers,
                                                          String encoding,
                                                          T httpRequest) throws IOException {
        // 请求开始
        this.clientTracer.start(httpRequest.getMethod(), reqURL);

        httpRequest.setHeader("User-Agent", "opensearch/java sdk " + version);
        if (this.gzip) {
            httpRequest.setHeader("Accept-Encoding", "gzip");
        }

        for (Entry<String, String> header : headers.entrySet()) {
            httpRequest.setHeader(header.getKey(), header.getValue());
        }
        LOG.debug("--------POST Headers: --------");
        for (Header header : httpRequest.getAllHeaders()) {
            LOG.debug(header.toString());
        }
        LOG.debug("--------------------------------");
        LOG.debug("httpRequest: " + httpRequest);

        // 发送请求
        this.clientTracer.send(httpRequest);

        HttpResponse response;
        try {
            response = httpClient.execute(httpRequest);
            validateResponse(response, httpRequest);
        } catch (RuntimeException e) {
            // 请求失败
            this.clientTracer.fail();
            throw e;
        } catch (IOException e) {
            // 请求失败
            this.clientTracer.fail();
            throw e;
        }

        return response;
    }

    private String getResponseContent(HttpEntity entity, String encoding) throws IOException {
        Preconditions.checkNotNull(entity);
        final String responseContent;
        Header contentEncoding = entity.getContentEncoding();
        if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
            GzipDecompressingEntity gzipEntity = new GzipDecompressingEntity(entity);
            responseContent = EntityUtils.toString(gzipEntity, encoding);
        } else {
            responseContent = EntityUtils.toString(entity, encoding);
        }
        return responseContent;
    }

    /**
     * 通过GET的方式向服务器发出请求。
     *
     * @param url      要请求的url。
     * @param headers  请求的headers。
     * @param encoding 指定的编码格式。
     * @param isPB     isPB。
     * @return 获取response返回的结果。
     * @throws IOException  IOException
     * @throws ClientProtocolException  ClientProtocolException
     */
    public HttpResult doGet(String url, Map<String, String> headers, String encoding, boolean isPB) throws IOException {
        HttpGetRequestResponse httpGetRequestResponse = doGetForHttpGetRequestResponse(url, headers, encoding, isPB);
        HttpResponse response = httpGetRequestResponse.getHttpResponse();
        HttpGet httpget = httpGetRequestResponse.getHttpGet();

        String result = "";
        try {
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                try {
                    result = getGetResponseContent(entity, encoding, isPB);
                } finally {
                    HttpClientUtils.closeQuietly(response);
                }
            }
        } catch (RuntimeException e) {
            // 请求失败
            this.clientTracer.fail();
            throw e;
        } catch (IOException e) {
            // 请求失败
            this.clientTracer.fail();
            throw e;
        }

        // 请求成功
        this.clientTracer.success(response, result);

        StatusLine statusLine = response.getStatusLine();
        List<Header> requestHeaders = Lists.newArrayList(httpget.getAllHeaders());
        List<Header> responseHeaders = Lists.newArrayList(response.getAllHeaders());
        return new HttpResult(statusLine.getStatusCode(), statusLine.getReasonPhrase(), result, httpget.getURI(),
            requestHeaders, responseHeaders);
    }

    /**
     * 请求并获取HttpResponse对象
     * <p>注意：执行过程中出现异常会调用{@link ClientTracer#fail()}方法。
     * 正常返回不会调用{@link ClientTracer#success(HttpResponse, String)}。
     * 调用者需要通过{@link #getClientTracer()}方法获取{@link ClientTracer}实例完成上述调用。</p>
     */
    public HttpResponse doGetForHttpResponse(String url, Map<String, String> headers, String encoding, boolean isPB) throws IOException {
        HttpGetRequestResponse httpGetRequestResponse
            = doGetForHttpGetRequestResponse(url, headers, encoding, isPB);
        return httpGetRequestResponse.getHttpResponse();
    }

    private HttpGetRequestResponse doGetForHttpGetRequestResponse(String url, Map<String, String> headers, String encoding, boolean isPB) throws IOException {
        LOG.debug("GET url: " + url);
        HttpGet httpget = new HttpGet(url);
        // 请求开始
        this.clientTracer.start(httpget.getMethod(), url);

        httpget.setHeader("User-Agent", "opensearch/java sdk " + version);
        if (this.gzip) {
            httpget.setHeader("Accept-Encoding", "gzip");
        }
        for (Entry<String, String> header : headers.entrySet()) {
            httpget.setHeader(header.getKey(), header.getValue());
        }
        LOG.debug("--------------- Get Headers: ---------------");
        for (Header header : httpget.getAllHeaders()) {
            LOG.debug(header.toString());
        }
        LOG.debug("--------------------------------");
        LOG.debug(httpget.toString());

        // 发送请求
        this.clientTracer.send(httpget);

        HttpResponse response;
        try {
            response = httpClient.execute(httpget);
            validateResponse(response, httpget);
        } catch (RuntimeException e) {
            // 请求失败
            this.clientTracer.fail();
            throw e;
        } catch (IOException e) {
            // 请求失败
            this.clientTracer.fail();
            throw e;
        }

        return new HttpGetRequestResponse(httpget, response);
    }

    private String getGetResponseContent(HttpEntity entity, String encoding, boolean isPB) throws IOException {
        String result = "";
        if (entity == null) {
            return result;
        }
        if (isPB) { encoding = "ISO8859-1"; }
        return getResponseContent(entity, encoding);
    }

    public void shutdownIdleConnectionMonitor() {
        if (scheduledExeService != null) {
            scheduledExeService.shutdown();
        }
    }

    private void validateResponse(HttpResponse response, HttpRequestBase request) {
        StatusLine status = response.getStatusLine();
        int code = status.getStatusCode();
        if (code >= HttpStatus.SC_BAD_REQUEST) {
            LOG.warn("Did not receive successful HTTP response: status code = {}, status message = {}",
                status.getStatusCode(), status.getReasonPhrase());
            // @隆宇 2024-1-15：对已经拿到响应结果的request调用`request.abort();`是没有意义的。
            // 再去获取响应体时有概率会出现Socket Closed错误。
        }
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public static void setCleanIdelConnCheckInterval(int checkInterval) {
        clean_check_interval = checkInterval;
    }

    static class HttpGetRequestResponse {
        private HttpGet httpGet;
        private HttpResponse httpResponse;

        public HttpGetRequestResponse(HttpGet httpGet, HttpResponse httpResponse) {
            this.httpGet = httpGet;
            this.httpResponse = httpResponse;
        }

        public HttpGet getHttpGet() {
            return httpGet;
        }

        public HttpResponse getHttpResponse() {
            return httpResponse;
        }
    }
}
