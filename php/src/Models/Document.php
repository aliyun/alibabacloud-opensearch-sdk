<?php

// This file is auto-generated, don't edit it. Thanks.

namespace AlibabaCloud\SDK\OpenSearch\Models;

use AlibabaCloud\Tea\Model;

class Document extends Model
{
    /**
     * @description 必选字段。定义该文档的操作行为，可以为“add”、“update”、“delete”，标准版不支持“update”。建议一个请求中进行批量更新操作，提高网络交互及处理效率。“add”表示新增文档，如果该主键对应文档已经存在，则执行先“delete”再“add”的操作；“update”表示更新文档，对该主键对应文档进行部分字段更新；“delete”表示删除文档，如果该主键对应文档已经不存在，则认为删除成功。
     *
     * @var string
     */
    public $cmd;

    /**
     * @description 可选字段。用来记录文档实际发生时间，单位为毫秒。系统会用该时间戳来作为同一主键文档更新顺序的判断标准，该选项仅支持高级版，标准版应用，没有该timestamp选项，若指定该选项，推送会报4007错误码。在没有该timestamp项时，默认以文档发送到OpenSearch的时间作为文档更新时间进行操作。
     *
     * @var int
     */
    public $timestamp;

    /**
     * @description 必选字段。要操作的文档内容，主键字段必选，系统所有操作都是通过主键来进行的。对于“delete”只需要提供文档主键即可。
     *
     * @var array
     */
    public $fields;
    protected $_name = [
        'cmd'       => 'cmd',
        'timestamp' => 'timestamp',
        'fields'    => 'fields',
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
        if (null !== $this->timestamp) {
            $res['timestamp'] = $this->timestamp;
        }
        if (null !== $this->fields) {
            $res['fields'] = $this->fields;
        }

        return $res;
    }

    /**
     * @param array $map
     *
     * @return Document
     */
    public static function fromMap($map = [])
    {
        $model = new self();
        if (isset($map['cmd'])) {
            $model->cmd = $map['cmd'];
        }
        if (isset($map['timestamp'])) {
            $model->timestamp = $map['timestamp'];
        }
        if (isset($map['fields'])) {
            $model->fields = $map['fields'];
        }

        return $model;
    }
}
