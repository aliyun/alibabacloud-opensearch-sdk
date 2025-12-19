package com.aliyun.opensearch;

import com.aliyun.opensearch.sdk.generated.commons.OpenSearchClientException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchException;
import com.aliyun.opensearch.sdk.generated.search.MultiPathSearchParams;
import com.aliyun.opensearch.sdk.generated.search.general.SearchResult;
import com.aliyun.opensearch.search.MultiPathSearchParamsBuilder;
import com.aliyun.opensearch.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;


public class MultiPathSearchClient {
    private OpenSearchClient serviceClient;

    private static final String SEARCH_API_PATH = "/apps/{app_name}/multi-path-search";

    public MultiPathSearchClient(OpenSearchClient serviceClient) {
        this.serviceClient = serviceClient;
    }

    /**
     *
     * @param appName 应用名字
     * @param multiPathSearchParams 多路搜索参数
     */
    public SearchResult execute(String appName, MultiPathSearchParams multiPathSearchParams) throws OpenSearchClientException, OpenSearchException {
        MultiPathSearchParamsBuilder builder = new MultiPathSearchParamsBuilder(multiPathSearchParams);
        String bodyString = JsonUtil.toJson(builder.getHttpParams());
        return execute(appName, bodyString);
    }

    /**
     * @param appName 应用名字
     * @param body   多路搜索参数请求体
     */
    public SearchResult execute(String appName, String body) throws OpenSearchClientException, OpenSearchException {
        String requestPath = SEARCH_API_PATH.replace("{app_name}", appName);
        Map<String, String> params = new HashMap<>();
        params.put(OpenSearchClient.POST_BODY_PARAM_KEY, body);

        String result = this.serviceClient.call(requestPath, params, OpenSearchClient.METHOD_POST);

        return new SearchResult(result);
    }
}

