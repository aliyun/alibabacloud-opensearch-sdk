<?php

// This file is auto-generated, don't edit it. Thanks.

namespace AlibabaCloud\SDK\OpenSearch\Models;

use AlibabaCloud\Tea\Model;

class Behavior extends Model
{
    /**
     * @description 必选字段。定义该文档的操作行为，目前仅支持ADD
     *
     * @var string
     */
    public $cmd;

    /**
     * @description 必选字段。行为数据主体。
     *
     * @var array
     */
    public $fields;
    protected $_name = [
        'cmd'    => 'cmd',
        'fields' => 'fields',
    ];

    public function validate()
    {
        Model::validateRequired('cmd', $this->cmd, true);
        Model::validateRequired('fields', $this->fields, true);
    }

    public function toMap()
    {
        $res = [];
        if (null !== $this->cmd) {
            $res['cmd'] = $this->cmd;
        }
        if (null !== $this->fields) {
            $res['fields'] = $this->fields;
        }

        return $res;
    }

    /**
     * @param array $map
     *
     * @return Behavior
     */
    public static function fromMap($map = [])
    {
        $model = new self();
        if (isset($map['cmd'])) {
            $model->cmd = $map['cmd'];
        }
        if (isset($map['fields'])) {
            $model->fields = $map['fields'];
        }

        return $model;
    }
}
