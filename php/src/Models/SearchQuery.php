<?php

// This file is auto-generated, don't edit it. Thanks.

namespace AlibabaCloud\SDK\OpenSearch\Models;

use AlibabaCloud\Tea\Model;

class SearchQuery extends Model
{
    /**
     * @description 搜索主体，不能为空。主要支持子句有 config子句、query子句、sort子句、filter子句、aggregate子句、distinct子句 、kvpairs子句。
     *
     * @var string
     */
    public $query;

    /**
     * @description 表示本次查询需要召回哪些字段值，多个字段之间通过英文分号;分隔，对应控制台中的默认展示字段功能。默认使用全部可展示字段。
     *
     * @var string
     */
    public $fetchFields;

    /**
     * @description 指定要使用的查询分析规则，多个规则使用英文逗号,分隔。默认使用已上线规则
     *
     * @var string
     */
    public $qp;

    /**
     * @description 关闭指定已生效的参数功能。
     *
     * @var string
     */
    public $disable;

    /**
     * @description 设置粗排表达式名字。
     *
     * @var string
     */
    public $firstRankName;

    /**
     * @description 设置精排表达式名字。
     *
     * @var string
     */
    public $secondRankName;

    /**
     * @description 用来标识发起当前搜索请求的终端用户。该值可以设置为下列值，优先级从高到低：1. 终端用户的长登录会员ID；2. 终端用户的移动设备imei标识；3. 终端用户的client_ip
     *
     * @var string
     */
    public $userId;

    /**
     * @description 使用A/B Test功能时需要设置该参数。
     *
     * @var string
     */
    public $abtest;

    /**
     * @description 通过类目预测功能搜索时需要设置该参数。
     *
     * @var string
     */
    public $categoryPrediction;

    /**
     * @description 终端用户输入的query，用于类目预测相关模型训练。
     *
     * @var string
     */
    public $rawQuery;

    /**
     * @description 搜索结果摘要配置，可以指定某些字段进行飘红、截断等操作。
     *
     * @var string
     */
    public $summary;
    protected $_name = [
        'query'              => 'query',
        'fetchFields'        => 'fetch_fields',
        'qp'                 => 'qp',
        'disable'            => 'disable',
        'firstRankName'      => 'first_rank_name',
        'secondRankName'     => 'second_rank_name',
        'userId'             => 'user_id',
        'abtest'             => 'abtest',
        'categoryPrediction' => 'category_prediction',
        'rawQuery'           => 'raw_query',
        'summary'            => 'summary',
    ];

    public function validate()
    {
        Model::validateRequired('query', $this->query, true);
    }

    public function toMap()
    {
        $res = [];
        if (null !== $this->query) {
            $res['query'] = $this->query;
        }
        if (null !== $this->fetchFields) {
            $res['fetch_fields'] = $this->fetchFields;
        }
        if (null !== $this->qp) {
            $res['qp'] = $this->qp;
        }
        if (null !== $this->disable) {
            $res['disable'] = $this->disable;
        }
        if (null !== $this->firstRankName) {
            $res['first_rank_name'] = $this->firstRankName;
        }
        if (null !== $this->secondRankName) {
            $res['second_rank_name'] = $this->secondRankName;
        }
        if (null !== $this->userId) {
            $res['user_id'] = $this->userId;
        }
        if (null !== $this->abtest) {
            $res['abtest'] = $this->abtest;
        }
        if (null !== $this->categoryPrediction) {
            $res['category_prediction'] = $this->categoryPrediction;
        }
        if (null !== $this->rawQuery) {
            $res['raw_query'] = $this->rawQuery;
        }
        if (null !== $this->summary) {
            $res['summary'] = $this->summary;
        }

        return $res;
    }

    /**
     * @param array $map
     *
     * @return SearchQuery
     */
    public static function fromMap($map = [])
    {
        $model = new self();
        if (isset($map['query'])) {
            $model->query = $map['query'];
        }
        if (isset($map['fetch_fields'])) {
            $model->fetchFields = $map['fetch_fields'];
        }
        if (isset($map['qp'])) {
            $model->qp = $map['qp'];
        }
        if (isset($map['disable'])) {
            $model->disable = $map['disable'];
        }
        if (isset($map['first_rank_name'])) {
            $model->firstRankName = $map['first_rank_name'];
        }
        if (isset($map['second_rank_name'])) {
            $model->secondRankName = $map['second_rank_name'];
        }
        if (isset($map['user_id'])) {
            $model->userId = $map['user_id'];
        }
        if (isset($map['abtest'])) {
            $model->abtest = $map['abtest'];
        }
        if (isset($map['category_prediction'])) {
            $model->categoryPrediction = $map['category_prediction'];
        }
        if (isset($map['raw_query'])) {
            $model->rawQuery = $map['raw_query'];
        }
        if (isset($map['summary'])) {
            $model->summary = $map['summary'];
        }

        return $model;
    }
}
