package com.aliyun.opensearch.client;
/**
 * Created by dengwx on 16/8/31.
 */

import java.util.HashMap;
import java.util.Map;

import com.aliyun.opensearch.OpenSearchClient;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchClientException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceClient {
    private static final Logger LOG = LoggerFactory.getLogger(ResourceClient.class);

  private OpenSearchClient openSearchClient;
  private String baseURI;

  public ResourceClient(String baseURI, OpenSearchClient openSearchClient) {
    this.openSearchClient = openSearchClient;
    this.baseURI = baseURI;
  }

  public OpenSearchResult post(String path, String body) throws OpenSearchException, OpenSearchClientException {
    Map<String, String> params = new HashMap<String, String>();
    if (!StringUtils.isEmpty(body)) {
      params.put(OpenSearchClient.POST_BODY_PARAM_KEY, body);
    }
    String uri = baseURI;
    if (!StringUtils.isEmpty(path)) {
      uri += path;
    }
    return call(uri, params, OpenSearchClient.METHOD_POST);
  }

    public OpenSearchResult put(String path, String body) throws OpenSearchException, OpenSearchClientException {
        Map<String, String> params = new HashMap<String, String>();
        if (!StringUtils.isEmpty(body)) {
            params.put(OpenSearchClient.POST_BODY_PARAM_KEY, body);
        }
        String uri = baseURI;
        if (!StringUtils.isEmpty(path)) {
            uri += path;
        }
        return call(uri, params, OpenSearchClient.METHOD_PUT);
    }

  public OpenSearchResult get(String path) throws OpenSearchException, OpenSearchClientException {
    Map<String, String> params = new HashMap<String, String>();
    return get(path, params);
  }

  public OpenSearchResult get(String path, Map<String, String> params) throws OpenSearchException, OpenSearchClientException {
    String uri;
    if (!StringUtils.isEmpty(path)) {
      uri = String.format("%s/%s", baseURI, path);
    } else {
      uri = baseURI;
    }
    return call(uri, params, OpenSearchClient.METHOD_GET);
  }

  public OpenSearchResult delete(String path) throws OpenSearchException, OpenSearchClientException {
    Map<String, String> params = new HashMap<String, String>();
    String uri = String.format("%s/%s", baseURI, path);
    return call(uri, params, OpenSearchClient.METHOD_DELETE);
  }

  public OpenSearchResult patch(String path, String body) throws OpenSearchException, OpenSearchClientException {
    Map<String, String> params = new HashMap<String, String>();
    params.put(OpenSearchClient.POST_BODY_PARAM_KEY, body);
    String uri = String.format("%s/%s", baseURI, path);
    return call(uri, params, OpenSearchClient.METHOD_PATCH);
  }

  public String getBaseURI() {
    return baseURI;
  }

  public void setBaseURI(String baseURI) {
    this.baseURI = baseURI;
  }

  private OpenSearchResult call(String path, Map<String, String> params, String method) throws OpenSearchClientException, OpenSearchException {
    return openSearchClient.callAndDecodeResult(path, params, method);
  }
}
