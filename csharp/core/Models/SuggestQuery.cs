// This file is auto-generated, don't edit it. Thanks.

using System;
using System.Collections.Generic;
using System.IO;

using Tea;

namespace AlibabaCloud.SDK.OpenSearch.Models
{
    public class SuggestQuery : TeaModel {
        [NameInMap("query")]
        [Validation(Required=true)]
        public string Query { get; set; }

        [NameInMap("hit")]
        [Validation(Required=false)]
        public int? Hit { get; set; }

    }

}
