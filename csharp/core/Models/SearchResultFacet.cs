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
    public class SearchResultFacet : TeaModel {
        [NameInMap("key")]
        [Validation(Required=false)]
        public string Key { get; set; }

        [NameInMap("items")]
        [Validation(Required=false)]
        public List<SearchResultFacetItems> Items { get; set; }
        public class SearchResultFacetItems : TeaModel {
            [NameInMap("value")]
            [Validation(Required=false)]
            public string Value { get; set; }

            [NameInMap("count")]
            [Validation(Required=false)]
            public int? Count { get; set; }

        }

    }

}
