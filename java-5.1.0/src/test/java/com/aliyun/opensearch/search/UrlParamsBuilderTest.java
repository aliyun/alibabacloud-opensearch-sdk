package com.aliyun.opensearch.search;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aliyun.opensearch.sdk.generated.search.*;
import org.apache.commons.lang.StringUtils;
import org.apache.http.params.BasicHttpParams;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public class UrlParamsBuilderTest {
	private SearchParams searchParams;

	@Before
	public void setUp() throws Exception {
		Config config = new Config(Lists.newArrayList("appname1"));
		config.setSearchFormat(SearchFormat.JSON);
		config.setKvpairs("name:company_name,price:new_price,title:short_title");
		config.setFetchFields(Lists.newArrayList("field1", "field2"));

        searchParams = new SearchParams(config);

        Sort sorter = new Sort();
		sorter.addToSortFields(new SortField("field1", Order.DECREASE));
		sorter.addToSortFields(new SortField("RANK", Order.INCREASE));
		searchParams.setSort(sorter);

        Map<String, Distinct> distincts = Maps.newHashMap();
		distincts.put("key1", new Distinct("key1"));
		distincts.put("key2", new Distinct("key2"));
		searchParams.setDistincts(Sets.newLinkedHashSet(distincts.values()));

        Map<String, Aggregate> aggregates = Maps.newHashMap();
		aggregates.put("key1", new Aggregate("groupKey1"));
		searchParams.setAggregates(Sets.newLinkedHashSet(aggregates.values()));

        searchParams.setFilter("id=7 AND company_id=5");

        Map<String, Summary> summaries = Maps.newHashMap();
		Summary summary1 = new Summary("summary_field1");
		summary1.setSummary_len("10");
		summary1.setSummary_snippet("20");

        Summary summary2 = new Summary("summary_field2");
		summary2.setSummary_element("");
		summaries.put("summary1", summary1);
		summaries.put("summary2", summary2);

        searchParams.setSummaries(Sets.newLinkedHashSet(summaries.values()));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testQuery() {

		UrlParamsBuilder builder = new UrlParamsBuilder(searchParams);
		System.out.println(builder);
		BasicHttpParams httpParams = builder.getHttpParams();
		String query = (String) httpParams.getParameter("query");

		Iterable<String> parts = Splitter.on("&&").split(query);

		Map<String, String> partsMap = Maps.newHashMap();
		for (String string : parts) {
			List<String> splitToList = Splitter.on("=").splitToList(string);
			partsMap.put(splitToList.get(0), splitToList.get(1));
		}

		Set<String> keySet = partsMap.keySet();
		assertTrue(keySet.contains("config"));
		assertTrue(keySet.contains("query"));
		assertTrue(keySet.contains("sort"));
		assertTrue(keySet.contains("filter"));
		assertTrue(keySet.contains("distinct"));
		assertTrue(keySet.contains("aggregate"));

		assertTrue(StringUtils.contains(partsMap.get("query"), "''"));
		assertTrue(StringUtils.contains(partsMap.get("sort"), "-field1;+RANK"));
//		assertTrue(StringUtils.contains(partsMap.get("filter"), "id=7 AND company_id=5"));
        // System.out.println("partsMap.get(\"distinct\"): " + partsMap.get("distinct"));

        assertTrue(StringUtils.contains(
            partsMap.get("distinct"),
            "dist_key:key1,dist_count:1,dist_times:1,reserved:true,update_total_hit:false;dist_key:key2,dist_count:1,dist_times:1,reserved:true,update_total_hit:false"
        ));

		assertTrue(StringUtils.contains(partsMap.get("aggregate"), "group_key:groupKey1"));
	}

	@Test
	public void testSetSummary() {
		UrlParamsBuilder builder = new UrlParamsBuilder(searchParams);
		System.out.println(builder);

		String expectedStyle = "summary_field:summary_field1,summary_len:10,summary_ellipsis:...,summary_snippet:20;summary_field:summary_field2,summary_ellipsis:...,summary_element:";

		Map<String, Map<String, String>> sss = Maps.newHashMap();
		List<String> toList = Splitter.on(";").splitToList(expectedStyle);
		for (String string2 : toList) {
			Map<String, String> expectedMap = Splitter.on(",").withKeyValueSeparator(":").split(string2);
			sss.put(expectedMap.get("summary_field"), expectedMap);
		}
		System.out.println(sss);
		Map<String, Map<String, String>> ggg = Maps.newHashMap();
		String actual = (String) builder.getHttpParams().getParameter("summary");
		List<String> toList2 = Splitter.on(";").splitToList(actual);
		for (String string2 : toList2) {
			Map<String, String> actualMap = Splitter.on(",").withKeyValueSeparator(":").split(string2);
			ggg.put(actualMap.get("summary_field"), actualMap);
		}
		System.out.println(ggg);
		assertEquals(sss, ggg);
	}

    /**
     * 测试查询增加 abtest 参数
     */
    @Test
    public void testSetAbtest() {
        Abtest abtest = new Abtest();
        abtest.setSceneTag("mz");
        abtest.setFlowDivider("1001");
        searchParams.setAbtest(abtest);

        UrlParamsBuilder builder = new UrlParamsBuilder(searchParams);

        String expected = "scene_tag:mz,flow_divider:1001";
        String actual = (String)builder.getHttpParams().getParameter("abtest");

        assertEquals(expected, actual);
    }

    /**
     * 测试查询增加 user_id 参数
     */
    @Test
    public void testSetUserId() {
        String userId = "2001";

        searchParams.setUserId(userId);

        UrlParamsBuilder builder = new UrlParamsBuilder(searchParams);

        String expected = "2001";
        String actual = (String)builder.getHttpParams().getParameter("user_id");

        assertEquals(expected, actual);
    }

    /**
     * 测试查询增加 raw_query 参数
     */
    @Test
    public void testSetRawQuery() {
        String rawQuery = "连衣裙";

        searchParams.setRawQuery(rawQuery);

        UrlParamsBuilder builder = new UrlParamsBuilder(searchParams);

        String expected = "连衣裙";
        String actual = (String)builder.getHttpParams().getParameter("raw_query");

        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] secondRankTypeList() {
        return new Object[][] {
            {RankType.CAVA_SCRIPT, "cava_script"},
            {RankType.EXPRESSION, "expression"},
            {null, null}
        };
    }

    @Test
    @UseDataProvider("secondRankTypeList")
    public void secondRankType(RankType rankType, String expected) {
        Rank rank = searchParams.getRank();
        rank.setSecondRankType(rankType);

        searchParams.setRank(rank);

        UrlParamsBuilder builder = new UrlParamsBuilder(searchParams);

        String actual = (String)builder.getHttpParams().getParameter("second_rank_type");

        assertEquals(expected, actual);
    }

}
