<?php

// This file is auto-generated, don't edit it. Thanks.

namespace AlibabaCloud\SDK\OpenSearch\Models;

use AlibabaCloud\Tea\Model;

class SearchResponse extends Model
{
    /**
     * @var string
     */
    public $status;

    /**
     * @var string
     */
    public $requestId;

    /**
     * @var SearchResult
     */
    public $result;

    /**
     * @var array
     */
    public $errors;
    protected $_name = [
        'status'    => 'status',
        'requestId' => 'request_id',
        'result'    => 'result',
        'errors'    => 'errors',
    ];

    public function validate()
    {
    }

    public function toMap()
    {
        $res = [];
        if (null !== $this->status) {
            $res['status'] = $this->status;
        }
        if (null !== $this->requestId) {
            $res['request_id'] = $this->requestId;
        }
        if (null !== $this->result) {
            $res['result'] = null !== $this->result ? $this->result->toMap() : null;
        }
        if (null !== $this->errors) {
            $res['errors'] = [];
            if (null !== $this->errors && \is_array($this->errors)) {
                $n = 0;
                foreach ($this->errors as $item) {
                    $res['errors'][$n++] = null !== $item ? $item->toMap() : $item;
                }
            }
        }

        return $res;
    }

    /**
     * @param array $map
     *
     * @return SearchResponse
     */
    public static function fromMap($map = [])
    {
        $model = new self();
        if (isset($map['status'])) {
            $model->status = $map['status'];
        }
        if (isset($map['request_id'])) {
            $model->requestId = $map['request_id'];
        }
        if (isset($map['result'])) {
            $model->result = SearchResult::fromMap($map['result']);
        }
        if (isset($map['errors'])) {
            if (!empty($map['errors'])) {
                $model->errors = [];
                $n             = 0;
                foreach ($map['errors'] as $item) {
                    $model->errors[$n++] = null !== $item ? Error::fromMap($item) : $item;
                }
            }
        }

        return $model;
    }
}
