package com.aliyun.opensearch.suggestion;

import com.aliyun.opensearch.OpenSearchClient;
import com.aliyun.opensearch.SuggestionClient;
import com.aliyun.opensearch.sdk.generated.OpenSearch;

import com.aliyun.opensearch.sdk.generated.commons.OpenSearchResult;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SuggestionClientTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSearchWithCustomParameters() {
        final String ACCESSKEY = "your_access_key";
        final String SECRET = "your_secret";
        final String HOST = "http://opensearch-cn-beijing.aliyuncs.com";

        final String APP_NAME = "test";
        final String SUGGESTION_NAME = "mz6";
        final String QUERY = "香"; // "香蕉"
        final byte HITS = 8;

        final OpenSearch opensearch = new OpenSearch(ACCESSKEY, SECRET, HOST);
        final OpenSearchClient serviceClient = new OpenSearchClient(opensearch);
        final SuggestionClient suggestionClient = new SuggestionClient(APP_NAME, SUGGESTION_NAME, serviceClient);

        suggestionClient.setQuery(QUERY);
        suggestionClient.setHits(HITS);

        String result;

        try {
            result = suggestionClient.search();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
            return;
        }

        JSONObject jsonResult = new JSONObject(result);
        List<String> suggestions = new ArrayList<String>();

        if (!jsonResult.has("errors")) {
            JSONArray itemsJsonArray = (JSONArray) jsonResult.get("suggestions");

            for (int i = 0; i < itemsJsonArray.length(); i++){
                JSONObject item = (JSONObject) itemsJsonArray.get(i);
                suggestions.add(item.getString("suggestion"));
            }

            Map<String, Object> ret = new HashMap<String, Object>();
            ret.put("result", suggestions);
            ret.put("status", "OK");

            System.out.println(new JSONObject(ret).toString());

            assertTrue(suggestions.contains("香蕉"));
            assertTrue(suggestions.size() <= HITS);
        } else {
            assertTrue(false);
        }
    }

    @Test
    public void testSearchWithDefaultParameters() {
        final String ACCESSKEY = "your_access_key";
        final String SECRET = "your_secret";
        final String HOST = "http://opensearch-cn-beijing.aliyuncs.com";

        final String APP_NAME = "test";
        final String SUGGESTION_NAME = "mz6";

        final OpenSearch opensearch = new OpenSearch(ACCESSKEY, SECRET, HOST);
        final OpenSearchClient serviceClient = new OpenSearchClient(opensearch);
        final SuggestionClient suggestionClient = new SuggestionClient(APP_NAME, SUGGESTION_NAME, serviceClient);

        suggestionClient.setQuery("香");

        String result;

        try {
            result = suggestionClient.search();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
            return;
        }

        JSONObject jsonResult = new JSONObject(result);
        List<String> suggestions = new ArrayList<String>();

        if (!jsonResult.has("errors")) {
            JSONArray itemsJsonArray = (JSONArray) jsonResult.get("suggestions");

            for (int i = 0; i < itemsJsonArray.length(); i++){
                JSONObject item = (JSONObject) itemsJsonArray.get(i);
                suggestions.add(item.getString("suggestion"));
            }

            Map<String,Object> ret = new HashMap<String,Object>();
            ret.put("result", suggestions);
            ret.put("status", "OK");
            System.out.println(new JSONObject(ret).toString());

            assertTrue(suggestions.contains("香蕉"));
            assertTrue(suggestions.size() <= 10);
        } else {
            assertTrue(false);
        }
    }
}