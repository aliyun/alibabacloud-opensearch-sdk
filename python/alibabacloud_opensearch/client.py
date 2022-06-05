# -*- coding: utf-8 -*-
# This file is auto-generated, don't edit it. Thanks.
import time

from Tea.exceptions import TeaException, UnretryableException
from Tea.request import TeaRequest
from Tea.core import TeaCore
from alibabacloud_opensearch_util.opensearch_util import OpensearchUtil
from typing import Dict, Any

from alibabacloud_credentials.client import Client as CredentialClient
from alibabacloud_opensearch import models as open_search_models
from alibabacloud_tea_util.client import Client as UtilClient
from alibabacloud_credentials import models as credential_models
from alibabacloud_tea_util import models as util_models


class Client:
    """
    *\
    """
    _endpoint: str = None
    _protocol: str = None
    _user_agent: str = None
    _credential: CredentialClient = None

    def __init__(
        self, 
        config: open_search_models.Config,
    ):
        if UtilClient.is_unset(config):
            raise TeaException({
                'name': 'ParameterMissing',
                'message': "'config' can not be unset"
            })
        if UtilClient.empty(config.type):
            config.type = 'access_key'
        credential_config = credential_models.Config(
            access_key_id=config.access_key_id,
            type=config.type,
            access_key_secret=config.access_key_secret,
            security_token=config.security_token
        )
        self._credential = CredentialClient(credential_config)
        self._endpoint = config.endpoint
        self._protocol = config.protocol
        self._user_agent = config.user_agent

    def _request(
        self,
        method: str,
        pathname: str,
        query: Dict[str, Any],
        headers: Dict[str, str],
        body: Any,
        runtime: util_models.RuntimeOptions,
    ) -> Dict[str, Any]:
        runtime.validate()
        _runtime = {
            'timeouted': 'retry',
            'readTimeout': runtime.read_timeout,
            'connectTimeout': runtime.connect_timeout,
            'httpProxy': runtime.http_proxy,
            'httpsProxy': runtime.https_proxy,
            'noProxy': runtime.no_proxy,
            'maxIdleConns': runtime.max_idle_conns,
            'retry': {
                'retryable': runtime.autoretry,
                'maxAttempts': UtilClient.default_number(runtime.max_attempts, 3)
            },
            'backoff': {
                'policy': UtilClient.default_string(runtime.backoff_policy, 'no'),
                'period': UtilClient.default_number(runtime.backoff_period, 1)
            },
            'ignoreSSL': runtime.ignore_ssl
        }
        _last_request = None
        _last_exception = None
        _now = time.time()
        _retry_times = 0
        while TeaCore.allow_retry(_runtime.get('retry'), _retry_times, _now):
            if _retry_times > 0:
                _backoff_time = TeaCore.get_backoff_time(_runtime.get('backoff'), _retry_times)
                if _backoff_time > 0:
                    TeaCore.sleep(_backoff_time)
            _retry_times = _retry_times + 1
            try:
                _request = TeaRequest()
                accesskey_id = self.get_access_key_id()
                access_key_secret = self.get_access_key_secret()
                _request.protocol = UtilClient.default_string(self._protocol, 'HTTP')
                _request.method = method
                _request.pathname = pathname
                _request.headers = TeaCore.merge({
                    'user-agent': self.get_user_agent(),
                    'Date': OpensearchUtil.get_date(),
                    'host': UtilClient.default_string(self._endpoint, f'opensearch-cn-hangzhou.aliyuncs.com'),
                    'X-Opensearch-Nonce': UtilClient.get_nonce()
                }, headers)
                if not UtilClient.is_unset(query):
                    _request.query = UtilClient.stringify_map_value(query)
                if not UtilClient.is_unset(body):
                    req_body = UtilClient.to_jsonstring(body)
                    _request.headers['Content-MD5'] = OpensearchUtil.get_content_md5(req_body)
                    _request.headers['Content-Type'] = 'application/json'
                    _request.body = req_body
                _request.headers['Authorization'] = OpensearchUtil.get_signature(_request, accesskey_id, access_key_secret)
                _last_request = _request
                _response = TeaCore.do_action(_request, _runtime)
                obj_str = UtilClient.read_as_string(_response.body)
                if UtilClient.is_4xx(_response.status_code) or UtilClient.is_5xx(_response.status_code):
                    raise TeaException({
                        'message': _response.status_message,
                        'data': obj_str,
                        'code': _response.status_code
                    })
                obj = UtilClient.parse_json(obj_str)
                res = UtilClient.assert_as_map(obj)
                return {
                    'body': res,
                    'headers': _response.headers
                }
            except Exception as e:
                if TeaCore.is_retryable(e):
                    _last_exception = e
                    continue
                raise e
        raise UnretryableException(_last_request, _last_exception)

    async def _request_async(
        self,
        method: str,
        pathname: str,
        query: Dict[str, Any],
        headers: Dict[str, str],
        body: Any,
        runtime: util_models.RuntimeOptions,
    ) -> Dict[str, Any]:
        runtime.validate()
        _runtime = {
            'timeouted': 'retry',
            'readTimeout': runtime.read_timeout,
            'connectTimeout': runtime.connect_timeout,
            'httpProxy': runtime.http_proxy,
            'httpsProxy': runtime.https_proxy,
            'noProxy': runtime.no_proxy,
            'maxIdleConns': runtime.max_idle_conns,
            'retry': {
                'retryable': runtime.autoretry,
                'maxAttempts': UtilClient.default_number(runtime.max_attempts, 3)
            },
            'backoff': {
                'policy': UtilClient.default_string(runtime.backoff_policy, 'no'),
                'period': UtilClient.default_number(runtime.backoff_period, 1)
            },
            'ignoreSSL': runtime.ignore_ssl
        }
        _last_request = None
        _last_exception = None
        _now = time.time()
        _retry_times = 0
        while TeaCore.allow_retry(_runtime.get('retry'), _retry_times, _now):
            if _retry_times > 0:
                _backoff_time = TeaCore.get_backoff_time(_runtime.get('backoff'), _retry_times)
                if _backoff_time > 0:
                    TeaCore.sleep(_backoff_time)
            _retry_times = _retry_times + 1
            try:
                _request = TeaRequest()
                accesskey_id = await self.get_access_key_id_async()
                access_key_secret = await self.get_access_key_secret_async()
                _request.protocol = UtilClient.default_string(self._protocol, 'HTTP')
                _request.method = method
                _request.pathname = pathname
                _request.headers = TeaCore.merge({
                    'user-agent': self.get_user_agent(),
                    'Date': OpensearchUtil.get_date(),
                    'host': UtilClient.default_string(self._endpoint, f'opensearch-cn-hangzhou.aliyuncs.com'),
                    'X-Opensearch-Nonce': UtilClient.get_nonce()
                }, headers)
                if not UtilClient.is_unset(query):
                    _request.query = UtilClient.stringify_map_value(query)
                if not UtilClient.is_unset(body):
                    req_body = UtilClient.to_jsonstring(body)
                    _request.headers['Content-MD5'] = OpensearchUtil.get_content_md5(req_body)
                    _request.headers['Content-Type'] = 'application/json'
                    _request.body = req_body
                _request.headers['Authorization'] = OpensearchUtil.get_signature(_request, accesskey_id, access_key_secret)
                _last_request = _request
                _response = await TeaCore.async_do_action(_request, _runtime)
                obj_str = await UtilClient.read_as_string_async(_response.body)
                if UtilClient.is_4xx(_response.status_code) or UtilClient.is_5xx(_response.status_code):
                    raise TeaException({
                        'message': _response.status_message,
                        'data': obj_str,
                        'code': _response.status_code
                    })
                obj = UtilClient.parse_json(obj_str)
                res = UtilClient.assert_as_map(obj)
                return {
                    'body': res,
                    'headers': _response.headers
                }
            except Exception as e:
                if TeaCore.is_retryable(e):
                    _last_exception = e
                    continue
                raise e
        raise UnretryableException(_last_request, _last_exception)

    def search_ex(
        self,
        app_name: str,
        request: open_search_models.SearchRequestModel,
        runtime: util_models.RuntimeOptions,
    ) -> open_search_models.SearchResponseModel:
        """
        系统提供了丰富的搜索语法以满足用户各种场景下的搜索需求。
        """
        return TeaCore.from_map(
            open_search_models.SearchResponseModel(),
            self._request('GET', f'/v3/openapi/apps/{app_name}/search', TeaCore.to_map(request.query), request.headers, None, runtime)
        )

    async def search_ex_async(
        self,
        app_name: str,
        request: open_search_models.SearchRequestModel,
        runtime: util_models.RuntimeOptions,
    ) -> open_search_models.SearchResponseModel:
        """
        系统提供了丰富的搜索语法以满足用户各种场景下的搜索需求。
        """
        return TeaCore.from_map(
            open_search_models.SearchResponseModel(),
            await self._request_async('GET', f'/v3/openapi/apps/{app_name}/search', TeaCore.to_map(request.query), request.headers, None, runtime)
        )

    def search(
        self,
        app_name: str,
        request: open_search_models.SearchRequestModel,
    ) -> open_search_models.SearchResponseModel:
        """
        系统提供了丰富的搜索语法以满足用户各种场景下的搜索需求。
        """
        runtime = util_models.RuntimeOptions(
            connect_timeout=5000,
            read_timeout=10000,
            autoretry=False,
            ignore_ssl=False,
            max_idle_conns=50
        )
        return self.search_ex(app_name, request, runtime)

    async def search_async(
        self,
        app_name: str,
        request: open_search_models.SearchRequestModel,
    ) -> open_search_models.SearchResponseModel:
        """
        系统提供了丰富的搜索语法以满足用户各种场景下的搜索需求。
        """
        runtime = util_models.RuntimeOptions(
            connect_timeout=5000,
            read_timeout=10000,
            autoretry=False,
            ignore_ssl=False,
            max_idle_conns=50
        )
        return await self.search_ex_async(app_name, request, runtime)

    def suggest_ex(
        self,
        app_name: str,
        model_name: str,
        request: open_search_models.SuggestRequestModel,
        runtime: util_models.RuntimeOptions,
    ) -> open_search_models.SuggestResponseModel:
        """
        下拉提示是搜索服务的基础功能，在用户输入查询词的过程中，智能推荐候选query，减少用户输入，帮助用户尽快找到想要的内容。
        """
        return TeaCore.from_map(
            open_search_models.SuggestResponseModel(),
            self._request('GET', f'/v3/openapi/apps/{app_name}/suggest/{model_name}/search', TeaCore.to_map(request.query), request.headers, None, runtime)
        )

    async def suggest_ex_async(
        self,
        app_name: str,
        model_name: str,
        request: open_search_models.SuggestRequestModel,
        runtime: util_models.RuntimeOptions,
    ) -> open_search_models.SuggestResponseModel:
        """
        下拉提示是搜索服务的基础功能，在用户输入查询词的过程中，智能推荐候选query，减少用户输入，帮助用户尽快找到想要的内容。
        """
        return TeaCore.from_map(
            open_search_models.SuggestResponseModel(),
            await self._request_async('GET', f'/v3/openapi/apps/{app_name}/suggest/{model_name}/search', TeaCore.to_map(request.query), request.headers, None, runtime)
        )

    def suggest(
        self,
        app_name: str,
        model_name: str,
        request: open_search_models.SuggestRequestModel,
    ) -> open_search_models.SuggestResponseModel:
        """
        下拉提示是搜索服务的基础功能，在用户输入查询词的过程中，智能推荐候选query，减少用户输入，帮助用户尽快找到想要的内容。
        """
        runtime = util_models.RuntimeOptions(
            connect_timeout=5000,
            read_timeout=10000,
            autoretry=False,
            ignore_ssl=False,
            max_idle_conns=50
        )
        return self.suggest_ex(app_name, model_name, request, runtime)

    async def suggest_async(
        self,
        app_name: str,
        model_name: str,
        request: open_search_models.SuggestRequestModel,
    ) -> open_search_models.SuggestResponseModel:
        """
        下拉提示是搜索服务的基础功能，在用户输入查询词的过程中，智能推荐候选query，减少用户输入，帮助用户尽快找到想要的内容。
        """
        runtime = util_models.RuntimeOptions(
            connect_timeout=5000,
            read_timeout=10000,
            autoretry=False,
            ignore_ssl=False,
            max_idle_conns=50
        )
        return await self.suggest_ex_async(app_name, model_name, request, runtime)

    def push_document_ex(
        self,
        app_name: str,
        table_name: str,
        request: open_search_models.PushDocumentRequestModel,
        runtime: util_models.RuntimeOptions,
    ) -> open_search_models.PushDocumentResponseModel:
        """
        支持新增、更新、删除 等操作，以及对应批量操作
        """
        return TeaCore.from_map(
            open_search_models.PushDocumentResponseModel(),
            self._request('POST', f'/v3/openapi/apps/{app_name}/{table_name}/actions/bulk', None, request.headers, request.body, runtime)
        )

    async def push_document_ex_async(
        self,
        app_name: str,
        table_name: str,
        request: open_search_models.PushDocumentRequestModel,
        runtime: util_models.RuntimeOptions,
    ) -> open_search_models.PushDocumentResponseModel:
        """
        支持新增、更新、删除 等操作，以及对应批量操作
        """
        return TeaCore.from_map(
            open_search_models.PushDocumentResponseModel(),
            await self._request_async('POST', f'/v3/openapi/apps/{app_name}/{table_name}/actions/bulk', None, request.headers, request.body, runtime)
        )

    def push_document(
        self,
        app_name: str,
        table_name: str,
        request: open_search_models.PushDocumentRequestModel,
    ) -> open_search_models.PushDocumentResponseModel:
        """
        支持新增、更新、删除 等操作，以及对应批量操作
        """
        runtime = util_models.RuntimeOptions(
            connect_timeout=5000,
            read_timeout=10000,
            autoretry=False,
            ignore_ssl=False,
            max_idle_conns=50
        )
        return self.push_document_ex(app_name, table_name, request, runtime)

    async def push_document_async(
        self,
        app_name: str,
        table_name: str,
        request: open_search_models.PushDocumentRequestModel,
    ) -> open_search_models.PushDocumentResponseModel:
        """
        支持新增、更新、删除 等操作，以及对应批量操作
        """
        runtime = util_models.RuntimeOptions(
            connect_timeout=5000,
            read_timeout=10000,
            autoretry=False,
            ignore_ssl=False,
            max_idle_conns=50
        )
        return await self.push_document_ex_async(app_name, table_name, request, runtime)

    def collect_data_ex(
        self,
        app_name: str,
        collector_name: str,
        request: open_search_models.CollectDataRequestModel,
        runtime: util_models.RuntimeOptions,
    ) -> open_search_models.CollectDataResponseModel:
        """
        为了给客户提供更高质量的搜索效果，opensearch目前支持客户通过server端上传点击数据。
        """
        return TeaCore.from_map(
            open_search_models.CollectDataResponseModel(),
            self._request('POST', f'/v3/openapi/app-groups/{app_name}/data-collections/{collector_name}/actions/bulk', None, request.headers, request.body, runtime)
        )

    async def collect_data_ex_async(
        self,
        app_name: str,
        collector_name: str,
        request: open_search_models.CollectDataRequestModel,
        runtime: util_models.RuntimeOptions,
    ) -> open_search_models.CollectDataResponseModel:
        """
        为了给客户提供更高质量的搜索效果，opensearch目前支持客户通过server端上传点击数据。
        """
        return TeaCore.from_map(
            open_search_models.CollectDataResponseModel(),
            await self._request_async('POST', f'/v3/openapi/app-groups/{app_name}/data-collections/{collector_name}/actions/bulk', None, request.headers, request.body, runtime)
        )

    def collect_data(
        self,
        app_name: str,
        collector_name: str,
        request: open_search_models.CollectDataRequestModel,
    ) -> open_search_models.CollectDataResponseModel:
        """
        为了给客户提供更高质量的搜索效果，opensearch目前支持客户通过server端上传点击数据。
        """
        runtime = util_models.RuntimeOptions(
            connect_timeout=5000,
            read_timeout=10000,
            autoretry=False,
            ignore_ssl=False,
            max_idle_conns=50
        )
        return self.collect_data_ex(app_name, collector_name, request, runtime)

    async def collect_data_async(
        self,
        app_name: str,
        collector_name: str,
        request: open_search_models.CollectDataRequestModel,
    ) -> open_search_models.CollectDataResponseModel:
        """
        为了给客户提供更高质量的搜索效果，opensearch目前支持客户通过server端上传点击数据。
        """
        runtime = util_models.RuntimeOptions(
            connect_timeout=5000,
            read_timeout=10000,
            autoretry=False,
            ignore_ssl=False,
            max_idle_conns=50
        )
        return await self.collect_data_ex_async(app_name, collector_name, request, runtime)

    def set_user_agent(
        self,
        user_agent: str,
    ) -> None:
        self._user_agent = user_agent

    def append_user_agent(
        self,
        user_agent: str,
    ) -> None:
        self._user_agent = f'{self._user_agent} {user_agent}'

    def get_user_agent(self) -> str:
        user_agent = UtilClient.get_user_agent(self._user_agent)
        return user_agent

    def get_access_key_id(self) -> str:
        if UtilClient.is_unset(self._credential):
            return ''
        access_key_id = self._credential.get_access_key_id()
        return access_key_id

    async def get_access_key_id_async(self) -> str:
        if UtilClient.is_unset(self._credential):
            return ''
        access_key_id = await self._credential.get_access_key_id_async()
        return access_key_id

    def get_access_key_secret(self) -> str:
        if UtilClient.is_unset(self._credential):
            return ''
        secret = self._credential.get_access_key_secret()
        return secret

    async def get_access_key_secret_async(self) -> str:
        if UtilClient.is_unset(self._credential):
            return ''
        secret = await self._credential.get_access_key_secret_async()
        return secret
