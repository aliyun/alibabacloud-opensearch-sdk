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
    public class SuggestionResponse : TeaModel {
        [NameInMap("request_id")]
        [Validation(Required=false)]
        public string RequestId { get; set; }

        [NameInMap("searchtime")]
        [Validation(Required=false)]
        public double? Searchtime { get; set; }

        [NameInMap("suggestions")]
        [Validation(Required=false)]
        public List<SuggestionResponseSuggestions> Suggestions { get; set; }
        public class SuggestionResponseSuggestions : TeaModel {
            [NameInMap("suggestion")]
            [Validation(Required=false)]
            public string Suggestion { get; set; }

        }

        [NameInMap("errors")]
        [Validation(Required=false)]
        public List<Error> Errors { get; set; }

    }

}
