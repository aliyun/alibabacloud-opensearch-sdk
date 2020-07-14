// This file is auto-generated, don't edit it. Thanks.
package com.aliyun.opensearch.models;

import com.aliyun.tea.*;

/**
 * 
 */
public class SearchResultItemFullJson extends TeaModel {
    @NameInMap("fields")
    public java.util.Map<String, ?> fields;

    @NameInMap("variableValue")
    public java.util.Map<String, ?> variableValue;

    @NameInMap("sortExprValues")
    public java.util.List<Integer> sortExprValues;

    public static SearchResultItemFullJson build(java.util.Map<String, ?> map) throws Exception {
        SearchResultItemFullJson self = new SearchResultItemFullJson();
        return TeaModel.build(map, self);
    }

}
