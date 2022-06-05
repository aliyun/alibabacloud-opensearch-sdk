// This file is auto-generated, don't edit it. Thanks.

using System;
using System.Collections.Generic;
using System.IO;

using Tea;

namespace AlibabaCloud.SDK.OpenSearch.Models
{
    /**
     * object_id=pk,object_type=ops_search_doc,ops_request_misc=xxx
     */
    public class Event2001Args : TeaModel {
        [NameInMap("object_id")]
        [Validation(Required=false)]
        public string ObjectId { get; set; }

        [NameInMap("object_type")]
        [Validation(Required=false)]
        public string ObjectType { get; set; }

        [NameInMap("ops_request_misc")]
        [Validation(Required=false)]
        public string OpsRequestMisc { get; set; }

    }

}
