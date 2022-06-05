// This file is auto-generated, don't edit it. Thanks.
package com.aliyun.opensearch.models;

import com.aliyun.tea.*;

/**
 * 
 */
public class Behavior extends TeaModel {
    @NameInMap("cmd")
    @Validation(required = true)
    public String cmd;

    @NameInMap("fields")
    @Validation(required = true)
    public java.util.Map<String, ?> fields;

    public static Behavior build(java.util.Map<String, ?> map) throws Exception {
        Behavior self = new Behavior();
        return TeaModel.build(map, self);
    }

}
