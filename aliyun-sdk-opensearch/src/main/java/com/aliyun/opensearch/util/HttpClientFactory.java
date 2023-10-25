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
        HttpPost httpPost = new HttpPost(reqURL);
        if (!StringUtils.isEmpty(body)) {
            httpPost.setEntity(new StringEntity(body, encoding));
        }
        return doPost(reqURL, headers, encoding, httpPost);
    }

    public HttpResult doPut(String reqURL, Map<String, String> headers, String body, String encoding)
        throws IOException {
        HttpPut httpPut = new HttpPut(reqURL);
        if (!StringUtils.isEmpty(body)) {
            httpPut.setEntity(new StringEntity(body, encoding));
        }
        return doPost(reqURL, headers, encoding, httpPut);
    }

    public HttpResult doPatch(String reqURL, Map<String, String> headers, String body, String encoding)
        throws IOException {
        HttpPatch httpPatch = new HttpPatch(reqURL);
        if (!StringUtils.isEmpty(body)) {
            httpPatch.setEntity(new StringEntity(body, encoding));
        }
        return doPost(reqURL, headers, encoding, httpPatch);
    }

    public HttpResult doDelete(String reqURL, Map<String, String> headers, String encoding) throws IOException {
        HttpDelete httpDelete = new HttpDelete(reqURL);
        return doPost(reqURL, headers, encoding, httpDelete);
    }

    private <T extends HttpRequestBase> HttpResult doPost(String reqURL, Map<String, String> headers,
                                                          String encoding,
                                                          T httpRequest) throws IOException {
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

        HttpResponse response = httpClient.execute(httpRequest);
        validateResponse(response, httpRequest);

        HttpEntity entity = response.getEntity();
        String result = "";
        if (null != entity) {
            try {
                result = getResponseContent(entity, encoding);
            } finally {
                HttpClientUtils.closeQuietly(response);
            }

        }
        StatusLine statusLine = response.getStatusLine();
        List<Header> requestHeaders = Lists.newArrayList(httpRequest.getAllHeaders());
        List<Header> responseHeaders = Lists.newArrayList(response.getAllHeaders());
        return new HttpResult(statusLine.getStatusCode(), statusLine.getReasonPhrase(), result, httpRequest.getURI(),
            requestHeaders, responseHeaders);
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
        LOG.debug("GET url: " + url);
        HttpGet httpget = new HttpGet(url);
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

        HttpResponse response = httpClient.execute(httpget);
        validateResponse(response, httpget);

        HttpEntity entity = response.getEntity();
        String result = "";
        if (null != entity) {
            try {
                result = getGetResponseContent(entity, encoding, isPB);
            } finally {
                HttpClientUtils.closeQuietly(response);
            }
        }
        StatusLine statusLine = response.getStatusLine();
        List<Header> requestHeaders = Lists.newArrayList(httpget.getAllHeaders());
        List<Header> responseHeaders = Lists.newArrayList(response.getAllHeaders());
        return new HttpResult(statusLine.getStatusCode(), statusLine.getReasonPhrase(), result, httpget.getURI(),
            requestHeaders, responseHeaders);
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
            try {
                request.abort();
            } catch (Throwable t) {
                LOG.warn("failed to abort request", t);
            }
        }
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public static void setCleanIdelConnCheckInterval(int checkInterval) {
        clean_check_interval = checkInterval;
    }
}
