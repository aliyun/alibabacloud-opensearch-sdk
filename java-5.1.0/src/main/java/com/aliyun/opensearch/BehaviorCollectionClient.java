package com.aliyun.opensearch;

import com.aliyun.opensearch.client.ResourceClient;
import com.aliyun.opensearch.sdk.generated.behavior_collection.BehaviorCollectionConstants;
import com.aliyun.opensearch.sdk.generated.behavior_collection.BehaviorCollectionService;
import com.aliyun.opensearch.sdk.generated.behavior_collection.Command;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchClientException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchResult;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 * @deprecated
 */
public class BehaviorCollectionClient implements BehaviorCollectionService.Iface {
    private static final Integer SEARCH_DOC_CLICK_EVENT_ID = 2001;

    private static final String SEARCH_DOC_SDK_TYPE = "opensearch_sdk";
    private static final String SEARCH_DOC_SDK_VERSION = "v3.3.0";

    private ResourceClient resourceClient;
    private JSONArray recordBuffer = new JSONArray();

    public BehaviorCollectionClient(OpenSearchClient client) {
        resourceClient = new ResourceClient("/app-groups", client);
    }

    /**
     * 增加一条搜索点击文档
     *
     * 这条文档只是增加到sdk client buffer中，没有正式提交到服务端；只有调用了commit方法才会被提交到服务端。
     * 你可以多次addSearchDocClickRecord然后调用commit() 统一提交。
     *
     * @param searchDocListPage     搜索结果列表所在的页面名称
     * @param docDetailPage         某个搜索文档被点击后，搜索文档的详情页面名称
     * @param detailPageStayTime    用户在详情页停留的时长(单位为ms)
     * @param objectId              被点击的文档的主键，不能为空
     * @param opsRequestMisc        opensearch返回的查询结果中的ops_request_misc字段
     * @param basicFields           其他基础字段
     */
    public void addSearchDocClickRecord(
        String searchDocListPage,
        String docDetailPage,
        Integer detailPageStayTime,
        String objectId,
        String opsRequestMisc,
        Map<String, String> basicFields) {

        JSONObject jsonFields = new JSONObject();
        jsonFields.put("event_id", SEARCH_DOC_CLICK_EVENT_ID);
        jsonFields.put("sdk_type", SEARCH_DOC_SDK_TYPE);
        jsonFields.put("sdk_version", SEARCH_DOC_SDK_VERSION);
        jsonFields.put("page", docDetailPage);
        jsonFields.put("arg1", searchDocListPage);
        jsonFields.put("arg2", "");
        jsonFields.put("arg3", detailPageStayTime);
        jsonFields.put("args", createSearchDocClickArgs(objectId, opsRequestMisc));

        if (basicFields != null && !basicFields.isEmpty()) {
            for (Map.Entry<String, String> entry : basicFields.entrySet()) {
                jsonFields.put(entry.getKey(), entry.getValue());
            }
        }

        addOneRecord(jsonFields, Command.ADD);
    }

    /**
     * 把sdk client buffer中的文档发布到服务端。
     *
     * 在发送之前会把buffer中的文档清空，所以如果服务端返回错误需要重试的情况下，需要重新生成文档并commit，避免丢数据的可能。
     *
     * @param searchAppName             关联的搜索应用名
     * @param behaviorCollectionName    行为数据采集名称，开通时控制台会返回该名称
     * @return 请求API并返回相应的结果
     * @throws OpenSearchException          OpenSearchException
     * @throws OpenSearchClientException    OpenSearchClientException
     */
    public OpenSearchResult commit(String searchAppName, String behaviorCollectionName) throws OpenSearchException, OpenSearchClientException {
        OpenSearchResult result;

        try {
            result = doPush(recordBuffer, searchAppName, behaviorCollectionName);
        } finally {
            recordBuffer = new JSONArray();
        }

        return result;
    }

    /**
     * 批量推送文档。
     *
     * 通过此接口可以将符合格式的行为数据直接推送到指定的行为数据采集表中
     *
     * @param recordsJson            用户推送的行为数据，此字段为json类型的字符串
     * @param searchAppName          关联的搜索应用名
     * @param behaviorCollectionName 行为数据采集名称，开通时控制台会返回该名称
     * @return 请求API并返回相应的结果
     * @throws OpenSearchException          OpenSearchException
     * @throws OpenSearchClientException    OpenSearchClientException
     */
    @Override
    public OpenSearchResult push(String recordsJson, String searchAppName, String behaviorCollectionName) throws OpenSearchException, OpenSearchClientException {
        JSONArray recordsJsonArray = new JSONArray(recordsJson);
        return doPush(recordsJsonArray, searchAppName, behaviorCollectionName);
    }

    private OpenSearchResult doPush(JSONArray recordsJsonArray, String searchAppName, String behaviorCollectionName) throws
    OpenSearchException, OpenSearchClientException {
        return resourceClient.post(createPushPath(searchAppName, behaviorCollectionName), recordsJsonArray.toString());
    }

    private void addOneRecord(JSONObject jsonFields, Command cmd) {
        JSONObject jsonRecord = new JSONObject();

        jsonRecord.put(BehaviorCollectionConstants.DOC_KEY_FIELDS, jsonFields);
        jsonRecord.put(BehaviorCollectionConstants.DOC_KEY_CMD, cmd.toString());

        recordBuffer.put(jsonRecord);
    }

    private String createPushPath (String searchAppName, String behaviorCollectionName){
        return String.format("/%s/data-collections/%s/actions/bulk", searchAppName, behaviorCollectionName);
    }

    private String createSearchDocClickArgs(String objectId, String opsRequestMisc) {
        return String.format("object_id=%s,object_type=ops_search_doc,ops_request_misc=%s", objectId, opsRequestMisc);
    }
}
