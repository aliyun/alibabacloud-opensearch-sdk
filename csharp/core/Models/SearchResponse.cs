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
    public class SearchResponse : TeaModel {
        [NameInMap("status")]
        [Validation(Required=false)]
        public string Status { get; set; }

        [NameInMap("request_id")]
        [Validation(Required=false)]
        public string RequestId { get; set; }

        [NameInMap("result")]
        [Validation(Required=false)]
        public SearchResult Result { get; set; }

        [NameInMap("errors")]
        [Validation(Required=false)]
        public List<Error> Errors { get; set; }

    }

}
