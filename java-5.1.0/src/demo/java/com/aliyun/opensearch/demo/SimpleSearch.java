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
import com.google.common.collect.Lists;

/**
 * The demo SimpleSearch.
 *
 * @author Ken
 */
public class SimpleSearch {
	private String appName = "";
	private String accesskey = "";
	private String secret = "";
	private String host = "";

	void search() {
		// 用基础信息创建OpenSearch
		OpenSearch openSearch = new OpenSearch(accesskey, secret, host);

		// create OpenSearchClient
		OpenSearchClient serviceClient = new OpenSearchClient(openSearch);
		// create SearcherClient
		SearcherClient searcherClient = new SearcherClient(serviceClient);
		// search config, start from 0, and 30 per page.
		Config config = new Config(Lists.newArrayList(appName));
		// result format, default is xml
		config.setSearchFormat(SearchFormat.JSON);
		// specify which fields to return in search result.
		config.setFetchFields(Lists.newArrayList("field1", "field2", "fieldN"));
		// search params
		SearchParams searchParams = new SearchParams(config);
		// query by id. remain empty for return all docs.
		searchParams.setQuery("id:'123456'");
		//set filter conditions
		searchParams.setFilter("user_id=7 AND company_id=5");
		// create sort condition
		Sort sorter = new Sort();
		sorter.addToSortFields(new SortField("field1", Order.DECREASE));
		sorter.addToSortFields(new SortField("RANK", Order.INCREASE));
		searchParams.setSort(sorter);

		// execute search with params.
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
		SimpleSearch simpleSearch = new SimpleSearch();
		simpleSearch.search();
	}

}
