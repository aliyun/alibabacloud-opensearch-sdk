package com.aliyun.opensearch.search;

import java.nio.charset.Charset;
import java.util.Iterator;

import org.json.JSONObject;

import com.aliyun.opensearch.OpenSearchClient;
import com.aliyun.opensearch.SearcherClient;
import com.aliyun.opensearch.sdk.generated.OpenSearch;
import com.aliyun.opensearch.sdk.generated.search.Config;
import com.aliyun.opensearch.sdk.generated.search.DeepPaging;
import com.aliyun.opensearch.sdk.generated.search.SearchFormat;
import com.aliyun.opensearch.sdk.generated.search.SearchParams;
import com.aliyun.opensearch.sdk.generated.search.general.SearchResult;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class ScrollSearch {
	private String appName = "twb_seller";
	private String accesskey = "your_access_key";
	private String secret = "your_secret";
	private String host = "http://opensearch-cn-corp.aliyuncs.com";

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
		config.setHits(22);// 设置每页为5条记录

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

		DeepPageingIterator pagesIterator = new DeepPageingIterator(searcherClient, searchParams);

		while (pagesIterator.hasNext()) {
			String jsonResult = pagesIterator.next();
		}
	}

	public static void main(String[] args) {
		ScrollSearch scroll = new ScrollSearch();
		scroll.testScroll();
	}
}