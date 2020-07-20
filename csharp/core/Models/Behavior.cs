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
    public class Behavior : TeaModel {
        [NameInMap("cmd")]
        [Validation(Required=true)]
        public string Cmd { get; set; }

        [NameInMap("fields")]
        [Validation(Required=true)]
        public Dictionary<string, string> Fields { get; set; }

    }

}
