// This file is auto-generated, don't edit it. Thanks.
package com.aliyun.opensearch.models;

import com.aliyun.tea.*;

public class CollectDataResponseModel extends TeaModel {
    @NameInMap("headers")
    public java.util.Map<String, String> headers;

    @NameInMap("body")
    @Validation(required = true)
    public Response body;

    public static CollectDataResponseModel build(java.util.Map<String, ?> map) throws Exception {
        CollectDataResponseModel self = new CollectDataResponseModel();
        return TeaModel.build(map, self);
    }

}
