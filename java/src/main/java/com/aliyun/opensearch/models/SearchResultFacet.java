// This file is auto-generated, don't edit it. Thanks.
package com.aliyun.opensearch.models;

import com.aliyun.tea.*;

/**
 * 
 */
public class SearchResultFacet extends TeaModel {
    @NameInMap("key")
    public String key;

    @NameInMap("items")
    public java.util.List<SearchResultFacetItems> items;

    public static SearchResultFacet build(java.util.Map<String, ?> map) throws Exception {
        SearchResultFacet self = new SearchResultFacet();
        return TeaModel.build(map, self);
    }

    public static class SearchResultFacetItems extends TeaModel {
        @NameInMap("value")
        public String value;

        @NameInMap("count")
        public Integer count;

        public static SearchResultFacetItems build(java.util.Map<String, ?> map) throws Exception {
            SearchResultFacetItems self = new SearchResultFacetItems();
            return TeaModel.build(map, self);
        }

    }

}
