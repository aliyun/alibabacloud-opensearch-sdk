package com.aliyun.opensearch.demo;

import com.aliyun.opensearch.DataCollectionClient;
import com.aliyun.opensearch.OpenSearchClient;
import com.aliyun.opensearch.sdk.generated.OpenSearch;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchResult;

import java.util.HashMap;
import java.util.Map;

public class PushDataCollectionDoc {
    private static String accesskey = "your ak";
    private static String secret = "your secret";
    private static String host = "your host";

    private static String searchAppName = "zhao_special";
    private static String dataCollectionName = "zhao_special";
    private static String dataCollectionType = "BEHAVIOR";

    public static void main(String[] args) {

        //创建并构造OpenSearch对象
        OpenSearch opensearch = new OpenSearch(accesskey, secret, host);

        //创建OpenSearchClient对象，并以OpenSearch对象作为构造参数
        OpenSearchClient client = new OpenSearchClient(opensearch);

        //创建DataCollectionClient对象，并以OpenSearchClient对象作为构造参数
        DataCollectionClient dataCollectionClient = new DataCollectionClient(client);

        Map<String, Object> fields = new HashMap<String, Object>();
        fields.put("user_id", "1120021255");
        fields.put("biz_id", "your_biz_id");
        fields.put("rn", "1564455556323223680397827");
        fields.put("trace_id", "Alibaba");
        fields.put("trace_info", "%7B%22request%5Fid%22%3A%22156516585419723283227314%22%2C%22scm%22%3A%2220140713.120006678..%22%7D");
        fields.put("item_id", "2223");
        fields.put("item_type", "goods");
        fields.put("bhv_type", "click");
        fields.put("bhv_time", "1566475047");

        //增加一条文档
        //这条文档只是增加到 SDK Client buffer中，没有正式提交到服务端；只有调用了 commit 方法才会被提交到服务端。
        //可以多次调用 add，然后调用commit() 统一提交。
        dataCollectionClient.add(fields);

        OpenSearchResult openSearchResult;

        try {
            openSearchResult = dataCollectionClient.commit(searchAppName, dataCollectionName, dataCollectionType);
            System.out.println(openSearchResult);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
            return;
        }
    }
}
