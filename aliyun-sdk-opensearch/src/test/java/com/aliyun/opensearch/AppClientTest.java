package com.aliyun.opensearch;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aliyun.opensearch.client.OpenSearchResponse;
import com.aliyun.opensearch.sdk.generated.OpenSearch;
import com.aliyun.opensearch.sdk.generated.app.App;
import com.aliyun.opensearch.sdk.generated.app.AppStatus;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchResult;
import com.aliyun.opensearch.sdk.generated.commons.Pageable;
import com.aliyun.opensearch.test.MockOpenSearchClient;
import com.aliyun.opensearch.util.JsonUtil;
import com.aliyun.opensearch.util.JsonUtilWrapper;
import com.google.gson.reflect.TypeToken;

/**
 * Created by dengwx on 16/8/31.
 */
public class AppClientTest {
  private MockOpenSearchClient mockOpenSearchClient;
  private AppClient appClient;

  @Before
  public void setUp() throws Exception {
    mockOpenSearchClient = new MockOpenSearchClient(new OpenSearch("name", "secret", "host"));
    appClient = new AppClient(mockOpenSearchClient);
  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void testCreate() throws Exception {
    App app = new App();
    app.setAuto_switch(true);
    app.setStatus(AppStatus.FORBID);
    OpenSearchResponse response = new OpenSearchResponse();
    response.setResult(app);
    mockOpenSearchClient.setResponse(JsonUtil.toJson(response));
    OpenSearchResult openSearchResult = appClient.save(JsonUtilWrapper.toJson(app));
    App app2 = JsonUtilWrapper.fromJson(openSearchResult.getResult(), App.class);
    assertEquals(app2, app);
  }

  @Test
  public void testGetAll() throws Exception {
    App app = new App();
    app.setStatus(AppStatus.FORBID);
    List<App> appList = new ArrayList<App>();
    appList.add(app);
    OpenSearchResponse response = new OpenSearchResponse();
    response.setResult(appList);
    mockOpenSearchClient.setResponse(JsonUtil.toJson(response));
    System.out.println(mockOpenSearchClient.getResponse());
    Pageable pageable = new Pageable().setPage(1).setSize(2).setStart(3);
    OpenSearchResult openSearchResult = appClient.listAll(pageable);
    List<App> appList2 = JsonUtil.fromJsonList(openSearchResult.getResult(), new TypeToken<List<App>>(){}.getType());
    assertEquals(appList, appList2);
  }
}