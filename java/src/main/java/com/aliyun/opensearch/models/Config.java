// This file is auto-generated, don't edit it. Thanks.
package com.aliyun.opensearch.models;

import com.aliyun.tea.*;

public class Config extends TeaModel {
    @NameInMap("endpoint")
    public String endpoint;

    @NameInMap("protocol")
    public String protocol;

    @NameInMap("type")
    public String type;

    @NameInMap("securityToken")
    public String securityToken;

    @NameInMap("accessKeyId")
    public String accessKeyId;

    @NameInMap("accessKeySecret")
    public String accessKeySecret;

    @NameInMap("userAgent")
    public String userAgent;

    public static Config build(java.util.Map<String, ?> map) throws Exception {
        Config self = new Config();
        return TeaModel.build(map, self);
    }

}
