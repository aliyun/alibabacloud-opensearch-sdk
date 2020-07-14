// This file is auto-generated, don't edit it. Thanks.
package com.aliyun.opensearch.models;

import com.aliyun.tea.*;

public class SuggestQuery extends TeaModel {
    @NameInMap("query")
    @Validation(required = true)
    public String query;

    @NameInMap("hit")
    public Integer hit;

    public static SuggestQuery build(java.util.Map<String, ?> map) throws Exception {
        SuggestQuery self = new SuggestQuery();
        return TeaModel.build(map, self);
    }

}
