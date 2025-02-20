package com.aliyun.opensearch;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.aliyun.opensearch.client.OpenSearchResponseConsumer;
import com.aliyun.opensearch.client.ServerSentEvent;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchResult;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.params.BasicHttpParams;

import com.aliyun.opensearch.sdk.generated.commons.OpenSearchClientException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchException;
import com.aliyun.opensearch.sdk.generated.search.OpenSearchSearcherService;
import com.aliyun.opensearch.sdk.generated.search.SearchParams;
import com.aliyun.opensearch.sdk.generated.search.general.SearchResult;
import com.aliyun.opensearch.search.SearchParamsBuilder;
import com.aliyun.opensearch.search.SearchResultDebug;
import com.aliyun.opensearch.search.UrlParamsBuilder;
import com.aliyun.opensearch.util.HttpResult;
import com.aliyun.opensearch.util.Utils;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;

/**
 * The OpenSearch Searcher Client.
 *
 * @author Ken
 */
public class SearcherClient implements OpenSearchSearcherService.Iface {

	/** 和API服务进行交互的对象 */
	private OpenSearchClient serviceClient;

	/** 请求API搜索部分的path */
	private static final String SEARCH_API_PATH = "/apps/{app_name}/search";

	/**
	 * <p>请求API智能搜索部分的path</p>
	 */
	private static final String RAG_SEARCH_API_PATH = "/apps/{app_name}/rag";

	/** <p>请求API下拉提示部分的path</p>
	 * */
	private static final String SUGGEST_API_PATH = "/apps/{app_name}/suggest/{suggest_name}/search";

	/**
	 * 构造函数.
	 *
	 * @param serviceClient the service client
	 */
	public SearcherClient(OpenSearchClient serviceClient) {
		this.serviceClient = serviceClient;
	}

	/**
	 * Execute search with param build.
	 *
	 * @param searchParamsBuilder the search params builder
	 * @return the search result
	 * @throws OpenSearchException the open search exception
	 * @throws OpenSearchClientException the open search client exception
	 */
	public SearchResult execute(SearchParamsBuilder searchParamsBuilder) throws OpenSearchException,
			OpenSearchClientException {
		return execute(searchParamsBuilder.build());
	}

	/* (non-Javadoc)
	 * @see com.aliyun.opensearch.sdk.generated.search.OpenSearchSearcherService.Iface#execute(com.aliyun.opensearch.sdk.generated.search.SearchParams)
	 */
	@Override
	public SearchResult execute(SearchParams searchParams) throws OpenSearchException, OpenSearchClientException {
		UrlParamsBuilder urlParamsBuilder = new UrlParamsBuilder(searchParams);
		Map<String, String> params = toMap(urlParamsBuilder.getHttpParams());
		String requestPath = parseApiPathTemplate(searchParams);
		String response = this.serviceClient.call(requestPath, params, OpenSearchClient.METHOD_GET);
		return new SearchResult(response);
	}

	public SearchResultDebug executeDebug(SearchParams searchParams) throws OpenSearchException, OpenSearchClientException {
		UrlParamsBuilder urlParamsBuilder = new UrlParamsBuilder(searchParams);
		Map<String, String> params = toMap(urlParamsBuilder.getHttpParams());
		String requestPath = parseApiPathTemplate(searchParams);
		HttpResult httpResult = this.serviceClient.authAndCall(requestPath, params, OpenSearchClient.METHOD_GET);
		SearchResult searchResult = new SearchResult(httpResult.getResult());
		URL requestUrl;
		try {
			requestUrl = httpResult.getRequestUri().toURL();
		} catch (MalformedURLException e) {
			throw new OpenSearchClientException(e);
		}
		List<Header> requestHeaders = httpResult.getRequestHeaders();
		List<Header> responseHeaders = httpResult.getResponseHeaders();
		return new SearchResultDebug(searchResult,requestUrl,requestHeaders,responseHeaders,httpResult.getCode(),httpResult.getReason());
	}

	public void executeForServerSentEvents(SearchParams searchParams,
										   Consumer<ServerSentEvent<String>> eventConsumer) throws OpenSearchClientException {
		UrlParamsBuilder urlParamsBuilder = new UrlParamsBuilder(searchParams);
		Map<String, String> params = toMap(urlParamsBuilder.getHttpParams());
		String requestPath = parseApiPathTemplate(searchParams);
		this.serviceClient.callForServerSentEvents(requestPath, params, OpenSearchClient.METHOD_GET, eventConsumer);
	}

	public void executeForOpenSearchResultEvents(SearchParams searchParams,
												 OpenSearchResponseConsumer<ServerSentEvent<OpenSearchResult>> eventConsumer) throws OpenSearchException, OpenSearchClientException {
		UrlParamsBuilder urlParamsBuilder = new UrlParamsBuilder(searchParams);
		Map<String, String> params = toMap(urlParamsBuilder.getHttpParams());
		String requestPath = parseApiPathTemplate(searchParams);
		this.serviceClient.callForOpenSearchResultEvents(requestPath, params, OpenSearchClient.METHOD_GET, eventConsumer);
	}

	/**
	 * httpParams to map.
	 *
	 * @param httpParams the http params
	 * @return the map
	 */
	private static Map<String, String> toMap(BasicHttpParams httpParams) {
		return Utils.toMap(httpParams);
	}

	/**
	 * Parses the template.
	 *
	 * @param searchParams the search params
	 * @return the string
	 */
	private static String parseApiPathTemplate(SearchParams searchParams) {
		List<String> appNames = searchParams.getConfig().getAppNames();
		Preconditions.checkArgument(!appNames.isEmpty(), "at least one app name or id should be specified.");
		Preconditions.checkNotNull(appNames);
		Preconditions.checkArgument(appNames.size() >= 1);
		String app_name = Joiner.on(',').join(appNames);
		final String path;
		if (searchParams.isSetSuggest()) {
			String suggestName = searchParams.getSuggest().getSuggestName();
			path = SUGGEST_API_PATH.replaceAll("\\{app_name\\}", app_name)
					.replaceAll("\\{suggest_name\\}", suggestName);
		} else if (searchParams.isSetChat()) {
			path = RAG_SEARCH_API_PATH.replaceAll("\\{app_name\\}", app_name);
		} else {
			path = SEARCH_API_PATH.replaceAll("\\{app_name\\}", app_name);
		}
		return path;
	}
}
