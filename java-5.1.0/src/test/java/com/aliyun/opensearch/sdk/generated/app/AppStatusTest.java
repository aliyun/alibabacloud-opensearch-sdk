package com.aliyun.opensearch.sdk.generated.app;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aliyun.opensearch.util.JsonUtilWrapper;

/**
 * Created by dengwx on 16/9/1.
 */
public class AppStatusTest {

  @Before
  public void setUp() throws Exception {

  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void testGetValue() throws Exception {
    checkAppStatus(AppStatus.AVAILABLE);
    checkAppStatus(AppStatus.PAUSE);
    checkAppStatus(AppStatus.FORBID);
    checkAppStatus(AppStatus.UNOPEN);
    checkAppStatus(AppStatus.CREATING);
    checkAppStatus(AppStatus.FAILED);
  }

  private void checkAppStatus(AppStatus appStatus) {
    App app = new App();
    app.setStatus(appStatus);
    app.setAuto_switch(true);
    String appStr = JsonUtilWrapper.toJson(app);
    System.out.println(appStr);
    App app2 = JsonUtilWrapper.fromJson(appStr, App.class);
    System.out.println(JsonUtilWrapper.toJson(app2));
    //assertEquals(app, app2);
  }
}