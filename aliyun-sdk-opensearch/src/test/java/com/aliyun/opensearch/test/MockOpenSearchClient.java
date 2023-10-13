package com.aliyun.opensearch.test;
/**
 * Created by dengwx on 16/9/6.
 */

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aliyun.opensearch.OpenSearchClient;
import com.aliyun.opensearch.client.OpenSearchResponse;
import com.aliyun.opensearch.exceptions.InvalidParameterException;
import com.aliyun.opensearch.sdk.generated.OpenSearch;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchClientException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchResult;
import com.aliyun.opensearch.util.HttpResult;
import com.aliyun.opensearch.util.JsonUtil;

public class MockOpenSearchClient extends OpenSearchClient {
  private static final Log log = LogFactory.getLog(MockOpenSearchClient.class);
  private String path;
  private String body;
  private String method;
  private String response;

  public MockOpenSearchClient(OpenSearch opensearch) throws InvalidParameterException {
    super(opensearch);
    response = JsonUtil.toJson(new OpenSearchResponse());
  }

  public OpenSearchResult callAndDecodeResult(String path, Map<String, String> params, String method) throws OpenSearchException, OpenSearchClientException {
    HttpResult httpResult = new HttpResult(200, "", response);
    OpenSearchResult openSearchResult = fromHttpResult(httpResult);
    this.path = path;
    this.body = params.get(OpenSearchClient.POST_BODY_PARAM_KEY);
    this.method = method;
    return openSearchResult;
  }

  public String getPath() {
    return path;
  }

  public String getBody() {
    return body;
  }

  public String getMethod() {
    return method;
  }

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }
}
