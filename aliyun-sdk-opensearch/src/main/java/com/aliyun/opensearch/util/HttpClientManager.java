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
import java.util.Map;

import com.aliyun.opensearch.tracer.ClientTracer;

import org.apache.http.HttpResponse;
import org.apache.http.impl.conn.PoolingClientConnectionManager;

public class HttpClientManager {

    private HttpClientFactory httpClientFactory;

    public HttpClientManager() {
        httpClientFactory = new HttpClientFactory(0, 0, 0);
    }

    /**
     * 设置 OpenSearchClient 请求监控
     */
    public void setClientTracer(ClientTracer clientTracer) {
        httpClientFactory.setClientTracer(clientTracer);
    }
    /**
     * 获取 OpenSearchClient 请求监控
     */
    public ClientTracer getClientTracer() {
        return httpClientFactory.getClientTracer();
    }

    public PoolingClientConnectionManager getConnectionManager() {
        return httpClientFactory.connectionManager;
    }

    //设置连接池最大连接数
    public void setMaxConnections(int maxConnections) {
        if (maxConnections > 0 && maxConnections != httpClientFactory.getMaxConnections()) {
            if (httpClientFactory != null) {
                httpClientFactory.shutdownIdleConnectionMonitor();
            }
            httpClientFactory = new HttpClientFactory(0, 0, maxConnections);
        }
    }

    //设置超时时间
    public void setTimeout(int timeout) {
        httpClientFactory.setTimeOut(timeout);
    }

    public void enableGzip() {
        httpClientFactory.enableGzip();
    }

    //设置连接超时时间
    public void setConnectTimeout(int connectTimeout) {
        httpClientFactory.setConnectTimeout(connectTimeout);
    }

    public HttpResult doPost(String requestPath, Map<String, String> headers,
                                    String body, String encoding) throws IOException {
        return httpClientFactory.doPost(requestPath, headers, body, encoding);
    }

    public HttpResponse doPostForHttpResponse(String requestPath, Map<String, String> headers,
                                              String body, String encoding) throws IOException {
        return httpClientFactory.doPostForHttpResponse(requestPath, headers, body, encoding);
    }

    public HttpResult doPatch(String requestPath, Map<String, String> headers,
                                     String body, String encoding) throws IOException {
        return httpClientFactory.doPatch(requestPath, headers, body, encoding);
    }

    public HttpResponse doPatchForHttpResponse(String requestPath, Map<String, String> headers,
                              String body, String encoding) throws IOException {
        return httpClientFactory.doPatchForHttpResponse(requestPath, headers, body, encoding);
    }

    public HttpResult doDelete(String requestPath, Map<String, String> headers,
                                      String encoding) throws IOException {
        return httpClientFactory.doDelete(requestPath, headers, encoding);
    }

    public HttpResponse doDeleteForHttpResponse(String requestPath, Map<String, String> headers,
                               String encoding) throws IOException {
        return httpClientFactory.doDeleteForHttpResponse(requestPath, headers, encoding);
    }

    public HttpResult doGet(String url, Map<String, String> headers, String encoding, boolean isPB) throws IOException {
        return httpClientFactory.doGet(url, headers, encoding, isPB);
    }

    public HttpResponse doGetForHttpResponse(String url, Map<String, String> headers, String encoding, boolean isPB) throws IOException {
        return httpClientFactory.doGetForHttpResponse(url, headers, encoding, isPB);
    }

    public HttpResult doPut(String requestPath, Map<String, String> headers,
                                   String body, String encoding) throws IOException {
        return httpClientFactory.doPut(requestPath, headers, body, encoding);
    }

    public HttpResponse doPutForHttpResponse(String requestPath, Map<String, String> headers,
                            String body, String encoding) throws IOException {
        return httpClientFactory.doPutForHttpResponse(requestPath, headers, body, encoding);
    }
}
