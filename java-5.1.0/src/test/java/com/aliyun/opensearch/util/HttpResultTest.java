package com.aliyun.opensearch.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dengwx on 16/9/9.
 */
public class HttpResultTest {

  @Before
  public void setUp() throws Exception {

  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void testToString() throws Exception {
    HttpResult httpResult = new HttpResult(1, null, null);
    System.out.println(httpResult.toString());
  }
}