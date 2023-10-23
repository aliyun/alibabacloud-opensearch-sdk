package com.aliyun.opensearch.client;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aliyun.opensearch.sdk.generated.app.App;
import com.aliyun.opensearch.util.JsonUtil;
import com.aliyun.opensearch.util.JsonUtilWrapper;
import com.google.gson.reflect.TypeToken;

/**
 * Created by dengwx on 16/9/7.
 */
public class OpenSearchResponseTest {

  @Before
  public void setUp() throws Exception {

  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void testGetStatus() throws Exception {
    String json = "{\"result\":{\"type\":\"advance\",\"status\":6}}";
    OpenSearchResponse result = JsonUtilWrapper.fromJson(json);
    System.out.println(result.getResultString());
  }

  @Test
  public void testFromJson() throws Exception {
    OpenSearchResponse openSearchResponse = new OpenSearchResponse();
    openSearchResponse.setRequest_id("11");
    openSearchResponse.setTracer("tracer");
    openSearchResponse.addError(new ErrorResult(1, "msg"));
    openSearchResponse.addError(new ErrorResult(2, "msg2"));
    String json = JsonUtil.toJson(openSearchResponse);
    OpenSearchResponse result = JsonUtilWrapper.fromJson(json);
    assertEquals(result, openSearchResponse);
  }

  @Test
  public void testResult() throws Exception {
    OpenSearchResponse openSearchResponse = new OpenSearchResponse();
    openSearchResponse.setResult(true);
    assertEquals("true", openSearchResponse.getResultString());

    App app = new App().setAuto_switch(true).setDescription("desc");
    openSearchResponse.setResult(app);
    OpenSearchResponse result = JsonUtilWrapper.fromJson(JsonUtil.toJson(openSearchResponse));
    assertEquals(app, JsonUtil.fromJson(result.getResultString(), App.class));

    openSearchResponse.setResult(10);
    assertEquals((Integer) 10, JsonUtil.fromJson(openSearchResponse.getResultString(), Integer.class));

    List<ErrorResult> errorResults = new ArrayList<ErrorResult>();
    errorResults.add(new ErrorResult(10, "m1"));
    errorResults.add(new ErrorResult(20, "m2"));
    openSearchResponse.setResult(errorResults);
    String jsonList = JsonUtil.toJson(openSearchResponse);
    OpenSearchResponse response2 = JsonUtilWrapper.fromJson(jsonList);
    assertEquals(errorResults, JsonUtil.fromJsonList(response2.getResultString(),
            new TypeToken<List<ErrorResult>>(){}.getType()));
  }
}