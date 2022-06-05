// This file is auto-generated, don't edit it. Thanks.

using System;
using System.Collections.Generic;
using System.IO;

using Tea;

namespace AlibabaCloud.SDK.OpenSearch.Models
{
    /**
     * 实际返回结果，包括查询耗时searchtime、引擎总结果数total、本次请求返回结果数num、本次查询最大返回结果数viewtotal、查询结果items、统计结果facet等信息
     */
    public class SearchResult : TeaModel {
        [NameInMap("searchtime")]
        [Validation(Required=false)]
        public double? Searchtime { get; set; }

        [NameInMap("total")]
        [Validation(Required=false)]
        public int? Total { get; set; }

        [NameInMap("viewtotal")]
        [Validation(Required=false)]
        public int? Viewtotal { get; set; }

        [NameInMap("num")]
        [Validation(Required=false)]
        public int? Num { get; set; }

        [NameInMap("items")]
        [Validation(Required=false)]
        public List<Dictionary<string, object>> Items { get; set; }

        [NameInMap("facet")]
        [Validation(Required=false)]
        public List<SearchResultFacet> Facet { get; set; }

    }

}
