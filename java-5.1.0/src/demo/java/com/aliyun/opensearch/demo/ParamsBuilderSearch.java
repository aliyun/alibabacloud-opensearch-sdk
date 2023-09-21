package com.aliyun.opensearch.demo;

import org.json.JSONObject;

import com.aliyun.opensearch.OpenSearchClient;
import com.aliyun.opensearch.SearcherClient;
import com.aliyun.opensearch.sdk.generated.OpenSearch;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchClientException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchException;
import com.aliyun.opensearch.sdk.generated.search.Config;
import com.aliyun.opensearch.sdk.generated.search.Order;
import com.aliyun.opensearch.sdk.generated.search.SearchFormat;
import com.aliyun.opensearch.sdk.generated.search.SearchParams;
import com.aliyun.opensearch.sdk.generated.search.Sort;
import com.aliyun.opensearch.sdk.generated.search.SortField;
import com.aliyun.opensearch.sdk.generated.search.general.SearchResult;
import com.aliyun.opensearch.search.SearchParamsBuilder;
import com.google.common.collect.Lists;

/**
 * The demo ParamsBuilderSearch.
 *
 * @author Ken
 */
public class ParamsBuilderSearch {
	private String appName = "QA_Push_Click_Log";
	private String accesskey = "";
	private String secret = "";
	private String host = "http://opensearch-cn-beijing.aliyuncs.com";

	void search() {
		// 用基础信息创建OpenSearch
		OpenSearch openSearch = new OpenSearch(accesskey, secret, host);

		// 创建OpenSearchClient
		OpenSearchClient serviceClient = new OpenSearchClient(openSearch);

		SearcherClient searcherClient = new SearcherClient(serviceClient);
		// search config
		Config config = new Config(Lists.newArrayList(appName));
		// result format, default is xml
		config.setSearchFormat(SearchFormat.JSON);
		// set multi-partitions search route value.
		config.setRouteValue("43215");
		// specify which fields to return in search result.
		config.setFetchFields(Lists.newArrayList("field1", "field2", "fieldN"));
		// search params
		SearchParams searchParams = new SearchParams(config);
		// query by id.
		searchParams.setQuery("id:'123456'");

		Sort sorter = new Sort();
		sorter.addToSortFields(new SortField("field1", Order.DECREASE));
		sorter.addToSortFields(new SortField("RANK", Order.INCREASE));
		searchParams.setSort(sorter);

		// convenient builder for building search params.
		SearchParamsBuilder paramsBuilder = SearchParamsBuilder.create(searchParams);

		//set filter conditions
		paramsBuilder.addFilter("group_id=3", "OR");
		paramsBuilder.addFilter("log_id=6", "AND");

		SearchResult searchResult = null;
		try {
			searchResult = searcherClient.execute(searchParams);
		} catch (OpenSearchException e) {
			e.printStackTrace();
		} catch (OpenSearchClientException e) {
			e.printStackTrace();
		}

		String result = searchResult.getResult();
		JSONObject obj = new JSONObject(result);
		obj.get("status"); // OK or FAIL
		JSONObject r = (JSONObject) obj.get("result"); // retrieve search result list 
		r.get("total"); // total hits count.
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ParamsBuilderSearch simpleSearch = new ParamsBuilderSearch();
		simpleSearch.search();
	}

}
