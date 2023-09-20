package com.aliyun.opensearch.quota;

import com.aliyun.opensearch.OpenSearchClient;
import com.aliyun.opensearch.QuotaClient;
import com.aliyun.opensearch.sdk.generated.OpenSearch;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchResult;
import com.aliyun.opensearch.sdk.generated.quota.Quota;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QuotaClientTest {
  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }
  /**
   * 老的 Quota
   */
  @Test
  public void testGetByAppName() {
    String accesskey = "LTAIswgathXC6AXv";
    String secret = "QKxVRlGhF0SjDKTo7tmcbXL2gicoW1";
    String host = "http://opensearch-cn-beijing.aliyuncs.com";

    String appName = "MZ_Enh_JavaSdkTest_2";

    OpenSearch opensearch = new OpenSearch(accesskey, secret, host);
    OpenSearchClient serviceClient = new OpenSearchClient(opensearch);
    QuotaClient quotaClient = new QuotaClient(serviceClient);

    OpenSearchResult openSearchResult;

    try {
      openSearchResult = quotaClient.getByAppIdentity(appName);
      System.out.println(openSearchResult);
    } catch (Exception e) {
      e.printStackTrace();
      assertTrue(false);
      return;
    }

    JSONObject quota = new JSONObject(openSearchResult.getResult());
    assertTrue(quota.has("doc_size"));
    assertTrue(quota.has("qps"));
  }

  /**
   * 老的 Quota
   */
  @Test
  public void testGetByAppId() {
    String accesskey = "LTAIswgathXC6AXv";
    String secret = "QKxVRlGhF0SjDKTo7tmcbXL2gicoW1";
    String host = "http://opensearch-cn-beijing.aliyuncs.com";

    String appId = "130013919";

    OpenSearch opensearch = new OpenSearch(accesskey, secret, host);
    OpenSearchClient serviceClient = new OpenSearchClient(opensearch);
    QuotaClient quotaClient = new QuotaClient(serviceClient);

    OpenSearchResult openSearchResult;

    try {
      openSearchResult = quotaClient.getByAppIdentity(appId);
      System.out.println(openSearchResult);
    } catch (Exception e) {
      e.printStackTrace();
      assertTrue(false);
      return;
    }

    JSONObject quota = new JSONObject(openSearchResult.getResult());
    assertTrue(quota.has("doc_size"));
    assertTrue(quota.has("qps"));
  }

  /**
   * 老的 Quota
   */
  @Test
  public void testGetByAppNameAndAppIdReturnSameResult() {
    String accesskey = "LTAIswgathXC6AXv";
    String secret = "QKxVRlGhF0SjDKTo7tmcbXL2gicoW1";
    String host = "http://opensearch-cn-beijing.aliyuncs.com";

    String appName = "MZ_Enh_JavaSdkTest_2";
    String appId = "130013919";

    OpenSearch opensearch = new OpenSearch(accesskey, secret, host);
    OpenSearchClient serviceClient = new OpenSearchClient(opensearch);
    QuotaClient quotaClient = new QuotaClient(serviceClient);

    OpenSearchResult openSearchResultGetByAppName;
    OpenSearchResult openSearchResultGetByAppId;

    try {
      openSearchResultGetByAppName = quotaClient.getByAppIdentity(appName);
      openSearchResultGetByAppId = quotaClient.getByAppIdentity(appId);

      System.out.println(openSearchResultGetByAppName);
      System.out.println(openSearchResultGetByAppId);
    } catch (Exception e) {
      e.printStackTrace();
      assertTrue(false);
      return;
    }

    JSONObject quotaGetByAppName = new JSONObject(openSearchResultGetByAppName.getResult());
    JSONObject quotaGetByAppId = new JSONObject(openSearchResultGetByAppId.getResult());

    assertEquals(quotaGetByAppName.get("doc_size"), quotaGetByAppId.get("doc_size"));
    assertEquals(quotaGetByAppName.get("id"), quotaGetByAppId.get("id"));
    assertEquals(quotaGetByAppName.get("qps"), quotaGetByAppId.get("qps"));
  }

  /**
   * 计费模型改版后的 Quota
   */
  @Test
  public void testUpdateByAppName() {
    final String ACCESSKEY = "LTAIswgathXC6AXv";
    final String SECRET = "QKxVRlGhF0SjDKTo7tmcbXL2gicoW1";
    final String HOST = "http://opensearch-cn-beijing.aliyuncs.com";

    final String APP_NAME = "MZ_Enh_JavaSdkTest_2";

    final OpenSearch opensearch = new OpenSearch(ACCESSKEY, SECRET, HOST);
    final OpenSearchClient serviceClient = new OpenSearchClient(opensearch);
    final QuotaClient quotaClient = new QuotaClient(serviceClient);
    final Quota quota = new Quota().setDoc_size(6).setCompute_resource(21);
    OpenSearchResult openSearchResult;

    try {
      openSearchResult = quotaClient.updateByAppIdentity(APP_NAME, quota);
      System.out.println(openSearchResult);
    } catch (Exception e) {
      e.printStackTrace();
      assertTrue(false);
      return;
    }

    final JSONObject quotaUpdated = new JSONObject(openSearchResult.getResult());

    assertEquals(quotaUpdated.get("doc_size"), 6);
    assertEquals(quotaUpdated.get("compute_resource"), 21);
  }

  /**
   * 计费模型改版后的 Quota
   */
  @Test
  public void testUpdateByAppId() {
    final String ACCESSKEY = "LTAIswgathXC6AXv";
    final String SECRET = "QKxVRlGhF0SjDKTo7tmcbXL2gicoW1";
    final String HOST = "http://opensearch-cn-beijing.aliyuncs.com";

    final String APP_ID = "130013919";

    final OpenSearch opensearch = new OpenSearch(ACCESSKEY, SECRET, HOST);
    final OpenSearchClient serviceClient = new OpenSearchClient(opensearch);
    final QuotaClient quotaClient = new QuotaClient(serviceClient);
    final Quota quota = new Quota().setDoc_size(7).setCompute_resource(22);

    OpenSearchResult openSearchResult;

    try {
      openSearchResult = quotaClient.updateByAppIdentity(APP_ID, quota);
      System.out.println(openSearchResult);
    } catch (Exception e) {
      e.printStackTrace();
      assertTrue(false);
      return;
    }

    final JSONObject quotaUpdated = new JSONObject(openSearchResult.getResult());

    assertEquals(quotaUpdated.get("doc_size"), 7);
    assertEquals(quotaUpdated.get("compute_resource"), 22);
  }
}
