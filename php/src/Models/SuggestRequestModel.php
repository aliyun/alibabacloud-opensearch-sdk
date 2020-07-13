<?php

// This file is auto-generated, don't edit it. Thanks.

namespace AlibabaCloud\SDK\OpenSearch\Models;

use AlibabaCloud\Tea\Model;

class SuggestRequestModel extends Model
{
    /**
     * @description headers
     *
     * @var array
     */
    public $headers;

    /**
     * @description query
     *
     * @var SuggestQuery
     */
    public $query;
    protected $_name = [
        'headers' => 'headers',
        'query'   => 'query',
    ];

    public function validate()
    {
        Model::validateRequired('query', $this->query, true);
    }

    public function toMap()
    {
        $res = [];
        if (null !== $this->headers) {
            $res['headers'] = $this->headers;
        }
        if (null !== $this->query) {
            $res['query'] = null !== $this->query ? $this->query->toMap() : null;
        }

        return $res;
    }

    /**
     * @param array $map
     *
     * @return SuggestRequestModel
     */
    public static function fromMap($map = [])
    {
        $model = new self();
        if (isset($map['headers'])) {
            $model->headers = $map['headers'];
        }
        if (isset($map['query'])) {
            $model->query = SuggestQuery::fromMap($map['query']);
        }

        return $model;
    }
}
