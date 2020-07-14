// This file is auto-generated, don't edit it. Thanks.
package com.aliyun.opensearch.models;

import com.aliyun.tea.*;

public class SearchRequestModel extends TeaModel {
    @NameInMap("headers")
    public java.util.Map<String, String> headers;

    @NameInMap("query")
    @Validation(required = true)
    public SearchQuery query;

    public static SearchRequestModel build(java.util.Map<String, ?> map) throws Exception {
        SearchRequestModel self = new SearchRequestModel();
        return TeaModel.build(map, self);
    }

}
