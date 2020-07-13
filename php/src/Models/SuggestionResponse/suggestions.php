<?php

// This file is auto-generated, don't edit it. Thanks.

namespace AlibabaCloud\SDK\OpenSearch\Models\SuggestionResponse;

use AlibabaCloud\Tea\Model;

class suggestions extends Model
{
    /**
     * @var string
     */
    public $suggestion;
    protected $_name = [
        'suggestion' => 'suggestion',
    ];

    public function validate()
    {
    }

    public function toMap()
    {
        $res = [];
        if (null !== $this->suggestion) {
            $res['suggestion'] = $this->suggestion;
        }

        return $res;
    }

    /**
     * @param array $map
     *
     * @return suggestions
     */
    public static function fromMap($map = [])
    {
        $model = new self();
        if (isset($map['suggestion'])) {
            $model->suggestion = $map['suggestion'];
        }

        return $model;
    }
}
