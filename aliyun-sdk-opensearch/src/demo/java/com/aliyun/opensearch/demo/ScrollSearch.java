package com.aliyun.opensearch.demo;

import java.nio.charset.Charset;

import com.aliyun.opensearch.OpenSearchClient;
import com.aliyun.opensearch.SearcherClient;
import com.aliyun.opensearch.sdk.generated.OpenSearch;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchClientException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchException;
import com.aliyun.opensearch.sdk.generated.search.Config;
import com.aliyun.opensearch.sdk.generated.search.DeepPaging;
import com.aliyun.opensearch.sdk.generated.search.SearchFormat;
import com.aliyun.opensearch.sdk.generated.search.SearchParams;
import com.aliyun.opensearch.sdk.generated.search.general.SearchResult;
import com.aliyun.opensearch.search.SearchParamsBuilder;
import com.google.common.collect.Lists;

public class ScrollSearch {
	private String appName = "your_app_name";
	private String accesskey = "your_access_key";
	private String secret = "your_access_secret";
	private String host = "your_endpoint";

	public void testScroll() {

		// 查看文件和默认编码格式
		System.out.println(String.format("file.encoding: %s", System.getProperty("file.encoding")));
		System.out.println(String.format("defaultCharset: %s", Charset.defaultCharset().name()));

		// 创建并构造OpenSearch对象
		OpenSearch openSearch = new OpenSearch(accesskey, secret, host);

		// 创建OpenSearchClient对象，并以OpenSearch对象作为构造参数
		OpenSearchClient serviceClient = new OpenSearchClient(openSearch);

		// 创建SearcherClient对象，并以OpenSearchClient对象作为构造参数
		SearcherClient searcherClient = new SearcherClient(serviceClient);

		// 定义Config对象，用于设定config子句参数，分页，数据返回格式，应用名等等
		Config config = new Config(Lists.newArrayList(appName));

		// config.setStart(start) scroll该参数不起作用，默认为0
		config.setHits(2);// 设置每页为5条记录

		// 设置返回格式为json,目前只支持返回xml和json格式，暂不支持返回fulljson类型
		config.setSearchFormat(SearchFormat.JSON);

		// 设置搜索结果返回应用中哪些字段
		// config.setFetchFields(Lists.newArrayList("id", "name", "phone", "int_arr", "literal_arr", " float_arr"));
		// 注意：config子句中的rerank_size参数，在Rank类对象中设置

		// 创建参数对象
		SearchParams searchParams = new SearchParams(config);

		// 设置查询子句，若需多个索引组合查询，需要setQuery处合并，否则若设置多个setQuery后面的会替换前面查询
		searchParams.setQuery("seller_id:'1733410482'");

		// 设置查询过滤条件
		// searchParams.setFilter("id > \"0\""); //此处也可改用后面的ParamsBuilder实现添加过滤条件

		// 设置scroll方式查询
		DeepPaging deep = new DeepPaging();
		deep.setScrollExpire("3m");// 不设置默认为1m表示1分钟

		// 添加DeepPaging对象参数
		searchParams.setDeepPaging(deep);

		// SearchParams的工具类，提供了更为便捷的操作
		SearchParamsBuilder paramsBuilder = SearchParamsBuilder.create(searchParams);

		// 设置查询过滤条件
		// paramsBuilder.addFilter("id>\"0\"", "AND");

		// 执行返回查询结果
		SearchResult searchResult;
		try {
			searchResult = searcherClient.execute(paramsBuilder);
			String result = searchResult.getResult();
			System.out.println("结果" + result);
			// JSONObject obj = new JSONObject(result);

			// 假设数据库中只有25条，且每页展示5条，则第6次打印出来items数据为空
			for (int i = 1; i <= 6; i++) {

				// // 第一次执行不返回数据，主要返回scroll_id值，第二次调用查询时设置scroll_id
				// deep.setScrollId(new JSONObject(obj.get("result").toString()).get("scroll_id").toString());
				// deep.setScrollExpire("3m");// 不设置默认为1m表示1分钟，如不想使用默认值，每次重新调用前必须要重新设置下
				// searchResult = searcherClient.execute(paramsBuilder);
				// result = searchResult.getResult();
				// obj = new JSONObject(result);
				//
				// // 输出查询结果
				// System.out.println("第" + i + "次执行:" + obj.get("result"));
				// try {
				// Thread.sleep(1000);
				// } catch (InterruptedException e) {
				// e.printStackTrace();
				// } // 休眠1秒，防止qps请求过高报错
			}

			// 判断返回结果是否OK
			// assertEquals("OK", obj.get("status"));

		} catch (OpenSearchException e) {
			e.printStackTrace();
		} catch (OpenSearchClientException e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		ScrollSearch scroll = new ScrollSearch();
		scroll.testScroll();
	}
}
