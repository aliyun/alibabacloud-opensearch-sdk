// This file is auto-generated, don't edit it. Thanks.
package com.aliyun.opensearch.models;

import com.aliyun.tea.*;

public class CollectDataRequestModel extends TeaModel {
    @NameInMap("headers")
    public java.util.Map<String, String> headers;

    @NameInMap("body")
    @Validation(required = true)
    public java.util.List<Behavior> body;

    public static CollectDataRequestModel build(java.util.Map<String, ?> map) throws Exception {
        CollectDataRequestModel self = new CollectDataRequestModel();
        return TeaModel.build(map, self);
    }

}
