// This file is auto-generated, don't edit it. Thanks.
/**
 *
 */
package client

import (
	opensearchutil "github.com/alibabacloud-go/opensearch-util/service"
	util "github.com/alibabacloud-go/tea-utils/service"
	"github.com/alibabacloud-go/tea/tea"
	credential "github.com/aliyun/credentials-go/credentials"
)

type Config struct {
	Endpoint        *string `json:"endpoint,omitempty" xml:"endpoint,omitempty"`
	Protocol        *string `json:"protocol,omitempty" xml:"protocol,omitempty"`
	Type            *string `json:"type,omitempty" xml:"type,omitempty"`
	SecurityToken   *string `json:"securityToken,omitempty" xml:"securityToken,omitempty"`
	AccessKeyId     *string `json:"accessKeyId,omitempty" xml:"accessKeyId,omitempty"`
	AccessKeySecret *string `json:"accessKeySecret,omitempty" xml:"accessKeySecret,omitempty"`
	UserAgent       *string `json:"userAgent,omitempty" xml:"userAgent,omitempty"`
}

func (s Config) String() string {
	return tea.Prettify(s)
}

func (s Config) GoString() string {
	return s.String()
}

func (s *Config) SetEndpoint(v string) *Config {
	s.Endpoint = &v
	return s
}

func (s *Config) SetProtocol(v string) *Config {
	s.Protocol = &v
	return s
}

func (s *Config) SetType(v string) *Config {
	s.Type = &v
	return s
}

func (s *Config) SetSecurityToken(v string) *Config {
	s.SecurityToken = &v
	return s
}

func (s *Config) SetAccessKeyId(v string) *Config {
	s.AccessKeyId = &v
	return s
}

func (s *Config) SetAccessKeySecret(v string) *Config {
	s.AccessKeySecret = &v
	return s
}

func (s *Config) SetUserAgent(v string) *Config {
	s.UserAgent = &v
	return s
}

type SearchQuery struct {
	Query              *string `json:"query,omitempty" xml:"query,omitempty" require:"true"`
	FetchFields        *string `json:"fetch_fields,omitempty" xml:"fetch_fields,omitempty"`
	Qp                 *string `json:"qp,omitempty" xml:"qp,omitempty"`
	Disable            *string `json:"disable,omitempty" xml:"disable,omitempty"`
	FirstRankName      *string `json:"first_rank_name,omitempty" xml:"first_rank_name,omitempty"`
	SecondRankName     *string `json:"second_rank_name,omitempty" xml:"second_rank_name,omitempty"`
	UserId             *string `json:"user_id,omitempty" xml:"user_id,omitempty"`
	Abtest             *string `json:"abtest,omitempty" xml:"abtest,omitempty"`
	CategoryPrediction *string `json:"category_prediction,omitempty" xml:"category_prediction,omitempty"`
	RawQuery           *string `json:"raw_query,omitempty" xml:"raw_query,omitempty"`
	Summary            *string `json:"summary,omitempty" xml:"summary,omitempty"`
}

func (s SearchQuery) String() string {
	return tea.Prettify(s)
}

func (s SearchQuery) GoString() string {
	return s.String()
}

func (s *SearchQuery) SetQuery(v string) *SearchQuery {
	s.Query = &v
	return s
}

func (s *SearchQuery) SetFetchFields(v string) *SearchQuery {
	s.FetchFields = &v
	return s
}

func (s *SearchQuery) SetQp(v string) *SearchQuery {
	s.Qp = &v
	return s
}

func (s *SearchQuery) SetDisable(v string) *SearchQuery {
	s.Disable = &v
	return s
}

func (s *SearchQuery) SetFirstRankName(v string) *SearchQuery {
	s.FirstRankName = &v
	return s
}

func (s *SearchQuery) SetSecondRankName(v string) *SearchQuery {
	s.SecondRankName = &v
	return s
}

func (s *SearchQuery) SetUserId(v string) *SearchQuery {
	s.UserId = &v
	return s
}

func (s *SearchQuery) SetAbtest(v string) *SearchQuery {
	s.Abtest = &v
	return s
}

func (s *SearchQuery) SetCategoryPrediction(v string) *SearchQuery {
	s.CategoryPrediction = &v
	return s
}

func (s *SearchQuery) SetRawQuery(v string) *SearchQuery {
	s.RawQuery = &v
	return s
}

func (s *SearchQuery) SetSummary(v string) *SearchQuery {
	s.Summary = &v
	return s
}

type SearchRequestModel struct {
	Headers map[string]*string `json:"headers,omitempty" xml:"headers,omitempty"`
	Query   *SearchQuery       `json:"query,omitempty" xml:"query,omitempty" require:"true"`
}

func (s SearchRequestModel) String() string {
	return tea.Prettify(s)
}

func (s SearchRequestModel) GoString() string {
	return s.String()
}

func (s *SearchRequestModel) SetHeaders(v map[string]*string) *SearchRequestModel {
	s.Headers = v
	return s
}

func (s *SearchRequestModel) SetQuery(v *SearchQuery) *SearchRequestModel {
	s.Query = v
	return s
}

type SearchResponseModel struct {
	Headers map[string]*string `json:"headers,omitempty" xml:"headers,omitempty"`
	Body    *SearchResponse    `json:"body,omitempty" xml:"body,omitempty" require:"true"`
}

func (s SearchResponseModel) String() string {
	return tea.Prettify(s)
}

func (s SearchResponseModel) GoString() string {
	return s.String()
}

func (s *SearchResponseModel) SetHeaders(v map[string]*string) *SearchResponseModel {
	s.Headers = v
	return s
}

func (s *SearchResponseModel) SetBody(v *SearchResponse) *SearchResponseModel {
	s.Body = v
	return s
}

type SuggestQuery struct {
	Query *string `json:"query,omitempty" xml:"query,omitempty" require:"true"`
	Hit   *int    `json:"hit,omitempty" xml:"hit,omitempty" minimum:"1" maximum:"10"`
}

func (s SuggestQuery) String() string {
	return tea.Prettify(s)
}

func (s SuggestQuery) GoString() string {
	return s.String()
}

func (s *SuggestQuery) SetQuery(v string) *SuggestQuery {
	s.Query = &v
	return s
}

func (s *SuggestQuery) SetHit(v int) *SuggestQuery {
	s.Hit = &v
	return s
}

type SuggestRequestModel struct {
	Headers map[string]*string `json:"headers,omitempty" xml:"headers,omitempty"`
	Query   *SuggestQuery      `json:"query,omitempty" xml:"query,omitempty" require:"true"`
}

func (s SuggestRequestModel) String() string {
	return tea.Prettify(s)
}

func (s SuggestRequestModel) GoString() string {
	return s.String()
}

func (s *SuggestRequestModel) SetHeaders(v map[string]*string) *SuggestRequestModel {
	s.Headers = v
	return s
}

func (s *SuggestRequestModel) SetQuery(v *SuggestQuery) *SuggestRequestModel {
	s.Query = v
	return s
}

type SuggestResponseModel struct {
	Headers map[string]*string  `json:"headers,omitempty" xml:"headers,omitempty"`
	Body    *SuggestionResponse `json:"body,omitempty" xml:"body,omitempty" require:"true"`
}

func (s SuggestResponseModel) String() string {
	return tea.Prettify(s)
}

func (s SuggestResponseModel) GoString() string {
	return s.String()
}

func (s *SuggestResponseModel) SetHeaders(v map[string]*string) *SuggestResponseModel {
	s.Headers = v
	return s
}

func (s *SuggestResponseModel) SetBody(v *SuggestionResponse) *SuggestResponseModel {
	s.Body = v
	return s
}

type PushDocumentRequestModel struct {
	Headers map[string]*string `json:"headers,omitempty" xml:"headers,omitempty"`
	Body    []*Document        `json:"body,omitempty" xml:"body,omitempty" require:"true" type:"Repeated"`
}

func (s PushDocumentRequestModel) String() string {
	return tea.Prettify(s)
}

func (s PushDocumentRequestModel) GoString() string {
	return s.String()
}

func (s *PushDocumentRequestModel) SetHeaders(v map[string]*string) *PushDocumentRequestModel {
	s.Headers = v
	return s
}

func (s *PushDocumentRequestModel) SetBody(v []*Document) *PushDocumentRequestModel {
	s.Body = v
	return s
}

type PushDocumentResponseModel struct {
	Headers map[string]*string `json:"headers,omitempty" xml:"headers,omitempty"`
	Body    *Response          `json:"body,omitempty" xml:"body,omitempty" require:"true"`
}

func (s PushDocumentResponseModel) String() string {
	return tea.Prettify(s)
}

func (s PushDocumentResponseModel) GoString() string {
	return s.String()
}

func (s *PushDocumentResponseModel) SetHeaders(v map[string]*string) *PushDocumentResponseModel {
	s.Headers = v
	return s
}

func (s *PushDocumentResponseModel) SetBody(v *Response) *PushDocumentResponseModel {
	s.Body = v
	return s
}

type CollectDataRequestModel struct {
	Headers map[string]*string `json:"headers,omitempty" xml:"headers,omitempty"`
	Body    []*Behavior        `json:"body,omitempty" xml:"body,omitempty" require:"true" type:"Repeated"`
}

func (s CollectDataRequestModel) String() string {
	return tea.Prettify(s)
}

func (s CollectDataRequestModel) GoString() string {
	return s.String()
}

func (s *CollectDataRequestModel) SetHeaders(v map[string]*string) *CollectDataRequestModel {
	s.Headers = v
	return s
}

func (s *CollectDataRequestModel) SetBody(v []*Behavior) *CollectDataRequestModel {
	s.Body = v
	return s
}

type CollectDataResponseModel struct {
	Headers map[string]*string `json:"headers,omitempty" xml:"headers,omitempty"`
	Body    *Response          `json:"body,omitempty" xml:"body,omitempty" require:"true"`
}

func (s CollectDataResponseModel) String() string {
	return tea.Prettify(s)
}

func (s CollectDataResponseModel) GoString() string {
	return s.String()
}

func (s *CollectDataResponseModel) SetHeaders(v map[string]*string) *CollectDataResponseModel {
	s.Headers = v
	return s
}

func (s *CollectDataResponseModel) SetBody(v *Response) *CollectDataResponseModel {
	s.Body = v
	return s
}

/**
 *
 */
type SearchResponse struct {
	Status    *string       `json:"status,omitempty" xml:"status,omitempty"`
	RequestId *string       `json:"request_id,omitempty" xml:"request_id,omitempty"`
	Result    *SearchResult `json:"result,omitempty" xml:"result,omitempty"`
	Errors    []*Error      `json:"errors,omitempty" xml:"errors,omitempty" type:"Repeated"`
}

func (s SearchResponse) String() string {
	return tea.Prettify(s)
}

func (s SearchResponse) GoString() string {
	return s.String()
}

func (s *SearchResponse) SetStatus(v string) *SearchResponse {
	s.Status = &v
	return s
}

func (s *SearchResponse) SetRequestId(v string) *SearchResponse {
	s.RequestId = &v
	return s
}

func (s *SearchResponse) SetResult(v *SearchResult) *SearchResponse {
	s.Result = v
	return s
}

func (s *SearchResponse) SetErrors(v []*Error) *SearchResponse {
	s.Errors = v
	return s
}

/**
 * 实际返回结果，包括查询耗时searchtime、引擎总结果数total、本次请求返回结果数num、本次查询最大返回结果数viewtotal、查询结果items、统计结果facet等信息
 */
type SearchResult struct {
	Searchtime *float64                 `json:"searchtime,omitempty" xml:"searchtime,omitempty"`
	Total      *int                     `json:"total,omitempty" xml:"total,omitempty"`
	Viewtotal  *int                     `json:"viewtotal,omitempty" xml:"viewtotal,omitempty"`
	Num        *int                     `json:"num,omitempty" xml:"num,omitempty"`
	Items      []map[string]interface{} `json:"items,omitempty" xml:"items,omitempty" type:"Repeated"`
	Facet      []*SearchResultFacet     `json:"facet,omitempty" xml:"facet,omitempty" type:"Repeated"`
}

func (s SearchResult) String() string {
	return tea.Prettify(s)
}

func (s SearchResult) GoString() string {
	return s.String()
}

func (s *SearchResult) SetSearchtime(v float64) *SearchResult {
	s.Searchtime = &v
	return s
}

func (s *SearchResult) SetTotal(v int) *SearchResult {
	s.Total = &v
	return s
}

func (s *SearchResult) SetViewtotal(v int) *SearchResult {
	s.Viewtotal = &v
	return s
}

func (s *SearchResult) SetNum(v int) *SearchResult {
	s.Num = &v
	return s
}

func (s *SearchResult) SetItems(v []map[string]interface{}) *SearchResult {
	s.Items = v
	return s
}

func (s *SearchResult) SetFacet(v []*SearchResultFacet) *SearchResult {
	s.Facet = v
	return s
}

/**
 *
 */
type SearchResultItemFullJson struct {
	Fields         map[string]interface{} `json:"fields,omitempty" xml:"fields,omitempty"`
	VariableValue  map[string]interface{} `json:"variableValue,omitempty" xml:"variableValue,omitempty"`
	SortExprValues []*int                 `json:"sortExprValues,omitempty" xml:"sortExprValues,omitempty" type:"Repeated"`
}

func (s SearchResultItemFullJson) String() string {
	return tea.Prettify(s)
}

func (s SearchResultItemFullJson) GoString() string {
	return s.String()
}

func (s *SearchResultItemFullJson) SetFields(v map[string]interface{}) *SearchResultItemFullJson {
	s.Fields = v
	return s
}

func (s *SearchResultItemFullJson) SetVariableValue(v map[string]interface{}) *SearchResultItemFullJson {
	s.VariableValue = v
	return s
}

func (s *SearchResultItemFullJson) SetSortExprValues(v []*int) *SearchResultItemFullJson {
	s.SortExprValues = v
	return s
}

/**
 *
 */
type SearchResultFacet struct {
	Key   *string                   `json:"key,omitempty" xml:"key,omitempty"`
	Items []*SearchResultFacetItems `json:"items,omitempty" xml:"items,omitempty" type:"Repeated"`
}

func (s SearchResultFacet) String() string {
	return tea.Prettify(s)
}

func (s SearchResultFacet) GoString() string {
	return s.String()
}

func (s *SearchResultFacet) SetKey(v string) *SearchResultFacet {
	s.Key = &v
	return s
}

func (s *SearchResultFacet) SetItems(v []*SearchResultFacetItems) *SearchResultFacet {
	s.Items = v
	return s
}

type SearchResultFacetItems struct {
	Value *string `json:"value,omitempty" xml:"value,omitempty"`
	Count *int    `json:"count,omitempty" xml:"count,omitempty"`
}

func (s SearchResultFacetItems) String() string {
	return tea.Prettify(s)
}

func (s SearchResultFacetItems) GoString() string {
	return s.String()
}

func (s *SearchResultFacetItems) SetValue(v string) *SearchResultFacetItems {
	s.Value = &v
	return s
}

func (s *SearchResultFacetItems) SetCount(v int) *SearchResultFacetItems {
	s.Count = &v
	return s
}

/**
 *
 */
type Error struct {
	Code    *int    `json:"code,omitempty" xml:"code,omitempty"`
	Message *string `json:"message,omitempty" xml:"message,omitempty"`
}

func (s Error) String() string {
	return tea.Prettify(s)
}

func (s Error) GoString() string {
	return s.String()
}

func (s *Error) SetCode(v int) *Error {
	s.Code = &v
	return s
}

func (s *Error) SetMessage(v string) *Error {
	s.Message = &v
	return s
}

/**
 *
 */
type Document struct {
	Cmd       *string                `json:"cmd,omitempty" xml:"cmd,omitempty" require:"true"`
	Timestamp *int                   `json:"timestamp,omitempty" xml:"timestamp,omitempty"`
	Fields    map[string]interface{} `json:"fields,omitempty" xml:"fields,omitempty" require:"true"`
}

func (s Document) String() string {
	return tea.Prettify(s)
}

func (s Document) GoString() string {
	return s.String()
}

func (s *Document) SetCmd(v string) *Document {
	s.Cmd = &v
	return s
}

func (s *Document) SetTimestamp(v int) *Document {
	s.Timestamp = &v
	return s
}

func (s *Document) SetFields(v map[string]interface{}) *Document {
	s.Fields = v
	return s
}

/**
 *
 */
type Behavior struct {
	Cmd    *string                `json:"cmd,omitempty" xml:"cmd,omitempty" require:"true"`
	Fields map[string]interface{} `json:"fields,omitempty" xml:"fields,omitempty" require:"true"`
}

func (s Behavior) String() string {
	return tea.Prettify(s)
}

func (s Behavior) GoString() string {
	return s.String()
}

func (s *Behavior) SetCmd(v string) *Behavior {
	s.Cmd = &v
	return s
}

func (s *Behavior) SetFields(v map[string]interface{}) *Behavior {
	s.Fields = v
	return s
}

/**
 * object_id=pk,object_type=ops_search_doc,ops_request_misc=xxx
 */
type Event2001Args struct {
	ObjectId       *string `json:"object_id,omitempty" xml:"object_id,omitempty"`
	ObjectType     *string `json:"object_type,omitempty" xml:"object_type,omitempty"`
	OpsRequestMisc *string `json:"ops_request_misc,omitempty" xml:"ops_request_misc,omitempty"`
}

func (s Event2001Args) String() string {
	return tea.Prettify(s)
}

func (s Event2001Args) GoString() string {
	return s.String()
}

func (s *Event2001Args) SetObjectId(v string) *Event2001Args {
	s.ObjectId = &v
	return s
}

func (s *Event2001Args) SetObjectType(v string) *Event2001Args {
	s.ObjectType = &v
	return s
}

func (s *Event2001Args) SetOpsRequestMisc(v string) *Event2001Args {
	s.OpsRequestMisc = &v
	return s
}

/**
 *
 */
type SuggestionResponse struct {
	RequestId   *string                          `json:"request_id,omitempty" xml:"request_id,omitempty"`
	Searchtime  *float64                         `json:"searchtime,omitempty" xml:"searchtime,omitempty"`
	Suggestions []*SuggestionResponseSuggestions `json:"suggestions,omitempty" xml:"suggestions,omitempty" type:"Repeated"`
	Errors      []*Error                         `json:"errors,omitempty" xml:"errors,omitempty" type:"Repeated"`
}

func (s SuggestionResponse) String() string {
	return tea.Prettify(s)
}

func (s SuggestionResponse) GoString() string {
	return s.String()
}

func (s *SuggestionResponse) SetRequestId(v string) *SuggestionResponse {
	s.RequestId = &v
	return s
}

func (s *SuggestionResponse) SetSearchtime(v float64) *SuggestionResponse {
	s.Searchtime = &v
	return s
}

func (s *SuggestionResponse) SetSuggestions(v []*SuggestionResponseSuggestions) *SuggestionResponse {
	s.Suggestions = v
	return s
}

func (s *SuggestionResponse) SetErrors(v []*Error) *SuggestionResponse {
	s.Errors = v
	return s
}

type SuggestionResponseSuggestions struct {
	Suggestion *string `json:"suggestion,omitempty" xml:"suggestion,omitempty"`
}

func (s SuggestionResponseSuggestions) String() string {
	return tea.Prettify(s)
}

func (s SuggestionResponseSuggestions) GoString() string {
	return s.String()
}

func (s *SuggestionResponseSuggestions) SetSuggestion(v string) *SuggestionResponseSuggestions {
	s.Suggestion = &v
	return s
}

/**
 *
 */
type Response struct {
	Status    *string  `json:"status,omitempty" xml:"status,omitempty"`
	RequestId *string  `json:"request_id,omitempty" xml:"request_id,omitempty"`
	Errors    []*Error `json:"errors,omitempty" xml:"errors,omitempty" type:"Repeated"`
}

func (s Response) String() string {
	return tea.Prettify(s)
}

func (s Response) GoString() string {
	return s.String()
}

func (s *Response) SetStatus(v string) *Response {
	s.Status = &v
	return s
}

func (s *Response) SetRequestId(v string) *Response {
	s.RequestId = &v
	return s
}

func (s *Response) SetErrors(v []*Error) *Response {
	s.Errors = v
	return s
}

type Client struct {
	Endpoint   *string
	Protocol   *string
	UserAgent  *string
	Credential credential.Credential
}

func NewClient(config *Config) (*Client, error) {
	client := new(Client)
	err := client.Init(config)
	return client, err
}

func (client *Client) Init(config *Config) (_err error) {
	if tea.BoolValue(util.IsUnset(tea.ToMap(config))) {
		_err = tea.NewSDKError(map[string]interface{}{
			"name":    "ParameterMissing",
			"message": "'config' can not be unset",
		})
		return _err
	}

	if tea.BoolValue(util.Empty(config.Type)) {
		config.Type = tea.String("access_key")
	}

	credentialConfig := &credential.Config{
		AccessKeyId:     config.AccessKeyId,
		Type:            config.Type,
		AccessKeySecret: config.AccessKeySecret,
		SecurityToken:   config.SecurityToken,
	}
	client.Credential, _err = credential.NewCredential(credentialConfig)
	if _err != nil {
		return _err
	}

	client.Endpoint = config.Endpoint
	client.Protocol = config.Protocol
	client.UserAgent = config.UserAgent
	return nil
}

func (client *Client) _request(method *string, pathname *string, query map[string]interface{}, headers map[string]*string, body interface{}, runtime *util.RuntimeOptions) (_result map[string]interface{}, _err error) {
	_err = tea.Validate(runtime)
	if _err != nil {
		return _result, _err
	}
	_runtime := map[string]interface{}{
		"timeouted":      "retry",
		"readTimeout":    tea.IntValue(runtime.ReadTimeout),
		"connectTimeout": tea.IntValue(runtime.ConnectTimeout),
		"httpProxy":      tea.StringValue(runtime.HttpProxy),
		"httpsProxy":     tea.StringValue(runtime.HttpsProxy),
		"noProxy":        tea.StringValue(runtime.NoProxy),
		"maxIdleConns":   tea.IntValue(runtime.MaxIdleConns),
		"retry": map[string]interface{}{
			"retryable":   tea.BoolValue(runtime.Autoretry),
			"maxAttempts": tea.IntValue(util.DefaultNumber(runtime.MaxAttempts, tea.Int(3))),
		},
		"backoff": map[string]interface{}{
			"policy": tea.StringValue(util.DefaultString(runtime.BackoffPolicy, tea.String("no"))),
			"period": tea.IntValue(util.DefaultNumber(runtime.BackoffPeriod, tea.Int(1))),
		},
		"ignoreSSL": tea.BoolValue(runtime.IgnoreSSL),
	}

	_resp := make(map[string]interface{})
	for _retryTimes := 0; tea.BoolValue(tea.AllowRetry(_runtime["retry"], tea.Int(_retryTimes))); _retryTimes++ {
		if _retryTimes > 0 {
			_backoffTime := tea.GetBackoffTime(_runtime["backoff"], tea.Int(_retryTimes))
			if tea.IntValue(_backoffTime) > 0 {
				tea.Sleep(_backoffTime)
			}
		}

		_resp, _err = func() (map[string]interface{}, error) {
			request_ := tea.NewRequest()
			accesskeyId, _err := client.GetAccessKeyId()
			if _err != nil {
				return _result, _err
			}

			accessKeySecret, _err := client.GetAccessKeySecret()
			if _err != nil {
				return _result, _err
			}

			request_.Protocol = util.DefaultString(client.Protocol, tea.String("HTTP"))
			request_.Method = method
			request_.Pathname = pathname
			request_.Headers = tea.Merge(map[string]*string{
				"user-agent":         client.GetUserAgent(),
				"Date":               opensearchutil.GetDate(),
				"host":               util.DefaultString(client.Endpoint, tea.String("opensearch-cn-hangzhou.aliyuncs.com")),
				"X-Opensearch-Nonce": util.GetNonce(),
			}, headers)
			if !tea.BoolValue(util.IsUnset(query)) {
				request_.Query = util.StringifyMapValue(query)
			}

			if !tea.BoolValue(util.IsUnset(body)) {
				reqBody := util.ToJSONString(body)
				request_.Headers["Content-MD5"] = opensearchutil.GetContentMD5(reqBody)
				request_.Headers["Content-Type"] = tea.String("application/json")
				request_.Body = tea.ToReader(reqBody)
			}

			request_.Headers["Authorization"] = opensearchutil.GetSignature(request_, accesskeyId, accessKeySecret)
			response_, _err := tea.DoRequest(request_, _runtime)
			if _err != nil {
				return _result, _err
			}
			objStr, _err := util.ReadAsString(response_.Body)
			if _err != nil {
				return _result, _err
			}

			if tea.BoolValue(util.Is4xx(response_.StatusCode)) || tea.BoolValue(util.Is5xx(response_.StatusCode)) {
				_err = tea.NewSDKError(map[string]interface{}{
					"message": tea.StringValue(response_.StatusMessage),
					"data":    tea.StringValue(objStr),
					"code":    tea.IntValue(response_.StatusCode),
				})
				return _result, _err
			}

			obj := util.ParseJSON(objStr)
			res := util.AssertAsMap(obj)
			_result = make(map[string]interface{})
			_err = tea.Convert(map[string]interface{}{
				"body":    res,
				"headers": response_.Headers,
			}, &_result)
			return _result, _err
		}()
		if !tea.BoolValue(tea.Retryable(_err)) {
			break
		}
	}

	return _resp, _err
}

/**
 * 系统提供了丰富的搜索语法以满足用户各种场景下的搜索需求。
 */
func (client *Client) SearchEx(appName *string, request *SearchRequestModel, runtime *util.RuntimeOptions) (_result *SearchResponseModel, _err error) {
	_result = &SearchResponseModel{}
	_body, _err := client._request(tea.String("GET"), tea.String("/v3/openapi/apps/"+tea.StringValue(appName)+"/search"), tea.ToMap(request.Query), request.Headers, nil, runtime)
	if _err != nil {
		return _result, _err
	}
	_err = tea.Convert(_body, &_result)
	return _result, _err
}

/**
 * 系统提供了丰富的搜索语法以满足用户各种场景下的搜索需求。
 */
func (client *Client) Search(appName *string, request *SearchRequestModel) (_result *SearchResponseModel, _err error) {
	runtime := &util.RuntimeOptions{
		ConnectTimeout: tea.Int(5000),
		ReadTimeout:    tea.Int(10000),
		Autoretry:      tea.Bool(false),
		IgnoreSSL:      tea.Bool(false),
		MaxIdleConns:   tea.Int(50),
	}
	_result = &SearchResponseModel{}
	_body, _err := client.SearchEx(appName, request, runtime)
	if _err != nil {
		return _result, _err
	}
	_result = _body
	return _result, _err
}

/**
 * 下拉提示是搜索服务的基础功能，在用户输入查询词的过程中，智能推荐候选query，减少用户输入，帮助用户尽快找到想要的内容。
 */
func (client *Client) SuggestEx(appName *string, modelName *string, request *SuggestRequestModel, runtime *util.RuntimeOptions) (_result *SuggestResponseModel, _err error) {
	_result = &SuggestResponseModel{}
	_body, _err := client._request(tea.String("GET"), tea.String("/v3/openapi/apps/"+tea.StringValue(appName)+"/suggest/"+tea.StringValue(modelName)+"/search"), tea.ToMap(request.Query), request.Headers, nil, runtime)
	if _err != nil {
		return _result, _err
	}
	_err = tea.Convert(_body, &_result)
	return _result, _err
}

/**
 * 下拉提示是搜索服务的基础功能，在用户输入查询词的过程中，智能推荐候选query，减少用户输入，帮助用户尽快找到想要的内容。
 */
func (client *Client) Suggest(appName *string, modelName *string, request *SuggestRequestModel) (_result *SuggestResponseModel, _err error) {
	runtime := &util.RuntimeOptions{
		ConnectTimeout: tea.Int(5000),
		ReadTimeout:    tea.Int(10000),
		Autoretry:      tea.Bool(false),
		IgnoreSSL:      tea.Bool(false),
		MaxIdleConns:   tea.Int(50),
	}
	_result = &SuggestResponseModel{}
	_body, _err := client.SuggestEx(appName, modelName, request, runtime)
	if _err != nil {
		return _result, _err
	}
	_result = _body
	return _result, _err
}

/**
 * 支持新增、更新、删除 等操作，以及对应批量操作
 */
func (client *Client) PushDocumentEx(appName *string, tableName *string, request *PushDocumentRequestModel, runtime *util.RuntimeOptions) (_result *PushDocumentResponseModel, _err error) {
	_result = &PushDocumentResponseModel{}
	_body, _err := client._request(tea.String("POST"), tea.String("/v3/openapi/apps/"+tea.StringValue(appName)+"/"+tea.StringValue(tableName)+"/actions/bulk"), nil, request.Headers, request.Body, runtime)
	if _err != nil {
		return _result, _err
	}
	_err = tea.Convert(_body, &_result)
	return _result, _err
}

/**
 * 支持新增、更新、删除 等操作，以及对应批量操作
 */
func (client *Client) PushDocument(appName *string, tableName *string, request *PushDocumentRequestModel) (_result *PushDocumentResponseModel, _err error) {
	runtime := &util.RuntimeOptions{
		ConnectTimeout: tea.Int(5000),
		ReadTimeout:    tea.Int(10000),
		Autoretry:      tea.Bool(false),
		IgnoreSSL:      tea.Bool(false),
		MaxIdleConns:   tea.Int(50),
	}
	_result = &PushDocumentResponseModel{}
	_body, _err := client.PushDocumentEx(appName, tableName, request, runtime)
	if _err != nil {
		return _result, _err
	}
	_result = _body
	return _result, _err
}

/**
 * 为了给客户提供更高质量的搜索效果，opensearch目前支持客户通过server端上传点击数据。
 */
func (client *Client) CollectDataEx(appName *string, collectorName *string, request *CollectDataRequestModel, runtime *util.RuntimeOptions) (_result *CollectDataResponseModel, _err error) {
	_result = &CollectDataResponseModel{}
	_body, _err := client._request(tea.String("POST"), tea.String("/v3/openapi/app-groups/"+tea.StringValue(appName)+"/data-collections/"+tea.StringValue(collectorName)+"/actions/bulk"), nil, request.Headers, request.Body, runtime)
	if _err != nil {
		return _result, _err
	}
	_err = tea.Convert(_body, &_result)
	return _result, _err
}

/**
 * 为了给客户提供更高质量的搜索效果，opensearch目前支持客户通过server端上传点击数据。
 */
func (client *Client) CollectData(appName *string, collectorName *string, request *CollectDataRequestModel) (_result *CollectDataResponseModel, _err error) {
	runtime := &util.RuntimeOptions{
		ConnectTimeout: tea.Int(5000),
		ReadTimeout:    tea.Int(10000),
		Autoretry:      tea.Bool(false),
		IgnoreSSL:      tea.Bool(false),
		MaxIdleConns:   tea.Int(50),
	}
	_result = &CollectDataResponseModel{}
	_body, _err := client.CollectDataEx(appName, collectorName, request, runtime)
	if _err != nil {
		return _result, _err
	}
	_result = _body
	return _result, _err
}

func (client *Client) SetUserAgent(userAgent *string) {
	client.UserAgent = userAgent
}

func (client *Client) AppendUserAgent(userAgent *string) {
	client.UserAgent = tea.String(tea.StringValue(client.UserAgent) + " " + tea.StringValue(userAgent))
}

func (client *Client) GetUserAgent() (_result *string) {
	userAgent := util.GetUserAgent(client.UserAgent)
	_result = userAgent
	return _result
}

func (client *Client) GetAccessKeyId() (_result *string, _err error) {
	if tea.BoolValue(util.IsUnset(client.Credential)) {
		return _result, _err
	}

	accessKeyId, _err := client.Credential.GetAccessKeyId()
	if _err != nil {
		return _result, _err
	}

	_result = accessKeyId
	return _result, _err
}

func (client *Client) GetAccessKeySecret() (_result *string, _err error) {
	if tea.BoolValue(util.IsUnset(client.Credential)) {
		return _result, _err
	}

	secret, _err := client.Credential.GetAccessKeySecret()
	if _err != nil {
		return _result, _err
	}

	_result = secret
	return _result, _err
}
