<?php

// This file is auto-generated, don't edit it. Thanks.

namespace AlibabaCloud\SDK\OpenSearch;

use AlibabaCloud\Credentials\Credential;
use AlibabaCloud\SDK\OpenSearch\Models\CollectDataRequestModel;
use AlibabaCloud\SDK\OpenSearch\Models\CollectDataResponseModel;
use AlibabaCloud\SDK\OpenSearch\Models\PushDocumentRequestModel;
use AlibabaCloud\SDK\OpenSearch\Models\PushDocumentResponseModel;
use AlibabaCloud\SDK\OpenSearch\Models\SearchRequestModel;
use AlibabaCloud\SDK\OpenSearch\Models\SearchResponseModel;
use AlibabaCloud\SDK\OpenSearch\Models\SuggestRequestModel;
use AlibabaCloud\SDK\OpenSearch\Models\SuggestResponseModel;
use AlibabaCloud\Tea\Exception\TeaError;
use AlibabaCloud\Tea\Exception\TeaUnableRetryError;
use AlibabaCloud\Tea\OpenSearchUtil\Client;
use AlibabaCloud\Tea\Request;
use AlibabaCloud\Tea\Tea;
use AlibabaCloud\Tea\Utils\Utils;
use AlibabaCloud\Tea\Utils\Utils\RuntimeOptions;

class OpenSearch
{
    protected $_endpoint;

    protected $_protocol;

    protected $_userAgent;

    protected $_credential;

    public function __construct($config)
    {
        if (Utils::isUnset($config)) {
            throw new TeaError([
                'name'    => 'ParameterMissing',
                'message' => "'config' can not be unset",
            ]);
        }
        if (Utils::empty_($config->type)) {
            $config->type = 'access_key';
        }
        $credentialConfig = new \AlibabaCloud\Credentials\Credential\Config([
            'accessKeyId'     => $config->accessKeyId,
            'type'            => $config->type,
            'accessKeySecret' => $config->accessKeySecret,
            'securityToken'   => $config->securityToken,
        ]);
        $this->_credential = new Credential($credentialConfig);
        $this->_endpoint   = $config->endpoint;
        $this->_protocol   = $config->protocol;
        $this->_userAgent  = $config->userAgent;
    }

    /**
     * @param string         $method
     * @param string         $pathname
     * @param array          $query
     * @param array          $headers
     * @param mixed          $body
     * @param RuntimeOptions $runtime
     *
     * @throws \Exception
     * @throws TeaError
     * @throws TeaUnableRetryError
     *
     * @return array
     */
    public function _request($method, $pathname, $query, $headers, $body, $runtime)
    {
        $runtime->validate();
        $_runtime = [
            'timeouted'      => 'retry',
            'readTimeout'    => $runtime->readTimeout,
            'connectTimeout' => $runtime->connectTimeout,
            'httpProxy'      => $runtime->httpProxy,
            'httpsProxy'     => $runtime->httpsProxy,
            'noProxy'        => $runtime->noProxy,
            'maxIdleConns'   => $runtime->maxIdleConns,
            'retry'          => [
                'retryable'   => $runtime->autoretry,
                'maxAttempts' => Utils::defaultNumber($runtime->maxAttempts, 3),
            ],
            'backoff' => [
                'policy' => Utils::defaultString($runtime->backoffPolicy, 'no'),
                'period' => Utils::defaultNumber($runtime->backoffPeriod, 1),
            ],
            'ignoreSSL' => $runtime->ignoreSSL,
        ];
        $_lastRequest   = null;
        $_lastException = null;
        $_now           = time();
        $_retryTimes    = 0;
        while (Tea::allowRetry($_runtime['retry'], $_retryTimes, $_now)) {
            if ($_retryTimes > 0) {
                $_backoffTime = Tea::getBackoffTime($_runtime['backoff'], $_retryTimes);
                if ($_backoffTime > 0) {
                    Tea::sleep($_backoffTime);
                }
            }
            $_retryTimes = $_retryTimes + 1;

            try {
                $_request           = new Request();
                $accesskeyId        = $this->getAccessKeyId();
                $accessKeySecret    = $this->getAccessKeySecret();
                $_request->protocol = Utils::defaultString($this->_protocol, 'HTTP');
                $_request->method   = $method;
                $_request->pathname = $pathname;
                $_request->headers  = Tea::merge([
                    'user-agent'         => $this->getUserAgent(),
                    'Date'               => Client::getDate(),
                    'host'               => Utils::defaultString($this->_endpoint, 'opensearch-cn-hangzhou.aliyuncs.com'),
                    'X-Opensearch-Nonce' => Utils::getNonce(),
                ], $headers);
                if (!Utils::isUnset($query)) {
                    $_request->query = Utils::stringifyMapValue($query);
                }
                if (!Utils::isUnset($body)) {
                    $reqBody                           = Utils::toJSONString($body);
                    $_request->headers['Content-MD5']  = Client::getContentMD5($reqBody);
                    $_request->headers['Content-Type'] = 'application/json';
                    $_request->body                    = $reqBody;
                }
                $_request->headers['Authorization'] = Client::getSignature($_request, $accesskeyId, $accessKeySecret);
                $_lastRequest                       = $_request;
                $_response                          = Tea::send($_request, $_runtime);
                $objStr                             = Utils::readAsString($_response->body);
                if (Utils::is4xx($_response->statusCode) || Utils::is5xx($_response->statusCode)) {
                    throw new TeaError([
                        'message' => $_response->statusMessage,
                        'data'    => $objStr,
                        'code'    => $_response->statusCode,
                    ]);
                }
                $obj = Utils::parseJSON($objStr);
                $res = Utils::assertAsMap($obj);

                return [
                    'body'    => $res,
                    'headers' => $_response->headers,
                ];
            } catch (\Exception $e) {
                if (!($e instanceof TeaError)) {
                    $e = new TeaError([], $e->getMessage(), $e->getCode(), $e);
                }
                if (Tea::isRetryable($e)) {
                    $_lastException = $e;

                    continue;
                }

                throw $e;
            }
        }

        throw new TeaUnableRetryError($_lastRequest, $_lastException);
    }

    /**
     * 系统提供了丰富的搜索语法以满足用户各种场景下的搜索需求。
     *
     * @param string             $appName
     * @param SearchRequestModel $request
     * @param RuntimeOptions     $runtime
     *
     * @return SearchResponseModel
     */
    public function searchEx($appName, $request, $runtime)
    {
        return SearchResponseModel::fromMap($this->_request('GET', '/v3/openapi/apps/' . $appName . '/search', $request->query, $request->headers, null, $runtime));
    }

    /**
     * 系统提供了丰富的搜索语法以满足用户各种场景下的搜索需求。
     *
     * @param string             $appName
     * @param SearchRequestModel $request
     *
     * @return SearchResponseModel
     */
    public function search($appName, $request)
    {
        $runtime = new RuntimeOptions([
            'connectTimeout' => 5000,
            'readTimeout'    => 10000,
            'autoretry'      => false,
            'ignoreSSL'      => false,
            'maxIdleConns'   => 50,
        ]);

        return $this->searchEx($appName, $request, $runtime);
    }

    /**
     * 下拉提示是搜索服务的基础功能，在用户输入查询词的过程中，智能推荐候选query，减少用户输入，帮助用户尽快找到想要的内容。
     *
     * @param string              $appName
     * @param string              $modelName
     * @param SuggestRequestModel $request
     * @param RuntimeOptions      $runtime
     *
     * @return SuggestResponseModel
     */
    public function suggestEx($appName, $modelName, $request, $runtime)
    {
        return SuggestResponseModel::fromMap($this->_request('GET', '/v3/openapi/apps/' . $appName . '/suggest/' . $modelName . '/search', $request->query, $request->headers, null, $runtime));
    }

    /**
     * 下拉提示是搜索服务的基础功能，在用户输入查询词的过程中，智能推荐候选query，减少用户输入，帮助用户尽快找到想要的内容。
     *
     * @param string              $appName
     * @param string              $modelName
     * @param SuggestRequestModel $request
     *
     * @return SuggestResponseModel
     */
    public function suggest($appName, $modelName, $request)
    {
        $runtime = new RuntimeOptions([
            'connectTimeout' => 5000,
            'readTimeout'    => 10000,
            'autoretry'      => false,
            'ignoreSSL'      => false,
            'maxIdleConns'   => 50,
        ]);

        return $this->suggestEx($appName, $modelName, $request, $runtime);
    }

    /**
     * 支持新增、更新、删除 等操作，以及对应批量操作.
     *
     * @param string                   $appName
     * @param string                   $tableName
     * @param PushDocumentRequestModel $request
     * @param RuntimeOptions           $runtime
     *
     * @return PushDocumentResponseModel
     */
    public function pushDocumentEx($appName, $tableName, $request, $runtime)
    {
        return PushDocumentResponseModel::fromMap($this->_request('POST', '/v3/openapi/apps/' . $appName . '/' . $tableName . '/actions/bulk', null, $request->headers, $request->body, $runtime));
    }

    /**
     * 支持新增、更新、删除 等操作，以及对应批量操作.
     *
     * @param string                   $appName
     * @param string                   $tableName
     * @param PushDocumentRequestModel $request
     *
     * @return PushDocumentResponseModel
     */
    public function pushDocument($appName, $tableName, $request)
    {
        $runtime = new RuntimeOptions([
            'connectTimeout' => 5000,
            'readTimeout'    => 10000,
            'autoretry'      => false,
            'ignoreSSL'      => false,
            'maxIdleConns'   => 50,
        ]);

        return $this->pushDocumentEx($appName, $tableName, $request, $runtime);
    }

    /**
     * 为了给客户提供更高质量的搜索效果，opensearch目前支持客户通过server端上传点击数据。
     *
     * @param string                  $appName
     * @param string                  $collectorName
     * @param CollectDataRequestModel $request
     * @param RuntimeOptions          $runtime
     *
     * @return CollectDataResponseModel
     */
    public function collectDataEx($appName, $collectorName, $request, $runtime)
    {
        return CollectDataResponseModel::fromMap($this->_request('POST', '/v3/openapi/app-groups/' . $appName . '/data-collections/' . $collectorName . '/actions/bulk', null, $request->headers, $request->body, $runtime));
    }

    /**
     * 为了给客户提供更高质量的搜索效果，opensearch目前支持客户通过server端上传点击数据。
     *
     * @param string                  $appName
     * @param string                  $collectorName
     * @param CollectDataRequestModel $request
     *
     * @return CollectDataResponseModel
     */
    public function collectData($appName, $collectorName, $request)
    {
        $runtime = new RuntimeOptions([
            'connectTimeout' => 5000,
            'readTimeout'    => 10000,
            'autoretry'      => false,
            'ignoreSSL'      => false,
            'maxIdleConns'   => 50,
        ]);

        return $this->collectDataEx($appName, $collectorName, $request, $runtime);
    }

    /**
     * @param string $userAgent
     */
    public function setUserAgent($userAgent)
    {
        $this->_userAgent = $userAgent;
    }

    /**
     * @param string $userAgent
     */
    public function appendUserAgent($userAgent)
    {
        $this->_userAgent = '' . $this->_userAgent . ' ' . $userAgent . '';
    }

    /**
     * @return string
     */
    public function getUserAgent()
    {
        return Utils::getUserAgent($this->_userAgent);
    }

    /**
     * @return string
     */
    public function getAccessKeyId()
    {
        if (Utils::isUnset($this->_credential)) {
            return '';
        }

        return $this->_credential->getAccessKeyId();
    }

    /**
     * @return string
     */
    public function getAccessKeySecret()
    {
        if (Utils::isUnset($this->_credential)) {
            return '';
        }

        return $this->_credential->getAccessKeySecret();
    }
}
