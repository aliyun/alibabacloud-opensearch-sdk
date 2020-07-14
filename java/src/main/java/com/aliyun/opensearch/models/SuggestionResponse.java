// This file is auto-generated, don't edit it. Thanks.
package com.aliyun.opensearch.models;

import com.aliyun.tea.*;

/**
 * 
 */
public class SuggestionResponse extends TeaModel {
    @NameInMap("request_id")
    public String requestId;

    @NameInMap("searchtime")
    public Double searchtime;

    @NameInMap("suggestions")
    public java.util.List<SuggestionResponseSuggestions> suggestions;

    @NameInMap("errors")
    public java.util.List<Error> errors;

    public static SuggestionResponse build(java.util.Map<String, ?> map) throws Exception {
        SuggestionResponse self = new SuggestionResponse();
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
