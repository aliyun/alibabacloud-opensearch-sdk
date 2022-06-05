// This file is auto-generated, don't edit it. Thanks.
package com.aliyun.opensearch.models;

import com.aliyun.tea.*;

public class SearchResponseModel extends TeaModel {
    @NameInMap("headers")
    public java.util.Map<String, String> headers;

    @NameInMap("body")
    @Validation(required = true)
    public SearchResponse body;

    public static SearchResponseModel build(java.util.Map<String, ?> map) throws Exception {
        SearchResponseModel self = new SearchResponseModel();
        return TeaModel.build(map, self);
    }

}
