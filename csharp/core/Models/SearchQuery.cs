// This file is auto-generated, don't edit it. Thanks.

using System;
using System.Collections.Generic;
using System.IO;

using Tea;

namespace AlibabaCloud.SDK.OpenSearch.Models
{
    public class SearchQuery : TeaModel {
        [NameInMap("query")]
        [Validation(Required=true)]
        public string Query { get; set; }

        [NameInMap("fetch_fields")]
        [Validation(Required=false)]
        public string FetchFields { get; set; }

        [NameInMap("qp")]
        [Validation(Required=false)]
        public string Qp { get; set; }

        [NameInMap("disable")]
        [Validation(Required=false)]
        public string Disable { get; set; }

        [NameInMap("first_rank_name")]
        [Validation(Required=false)]
        public string FirstRankName { get; set; }

        [NameInMap("second_rank_name")]
        [Validation(Required=false)]
        public string SecondRankName { get; set; }

        [NameInMap("user_id")]
        [Validation(Required=false)]
        public string UserId { get; set; }

        [NameInMap("abtest")]
        [Validation(Required=false)]
        public string Abtest { get; set; }

        [NameInMap("category_prediction")]
        [Validation(Required=false)]
        public string CategoryPrediction { get; set; }

        [NameInMap("raw_query")]
        [Validation(Required=false)]
        public string RawQuery { get; set; }

        [NameInMap("summary")]
        [Validation(Required=false)]
        public string Summary { get; set; }

    }

}
