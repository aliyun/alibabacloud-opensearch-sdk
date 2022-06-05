<?php

// This file is auto-generated, don't edit it. Thanks.

namespace AlibabaCloud\SDK\OpenSearch\Models;

use AlibabaCloud\SDK\OpenSearch\Models\SuggestionResponse\suggestions;
use AlibabaCloud\Tea\Model;

class SuggestionResponse extends Model
{
    /**
     * @var string
     */
    public $requestId;

    /**
     * @description 引擎查询耗时，单位为秒
     *
     * @var float
     */
    public $searchtime;

    /**
     * @var array
     */
    public $suggestions;

    /**
     * @var array
     */
    public $errors;
    protected $_name = [
        'requestId'   => 'request_id',
        'searchtime'  => 'searchtime',
        'suggestions' => 'suggestions',
        'errors'      => 'errors',
    ];

    public function validate()
    {
    }

    public function toMap()
    {
        $res = [];
        if (null !== $this->requestId) {
            $res['request_id'] = $this->requestId;
        }
        if (null !== $this->searchtime) {
            $res['searchtime'] = $this->searchtime;
        }
        if (null !== $this->suggestions) {
            $res['suggestions'] = [];
            if (null !== $this->suggestions && \is_array($this->suggestions)) {
                $n = 0;
                foreach ($this->suggestions as $item) {
                    $res['suggestions'][$n++] = null !== $item ? $item->toMap() : $item;
                }
            }
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
     * @return SuggestionResponse
     */
    public static function fromMap($map = [])
    {
        $model = new self();
        if (isset($map['request_id'])) {
            $model->requestId = $map['request_id'];
        }
        if (isset($map['searchtime'])) {
            $model->searchtime = $map['searchtime'];
        }
        if (isset($map['suggestions'])) {
            if (!empty($map['suggestions'])) {
                $model->suggestions = [];
                $n                  = 0;
                foreach ($map['suggestions'] as $item) {
                    $model->suggestions[$n++] = null !== $item ? suggestions::fromMap($item) : $item;
                }
            }
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
