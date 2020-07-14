// This file is auto-generated, don't edit it. Thanks.
package com.aliyun.opensearch.models;

import com.aliyun.tea.*;

public class SearchQuery extends TeaModel {
    @NameInMap("query")
    @Validation(required = true)
    public String query;

    @NameInMap("fetch_fields")
    public String fetchFields;

    @NameInMap("qp")
    public String qp;

    @NameInMap("disable")
    public String disable;

    @NameInMap("first_rank_name")
    public String firstRankName;

    @NameInMap("second_rank_name")
    public String secondRankName;

    @NameInMap("user_id")
    public String userId;

    @NameInMap("abtest")
    public String abtest;

    @NameInMap("category_prediction")
    public String categoryPrediction;

    @NameInMap("raw_query")
    public String rawQuery;

    @NameInMap("summary")
    public String summary;

    public static SearchQuery build(java.util.Map<String, ?> map) throws Exception {
        SearchQuery self = new SearchQuery();
        return TeaModel.build(map, self);
    }

}
