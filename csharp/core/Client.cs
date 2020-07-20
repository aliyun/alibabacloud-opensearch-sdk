// This file is auto-generated, don't edit it. Thanks.

using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Threading.Tasks;

using Tea;
using Tea.Utils;

using AlibabaCloud.SDK.OpenSearch.Models;

namespace AlibabaCloud.SDK.OpenSearch
{
    public class Client 
    {
        protected string _endpoint;
        protected string _protocol;
        protected string _userAgent;
        protected Aliyun.Credentials.Client _credential;

        public Client(Config config)
        {
            if (AlibabaCloud.TeaUtil.Common.IsUnset(config.ToMap()))
            {
                throw new TeaException(new Dictionary<string, string>
                {
                    {"name", "ParameterMissing"},
                    {"message", "'config' can not be unset"},
                });
            }
            if (AlibabaCloud.TeaUtil.Common.Empty(config.Type))
            {
                config.Type = "access_key";
            }
            Aliyun.Credentials.Models.Config credentialConfig = new Aliyun.Credentials.Models.Config
            {
                AccessKeyId = config.AccessKeyId,
                Type = config.Type,
                AccessKeySecret = config.AccessKeySecret,
                SecurityToken = config.SecurityToken,
            };
            this._credential = new Aliyun.Credentials.Client(credentialConfig);
            this._endpoint = config.Endpoint;
            this._protocol = config.Protocol;
            this._userAgent = config.UserAgent;
        }

        public Dictionary<string, object> _request(string method, string pathname, Dictionary<string, object> query, Dictionary<string, string> headers, object body, AlibabaCloud.TeaUtil.Models.RuntimeOptions runtime)
        {
            Dictionary<string, object> runtime_ = new Dictionary<string, object>
            {
                {"timeouted", "retry"},
                {"readTimeout", runtime.ReadTimeout},
                {"connectTimeout", runtime.ConnectTimeout},
                {"httpProxy", runtime.HttpProxy},
                {"httpsProxy", runtime.HttpsProxy},
                {"noProxy", runtime.NoProxy},
                {"maxIdleConns", runtime.MaxIdleConns},
                {"retry", new Dictionary<string, object>
                {
                    {"retryable", runtime.Autoretry},
                    {"maxAttempts", AlibabaCloud.TeaUtil.Common.DefaultNumber(runtime.MaxAttempts, 3)},
                }},
                {"backoff", new Dictionary<string, object>
                {
                    {"policy", AlibabaCloud.TeaUtil.Common.DefaultString(runtime.BackoffPolicy, "no")},
                    {"period", AlibabaCloud.TeaUtil.Common.DefaultNumber(runtime.BackoffPeriod, 1)},
                }},
                {"ignoreSSL", runtime.IgnoreSSL},
            };

            TeaRequest _lastRequest = null;
            Exception _lastException = null;
            long _now = System.DateTime.Now.Millisecond;
            int _retryTimes = 0;
            while (TeaCore.AllowRetry((IDictionary) runtime_["retry"], _retryTimes, _now))
            {
                if (_retryTimes > 0)
                {
                    int backoffTime = TeaCore.GetBackoffTime((IDictionary)runtime_["backoff"], _retryTimes);
                    if (backoffTime > 0)
                    {
                        TeaCore.Sleep(backoffTime);
                    }
                }
                _retryTimes = _retryTimes + 1;
                try
                {
                    TeaRequest request_ = new TeaRequest();
                    string accesskeyId = GetAccessKeyId();
                    string accessKeySecret = GetAccessKeySecret();
                    request_.Protocol = AlibabaCloud.TeaUtil.Common.DefaultString(_protocol, "HTTP");
                    request_.Method = method;
                    request_.Pathname = pathname;
                    request_.Headers = TeaConverter.merge<string>
                    (
                        new Dictionary<string, string>()
                        {
                            {"user-agent", GetUserAgent()},
                            {"Date", AlibabaCloud.OpenSearchUtil.Common.GetDate()},
                            {"host", AlibabaCloud.TeaUtil.Common.DefaultString(_endpoint, "opensearch-cn-hangzhou.aliyuncs.com")},
                            {"X-Opensearch-Nonce", AlibabaCloud.TeaUtil.Common.GetNonce()},
                        },
                        headers
                    );
                    if (!AlibabaCloud.TeaUtil.Common.IsUnset(query))
                    {
                        request_.Query = AlibabaCloud.TeaUtil.Common.StringifyMapValue(query);
                    }
                    if (!AlibabaCloud.TeaUtil.Common.IsUnset(body))
                    {
                        string reqBody = AlibabaCloud.TeaUtil.Common.ToJSONString(body);
                        request_.Headers["Content-MD5"] = AlibabaCloud.OpenSearchUtil.Common.GetContentMD5(reqBody);
                        request_.Headers["Content-Type"] = "application/json";
                        request_.Body = TeaCore.BytesReadable(reqBody);
                    }
                    request_.Headers["Authorization"] = AlibabaCloud.OpenSearchUtil.Common.GetSignature(request_, accesskeyId, accessKeySecret);
                    _lastRequest = request_;
                    TeaResponse response_ = TeaCore.DoAction(request_, runtime_);

                    string objStr = AlibabaCloud.TeaUtil.Common.ReadAsString(response_.Body);
                    if (AlibabaCloud.TeaUtil.Common.Is4xx(response_.StatusCode) || AlibabaCloud.TeaUtil.Common.Is5xx(response_.StatusCode))
                    {
                        throw new TeaException(new Dictionary<string, object>
                        {
                            {"message", response_.StatusMessage},
                            {"data", objStr},
                            {"code", response_.StatusCode},
                        });
                    }
                    object obj = AlibabaCloud.TeaUtil.Common.ParseJSON(objStr);
                    Dictionary<string, object> res = AlibabaCloud.TeaUtil.Common.AssertAsMap(obj);
                    return new Dictionary<string, object>
                    {
                        {"body", res},
                        {"headers", response_.Headers},
                    };
                }
                catch (Exception e)
                {
                    if (TeaCore.IsRetryable(e))
                    {
                        _lastException = e;
                        continue;
                    }
                    throw e;
                }
            }

            throw new TeaUnretryableException(_lastRequest, _lastException);
        }

        public async Task<Dictionary<string, object>> _requestAsync(string method, string pathname, Dictionary<string, object> query, Dictionary<string, string> headers, object body, AlibabaCloud.TeaUtil.Models.RuntimeOptions runtime)
        {
            Dictionary<string, object> runtime_ = new Dictionary<string, object>
            {
                {"timeouted", "retry"},
                {"readTimeout", runtime.ReadTimeout},
                {"connectTimeout", runtime.ConnectTimeout},
                {"httpProxy", runtime.HttpProxy},
                {"httpsProxy", runtime.HttpsProxy},
                {"noProxy", runtime.NoProxy},
                {"maxIdleConns", runtime.MaxIdleConns},
                {"retry", new Dictionary<string, object>
                {
                    {"retryable", runtime.Autoretry},
                    {"maxAttempts", AlibabaCloud.TeaUtil.Common.DefaultNumber(runtime.MaxAttempts, 3)},
                }},
                {"backoff", new Dictionary<string, object>
                {
                    {"policy", AlibabaCloud.TeaUtil.Common.DefaultString(runtime.BackoffPolicy, "no")},
                    {"period", AlibabaCloud.TeaUtil.Common.DefaultNumber(runtime.BackoffPeriod, 1)},
                }},
                {"ignoreSSL", runtime.IgnoreSSL},
            };

            TeaRequest _lastRequest = null;
            Exception _lastException = null;
            long _now = System.DateTime.Now.Millisecond;
            int _retryTimes = 0;
            while (TeaCore.AllowRetry((IDictionary) runtime_["retry"], _retryTimes, _now))
            {
                if (_retryTimes > 0)
                {
                    int backoffTime = TeaCore.GetBackoffTime((IDictionary)runtime_["backoff"], _retryTimes);
                    if (backoffTime > 0)
                    {
                        TeaCore.Sleep(backoffTime);
                    }
                }
                _retryTimes = _retryTimes + 1;
                try
                {
                    TeaRequest request_ = new TeaRequest();
                    string accesskeyId = await GetAccessKeyIdAsync();
                    string accessKeySecret = await GetAccessKeySecretAsync();
                    request_.Protocol = AlibabaCloud.TeaUtil.Common.DefaultString(_protocol, "HTTP");
                    request_.Method = method;
                    request_.Pathname = pathname;
                    request_.Headers = TeaConverter.merge<string>
                    (
                        new Dictionary<string, string>()
                        {
                            {"user-agent", GetUserAgent()},
                            {"Date", AlibabaCloud.OpenSearchUtil.Common.GetDate()},
                            {"host", AlibabaCloud.TeaUtil.Common.DefaultString(_endpoint, "opensearch-cn-hangzhou.aliyuncs.com")},
                            {"X-Opensearch-Nonce", AlibabaCloud.TeaUtil.Common.GetNonce()},
                        },
                        headers
                    );
                    if (!AlibabaCloud.TeaUtil.Common.IsUnset(query))
                    {
                        request_.Query = AlibabaCloud.TeaUtil.Common.StringifyMapValue(query);
                    }
                    if (!AlibabaCloud.TeaUtil.Common.IsUnset(body))
                    {
                        string reqBody = AlibabaCloud.TeaUtil.Common.ToJSONString(body);
                        request_.Headers["Content-MD5"] = AlibabaCloud.OpenSearchUtil.Common.GetContentMD5(reqBody);
                        request_.Headers["Content-Type"] = "application/json";
                        request_.Body = TeaCore.BytesReadable(reqBody);
                    }
                    request_.Headers["Authorization"] = AlibabaCloud.OpenSearchUtil.Common.GetSignature(request_, accesskeyId, accessKeySecret);
                    _lastRequest = request_;
                    TeaResponse response_ = await TeaCore.DoActionAsync(request_, runtime_);

                    string objStr = AlibabaCloud.TeaUtil.Common.ReadAsString(response_.Body);
                    if (AlibabaCloud.TeaUtil.Common.Is4xx(response_.StatusCode) || AlibabaCloud.TeaUtil.Common.Is5xx(response_.StatusCode))
                    {
                        throw new TeaException(new Dictionary<string, object>
                        {
                            {"message", response_.StatusMessage},
                            {"data", objStr},
                            {"code", response_.StatusCode},
                        });
                    }
                    object obj = AlibabaCloud.TeaUtil.Common.ParseJSON(objStr);
                    Dictionary<string, object> res = AlibabaCloud.TeaUtil.Common.AssertAsMap(obj);
                    return new Dictionary<string, object>
                    {
                        {"body", res},
                        {"headers", response_.Headers},
                    };
                }
                catch (Exception e)
                {
                    if (TeaCore.IsRetryable(e))
                    {
                        _lastException = e;
                        continue;
                    }
                    throw e;
                }
            }

            throw new TeaUnretryableException(_lastRequest, _lastException);
        }

        /**
         * 系统提供了丰富的搜索语法以满足用户各种场景下的搜索需求。
         */
        public SearchResponseModel SearchEx(string appName, SearchRequestModel request, AlibabaCloud.TeaUtil.Models.RuntimeOptions runtime)
        {
            return TeaModel.ToObject<SearchResponseModel>(_request("GET", "/v3/openapi/apps/" + appName + "/search", request.Query.ToMap(), request.Headers, null, runtime));
        }

        /**
         * 系统提供了丰富的搜索语法以满足用户各种场景下的搜索需求。
         */
        public async Task<SearchResponseModel> SearchExAsync(string appName, SearchRequestModel request, AlibabaCloud.TeaUtil.Models.RuntimeOptions runtime)
        {
            return TeaModel.ToObject<SearchResponseModel>(await _requestAsync("GET", "/v3/openapi/apps/" + appName + "/search", request.Query.ToMap(), request.Headers, null, runtime));
        }

        /**
         * 系统提供了丰富的搜索语法以满足用户各种场景下的搜索需求。
         */
        public SearchResponseModel Search(string appName, SearchRequestModel request)
        {
            AlibabaCloud.TeaUtil.Models.RuntimeOptions runtime = new AlibabaCloud.TeaUtil.Models.RuntimeOptions
            {
                ConnectTimeout = 5000,
                ReadTimeout = 10000,
                Autoretry = false,
                IgnoreSSL = false,
                MaxIdleConns = 50,
            };
            return SearchEx(appName, request, runtime);
        }

        /**
         * 系统提供了丰富的搜索语法以满足用户各种场景下的搜索需求。
         */
        public async Task<SearchResponseModel> SearchAsync(string appName, SearchRequestModel request)
        {
            AlibabaCloud.TeaUtil.Models.RuntimeOptions runtime = new AlibabaCloud.TeaUtil.Models.RuntimeOptions
            {
                ConnectTimeout = 5000,
                ReadTimeout = 10000,
                Autoretry = false,
                IgnoreSSL = false,
                MaxIdleConns = 50,
            };
            return await SearchExAsync(appName, request, runtime);
        }

        /**
         * 下拉提示是搜索服务的基础功能，在用户输入查询词的过程中，智能推荐候选query，减少用户输入，帮助用户尽快找到想要的内容。
         */
        public SuggestResponseModel SuggestEx(string appName, string modelName, SuggestRequestModel request, AlibabaCloud.TeaUtil.Models.RuntimeOptions runtime)
        {
            return TeaModel.ToObject<SuggestResponseModel>(_request("GET", "/v3/openapi/apps/" + appName + "/suggest/" + modelName + "/search", request.Query.ToMap(), request.Headers, null, runtime));
        }

        /**
         * 下拉提示是搜索服务的基础功能，在用户输入查询词的过程中，智能推荐候选query，减少用户输入，帮助用户尽快找到想要的内容。
         */
        public async Task<SuggestResponseModel> SuggestExAsync(string appName, string modelName, SuggestRequestModel request, AlibabaCloud.TeaUtil.Models.RuntimeOptions runtime)
        {
            return TeaModel.ToObject<SuggestResponseModel>(await _requestAsync("GET", "/v3/openapi/apps/" + appName + "/suggest/" + modelName + "/search", request.Query.ToMap(), request.Headers, null, runtime));
        }

        /**
         * 下拉提示是搜索服务的基础功能，在用户输入查询词的过程中，智能推荐候选query，减少用户输入，帮助用户尽快找到想要的内容。
         */
        public SuggestResponseModel Suggest(string appName, string modelName, SuggestRequestModel request)
        {
            AlibabaCloud.TeaUtil.Models.RuntimeOptions runtime = new AlibabaCloud.TeaUtil.Models.RuntimeOptions
            {
                ConnectTimeout = 5000,
                ReadTimeout = 10000,
                Autoretry = false,
                IgnoreSSL = false,
                MaxIdleConns = 50,
            };
            return SuggestEx(appName, modelName, request, runtime);
        }

        /**
         * 下拉提示是搜索服务的基础功能，在用户输入查询词的过程中，智能推荐候选query，减少用户输入，帮助用户尽快找到想要的内容。
         */
        public async Task<SuggestResponseModel> SuggestAsync(string appName, string modelName, SuggestRequestModel request)
        {
            AlibabaCloud.TeaUtil.Models.RuntimeOptions runtime = new AlibabaCloud.TeaUtil.Models.RuntimeOptions
            {
                ConnectTimeout = 5000,
                ReadTimeout = 10000,
                Autoretry = false,
                IgnoreSSL = false,
                MaxIdleConns = 50,
            };
            return await SuggestExAsync(appName, modelName, request, runtime);
        }

        /**
         * 支持新增、更新、删除 等操作，以及对应批量操作
         */
        public PushDocumentResponseModel PushDocumentEx(string appName, string tableName, PushDocumentRequestModel request, AlibabaCloud.TeaUtil.Models.RuntimeOptions runtime)
        {
            return TeaModel.ToObject<PushDocumentResponseModel>(_request("POST", "/v3/openapi/apps/" + appName + "/" + tableName + "/actions/bulk", null, request.Headers, request.Body, runtime));
        }

        /**
         * 支持新增、更新、删除 等操作，以及对应批量操作
         */
        public async Task<PushDocumentResponseModel> PushDocumentExAsync(string appName, string tableName, PushDocumentRequestModel request, AlibabaCloud.TeaUtil.Models.RuntimeOptions runtime)
        {
            return TeaModel.ToObject<PushDocumentResponseModel>(await _requestAsync("POST", "/v3/openapi/apps/" + appName + "/" + tableName + "/actions/bulk", null, request.Headers, request.Body, runtime));
        }

        /**
         * 支持新增、更新、删除 等操作，以及对应批量操作
         */
        public PushDocumentResponseModel PushDocument(string appName, string tableName, PushDocumentRequestModel request)
        {
            AlibabaCloud.TeaUtil.Models.RuntimeOptions runtime = new AlibabaCloud.TeaUtil.Models.RuntimeOptions
            {
                ConnectTimeout = 5000,
                ReadTimeout = 10000,
                Autoretry = false,
                IgnoreSSL = false,
                MaxIdleConns = 50,
            };
            return PushDocumentEx(appName, tableName, request, runtime);
        }

        /**
         * 支持新增、更新、删除 等操作，以及对应批量操作
         */
        public async Task<PushDocumentResponseModel> PushDocumentAsync(string appName, string tableName, PushDocumentRequestModel request)
        {
            AlibabaCloud.TeaUtil.Models.RuntimeOptions runtime = new AlibabaCloud.TeaUtil.Models.RuntimeOptions
            {
                ConnectTimeout = 5000,
                ReadTimeout = 10000,
                Autoretry = false,
                IgnoreSSL = false,
                MaxIdleConns = 50,
            };
            return await PushDocumentExAsync(appName, tableName, request, runtime);
        }

        /**
         * 为了给客户提供更高质量的搜索效果，opensearch目前支持客户通过server端上传点击数据。
         */
        public CollectDataResponseModel CollectDataEx(string appName, string collectorName, CollectDataRequestModel request, AlibabaCloud.TeaUtil.Models.RuntimeOptions runtime)
        {
            return TeaModel.ToObject<CollectDataResponseModel>(_request("POST", "/v3/openapi/app-groups/" + appName + "/data-collections/" + collectorName + "/actions/bulk", null, request.Headers, request.Body, runtime));
        }

        /**
         * 为了给客户提供更高质量的搜索效果，opensearch目前支持客户通过server端上传点击数据。
         */
        public async Task<CollectDataResponseModel> CollectDataExAsync(string appName, string collectorName, CollectDataRequestModel request, AlibabaCloud.TeaUtil.Models.RuntimeOptions runtime)
        {
            return TeaModel.ToObject<CollectDataResponseModel>(await _requestAsync("POST", "/v3/openapi/app-groups/" + appName + "/data-collections/" + collectorName + "/actions/bulk", null, request.Headers, request.Body, runtime));
        }

        /**
         * 为了给客户提供更高质量的搜索效果，opensearch目前支持客户通过server端上传点击数据。
         */
        public CollectDataResponseModel CollectData(string appName, string collectorName, CollectDataRequestModel request)
        {
            AlibabaCloud.TeaUtil.Models.RuntimeOptions runtime = new AlibabaCloud.TeaUtil.Models.RuntimeOptions
            {
                ConnectTimeout = 5000,
                ReadTimeout = 10000,
                Autoretry = false,
                IgnoreSSL = false,
                MaxIdleConns = 50,
            };
            return CollectDataEx(appName, collectorName, request, runtime);
        }

        /**
         * 为了给客户提供更高质量的搜索效果，opensearch目前支持客户通过server端上传点击数据。
         */
        public async Task<CollectDataResponseModel> CollectDataAsync(string appName, string collectorName, CollectDataRequestModel request)
        {
            AlibabaCloud.TeaUtil.Models.RuntimeOptions runtime = new AlibabaCloud.TeaUtil.Models.RuntimeOptions
            {
                ConnectTimeout = 5000,
                ReadTimeout = 10000,
                Autoretry = false,
                IgnoreSSL = false,
                MaxIdleConns = 50,
            };
            return await CollectDataExAsync(appName, collectorName, request, runtime);
        }

        public void SetUserAgent(string userAgent)
        {
            this._userAgent = userAgent;
        }

        public void AppendUserAgent(string userAgent)
        {
            this._userAgent = _userAgent + " " + userAgent;
        }

        public string GetUserAgent()
        {
            string userAgent = AlibabaCloud.TeaUtil.Common.GetUserAgent(_userAgent);
            return userAgent;
        }

        public string GetAccessKeyId()
        {
            if (AlibabaCloud.TeaUtil.Common.IsUnset(_credential))
            {
                return "";
            }
            string accessKeyId = this._credential.GetAccessKeyId();
            return accessKeyId;
        }

        public async Task<string> GetAccessKeyIdAsync()
        {
            if (AlibabaCloud.TeaUtil.Common.IsUnset(_credential))
            {
                return "";
            }
            string accessKeyId = await this._credential.GetAccessKeyIdAsync();
            return accessKeyId;
        }

        public string GetAccessKeySecret()
        {
            if (AlibabaCloud.TeaUtil.Common.IsUnset(_credential))
            {
                return "";
            }
            string secret = this._credential.GetAccessKeySecret();
            return secret;
        }

        public async Task<string> GetAccessKeySecretAsync()
        {
            if (AlibabaCloud.TeaUtil.Common.IsUnset(_credential))
            {
                return "";
            }
            string secret = await this._credential.GetAccessKeySecretAsync();
            return secret;
        }

    }
}
