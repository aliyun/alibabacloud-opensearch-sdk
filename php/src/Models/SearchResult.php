<?php

// This file is auto-generated, don't edit it. Thanks.

namespace AlibabaCloud\SDK\OpenSearch\Models;

use AlibabaCloud\Tea\Model;

/**
 * 实际返回结果，包括查询耗时searchtime、引擎总结果数total、本次请求返回结果数num、本次查询最大返回结果数viewtotal、查询结果items、统计结果facet等信息.
 */
class SearchResult extends Model
{
    /**
     * @description 指引擎耗时，单位为秒。
     *
     * @var float
     */
    public $searchtime;

    /**
     * @description total为一次查询（不考虑config子句）引擎中符合条件的结果数（在结果数较多情况下，该值会做优化），一般用来做展示。
     *
     * @var int
     */
    public $total;

    /**
     * @description 考虑到性能及相关性，引擎最多会返回viewtotal个结果。如果需要翻页的话，要求start+hit必需要小于viewtotal
     *
     * @var int
     */
    public $viewtotal;

    /**
     * @description num为本次查询请求（受限于config子句的start及hit）实际返回的条目，不会超过hit值。
     *
     * @var int
     */
    public $num;

    /**
     * @description 包含查询召回数据信息，其中fields为搜索召回内容。
     *
     * @var array
     */
    public $items;

    /**
     * @description 用于存放Aggregate子句返回的信息。
     *
     * @var array
     */
    public $facet;
    protected $_name = [
        'searchtime' => 'searchtime',
        'total'      => 'total',
        'viewtotal'  => 'viewtotal',
        'num'        => 'num',
        'items'      => 'items',
        'facet'      => 'facet',
    ];

    public function validate()
    {
    }

    public function toMap()
    {
        $res = [];
        if (null !== $this->searchtime) {
            $res['searchtime'] = $this->searchtime;
        }
        if (null !== $this->total) {
            $res['total'] = $this->total;
        }
        if (null !== $this->viewtotal) {
            $res['viewtotal'] = $this->viewtotal;
        }
        if (null !== $this->num) {
            $res['num'] = $this->num;
        }
        if (null !== $this->items) {
            $res['items'] = [];
            if (null !== $this->items) {
                $res['items'] = $this->items;
            }
        }
        if (null !== $this->facet) {
            $res['facet'] = [];
            if (null !== $this->facet && \is_array($this->facet)) {
                $n = 0;
                foreach ($this->facet as $item) {
                    $res['facet'][$n++] = null !== $item ? $item->toMap() : $item;
                }
            }
        }

        return $res;
    }

    /**
     * @param array $map
     *
     * @return SearchResult
     */
    public static function fromMap($map = [])
    {
        $model = new self();
        if (isset($map['searchtime'])) {
            $model->searchtime = $map['searchtime'];
        }
        if (isset($map['total'])) {
            $model->total = $map['total'];
        }
        if (isset($map['viewtotal'])) {
            $model->viewtotal = $map['viewtotal'];
        }
        if (isset($map['num'])) {
            $model->num = $map['num'];
        }
        if (isset($map['items'])) {
            if (!empty($map['items'])) {
                $model->items = [];
                $model->items = $map['items'];
            }
        }
        if (isset($map['facet'])) {
            if (!empty($map['facet'])) {
                $model->facet = [];
                $n            = 0;
                foreach ($map['facet'] as $item) {
                    $model->facet[$n++] = null !== $item ? SearchResultFacet::fromMap($item) : $item;
                }
            }
        }

        return $model;
    }
}
