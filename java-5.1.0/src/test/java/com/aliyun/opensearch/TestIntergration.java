package com.aliyun.opensearch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.aliyun.opensearch.sdk.generated.app.*;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchResult;
import com.aliyun.opensearch.util.JsonUtilWrapper;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aliyun.opensearch.sdk.generated.OpenSearch;
import com.aliyun.opensearch.sdk.generated.search.Config;
import com.aliyun.opensearch.sdk.generated.search.SearchFormat;
import com.aliyun.opensearch.sdk.generated.search.SearchParams;
import com.aliyun.opensearch.sdk.generated.search.general.SearchResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class TestIntergration {
	private String appName = "myblog";
	private String tableName = "blog_entries";
	private String accesskey = "your_access_key";
	private String secret = "your_secret";
	private String host = "http://opensearch-cn-corp.aliyuncs.com";

	private OpenSearch openSearch;
	private OpenSearchClient serviceClient;
	@Before
	public void setUp() throws Exception {
		openSearch = new OpenSearch(accesskey, secret, host);
		serviceClient = new OpenSearchClient(openSearch);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testCreateApp() throws Exception {
		//"http://10.101.86.24";
		host = "http://opensearch-cn-beijing.aliyuncs.com";
		accesskey = "your_access_key";
		secret = "your_secret";
		openSearch = new OpenSearch(accesskey, secret, host);
		serviceClient = new OpenSearchClient(openSearch);

		AppClient appClient = new AppClient(serviceClient);
		App app = new App();
		app.setQuota(new Quota().setDoc_size(100).setCompute_resource(6).setSpec("opensearch.share.common"));
		try {
			OpenSearchResult openSearchResult = appClient.save(JsonUtilWrapper.toJson(app));
			App resultApp = JsonUtilWrapper.fromJson(openSearchResult.getResult(), App.class);
		} catch (OpenSearchException e) {
			System.out.println(e.getCode());
			final int CODE_INDEX_NAME_NOT_SET = 2005;

			assertEquals(CODE_INDEX_NAME_NOT_SET, e.getCode());
			return;
		}
		assertTrue(false);
	}

	@Test
	public void testAddSearchRemove() throws Exception {
		System.out.println(String.format("file.encoding: %s", System.getProperty("file.encoding")));
		System.out.println(String.format("defaultCharset: %s", Charset.defaultCharset().name()));

		Random rand = new Random();
		int value = rand.nextInt(Integer.MAX_VALUE);
		System.out.println("id: " + value);
		DocumentClient documentClient = new DocumentClient(serviceClient);

		Map<String, Object> map = Maps.newLinkedHashMap();
		map.put("id", value);
		String title_string = "搜索引擎事业部简介";// utf-8
		byte[] bytes = title_string.getBytes("utf-8");
		String GB18030_string = new String(bytes, "GB18030");
		map.put("title", GB18030_string);
		map.put("auth", "濒湖");
		map.put("content", "测试.");
		documentClient.add(map);

		documentClient.commit(appName, tableName);

		Thread.sleep(10000);

		SearcherClient searcherClient = new SearcherClient(serviceClient);
		Config config = new Config(Lists.newArrayList(appName));
		config.setSearchFormat(SearchFormat.JSON);
		SearchParams searchParams = new SearchParams(config);
		searchParams.setQuery("id:'" + value + "'");
		SearchResult searchResult = searcherClient.execute(searchParams);
		String result = searchResult.getResult();
		JSONObject obj = new JSONObject(result);

		assertEquals("OK", obj.get("status"));

		JSONObject r = (JSONObject) obj.get("result");
		assertEquals(1, r.get("total"));

		documentClient.remove(map);
		documentClient.commit(appName, tableName);

		Thread.sleep(2000);

		SearchParams searchParams2 = new SearchParams(config);
		searchParams2.setQuery("id:'" + value + "'");
		SearchResult searchResult2 = searcherClient.execute(searchParams2);
		String result2 = searchResult2.getResult();
		System.out.println(result2);
		obj = new JSONObject(result2);
//		assertEquals("OK", obj.get("status"));
		r = (JSONObject) obj.get("result");
		assertEquals(0, r.get("total"));
	}
}