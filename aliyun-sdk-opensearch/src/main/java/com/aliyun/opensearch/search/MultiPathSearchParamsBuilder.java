package com.aliyun.opensearch.search;

import com.aliyun.opensearch.sdk.generated.search.MultiPathSearchParams;
import com.aliyun.opensearch.sdk.generated.search.MultiSearchCondition;
import com.aliyun.opensearch.sdk.generated.search.MultiSearchConfig;
import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import static com.aliyun.opensearch.sdk.generated.search.OpenSearchSearcherConstants.*;

public class MultiPathSearchParamsBuilder {
    private Map<String, Object> httpParams;

    public MultiPathSearchParamsBuilder() {
        this.httpParams = new HashMap<>(64);;
    }

    public MultiPathSearchParamsBuilder(MultiPathSearchParams multiPathSearchParams) {
        this();
        init(multiPathSearchParams);
    }

    private void init(MultiPathSearchParams multiPathSearchParams) {
        if (multiPathSearchParams.isSetMultiSearchCondition()) {
            initQueries(multiPathSearchParams.getMultiSearchCondition());
        }

        if (multiPathSearchParams.isSetMultiSearchConfig()) {
            initRawQuery(multiPathSearchParams.getMultiSearchConfig());
            initStart(multiPathSearchParams.getMultiSearchConfig());
            initHit(multiPathSearchParams.getMultiSearchConfig());
            initFormat(multiPathSearchParams.getMultiSearchConfig());
            initFetchFields(multiPathSearchParams.getMultiSearchConfig());
            initUnifiedRank(multiPathSearchParams.getMultiSearchConfig());
            initVectorSearch(multiPathSearchParams.getMultiSearchConfig());
            initUserId(multiPathSearchParams.getMultiSearchConfig());
            initTrace(multiPathSearchParams.getMultiSearchConfig());
            initRankTrace(multiPathSearchParams.getMultiSearchConfig());
        }
    }

    private void initRankTrace(MultiSearchConfig multiSearchConfig) {
        if (multiSearchConfig.isSetRankTrace()) {
            httpParams.put(RANK_TRACE, multiSearchConfig.getRankTrace());
        }
    }

    private void initTrace(MultiSearchConfig multiSearchConfig) {
        if (multiSearchConfig.isSetTrace()) {
            httpParams.put(TRACE, multiSearchConfig.getTrace());
        }
    }

    private void initUserId(MultiSearchConfig multiSearchConfig) {
        if (multiSearchConfig.isSetUserId()) {
            httpParams.put(USER_ID, multiSearchConfig.getUserId());
        }
    }

    private void initVectorSearch(MultiSearchConfig multiSearchConfig) {
        if (multiSearchConfig.isSetVectorSearch()) {
            Map<String, Object> resultMap = new HashMap<>();
            multiSearchConfig.getVectorSearch().forEach((vectorKey, vectorValue) -> {
                Map<String, Object> convertedMap = new HashMap<>();
                vectorValue.forEach((paramKey, paramValue) -> {
                    convertedMap.put(paramKey, convertTValueToObject(paramValue));
                });
                resultMap.put(vectorKey, convertedMap);
            });
            httpParams.put(VECTOR_SEARCH, resultMap);
        }
    }

    /**
     * 将 TValue 对象转换为相应的 Java 对象
     * @param tValue 需要转换的 TValue 对象
     * @return 转换后的 Java 对象
     */
    private Object convertTValueToObject(com.aliyun.opensearch.sdk.generated.search.TValue tValue) {
        switch (tValue.getSetField()) {
            case INT_VALUE:
                return tValue.getIntValue();
            case DOUBLE_VALUE:
                return tValue.getDoubleValue();
            case STRING_VALUE:
                return tValue.getStringValue();
            case BOOL_VALUE:
                return tValue.getBoolValue();
            case MAP_VALUE:
                // 递归转换嵌套的 Map
                Map<String, Object> nestedMap = new HashMap<>();
                tValue.getMapValue().forEach((key, value) -> {
                    nestedMap.put(key, convertTValueToObject(value));
                });
                return nestedMap;
            case STRING_LIST:
                return tValue.getStringList();
            case TVALUE_LIST:
                // 转换 TValue 列表
                List<Object> list = new ArrayList<>();
                tValue.getTvalueList().forEach(item -> {
                    list.add(convertTValueToObject(item));
                });
                return list;
            default:
                // 对于未处理的类型，转换为字符串表示
                return tValue.toString();
        }
    }

    private void initStart(MultiSearchConfig multiSearchConfig) {
        if (multiSearchConfig.isSetStart()) {
            httpParams.put(CONFIG_CLAUSE_START, multiSearchConfig.getStart());
        }
    }

    private void initQueries(MultiSearchCondition multiSearchCondition) {
        if (multiSearchCondition.isSetSearchConditionItems()) {
            List<Map<String, Object>> queriesList = multiSearchCondition.getSearchConditionItems().stream()
                .map(this::buildQueryMap)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
            httpParams.put(QUERIES, queriesList);
        }
    }

    private Map<String, Object> buildQueryMap(com.aliyun.opensearch.sdk.generated.search.MultiSearchConditionItem item) {
        Map<String, Object> queryMap = new HashMap<>();

        // 处理所有可选字段
        addToMapIfSet(queryMap, QUERY, item::isSetQuery, item::getQuery);
        addToMapIfSet(queryMap, FILTER, item::isSetFilter, item::getFilter);
        addToMapIfSet(queryMap, SORT, item::isSetSort, item::getSort);
        addToMapIfSet(queryMap, FIRST_RANK_NAME, item::isSetFirstRankName, item::getFirstRankName);
        addToMapIfSet(queryMap, SECOND_RANK_NAME, item::isSetSecondRankName, item::getSecondRankName);
        addToMapIfSet(queryMap, TOTAL_RANK_SIZE, item::isSetTotalRankSize, item::getTotalRankSize);
        addToMapIfSet(queryMap, TOTAL_RERANK_SIZE, item::isSetTotalRerankSize, item::getTotalRerankSize);
        addToMapIfSet(queryMap, QP, item::isSetQp, item::getQp);
        addToMapIfSet(queryMap, KVPAIRS, item::isSetKvpairs, item::getKvpairs);
        addToMapIfSet(queryMap, PATH, item::isSetPath, item::getPath);

        // 添加必填字段
        queryMap.put("priority", item.getPriority());
        queryMap.put("quota", item.getQuota());

        return queryMap;
    }

    /**
     * 如果字段已设置，则将其添加到Map中
     * @param map 要添加到的Map
     * @param key 键名
     * @param isSetMethod 判断字段是否已设置的方法引用
     * @param getValueMethod 获取字段值的方法引用
     * @param <T> 字段值的类型
     */
    private <T> void addToMapIfSet(Map<String, Object> map, String key,
                                  BooleanSupplier isSetMethod, Supplier<T> getValueMethod) {
        if (isSetMethod.getAsBoolean()) {
            map.put(key, getValueMethod.get());
        }
    }

    private void initRawQuery(MultiSearchConfig multiSearchConfig) {
        if (multiSearchConfig.isSetRawQuery()) {
            httpParams.put(RAW_QUERY, multiSearchConfig.getRawQuery());
        }
    }
    private void initUnifiedRank(MultiSearchConfig multiSearchConfig) {
        if (multiSearchConfig.isSetUnifiedRankName()) {
            httpParams.put(UNIFIED_RANK_NAME, multiSearchConfig.getUnifiedRankName());
        }

        if (multiSearchConfig.isSetUnifiedRankType()) {
            httpParams.put(UNIFIED_RANK_TYPE, multiSearchConfig.getUnifiedRankType().toString().toLowerCase());
        }

        if (multiSearchConfig.isSetUnifiedRankSize()) {
            httpParams.put(UNIFIED_RANK_SIZE, multiSearchConfig.getUnifiedRankSize());
        }
    }

    private void initFetchFields(MultiSearchConfig multiSearchConfig) {
        if (multiSearchConfig.isSetFetchFields()) {
            httpParams.put(FETCH_FIELDS, Joiner.on(VALUE_SPLITTER_DISABLEFUNCTIONS).join(multiSearchConfig.getFetchFields()));
        }
    }

    private void initFormat(MultiSearchConfig multiSearchConfig) {
        if (multiSearchConfig.isSetFormat()) {
            httpParams.put(FORMAT_PARAM, multiSearchConfig.getFormat().toString().toLowerCase());
        }
    }

    private void initHit(MultiSearchConfig multiSearchConfig) {
        if (multiSearchConfig.isSetHit()) {
            httpParams.put(CONFIG_CLAUSE_HIT, multiSearchConfig.getHit());
        }
    }

    public Map<String, Object> getHttpParams() {
        return httpParams;
    }
}

