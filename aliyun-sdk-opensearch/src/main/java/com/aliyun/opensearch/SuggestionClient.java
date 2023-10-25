package com.aliyun.opensearch;

import java.util.Map;

import com.aliyun.opensearch.sdk.generated.commons.OpenSearchClientException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchException;
import com.aliyun.opensearch.sdk.generated.search.general.SearchResult;
import com.aliyun.opensearch.sdk.generated.suggestion.ReSearch;
import com.aliyun.opensearch.sdk.generated.suggestion.SuggestParams;
import com.aliyun.opensearch.sdk.generated.suggestion.SuggestionService;
import com.aliyun.opensearch.suggest.UrlParamsBuilder;
import com.aliyun.opensearch.util.Utils;

public class SuggestionClient implements SuggestionService.Iface {
  private static final String SUGGESTION_API_PATH = "/suggestions/{suggestion_name}/actions/search";
  private OpenSearchClient client;
  private static final String ROOT = "suggestions";
  private String suggestionName;
  private String query = "";
  private byte hits = 10;
  private String userId;
  private ReSearch reSearch;

  /**
   * 构造函数
   *
   * @param suggestionName 下拉提示名字
   * @param client CloudsearchClient实例
   */
  public SuggestionClient(String suggestionName, OpenSearchClient client) {
    this.suggestionName = suggestionName;
    this.client = client;
  }

  /**
   * 设置下拉提示查询词
   * @param  query 查询词
   * @return SuggestionClient
   */
  public SuggestionClient setQuery(String query) {
    this.query = query;

    return this;
  }

  /**
   * 设置下拉提示结果条数
   * 取值范围 (0, 10]
   *
   * @param  hits 下拉提示结果条数
   * @return SuggestionClient
   */
  public SuggestionClient setHits(byte hits) {
    this.hits = hits;

    return this;
  }

  /**
   * 设置用来标识发起当前下拉提示请求的终端用户
   * @param userId 终端用户ID
   * @return SuggestionClient
   */
  public SuggestionClient setUserId(String userId) {
    this.userId = userId;
    return this;
  }

  /**
   * 设置重查策略
   * @param reSearch 重查策略
   * @return SuggestionClient
   */
  public SuggestionClient setReSearch(ReSearch reSearch) {
    this.reSearch = reSearch;
    return this;
  }

  /**
   * 下拉提示搜索
   *
   * @return                        搜索结果
   * @throws OpenSearchException OpenSearchException
   * @throws OpenSearchClientException OpenSearchClientException
   * @deprecated 请使用 execute() 方法代替。
   */
  @Override
  @Deprecated
  public String search() throws OpenSearchException, OpenSearchClientException {
      SuggestParams suggestParams = new SuggestParams();
      suggestParams.setQuery(query);
      suggestParams.setHits(hits);
      if (userId != null) {
          suggestParams.setUserId(userId);
      }
      if (reSearch != null) {
          suggestParams.setReSearch(reSearch);
      }
      UrlParamsBuilder urlParamsBuilder = new UrlParamsBuilder(suggestParams);
      Map<String, String> params = Utils.toMap(urlParamsBuilder.getHttpParams());

      return doGet(suggestionName + "/actions/search", params);
  }

  @Override
  public SearchResult execute(SuggestParams suggestParams) throws OpenSearchException, OpenSearchClientException {
      UrlParamsBuilder urlParamsBuilder = new UrlParamsBuilder(suggestParams);
      Map<String, String> params = Utils.toMap(urlParamsBuilder.getHttpParams());
      String requestPath = parseApiPathTemplate(suggestParams);
      String response = client.call(requestPath, params, OpenSearchClient.METHOD_GET);
      return new SearchResult(response);
  }

  private String doGet(String identity, Map<String, String> params) throws OpenSearchClientException {
      return client.call(createResourcePath(identity), params, OpenSearchClient.METHOD_GET);
  }

  private String createResourcePath(String identity) {
    String path = String.format("/%s/%s", ROOT, identity);

    return path;
  }

  private String parseApiPathTemplate(SuggestParams suggestParams) {
      return SUGGESTION_API_PATH.replaceAll("\\{suggestion_name\\}", suggestionName);
  }
}
