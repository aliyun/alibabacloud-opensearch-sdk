<?php

// This file is auto-generated, don't edit it. Thanks.

namespace AlibabaCloud\SDK\OpenSearch\Models;

use AlibabaCloud\Tea\Model;

class SuggestQuery extends Model
{
    /**
     * @description 搜索关键词（包含中文需进行urlencode编码）
     *
     * @var string
     */
    public $query;

    /**
     * @description 下拉提示条数
     *
     * @var int
     */
    public $hit;
    protected $_name = [
        'query' => 'query',
        'hit'   => 'hit',
    ];
    protected $_default = [
        'hit' => 10,
    ];

    public function validate()
    {
        Model::validateRequired('query', $this->query, true);
        Model::validateMinimum('hit', $this->hit, 1);
        Model::validateMaximum('hit', $this->hit, 10);
    }

    public function toMap()
    {
        $res = [];
        if (null !== $this->query) {
            $res['query'] = $this->query;
        }
        if (null !== $this->hit) {
            $res['hit'] = $this->hit;
        }

        return $res;
    }

    /**
     * @param array $map
     *
     * @return SuggestQuery
     */
    public static function fromMap($map = [])
    {
        $model = new self();
        if (isset($map['query'])) {
            $model->query = $map['query'];
        }
        if (isset($map['hit'])) {
            $model->hit = $map['hit'];
        }

        return $model;
    }
}
