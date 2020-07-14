// This file is auto-generated, don't edit it. Thanks.
package com.aliyun.opensearch.models;

import com.aliyun.tea.*;

/**
 * object_id=pk,object_type=ops_search_doc,ops_request_misc=xxx
 */
public class Event2001Args extends TeaModel {
    @NameInMap("object_id")
    public String objectId;

    @NameInMap("object_type")
    public String objectType;

    @NameInMap("ops_request_misc")
    public String opsRequestMisc;

    public static Event2001Args build(java.util.Map<String, ?> map) throws Exception {
        Event2001Args self = new Event2001Args();
        return TeaModel.build(map, self);
    }

}
