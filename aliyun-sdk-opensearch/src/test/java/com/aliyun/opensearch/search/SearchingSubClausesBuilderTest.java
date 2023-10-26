package com.aliyun.opensearch.search;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aliyun.opensearch.sdk.generated.search.Aggregate;
import com.aliyun.opensearch.sdk.generated.search.Config;
import com.aliyun.opensearch.sdk.generated.search.Distinct;
import com.aliyun.opensearch.sdk.generated.search.Order;
import com.aliyun.opensearch.sdk.generated.search.SearchFormat;
import com.aliyun.opensearch.sdk.generated.search.SearchParams;
import com.aliyun.opensearch.sdk.generated.search.Sort;
import com.aliyun.opensearch.sdk.generated.search.SortField;
import com.aliyun.opensearch.sdk.generated.search.Summary;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 *
 * @author Ken
 */
public class SearchingSubClausesBuilderTest {

	private SearchParams searchParams;

	@Before
	public void setUp() throws Exception {
		Config config = new Config(Lists.newArrayList("appname1"));
		config.setSearchFormat(SearchFormat.JSON);
		config.setKvpairs("name:company_name,price:new_price,title:short_title");
		config.setFetchFields(Lists.newArrayList("field1", "field2"));
		searchParams = new SearchParams(config);
		searchParams.setQuery("搜索 AND 计算");
		Sort sorter = new Sort();
		sorter.addToSortFields(new SortField("field1", Order.DECREASE));
		sorter.addToSortFields(new SortField("RANK", Order.INCREASE));
		searchParams.setSort(sorter);
		Map<String, Distinct> distincts = Maps.newHashMap();
		Distinct distinct1 = new Distinct("key1");
		distinct1.setGrade("5.2");
		distincts.put("justName1", distinct1);
		distincts.put("justName2", new Distinct("key2"));
		searchParams.setDistincts(Sets.newLinkedHashSet(distincts.values()));
		Map<String, Aggregate> aggregates = Maps.newHashMap();
		aggregates.put("justName1", new Aggregate("groupKey1"));
		searchParams.setAggregates(Sets.newLinkedHashSet(aggregates.values()));
		searchParams.setFilter("id=7 AND company_id=5");
		Map<String, Summary> summaries = Maps.newHashMap();
		Summary summary1 = new Summary("summary_field1");
		Summary summary2 = new Summary("summary_field2");
		summaries.put("summary1", summary1);
		summaries.put("summary2", summary2);
		searchParams.setSummaries(Sets.newHashSet(summaries.values()));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBuildConfigClause() {
		SearchingSubClausesBuilder builder = new SearchingSubClausesBuilder(searchParams);
		String string = builder.getClausesString();
		Map<String, String> map = configToParamsMap(string);
		String expectedStyle = "start:0,hit:15,rerank_size:200,format:json";
		Map<String, String> expectedMap = Splitter.on(",").withKeyValueSeparator(":").split(expectedStyle);
		String actualStyle = map.get("config");
		Map<String, String> actualMap = Splitter.on(",").withKeyValueSeparator(":").split(actualStyle);
		assertEquals(expectedMap, actualMap);
		assertEquals("start:0,hit:15,rerank_size:200,format:json", map.get("config"));
	}

	@Test
	public void testBuildQueryClause() {
		SearchingSubClausesBuilder builder = new SearchingSubClausesBuilder(searchParams);
		String string = builder.getClausesString();
		Map<String, String> map = configToParamsMap(string);
		assertEquals("搜索 AND 计算", map.get("query"));

	}

	@Test
	public void testBuildSortClause() {
		SearchingSubClausesBuilder builder = new SearchingSubClausesBuilder(searchParams);
		String string = builder.getClausesString();
		Map<String, String> map = configToParamsMap(string);
		assertEquals("-field1;+RANK", map.get("sort"));
	}

	@Test
	public void testBuildFilterClause() {
		SearchingSubClausesBuilder builder = new SearchingSubClausesBuilder(searchParams);
		String string = builder.getClausesString();
		Map<String, String> map = configToParamsMap(string);
		assertEquals("id=7 AND company_id=5", map.get("filter"));
	}

	@Test
	public void testBuildDistinctClause() {
		SearchingSubClausesBuilder builder = new SearchingSubClausesBuilder(searchParams);
		String string = builder.getClausesString();
		Map<String, String> map = configToParamsMap(string);
		String expectedStyle = "dist_key:key2,dist_count:1,dist_times:1,reserved:true,update_total_hit:false;dist_key:key1,dist_count:1,dist_times:1,reserved:true,update_total_hit:false,grade:5.2";

		Map<String, Map<String, String>> sss = Maps.newHashMap();
		List<String> toList = Splitter.on(";").splitToList(expectedStyle);
		for (String string2 : toList) {
			Map<String, String> expectedMap = Splitter.on(",").withKeyValueSeparator(":").split(string2);
			sss.put(expectedMap.get("dist_key"), expectedMap);
		}

		Map<String, Map<String, String>> ggg = Maps.newHashMap();
		String actual = map.get("distinct");
		List<String> toList2 = Splitter.on(";").splitToList(actual);
		for (String string2 : toList2) {
			Map<String, String> actualMap = Splitter.on(",").withKeyValueSeparator(":").split(string2);
			ggg.put(actualMap.get("dist_key"), actualMap);
		}
		assertEquals(sss, ggg);
	}

	@Test
	public void testBuildAggregateClause() {
		SearchingSubClausesBuilder builder = new SearchingSubClausesBuilder(searchParams);
		String string = builder.getClausesString();
		Map<String, String> map = configToParamsMap(string);
		String expectedStyle = "group_key:groupKey1";

		Map<String, Map<String, String>> sss = Maps.newHashMap();
		List<String> toList = Splitter.on(";").splitToList(expectedStyle);
		for (String string2 : toList) {
			Map<String, String> expectedMap = Splitter.on(",").withKeyValueSeparator(":").split(string2);
			sss.put(expectedMap.get("group_key"), expectedMap);
		}

		Map<String, Map<String, String>> ggg = Maps.newHashMap();
		String actual = map.get("aggregate");
		List<String> toList2 = Splitter.on(";").splitToList(actual);
		for (String string2 : toList2) {
			Map<String, String> actualMap = Splitter.on(",").withKeyValueSeparator(":").split(string2);
			ggg.put(actualMap.get("group_key"), actualMap);
		}
		assertEquals(sss, ggg);
	}

	private static Map<String, String> configToParamsMap(String configString) {
		List<String> list = Splitter.on("&&").splitToList(configString);
		Map<String, String> map = Maps.newHashMap();
		for (String kvstring : list) {
			String key = kvstring.substring(0, kvstring.indexOf("="));
			String value = kvstring.substring(kvstring.indexOf("=") + 1);
			map.put(key, value);
		}
		return map;
	}
}
