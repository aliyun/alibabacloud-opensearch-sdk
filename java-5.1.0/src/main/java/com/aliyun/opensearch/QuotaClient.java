package com.aliyun.opensearch;

import com.aliyun.opensearch.client.ResourceClient;
import com.aliyun.opensearch.sdk.generated.OpenSearch;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchClientException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchResult;
import com.aliyun.opensearch.sdk.generated.quota.Quota;
import com.aliyun.opensearch.sdk.generated.quota.QuotaService;
import com.aliyun.opensearch.util.JsonUtilWrapper;

public class QuotaClient implements QuotaService.Iface {
  private ResourceClient resourceClient;

  /**
   * 构造函数
   *
   * @param client CloudsearchClient实例。
   */
  public QuotaClient(OpenSearchClient client) {
    this.resourceClient = new ResourceClient("/quotas", client);
  }

  /**
   * 根据应用 ID 或应用名获取该应用的配额信息
   *
   * @param  appIdentity            应用 ID 或应用名
   * @return                        配额信息
   *
   * @throws OpenSearchException OpenSearchException
   * @throws OpenSearchClientException OpenSearchClientException
   */
  @Override
  public OpenSearchResult getByAppIdentity(String appIdentity) throws OpenSearchException, OpenSearchClientException {
    return doGet(appIdentity);
  }

  /**
   * 根据应用 ID 或应用名更新该应用的配额信息
   *
   * @param  appIdentity               应用 ID 或应用名
   * @param  quota                     待更新的配额信息
   * @return                           已更新的配额信息
   *
   * @throws OpenSearchException OpenSearchException
   * @throws OpenSearchClientException OpenSearchClientException
   */
  @Override
  public OpenSearchResult updateByAppIdentity(String appIdentity, Quota quota) throws OpenSearchException, OpenSearchClientException {
    return doUpdate(appIdentity, quota);
  }

  private OpenSearchResult doGet(String identity) throws OpenSearchException, OpenSearchClientException {
    return resourceClient.get(createResourcePath(identity));
  }

  private OpenSearchResult doUpdate(String identity, Quota quota) throws OpenSearchException, OpenSearchClientException {
    return resourceClient.patch(createResourcePath(identity), JsonUtilWrapper.toJson(quota));
  }

  private String createResourcePath(String identity) {
    return String.format("%s", identity);
  }
}
