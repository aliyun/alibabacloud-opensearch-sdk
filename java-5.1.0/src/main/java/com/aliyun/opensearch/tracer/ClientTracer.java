package com.aliyun.opensearch.tracer;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * OpenSearchClient 请求监控
 */
public interface ClientTracer {

    /**
     * 请求开始
     * @param method String
     * @param url String
     */
    void start(String method, String url);

    /**
     * 请求发送
     * @param request HttpRequestBase
     */
    void send(HttpRequestBase request);

    /**
     * 请求失败
     */
    void fail();

    /**
     * 请求成功
     * @param response  HttpResponse
     * @param result  result
     */
    void success(HttpResponse response, String result);
}
