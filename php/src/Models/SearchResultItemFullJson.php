<?php

// This file is auto-generated, don't edit it. Thanks.

namespace AlibabaCloud\SDK\OpenSearch\Models;

use AlibabaCloud\Tea\Model;

class SearchResultItemFullJson extends Model
{
    /**
     * @description 搜索召回内容
     *
     * @var array
     */
    public $fields;

    /**
     * @description 表示自定义参数返回结果，如获取distance距离值，variableValue 节点只有在config子句的format为xml或者fulljson时才能展现出来，json格式默认不展示。
     *
     * @var array
     */
    public $variableValue;

    /**
     * @description 表示对应文档排序分。
     *
     * @var array
     */
    public $sortExprValues;
    protected $_name = [
        'fields'         => 'fields',
        'variableValue'  => 'variableValue',
        'sortExprValues' => 'sortExprValues',
    ];

    public function validate()
    {
    }

    public function toMap()
    {
        $res = [];
        if (null !== $this->fields) {
            $res['fields'] = $this->fields;
        }
        if (null !== $this->variableValue) {
            $res['variableValue'] = $this->variableValue;
        }
        if (null !== $this->sortExprValues) {
            $res['sortExprValues'] = [];
            if (null !== $this->sortExprValues) {
                $res['sortExprValues'] = $this->sortExprValues;
            }
        }

        return $res;
    }

    /**
     * @param array $map
     *
     * @return SearchResultItemFullJson
     */
    public static function fromMap($map = [])
    {
        $model = new self();
        if (isset($map['fields'])) {
            $model->fields = $map['fields'];
        }
        if (isset($map['variableValue'])) {
            $model->variableValue = $map['variableValue'];
        }
        if (isset($map['sortExprValues'])) {
            if (!empty($map['sortExprValues'])) {
                $model->sortExprValues = [];
                $model->sortExprValues = $map['sortExprValues'];
            }
        }

        return $model;
    }
}
