// This file is auto-generated, don't edit it. Thanks.

using System;
using System.Collections.Generic;
using System.IO;

using Tea;

namespace AlibabaCloud.SDK.OpenSearch.Models
{
    /**
     * 
     */
    public class SearchResultItemFullJson : TeaModel {
        [NameInMap("fields")]
        [Validation(Required=false)]
        public Dictionary<string, string> Fields { get; set; }

        [NameInMap("variableValue")]
        [Validation(Required=false)]
        public Dictionary<string, string> VariableValue { get; set; }

        [NameInMap("sortExprValues")]
        [Validation(Required=false)]
        public List<int?> SortExprValues { get; set; }

    }

}
