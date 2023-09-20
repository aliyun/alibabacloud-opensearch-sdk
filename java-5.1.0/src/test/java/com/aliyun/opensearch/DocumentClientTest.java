package com.aliyun.opensearch;

import com.aliyun.opensearch.sdk.generated.OpenSearch;
import com.aliyun.opensearch.test.MockOpenSearchClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by dengwx on 16/9/6.
 */
public class DocumentClientTest {

  @Before
  public void setUp() throws Exception {

  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void testCommit() throws Exception {
    MockOpenSearchClient mockOpenSearchClient = new MockOpenSearchClient(new OpenSearch("name", "secret", "host"));
    DocumentClient documentClient = new DocumentClient(mockOpenSearchClient);
    Map<String, Object> fields = new HashMap<String, Object>();
    fields.put("a", "b");
    documentClient.add(fields);
    documentClient.remove(fields);
    documentClient.commit("app", "table");
    assertEquals("[{\"cmd\":\"ADD\",\"fields\":{\"a\":\"b\"}},{\"cmd\":\"DELETE\",\"fields\":{\"a\":\"b\"}}]",
            mockOpenSearchClient.getBody());
    assertEquals("/apps/app/table/actions/bulk", mockOpenSearchClient.getPath());
  }
}