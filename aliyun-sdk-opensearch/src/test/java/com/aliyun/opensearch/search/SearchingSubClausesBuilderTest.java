package com.aliyun.opensearch.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.aliyun.opensearch.sdk.generated.search.*;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

/**
 *
 * @author Ken
 */
@RunWith(DataProviderRunner.class)
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



    @DataProvider
    public static Object[][] finalDistinctCases() {
        SearchParams searchParams = new SearchParams();
        SearchParams param0 = searchParams.deepCopy();
        String expected0 = null;

        FinalDistinct finalDistinct = new FinalDistinct();
        finalDistinct.setType(FinalDistinctType.FIELD_SHUFFLE);
        finalDistinct.setKeyList(new ArrayList<FinalDistinctKey>() {{
            add(new FinalDistinctKey("user_id", 2));
            add(new FinalDistinctKey("category_id", 1));
        }});
        SearchParams param1 = searchParams.deepCopy();
        FinalDistinct finalDistinct1 = finalDistinct.deepCopy();
        param1.setFinalDistinct(finalDistinct1);
        String expected1 = "final_distinct=dist_type:field_shuffle,dist_key:user_id;category_id"
            + ",dist_count:2;1";

        finalDistinct.setSort(new ArrayList<String>() {{
            add("sales");
            add("hot");
        }});
        SearchParams param2 = searchParams.deepCopy();
        FinalDistinct finalDistinct2 = finalDistinct.deepCopy();
        param2.setFinalDistinct(finalDistinct2);
        String expected2 = expected1 + ",dist_sort:sales;hot";

        finalDistinct.setSpecialCount(new HashMap<String, Integer>() {{
            put("1", 2);
            put("3", 2);
        }});
        SearchParams param3 = searchParams.deepCopy();
        FinalDistinct finalDistinct3 = finalDistinct.deepCopy();
        param3.setFinalDistinct(finalDistinct3);
        String expected3 = expected2 + ",dist_special_count:1@2|3@2";

        finalDistinct.setCustomFinalDistinct(new TreeMap<String, String>() {{
            put("dist_foo", "foo");
            put("dist_bar", "bar");
        }});
        searchParams.setFinalDistinct(finalDistinct);
        String expected = expected3 + ",dist_bar:bar,dist_foo:foo";

        return new Object[][] {
            {param0, expected0},
            {param1, expected1},
            {param2, expected2},
            {param3, expected3},
            {searchParams, expected}
        };
    }

    @Test
    @UseDataProvider("finalDistinctCases")
    public void getDefaultFinalDistinctClause(
        SearchParams searchParams, String expected) {
        SearchingSubClausesBuilder builder = new SearchingSubClausesBuilder(searchParams);
        String actual = builder.getDefaultFinalDistinctClause().orNull();
        assertEquals(expected, actual);
    }

    @Test
    public void getDefaultCustomClauses() {
        SearchingSubClausesBuilder builder = new SearchingSubClausesBuilder(searchParams);
        String actual = builder.getDefaultCustomClauses().orNull();
        assertNull(actual);

	    SearchParams searchParams = new SearchParams();
        searchParams.setCustomClause(new TreeMap<String, String>() {{
            put("clause_foo", "foo");
            put("clause_bar", "bar");
        }});

	    String expected = "clause_bar=bar&&clause_foo=foo";
	    builder = new SearchingSubClausesBuilder(searchParams);
	    actual = builder.getDefaultCustomClauses().orNull();
	    assertEquals(expected, actual);
    }
}
