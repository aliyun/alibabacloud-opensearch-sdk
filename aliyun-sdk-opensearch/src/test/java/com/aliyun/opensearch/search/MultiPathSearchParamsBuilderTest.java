package com.aliyun.opensearch.search;

import com.aliyun.opensearch.sdk.generated.search.*;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class MultiPathSearchParamsBuilderTest {

    @Test
    public void test() {
        // 构建MultiPathSearchParams对象
        MultiPathSearchParams multiPathSearchParams = new MultiPathSearchParams();

        // 构建MultiSearchConfig
        MultiSearchConfig multiSearchConfig = new MultiSearchConfig();
        multiSearchConfig.setRawQuery("test query");
        multiSearchConfig.setStart(0);
        multiSearchConfig.setHit(20);
        multiSearchConfig.setFormat(SearchFormat.FULLJSON);
        multiSearchConfig.setFetchFields(Arrays.asList("field1", "field2", "field3"));
        multiSearchConfig.setUnifiedRankSize(100);
        multiSearchConfig.setUnifiedRankType(UnifiedRankType.RRF);
        multiSearchConfig.setUnifiedRankName("test_rank");
        multiSearchConfig.setUserId("test_user_id");

        // 设置vectorSearch参数
        Map<String, Map<String, TValue>> vectorSearch = new HashMap<>();

        Map<String, TValue> vectorParam = new HashMap<>();
        vectorParam.put("top_n", TValue.intValue(50));
        vectorParam.put("threshold",TValue.doubleValue( 0.8D));
        vectorSearch.put("test_vector", vectorParam);

        multiSearchConfig.setVectorSearch(vectorSearch);

        multiSearchConfig.setTrace("debug");
        multiSearchConfig.setRankTrace("info");

        // 设置搜索配置
        multiPathSearchParams.setMultiSearchConfig(multiSearchConfig);

        // 构建搜索条件
        List<MultiSearchConditionItem> searchConditionItems = new ArrayList<>();

        // 添加一个搜索条件
        MultiSearchConditionItem item1 = new MultiSearchConditionItem();
        item1.setPath("main");
        item1.setPriority(1);
        item1.setQuota(50);
        item1.setQuery("default:'test'");
        item1.setFilter("filter_condition");
        item1.setSort("+id");
        item1.setFirstRankName("first_rank_test");
        item1.setSecondRankName("second_rank_test");
        item1.setTotalRankSize(200);
        item1.setTotalRerankSize(100);
        item1.setQp("qp_test");

        // 设置kvpairs参数
        Map<String, String> kvpairs = new HashMap<>();
        kvpairs.put("key1", "value1");
        kvpairs.put("key2", "value2");
        item1.setKvpairs(kvpairs);

        searchConditionItems.add(item1);

        // 设置搜索条件
        MultiSearchCondition multiSearchCondition = new MultiSearchCondition();
        multiSearchCondition.setSearchConditionItems(searchConditionItems);
        multiPathSearchParams.setMultiSearchCondition(multiSearchCondition);

        MultiPathSearchParamsBuilder builder = new MultiPathSearchParamsBuilder(multiPathSearchParams);

        // 验证httpParams内容是否包含必要的参数
        Map<String, Object> httpParams = builder.getHttpParams();
        assertEquals("test query", httpParams.get("raw_query"));
        assertEquals(0, httpParams.get("start"));
        assertEquals(20, httpParams.get("hit"));
        assertEquals("fulljson", httpParams.get("format"));
        assertEquals("field1;field2;field3", httpParams.get("fetch_fields"));
        assertEquals(100, httpParams.get("unified_rank_size"));
        assertEquals("rrf", httpParams.get("unified_rank_type"));
        assertEquals("test_rank", httpParams.get("unified_rank_name"));
        assertEquals("test_user_id", httpParams.get("user_id"));
        assertEquals("debug", httpParams.get("trace"));
        assertEquals("info", httpParams.get("rank_trace"));

        // 验证vector_search参数
        Map<String, Object> vector_search = (Map<String, Object>)httpParams.get("vector_search");
        Map<String, Object> test_vector = (Map<String, Object>)vector_search.get("test_vector");
        assertEquals(Integer.valueOf(50), (Integer)test_vector.get("top_n"));
        assertEquals(Double.valueOf(0.8), (Double) test_vector.get("threshold"));

        // 验证queries部分
        List<Map<String, Object>> queries = (List<Map<String, Object>>) httpParams.get("queries");
        assertNotNull(queries);
        assertEquals(1, queries.size());

        Map<String, Object> queryItem = queries.get(0);
        assertEquals("main", queryItem.get("path"));
        assertEquals(1, queryItem.get("priority"));
        assertEquals(50, queryItem.get("quota"));
        assertEquals("default:'test'", queryItem.get("query"));
        assertEquals("filter_condition", queryItem.get("filter"));
        assertEquals("+id", queryItem.get("sort"));
        assertEquals("first_rank_test", queryItem.get("first_rank_name"));
        assertEquals("second_rank_test", queryItem.get("second_rank_name"));
        assertEquals(200, queryItem.get("total_rank_size"));
        assertEquals(100, queryItem.get("total_rerank_size"));
        assertEquals("qp_test", queryItem.get("qp"));

        Map<String, String> returnedKvpairs = (Map<String, String>) queryItem.get("kvpairs");
        assertNotNull(returnedKvpairs);
        assertEquals("value1", returnedKvpairs.get("key1"));
        assertEquals("value2", returnedKvpairs.get("key2"));
    }
}

