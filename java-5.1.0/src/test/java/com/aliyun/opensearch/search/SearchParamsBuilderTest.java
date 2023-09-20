package com.aliyun.opensearch.search;

import static org.junit.Assert.*;

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
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class SearchParamsBuilderTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddSummary() {
		Config config = new Config(Lists.newArrayList("appname1"));
		config.setSearchFormat(SearchFormat.JSON);
		config.setKvpairs("name:company_name,price:new_price,title:short_title");
		config.setFetchFields(Lists.newArrayList("field1", "field2"));
		SearchParams searchParams = new SearchParams(config);

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
		SearchParamsBuilder paramsBuilder = SearchParamsBuilder.create(searchParams);
		paramsBuilder.addSummary("fieldName1", 3, "element1", "ellipsis1", 2);

		paramsBuilder.addFilter("group_id=3", "OR");
		paramsBuilder.addFilter("log_id=6", "AND");

		System.out.println(paramsBuilder);

	}

}
