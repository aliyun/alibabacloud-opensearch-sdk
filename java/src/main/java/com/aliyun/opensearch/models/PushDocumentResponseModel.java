// This file is auto-generated, don't edit it. Thanks.
package com.aliyun.opensearch.models;

import com.aliyun.tea.*;

public class PushDocumentResponseModel extends TeaModel {
    @NameInMap("headers")
    public java.util.Map<String, String> headers;

    @NameInMap("body")
    @Validation(required = true)
    public Response body;

    public static PushDocumentResponseModel build(java.util.Map<String, ?> map) throws Exception {
        PushDocumentResponseModel self = new PushDocumentResponseModel();
        return TeaModel.build(map, self);
    }

}
