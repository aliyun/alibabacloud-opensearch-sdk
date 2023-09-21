package com.aliyun.opensearch;

import com.aliyun.opensearch.sdk.generated.commons.OpenSearchClientException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchException;
import com.aliyun.opensearch.util.HttpResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dengwx on 16/8/31.
 */
public class OpenSearchClientTest {

  @Before
  public void setUp() throws Exception {

  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void testFromHttpResultException() throws Exception {
    checkException(400, "", "");
  }

  private void checkException(int code, String reason, String result) {
    HttpResult httpResult = new HttpResult(400, "", "");
    try {
      OpenSearchClient.fromHttpResult(httpResult);
    } catch (OpenSearchException e) {
      return;
    } catch (OpenSearchClientException e) {
      assertTrue(false);
    }
    assertTrue(false);
  }
}