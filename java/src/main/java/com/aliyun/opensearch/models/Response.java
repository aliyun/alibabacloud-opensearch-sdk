// This file is auto-generated, don't edit it. Thanks.
package com.aliyun.opensearch.models;

import com.aliyun.tea.*;

/**
 * 
 */
public class Response extends TeaModel {
    @NameInMap("status")
    public String status;

    @NameInMap("request_id")
    public String requestId;

    @NameInMap("errors")
    public java.util.List<Error> errors;

    public static Response build(java.util.Map<String, ?> map) throws Exception {
        Response self = new Response();
        return TeaModel.build(map, self);
    }

    public static class SuggestionResponseSuggestions extends TeaModel {
        @NameInMap("suggestion")
        public String suggestion;

        public static SuggestionResponseSuggestions build(java.util.Map<String, ?> map) throws Exception {
            SuggestionResponseSuggestions self = new SuggestionResponseSuggestions();
            return TeaModel.build(map, self);
        }

    }

}
