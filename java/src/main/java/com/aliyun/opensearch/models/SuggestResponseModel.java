// This file is auto-generated, don't edit it. Thanks.
package com.aliyun.opensearch.models;

import com.aliyun.tea.*;

public class SuggestResponseModel extends TeaModel {
    @NameInMap("headers")
    public java.util.Map<String, String> headers;

    @NameInMap("body")
    @Validation(required = true)
    public SuggestionResponse body;

    public static SuggestResponseModel build(java.util.Map<String, ?> map) throws Exception {
        SuggestResponseModel self = new SuggestResponseModel();
        return TeaModel.build(map, self);
    }

}
