// This file is auto-generated, don't edit it. Thanks.
package com.aliyun.opensearch.models;

import com.aliyun.tea.*;

public class PushDocumentRequestModel extends TeaModel {
    @NameInMap("headers")
    public java.util.Map<String, String> headers;

    @NameInMap("body")
    @Validation(required = true)
    public java.util.List<Document> body;

    public static PushDocumentRequestModel build(java.util.Map<String, ?> map) throws Exception {
        PushDocumentRequestModel self = new PushDocumentRequestModel();
        return TeaModel.build(map, self);
    }

}
