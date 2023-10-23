package com.aliyun.opensearch.tracer;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * OpenSearchClient 请求默认监控
 * <p>什么都不做</p>
 */
public class DefaultClientTracer implements ClientTracer {
    @Override
    public void start(String method, String url) {

    }

    @Override
    public void send(HttpRequestBase request) {

    }

    @Override
    public void fail() {

    }

    @Override
    public void success(HttpResponse response, String result) {

    }
}
