// This file is auto-generated, don't edit it. Thanks.
package com.aliyun.opensearch.models;

import com.aliyun.tea.*;

/**
 * 实际返回结果，包括查询耗时searchtime、引擎总结果数total、本次请求返回结果数num、本次查询最大返回结果数viewtotal、查询结果items、统计结果facet等信息
 */
public class SearchResult extends TeaModel {
    @NameInMap("searchtime")
    public Double searchtime;

    @NameInMap("total")
    public Integer total;

    @NameInMap("viewtotal")
    public Integer viewtotal;

    @NameInMap("num")
    public Integer num;

    @NameInMap("items")
    public java.util.List<java.util.Map<String, ?>> items;

    @NameInMap("facet")
    public java.util.List<SearchResultFacet> facet;

    public static SearchResult build(java.util.Map<String, ?> map) throws Exception {
        SearchResult self = new SearchResult();
        return TeaModel.build(map, self);
    }

}
