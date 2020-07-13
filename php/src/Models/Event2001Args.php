<?php

// This file is auto-generated, don't edit it. Thanks.

namespace AlibabaCloud\SDK\OpenSearch\Models;

use AlibabaCloud\Tea\Model;

/**
 * object_id=pk,object_type=ops_search_doc,ops_request_misc=xxx.
 */
class Event2001Args extends Model
{
    /**
     * @description 被点击的对象的主键，不能为空，如果通过api上传需要urlencode
     *
     * @var string
     */
    public $objectId;

    /**
     * @description 必须为ops_search_doc
     *
     * @var string
     */
    public $objectType;

    /**
     * @description 开放搜索在搜索结果中返回的参数，只要原样设置即可。
     *
     * @var string
     */
    public $opsRequestMisc;
    protected $_name = [
        'objectId'       => 'object_id',
        'objectType'     => 'object_type',
        'opsRequestMisc' => 'ops_request_misc',
    ];
    protected $_default = [
        'objectType' => 'ops_search_doc',
    ];

    public function validate()
    {
    }

    public function toMap()
    {
        $res = [];
        if (null !== $this->objectId) {
            $res['object_id'] = $this->objectId;
        }
        if (null !== $this->objectType) {
            $res['object_type'] = $this->objectType;
        }
        if (null !== $this->opsRequestMisc) {
            $res['ops_request_misc'] = $this->opsRequestMisc;
        }

        return $res;
    }

    /**
     * @param array $map
     *
     * @return Event2001Args
     */
    public static function fromMap($map = [])
    {
        $model = new self();
        if (isset($map['object_id'])) {
            $model->objectId = $map['object_id'];
        }
        if (isset($map['object_type'])) {
            $model->objectType = $map['object_type'];
        }
        if (isset($map['ops_request_misc'])) {
            $model->opsRequestMisc = $map['ops_request_misc'];
        }

        return $model;
    }
}
