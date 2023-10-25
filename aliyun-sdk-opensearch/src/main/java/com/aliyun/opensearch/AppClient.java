package com.aliyun.opensearch;
/**
 * Created by dengwx on 16/8/31.
 */

import com.aliyun.opensearch.client.ResourceClient;
import com.aliyun.opensearch.sdk.generated.app.AppService;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchClientException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchResult;
import com.aliyun.opensearch.sdk.generated.commons.Pageable;
import com.aliyun.opensearch.util.ThriftUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppClient implements AppService.Iface {
    private static final Logger LOG = LoggerFactory.getLogger(AppClient.class);


    private ResourceClient resourceClient;

  public AppClient(OpenSearchClient resourceClient) {
    this.resourceClient = new ResourceClient("/apps", resourceClient);
  }

  @Override
  public OpenSearchResult save(String app) throws OpenSearchException, OpenSearchClientException {
    return resourceClient.post("", app);
  }

  @Override
  public OpenSearchResult getById(String identity) throws OpenSearchException, OpenSearchClientException {
    return resourceClient.get(identity);
  }

  @Override
  public OpenSearchResult listAll(Pageable pageable) throws OpenSearchException, OpenSearchClientException {
    return resourceClient.get("", ThriftUtil.pagableToMap(pageable));
  }

  @Override
  public OpenSearchResult removeById(String identity) throws OpenSearchException, OpenSearchClientException {
    return resourceClient.delete(identity);
  }

  @Override
  public OpenSearchResult updateById(String identity, String app) throws OpenSearchException, OpenSearchClientException {
    return resourceClient.patch(identity, app);
  }

  @Override
  public OpenSearchResult reindexById(String identity) throws OpenSearchException, OpenSearchClientException {
    String path = String.format("/%s/actions/reindex", identity);
    return resourceClient.post(path, "");
  }

    @Override
    public OpenSearchResult switchTo(String identity) throws OpenSearchException, OpenSearchClientException {
        String path = String.format("/%s/current", identity);
        return resourceClient.put(path, "");
    }
}
