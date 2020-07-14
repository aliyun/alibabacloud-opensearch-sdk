// This file is auto-generated, don't edit it. Thanks.
package com.aliyun.opensearch.models;

import com.aliyun.tea.*;

public class SuggestRequestModel extends TeaModel {
    @NameInMap("headers")
    public java.util.Map<String, String> headers;

    @NameInMap("query")
    @Validation(required = true)
    public SuggestQuery query;

    public static SuggestRequestModel build(java.util.Map<String, ?> map) throws Exception {
        SuggestRequestModel self = new SuggestRequestModel();
        return TeaModel.build(map, self);
    }

}
