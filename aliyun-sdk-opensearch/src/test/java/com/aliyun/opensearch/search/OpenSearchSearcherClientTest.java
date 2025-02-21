package com.aliyun.opensearch.search;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.aliyun.opensearch.sdk.generated.commons.OpenSearchException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchResult;
import com.aliyun.opensearch.sdk.generated.search.*;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.thrift.TException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aliyun.opensearch.OpenSearchClient;
import com.aliyun.opensearch.SearcherClient;
import com.aliyun.opensearch.sdk.generated.OpenSearch;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchClientException;
import com.aliyun.opensearch.sdk.generated.search.general.SearchResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class OpenSearchSearcherClientTest {
	private SearchParams searchParams;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUnknowHostException() throws Exception {
		String accesskey = "您的阿里云的Access Key ID";
		String secret = "阿里云 Access Key ID 对应的 Access Key Secret";
		String appName = "您要创建的应用名称";

		String host = "http://unknow.host";

		OpenSearch opensearch = new OpenSearch(accesskey, secret, host);
		OpenSearchClient serviceClient = new OpenSearchClient(opensearch);
		SearcherClient searcherClient = new SearcherClient(serviceClient);
		Config config = new Config(Lists.newArrayList(appName));
		searchParams = new SearchParams(config);
		SearchResult result;
		try {
			result = searcherClient.execute(searchParams);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(e instanceof OpenSearchClientException);
			assertTrue(e.getCause() instanceof UnknownHostException);
		}
	}

    @Test
    public void testRequestByBearerTokne() throws Exception {

        String host = "host";
        OpenSearch opensearch = new OpenSearch();
        opensearch.setHost(host);
        opensearch.setBearerToken("youer bearer token");
        OpenSearchClient openSearchClient = new OpenSearchClient(opensearch);

        String path = "/workspaces/default/text-embedding/ops-text-embedding-001";
        Map<String, String> params = new HashMap<String, String>() {{
            put("format", "full_json");
            put("_POST_BODY", "{\n" +
                "\n" +
                "  \"input\":[\n" +
                "    \"科学技术是第一生产力\",\"opensearch产品文档\"\n" +
                "  ],\n" +
                "  \"input_type\" : \"document\"\n" +
                "}"
            );
        }};
        OpenSearchResult openSearchResult = openSearchClient.callAndDecodeResult(path, params, "POST");
        System.out.println(openSearchResult.getResult());
    }

	@Test
	public void testSuggests() throws TException {
		String accesskey = "您的阿里云的Access Key ID";
		String secret = "阿里云 Access Key ID 对应的 Access Key Secret";
		String appName = "您要创建的应用名称";
		String tableName = "表名";
		String host = "";

		appName = "myblog";
		tableName = "blog_entries";
		accesskey = "ak";
		secret = "secret";
		host = "http://opensearch-cn-corp.aliyuncs.com";
		host = "http://internal1.api.aliyuncs.com";
		OpenSearch opensearch = new OpenSearch(accesskey, secret, host);
		OpenSearchClient serviceClient = new OpenSearchClient(opensearch);
		SearcherClient searcherClient = new SearcherClient(serviceClient);

		Config config = new Config(Lists.newArrayList(appName));
		searchParams = new SearchParams(config);

		searchParams.setQuery("id:'1'");
		Suggest suggest = new Suggest("suggestName1");
		searchParams.setSuggest(suggest);
		System.out.println(searchParams);
		SearchResult result;
		try {
			result = searcherClient.execute(searchParams);
			System.out.println(result);
		} catch (OpenSearchClientException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试包含相同索引的多应用搜索
	 *
	 * @throws TException
	 */
	@Test
	public void testMultipleAppsSearch() throws TException {
		String accesskey = "您的阿里云的Access Key ID";
		String secret = "阿里云 Access Key ID 对应的 Access Key Secret";
		String appName1 = "您要创建的应用名称";
		String appName2 = "您要创建的应用名称";
		String host = "";

		appName1 = "MZ_Std_JavaSdkTest_1";
		appName2 = "MZ_Std_JavaSdkTest_2";

		accesskey = "ak";
		secret = "secret";
		host = "http://opensearch-cn-corp.aliyuncs.com";

		OpenSearch opensearch = new OpenSearch(accesskey, secret, host);
		OpenSearchClient serviceClient = new OpenSearchClient(opensearch);
		SearcherClient searcherClient = new SearcherClient(serviceClient);

		Config config = new Config(Lists.newArrayList(appName1, appName2));
		config.setSearchFormat(SearchFormat.findByValue(1));
		searchParams = new SearchParams(config);

		SearchResult searchResult;
		try {
			searchResult = searcherClient.execute(searchParams);
			System.out.println(searchResult);
		} catch (OpenSearchClientException e) {
			e.printStackTrace();
			assertTrue(false);
			return;
		}

		assertNotEquals(searchResult.getResult(), null);
	}

	@Test
	public void testExecuteSearchParams() throws TException {
		String accesskey = "您的阿里云的Access Key ID";
		String secret = "阿里云 Access Key ID 对应的 Access Key Secret";
		String appName = "您要创建的应用名称";
		String tableName = "表名";
		String host = "";

		appName = "myblog";
		tableName = "blog_entries";
		accesskey = "ak";
		secret = "secret";
		host = "http://opensearch-cn-corp.aliyuncs.com";
		host = "http://internal1.api.aliyuncs.com";
		OpenSearch opensearch = new OpenSearch(accesskey, secret, host);
		OpenSearchClient serviceClient = new OpenSearchClient(opensearch);
		SearcherClient searcherClient = new SearcherClient(serviceClient);

		Config config = new Config(Lists.newArrayList(appName));
//		config.setRouteValue("213321");
		config.setSearchFormat(SearchFormat.JSON);
		config.setKvpairs("name:company_name,price:new_price,title:short_title");
		config.setFetchFields(Lists.newArrayList("field1", "field2"));
		searchParams = new SearchParams(config);

		searchParams.setQuery("id:'1'");

		Sort sorter = new Sort();
		sorter.addToSortFields(new SortField("field1", Order.DECREASE));
		sorter.addToSortFields(new SortField("RANK", Order.INCREASE));
		searchParams.setSort(sorter);
		Map<String, Distinct> distincts = Maps.newHashMap();
		distincts.put("key1", new Distinct("key1"));
		distincts.put("key2", new Distinct("key2"));
		searchParams.setDistincts(Sets.newHashSet(distincts.values()));
		Map<String, Aggregate> aggregates = Maps.newHashMap();
		Aggregate aggregate1 = new Aggregate("groupKey1");
		aggregate1.setAggFilter("aggFilter");
		aggregate1.setAggFun("aggFun");
		aggregate1.setAggSamplerStep("aggSamplerStep");
		aggregate1.setAggSamplerThresHold("aggSamplerThresHold");
		aggregate1.setGroupKey("groupKey");
		aggregate1.setMaxGroup("maxGroup");
		aggregates.put("key1", aggregate1);
		searchParams.setAggregates(Sets.newHashSet(aggregates.values()));
		searchParams.setFilter("id=7 AND company_id=5");

		Suggest suggest = new Suggest("suggestName1");
		searchParams.setSuggest(suggest);

		Map<String, Summary> summaries = Maps.newHashMap();
		Summary summary1 = new Summary("summary_field1");
		Summary summary2 = new Summary("summary_field2");
		summaries.put("summary1", summary1);
		summaries.put("summary2", summary2);
		searchParams.setSummaries(Sets.newHashSet(summaries.values()));

		System.out.println(searchParams);

		SearchResult result;
		try {
			result = searcherClient.execute(searchParams);
			System.out.println(result);
		} catch (OpenSearchClientException e) {
			e.printStackTrace();
		}

	}

    /**
     * 测试查询增加 abtest 参数
     *
     * @throws TException
     */
    @Test
    public void testSearchWithAbtest() throws TException {
        String appName = "K_Enh_Abtest_1";

        String accesskey = "ak";
        String secret = "secret";
        String host = "http://opensearch-test.aliyuncs.com";

        OpenSearch opensearch = new OpenSearch(accesskey, secret, host);
        OpenSearchClient serviceClient = new OpenSearchClient(opensearch);
        SearcherClient searcherClient = new SearcherClient(serviceClient);

        Config config = new Config(Lists.newArrayList(appName));
        config.setSearchFormat(SearchFormat.findByValue(1));
        searchParams = new SearchParams(config);
        searchParams.setAbtest(new Abtest().setSceneTag("mz").setFlowDivider("1001"));
        searchParams.setQuery("");

        SearchResult searchResult;
        try {
            searchResult = searcherClient.execute(searchParams);
            System.out.println(searchResult);
        } catch (OpenSearchClientException e) {
            e.printStackTrace();
            assertTrue(false);
            return;
        }

        assertNotEquals(searchResult.getResult(), null);
    }

    /**
     * 测试查询增加 user_id 参数
     *
     * @throws TException
     */
    @Test
    public void testSearchWithUserId() throws TException {
        String appName = "K_Enh_Abtest_1";

        String accesskey = "ak";
        String secret = "secret";
        String host = "http://opensearch-test.aliyuncs.com";

        String userId = "2001";

        OpenSearch opensearch = new OpenSearch(accesskey, secret, host);
        OpenSearchClient serviceClient = new OpenSearchClient(opensearch);
        SearcherClient searcherClient = new SearcherClient(serviceClient);

        Config config = new Config(Lists.newArrayList(appName));
        config.setSearchFormat(SearchFormat.findByValue(1));
        searchParams = new SearchParams(config);
        searchParams.setUserId(userId);

        SearchResult searchResult;
        try {
            searchResult = searcherClient.execute(searchParams);
            System.out.println(searchResult);
        } catch (OpenSearchClientException e) {
            e.printStackTrace();
            assertTrue(false);
            return;
        }

        assertNotEquals(searchResult.getResult(), null);
    }

    /**
     * 测试查询增加 raw_query 参数
     *
     * @throws TException
     */
    @Test
    public void testSearchWithRawQuery() throws TException {
        String appName = "K_Enh_Abtest_1";

        String accesskey = "ak";
        String secret = "secret";
        String host = "http://opensearch-test.aliyuncs.com";

        String rawQuery = "连衣裙";

        OpenSearch opensearch = new OpenSearch(accesskey, secret, host);
        OpenSearchClient serviceClient = new OpenSearchClient(opensearch);
        SearcherClient searcherClient = new SearcherClient(serviceClient);

        Config config = new Config(Lists.newArrayList(appName));
        config.setSearchFormat(SearchFormat.findByValue(1));
        searchParams = new SearchParams(config);
        searchParams.setRawQuery(rawQuery);

        SearchResult searchResult;
        try {
            searchResult = searcherClient.execute(searchParams);
            System.out.println(searchResult);
        } catch (OpenSearchClientException e) {
            e.printStackTrace();
            assertTrue(false);
            return;
        }

        assertNotEquals(searchResult.getResult(), null);
    }

    @Test
    public void testSearch() {
        String appName = "name";
        String accesskey = "ak";
        String secret = "secret";
        String host = "http://opensearch-test.aliyuncs.com";

        OpenSearch opensearch = new OpenSearch(accesskey, secret, host);
		opensearch.setHost(host);
		opensearch.setTimeout(90000);
        OpenSearchClient openSearchClient = new OpenSearchClient(opensearch);
        SearcherClient searcherClient = new SearcherClient(openSearchClient);

        Config config = new Config(Lists.newArrayList(appName));
		config.setHits(10);
		config.setStart(0);
        config.setSearchFormat(SearchFormat.findByValue(1));
		config.setFetchFields(Arrays.asList("title", "description", "id"));
        searchParams = new SearchParams(config);
		searchParams.setQuery("default:'OpenSearch'");
		searchParams.setRawQuery("OpenSearch");

        try {
			SearchResult searchResult = searcherClient.execute(searchParams);
			System.out.println(searchResult.getResult());
        } catch (OpenSearchClientException e) {
            System.out.println("ErrorMessage=" + e.getMessage());
        } catch (OpenSearchException e) {
            throw new RuntimeException(e);
        }
    }

	/**
	 * SSE
	 */
	@Test
	public void testExecuteForServerSentEvents() {
		String appName = "name";
        String accesskey = "ak";
        String secret = "secret";
        String host = "http://opensearch-test.aliyuncs.com";

		OpenSearch opensearch = new OpenSearch(accesskey, secret, host);
		opensearch.setHost(host);
		opensearch.setTimeout(90000);
		OpenSearchClient openSearchClient = new OpenSearchClient(opensearch);
		SearcherClient searcherClient = new SearcherClient(openSearchClient);

		Config config = new Config(Lists.newArrayList(appName));
		config.setHits(10);
		config.setStart(0);
		config.setSearchFormat(SearchFormat.findByValue(1));
		//config.setFetchFields(Arrays.asList("title", "description", "id"));
		searchParams = new SearchParams(config);

		searchParams.setQuery("default:'OpenSearch'");
		searchParams.setRawQuery("OpenSearch");
		String chatName = "test_chat";
		Map<String, String> promptParameters = new HashMap();
		Chat chat = new Chat(chatName, promptParameters);
		searchParams.setChat(chat);

		try {
			searcherClient.executeForServerSentEvents(searchParams, e -> {
				System.out.println(e.data());
			});
		} catch (OpenSearchClientException e) {
			System.out.println("ErrorMessage=" + e.getMessage());
		}
	}

	/**
	 * 对象流
	 */
	@Test
    public void testExecuteForOpenSearchResultEvents() {
		String appName = "name";
        String accesskey = "ak";
        String secret = "secret";
        String host = "http://opensearch-test.aliyuncs.com";

		OpenSearch opensearch = new OpenSearch(accesskey, secret, host);
		opensearch.setHost(host);
		opensearch.setTimeout(90000);
		OpenSearchClient openSearchClient = new OpenSearchClient(opensearch);
		SearcherClient searcherClient = new SearcherClient(openSearchClient);

		Config config = new Config(Lists.newArrayList(appName));
		config.setHits(10);
		config.setStart(0);
		config.setSearchFormat(SearchFormat.findByValue(1));
		config.setFetchFields(Arrays.asList("title", "description", "id"));
		searchParams = new SearchParams(config);

		searchParams.setQuery("default:'OpenSearch'");
		searchParams.setRawQuery("OpenSearch");
		String chatName = "test_chat";
		Map<String, String> promptParameters = new HashMap();
		Chat chat = new Chat(chatName, promptParameters);
		searchParams.setChat(chat);

        try {
			searcherClient.executeForOpenSearchResultEvents(searchParams, e -> {
				if ( e.data().getTraceInfo() != null) {
					System.out.println("RequestID=" + e.data().getTraceInfo().getRequestId());
				}

				if (!StringUtils.isEmpty(e.data().getResult())) {
					System.out.println("result=" + e.data().getResult());
				}

				if (!StringUtils.isEmpty(e.data().getChat())) {
					System.out.println("chat=" + e.data().getChat());
				}
			});
		} catch (OpenSearchException e) {
			System.out.println("RequestID=" + e.getRequestId());
			System.out.println("ErrorCode=" + e.getCode());
			System.out.println("ErrorMessage=" + e.getMessage());
		} catch (OpenSearchClientException e) {
			System.out.println("ErrorMessage=" + e.getMessage());
		}
	}

	// llm 对象流
	@Test
    public void testCallForOpenSearchResultEvents() {
		String appName = "name";
        String accesskey = "ak";
        String secret = "secret";
        String host = "http://opensearch-test.aliyuncs.com";
		String path = "/apps/" + appName + "/actions/knowledge-search";

		OpenSearch openSearch = new OpenSearch();
		openSearch.setHost(host);
		openSearch.setAccessKey(accesskey);
		openSearch.setSecret(secret);
		//openSearch.setBearerToken("OS-49ycmbrps632r774");

		openSearch.setTimeout(62000);

		OpenSearchClient openSearchClient = new OpenSearchClient(openSearch);

		Map<String, String> params = new HashMap<String, String>() {{
			put("_POST_BODY", "{\"options\":{\"chat\":{\"stream\":true},\"retrieve\":{\"filter\":\"\"}},\"question\":{\"text\":\"根据对各种类型典型站点的调研\",\"type\":\"TEXT\"}}");
		}};

 		// 对象流
		try {
			openSearchClient.callForOpenSearchResultEvents(path, params, "POST", e -> {
				//System.out.println("RequestID=" + e.data().getTraceInfo().getRequestId());
				System.out.println(e.data().getResult());
			});
		} catch (OpenSearchException e) {
			System.out.println("RequestID=" + e.getRequestId());
			System.out.println("ErrorCode=" + e.getCode());
			System.out.println("ErrorMessage=" + e.getMessage());
		} catch (OpenSearchClientException e) {
			System.out.println("ErrorMessage=" + e.getMessage());
		}
	}

	// llm 数据流
	@Test
    public void testCallForHttpResponse() {
		String appName = "name";
        String accesskey = "ak";
        String secret = "secret";
        String host = "http://opensearch-test.aliyuncs.com";
		String path = "/apps/" + appName + "/actions/knowledge-search";

		OpenSearch openSearch = new OpenSearch();
		openSearch.setHost(host);
		openSearch.setAccessKey(accesskey);
		openSearch.setSecret(secret);
		//openSearch.setBearerToken("OS-49ycmbrps632r774");

		openSearch.setTimeout(62000);

		OpenSearchClient openSearchClient = new OpenSearchClient(openSearch);

		Map<String, String> params = new HashMap<String, String>() {{
			put("_POST_BODY", "{\"options\":{\"chat\":{\"stream\":true},\"retrieve\":{\"filter\":\"\"}},\"question\":{\"text\":\"根据对各种类型典型站点的调研\",\"type\":\"TEXT\"}}");
		}};

		try {
			HttpResponse httpResponse = openSearchClient.callForHttpResponse(path, params, "POST");
			InputStream responseBodyStream = httpResponse.getEntity()
					.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(responseBodyStream, StandardCharsets.UTF_8));
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}

			br.close();

			// 请求成功
			openSearchClient.getHttpClientManager().getClientTracer().success(httpResponse, "");
		} catch (OpenSearchClientException e) {
			System.out.println("ErrorMessage=" + e.getMessage());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// llm sse
	@Test
    public void testCallForServerSentEvents() {
		String appName = "name";
        String accesskey = "ak";
        String secret = "secret";
        String host = "http://opensearch-test.aliyuncs.com";
		String path = "/apps/" + appName + "/actions/knowledge-search";

		OpenSearch openSearch = new OpenSearch();
		openSearch.setHost(host);
		openSearch.setAccessKey(accesskey);
		openSearch.setSecret(secret);
		openSearch.setTimeout(62000);

		OpenSearchClient openSearchClient = new OpenSearchClient(openSearch);

		Map<String, String> params = new HashMap<String, String>() {{
			put("_POST_BODY", "{\"options\":{\"chat\":{\"stream\":true},\"retrieve\":{\"filter\":\"\"}},\"question\":{\"text\":\"根据对各种类型典型站点的调研\",\"type\":\"TEXT\"}}");
		}};

		try {
			openSearchClient.callForServerSentEvents(path, params, "POST", e -> {
			   System.out.println(e.data());
			});
		} catch (OpenSearchClientException e) {
			System.out.println("ErrorMessage=" + e.getMessage());
		}
	}

}
