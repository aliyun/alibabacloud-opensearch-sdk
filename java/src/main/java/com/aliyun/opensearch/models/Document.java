// This file is auto-generated, don't edit it. Thanks.
package com.aliyun.opensearch.models;

import com.aliyun.tea.*;

/**
 * 
 */
public class Document extends TeaModel {
    @NameInMap("cmd")
    @Validation(required = true)
    public String cmd;

    @NameInMap("timestamp")
    public Integer timestamp;

    @NameInMap("fields")
    @Validation(required = true)
    public java.util.Map<String, ?> fields;

    public static Document build(java.util.Map<String, ?> map) throws Exception {
        Document self = new Document();
        return TeaModel.build(map, self);
    }

}
