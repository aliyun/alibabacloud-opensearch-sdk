package com.aliyun.opensearch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.aliyun.opensearch.client.ResourceClient;
import com.aliyun.opensearch.sdk.generated.OpenSearchConstants;
import com.aliyun.opensearch.sdk.generated.data_collection.Command;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchClientException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchResult;
import com.aliyun.opensearch.sdk.generated.data_collection.DataCollectionConstants;
import com.aliyun.opensearch.sdk.generated.data_collection.DataCollectionService;

import org.json.JSONArray;
import org.json.JSONObject;

public class DataCollectionClient implements DataCollectionService.Iface {
    private static final String DATA_COLLECTION_SDK_TYPE = "opensearch_sdk";

    private ResourceClient resourceClient;
    private JSONArray docBuffer = new JSONArray();

    public DataCollectionClient(OpenSearchClient client) {
        resourceClient = new ResourceClient("/app-groups", client);
    }

    /**
     * 增加一条文档
     *
     * 这条文档只是增加到 SDK Client buffer 中，没有正式提交到服务端；只有调用了 commit 方法才会被提交到服务端。
     * 你可以多次 add 然后调用 commit() 统一提交。
     *
     * @param fields  一条行为数据(或用户数据、物品数据)文档的所有字段
     */
    public void add(Map<String, Object> fields) {
        JSONObject record = new JSONObject();

        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            record.put(entry.getKey(), entry.getValue());
        }

        Map<String, Object> internalFields = getInternalFields();
        for (Map.Entry<String, Object> entry : internalFields.entrySet()) {
            record.put(entry.getKey(), entry.getValue());
        }

        JSONObject doc = new JSONObject();
        doc.put(DataCollectionConstants.DOC_KEY_FIELDS, record);
        doc.put(DataCollectionConstants.DOC_KEY_CMD, Command.ADD);

        docBuffer.put(doc);
    }

    /**
     * 把 SDK Client buffer 中的文档发布到服务端。
     *
     * 在发送之前会把 buffer 中的文档清空，所以如果服务端返回错误需要重试的情况下，需要重新生成文档并 commit，避免丢数据的可能。
     *
     * @param searchAppName         关联的搜索应用名
     * @param dataCollectionName    数据采集名称，开通时控制台会返回该名称
     * @param dataCollectionType    数据采集类型：USER、ITEM_INFO、BEHAVIOR、INDUSTRY_SPECIFIC
     * @return 请求API并返回相应的结果
     * @throws OpenSearchException          OpenSearchException
     * @throws OpenSearchClientException    OpenSearchClientException
     */
    public OpenSearchResult commit(String searchAppName, String dataCollectionName, String dataCollectionType) throws OpenSearchException, OpenSearchClientException {
        OpenSearchResult result;

        try {
            result = doPush(docBuffer.toString(), searchAppName, dataCollectionName, dataCollectionType);
        } finally {
            docBuffer = new JSONArray();
        }

        return result;
    }

    /**
     * 批量推送文档。
     *
     * 通过此接口可以将符合格式的数据直接推送到服务端
     *
     * @param docJson             用户推送的数据，此字段为 JSON 类型的字符串
     * @param searchAppName       关联的搜索应用名
     * @param dataCollectionName  数据采集名称，开通时控制台会返回该名称
     * @param dataCollectionType  数据采集类型：USER、ITEM_INFO、BEHAVIOR、INDUSTRY_SPECIFIC
     * @return 请求API并返回相应的结果
     * @throws OpenSearchException          OpenSearchException
     * @throws OpenSearchClientException    OpenSearchClientException
     */
    @Override
    public OpenSearchResult push(String docJson, String searchAppName, String dataCollectionName, String dataCollectionType) throws OpenSearchException, OpenSearchClientException {
        JSONArray docJsonArray = new JSONArray(docJson);

        int length = docJsonArray.length();
        Map<String, Object> internalFields = getInternalFields();

        for (int i = 0; i < length; i++) {
            JSONObject fields = docJsonArray.getJSONObject(i).getJSONObject(DataCollectionConstants.DOC_KEY_FIELDS);

            for (Map.Entry<String, Object> entry : internalFields.entrySet()) {
                fields.put(entry.getKey(), entry.getValue());
            }
        }

        return doPush(docJsonArray.toString(), searchAppName, dataCollectionName, dataCollectionType);
    }

    private OpenSearchResult doPush(String docJson, String searchAppName, String dataCollectionName, String dataCollectionType) throws
        OpenSearchException, OpenSearchClientException {
        return resourceClient.post(createPushPath(searchAppName, dataCollectionName, dataCollectionType), docJson);
    }

    private String createPushPath (String searchAppName, String dataCollectionName, String dataCollectionType) {
        return String.format("/%s/data-collections/%s/data-collection-type/%s/actions/bulk", searchAppName, dataCollectionName, dataCollectionType);
    }

    private Map<String, Object> getInternalFields() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        Map<String, Object> internalFields = new HashMap<String, Object>();
        internalFields.put("sdk_type", DATA_COLLECTION_SDK_TYPE);
        internalFields.put("sdk_version", OpenSearchConstants.VERSION);
        internalFields.put("reach_time", sdf.format(new Date()));

        return internalFields;
    }
}
