package com.aliyun.opensearch.util;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aliyun.opensearch.exceptions.ThriftSerializationException;
import com.aliyun.opensearch.sdk.generated.app.App;

/**
 * Created by dengwx on 16/9/1.
 */
public class JsonUtilWrapperTest {

  @Before
  public void setUp() throws Exception {

  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void testToJson() throws Exception, ThriftSerializationException {
    App app = new App();
    String jsonStr = JsonUtilWrapper.toJson(app);
    ThriftIO.toSimpleJson(app);
    String expect = "{\"type\":\"advance\"}";
    assertEquals(expect, jsonStr);

    app.setAuto_switch(true);
    String expect2 = "{\"type\":\"advance\",\"auto_switch\":true}";
    assertEquals(expect2, JsonUtilWrapper.toJson(app));

    app.setAuto_switch(false);
    String expect3 = "{\"type\":\"advance\",\"auto_switch\":false}";
    assertEquals(expect3, JsonUtilWrapper.toJson(app));
  }

  @Test
  public void testEnum() throws Exception, ThriftSerializationException {
    String expect3 = "{\"type\":\"advance\",\"auto_switch\":true}";
    App app = JsonUtilWrapper.fromJson(expect3, App.class);
    System.out.println(app.isAuto_switch());
    System.out.println(JsonUtilWrapper.toJson(app));
    //App app2 = JsonUtil.fromJson(json, App.class);
    //assertEquals(app2, app);
  }
}