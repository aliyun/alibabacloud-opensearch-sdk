// This file is auto-generated, don't edit it. Thanks.
package com.aliyun.opensearch.models;

import com.aliyun.tea.*;

/**
 * 
 */
public class SearchResponse extends TeaModel {
    @NameInMap("status")
    public String status;

    @NameInMap("request_id")
    public String requestId;

    @NameInMap("result")
    public SearchResult result;

    @NameInMap("errors")
    public java.util.List<Error> errors;

    public static SearchResponse build(java.util.Map<String, ?> map) throws Exception {
        SearchResponse self = new SearchResponse();
        return TeaModel.build(map, self);
    }

}
