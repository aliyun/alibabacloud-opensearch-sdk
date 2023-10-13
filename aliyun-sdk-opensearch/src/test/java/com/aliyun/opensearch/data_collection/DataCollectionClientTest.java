package com.aliyun.opensearch.data_collection;

import com.aliyun.opensearch.DataCollectionClient;
import com.aliyun.opensearch.OpenSearchClient;
import com.aliyun.opensearch.sdk.generated.OpenSearch;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class DataCollectionClientTest {
    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testPush() {
        String accesskey = "your ak";
        String secret    = "your secret";
        String host      = "your host";

        String searchAppName = "QA_Push_Click_Log";
        String dataCollectionName = "QA_Push_Click_Log";
        String dataCollectionType = "BEHAVIOR";

        OpenSearch opensearch   = new OpenSearch(accesskey, secret, host);
        OpenSearchClient client = new OpenSearchClient(opensearch);

        String docJson = "[{\"cmd\":\"ADD\",\"fields\":{\"user_id\":\"1120021255\",\"biz_id\":\"biz_name\",\"rn\":\"156516585419723283227314\",\"trace_id\":\"Alibaba\",\"trace_info\":\"%7B%22request%5Fid%22%3A%22156516585419723283227314%22%2C%22scm%22%3A%2220140713.120006678..%22%7D\",\"item_id\":\"id\",\"item_type\":\"2223\",\"bhv_type\":\"click\",\"bhv_time\":\"1566475047\"}}]";

        DataCollectionClient dataCollectionClient = new DataCollectionClient(client);

        try {
            OpenSearchResult openSearchResult = dataCollectionClient.push(docJson, searchAppName, dataCollectionName, dataCollectionType);
            System.out.println(openSearchResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCommit() {
        String accesskey = "your ak";
        String secret    = "your secret";
        String host      = "your host";

        String searchAppName = "zhao_special";
        String dataCollectionName = "zhao_special";
        String dataCollectionType = "BEHAVIOR";

        OpenSearch opensearch   = new OpenSearch(accesskey, secret, host);
        OpenSearchClient client = new OpenSearchClient(opensearch);

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

        DataCollectionClient dataCollectionClient = new DataCollectionClient(client);
        dataCollectionClient.add(fields);

        try {
            OpenSearchResult openSearchResult = dataCollectionClient.commit(searchAppName, dataCollectionName, dataCollectionType);
            System.out.println(openSearchResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
