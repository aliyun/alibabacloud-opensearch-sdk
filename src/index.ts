import Credential, * as $Credential from "@alicloud/credentials";
import OpenSearchUtil from "./util";
import * as $tea from "@alicloud/tea-typescript";
import Util, * as $Util from "@alicloud/tea-util";

export class Config extends $tea.Model {
  endpoint?: string;
  protocol?: string;
  type?: string;
  securityToken?: string;
  accessKeyId?: string;
  accessKeySecret?: string;
  userAgent?: string;

  static names(): { [key: string]: string } {
    return {
      endpoint: "endpoint",
      protocol: "protocol",
      type: "type",
      securityToken: "securityToken",
      accessKeyId: "accessKeyId",
      accessKeySecret: "accessKeySecret",
      userAgent: "userAgent"
    };
  }

  static types(): { [key: string]: any } {
    return {
      endpoint: "string",
      protocol: "string",
      type: "string",
      securityToken: "string",
      accessKeyId: "string",
      accessKeySecret: "string",
      userAgent: "string"
    };
  }

  constructor(map?: { [key: string]: any }) {
    super(map);
  }
}

export class SearchQuery extends $tea.Model {
  query: string;
  fetchFields?: string;
  qp?: string;
  disable?: string;
  firstRankName?: string;
  secondRankName?: string;
  userId?: string;
  abtest?: string;
  categoryPrediction?: string;
  rawQuery?: string;
  summary?: string;

  static names(): { [key: string]: string } {
    return {
      query: "query",
      fetchFields: "fetch_fields",
      qp: "qp",
      disable: "disable",
      firstRankName: "first_rank_name",
      secondRankName: "second_rank_name",
      userId: "user_id",
      abtest: "abtest",
      categoryPrediction: "category_prediction",
      rawQuery: "raw_query",
      summary: "summary"
    };
  }

  static types(): { [key: string]: any } {
    return {
      query: "string",
      fetchFields: "string",
      qp: "string",
      disable: "string",
      firstRankName: "string",
      secondRankName: "string",
      userId: "string",
      abtest: "string",
      categoryPrediction: "string",
      rawQuery: "string",
      summary: "string"
    };
  }

  constructor(map?: { [key: string]: any }) {
    super(map);
  }
}

export class SearchRequestModel extends $tea.Model {
  headers?: { [key: string]: string };
  query: SearchQuery;

  static names(): { [key: string]: string } {
    return {
      headers: "headers",
      query: "query"
    };
  }

  static types(): { [key: string]: any } {
    return {
      headers: { "type": "map", "keyType": "string", "valueType": "string" },
      query: SearchQuery
    };
  }

  constructor(map?: { [key: string]: any }) {
    super(map);
  }
}

export class SearchResponseModel extends $tea.Model {
  headers?: { [key: string]: string };
  body: SearchResponse;

  static names(): { [key: string]: string } {
    return {
      headers: "headers",
      body: "body"
    };
  }

  static types(): { [key: string]: any } {
    return {
      headers: { "type": "map", "keyType": "string", "valueType": "string" },
      body: SearchResponse
    };
  }

  constructor(map?: { [key: string]: any }) {
    super(map);
  }
}

export class SuggestQuery extends $tea.Model {
  query: string;
  hit?: number;

  static names(): { [key: string]: string } {
    return {
      query: "query",
      hit: "hit"
    };
  }

  static types(): { [key: string]: any } {
    return {
      query: "string",
      hit: "number"
    };
  }

  constructor(map?: { [key: string]: any }) {
    super(map);
  }
}

export class SuggestRequestModel extends $tea.Model {
  headers?: { [key: string]: string };
  query: SuggestQuery;

  static names(): { [key: string]: string } {
    return {
      headers: "headers",
      query: "query"
    };
  }

  static types(): { [key: string]: any } {
    return {
      headers: { "type": "map", "keyType": "string", "valueType": "string" },
      query: SuggestQuery
    };
  }

  constructor(map?: { [key: string]: any }) {
    super(map);
  }
}

export class SuggestResponseModel extends $tea.Model {
  headers?: { [key: string]: string };
  body: SuggestionResponse;

  static names(): { [key: string]: string } {
    return {
      headers: "headers",
      body: "body"
    };
  }

  static types(): { [key: string]: any } {
    return {
      headers: { "type": "map", "keyType": "string", "valueType": "string" },
      body: SuggestionResponse
    };
  }

  constructor(map?: { [key: string]: any }) {
    super(map);
  }
}

export class PushDocumentRequestModel extends $tea.Model {
  headers?: { [key: string]: string };
  body: Document[];

  static names(): { [key: string]: string } {
    return {
      headers: "headers",
      body: "body"
    };
  }

  static types(): { [key: string]: any } {
    return {
      headers: { "type": "map", "keyType": "string", "valueType": "string" },
      body: { "type": "array", "itemType": Document }
    };
  }

  constructor(map?: { [key: string]: any }) {
    super(map);
  }
}

export class PushDocumentResponseModel extends $tea.Model {
  headers?: { [key: string]: string };
  body: Response;

  static names(): { [key: string]: string } {
    return {
      headers: "headers",
      body: "body"
    };
  }

  static types(): { [key: string]: any } {
    return {
      headers: { "type": "map", "keyType": "string", "valueType": "string" },
      body: Response
    };
  }

  constructor(map?: { [key: string]: any }) {
    super(map);
  }
}

export class CollectDataRequestModel extends $tea.Model {
  headers?: { [key: string]: string };
  body: Behavior[];

  static names(): { [key: string]: string } {
    return {
      headers: "headers",
      body: "body"
    };
  }

  static types(): { [key: string]: any } {
    return {
      headers: { "type": "map", "keyType": "string", "valueType": "string" },
      body: { "type": "array", "itemType": Behavior }
    };
  }

  constructor(map?: { [key: string]: any }) {
    super(map);
  }
}

export class CollectDataResponseModel extends $tea.Model {
  headers?: { [key: string]: string };
  body: Response;

  static names(): { [key: string]: string } {
    return {
      headers: "headers",
      body: "body"
    };
  }

  static types(): { [key: string]: any } {
    return {
      headers: { "type": "map", "keyType": "string", "valueType": "string" },
      body: Response
    };
  }

  constructor(map?: { [key: string]: any }) {
    super(map);
  }
}

export class SearchResponse extends $tea.Model {
  status?: string;
  requestId?: string;
  result?: SearchResult;
  errors?: Error[];

  static names(): { [key: string]: string } {
    return {
      status: "status",
      requestId: "request_id",
      result: "result",
      errors: "errors"
    };
  }

  static types(): { [key: string]: any } {
    return {
      status: "string",
      requestId: "string",
      result: SearchResult,
      errors: { "type": "array", "itemType": Error }
    };
  }

  constructor(map?: { [key: string]: any }) {
    super(map);
  }
}

/**
 * 实际返回结果，包括查询耗时searchtime、引擎总结果数total、本次请求返回结果数num、本次查询最大返回结果数viewtotal、查询结果items、统计结果facet等信息
 */
export class SearchResult extends $tea.Model {
  searchtime?: number;
  total?: number;
  viewtotal?: number;
  num?: number;
  items?: { [key: string]: any }[];
  facet?: SearchResultFacet[];

  static names(): { [key: string]: string } {
    return {
      searchtime: "searchtime",
      total: "total",
      viewtotal: "viewtotal",
      num: "num",
      items: "items",
      facet: "facet"
    };
  }

  static types(): { [key: string]: any } {
    return {
      searchtime: "number",
      total: "number",
      viewtotal: "number",
      num: "number",
      items: { "type": "array", "itemType": "object" },
      facet: { "type": "array", "itemType": SearchResultFacet }
    };
  }

  constructor(map?: { [key: string]: any }) {
    super(map);
  }
}

export class SearchResultItemFullJson extends $tea.Model {
  fields?: { [key: string]: any };
  variableValue?: { [key: string]: any };
  sortExprValues?: number[];

  static names(): { [key: string]: string } {
    return {
      fields: "fields",
      variableValue: "variableValue",
      sortExprValues: "sortExprValues"
    };
  }

  static types(): { [key: string]: any } {
    return {
      fields: { "type": "map", "keyType": "string", "valueType": "any" },
      variableValue: { "type": "map", "keyType": "string", "valueType": "any" },
      sortExprValues: { "type": "array", "itemType": "number" }
    };
  }

  constructor(map?: { [key: string]: any }) {
    super(map);
  }
}

export class SearchResultFacet extends $tea.Model {
  key?: string;
  items?: SearchResultFacetItems[];

  static names(): { [key: string]: string } {
    return {
      key: "key",
      items: "items"
    };
  }

  static types(): { [key: string]: any } {
    return {
      key: "string",
      items: { "type": "array", "itemType": SearchResultFacetItems }
    };
  }

  constructor(map?: { [key: string]: any }) {
    super(map);
  }
}

export class Error extends $tea.Model {
  code?: number;
  message?: string;

  static names(): { [key: string]: string } {
    return {
      code: "code",
      message: "message"
    };
  }

  static types(): { [key: string]: any } {
    return {
      code: "number",
      message: "string"
    };
  }

  constructor(map?: { [key: string]: any }) {
    super(map);
  }
}

/**
 *
 */
export class Document extends $tea.Model {
  cmd: string;
  timestamp?: number;
  fields: { [key: string]: any };

  static names(): { [key: string]: string } {
    return {
      cmd: "cmd",
      timestamp: "timestamp",
      fields: "fields"
    };
  }

  static types(): { [key: string]: any } {
    return {
      cmd: "string",
      timestamp: "number",
      fields: { "type": "map", "keyType": "string", "valueType": "any" }
    };
  }

  constructor(map?: { [key: string]: any }) {
    super(map);
  }
}

/**
 *
 */
export class Behavior extends $tea.Model {
  cmd: string;
  fields: { [key: string]: any };

  static names(): { [key: string]: string } {
    return {
      cmd: "cmd",
      fields: "fields"
    };
  }

  static types(): { [key: string]: any } {
    return {
      cmd: "string",
      fields: { "type": "map", "keyType": "string", "valueType": "any" }
    };
  }

  constructor(map?: { [key: string]: any }) {
    super(map);
  }
}

/**
 * object_id=pk,object_type=ops_search_doc,ops_request_misc=xxx
 */
export class Event2001Args extends $tea.Model {
  objectId?: string;
  objectType?: string;
  opsRequestMisc?: string;

  static names(): { [key: string]: string } {
    return {
      objectId: "object_id",
      objectType: "object_type",
      opsRequestMisc: "ops_request_misc"
    };
  }

  static types(): { [key: string]: any } {
    return {
      objectId: "string",
      objectType: "string",
      opsRequestMisc: "string"
    };
  }

  constructor(map?: { [key: string]: any }) {
    super(map);
  }
}

export class SuggestionResponse extends $tea.Model {
  requestId?: string;
  searchtime?: number;
  suggestions?: SuggestionResponseSuggestions[];
  errors?: Error[];

  static names(): { [key: string]: string } {
    return {
      requestId: "request_id",
      searchtime: "searchtime",
      suggestions: "suggestions",
      errors: "errors"
    };
  }

  static types(): { [key: string]: any } {
    return {
      requestId: "string",
      searchtime: "number",
      suggestions: { "type": "array", "itemType": SuggestionResponseSuggestions },
      errors: { "type": "array", "itemType": Error }
    };
  }

  constructor(map?: { [key: string]: any }) {
    super(map);
  }
}

export class Response extends $tea.Model {
  status?: string;
  requestId?: string;
  errors?: Error[];

  static names(): { [key: string]: string } {
    return {
      status: "status",
      requestId: "request_id",
      errors: "errors"
    };
  }

  static types(): { [key: string]: any } {
    return {
      status: "string",
      requestId: "string",
      errors: { "type": "array", "itemType": Error }
    };
  }

  constructor(map?: { [key: string]: any }) {
    super(map);
  }
}

export class SearchResultFacetItems extends $tea.Model {
  value?: string;
  count?: number;

  static names(): { [key: string]: string } {
    return {
      value: "value",
      count: "count"
    };
  }

  static types(): { [key: string]: any } {
    return {
      value: "string",
      count: "number"
    };
  }

  constructor(map?: { [key: string]: any }) {
    super(map);
  }
}

export class SuggestionResponseSuggestions extends $tea.Model {
  suggestion?: string;

  static names(): { [key: string]: string } {
    return {
      suggestion: "suggestion"
    };
  }

  static types(): { [key: string]: any } {
    return {
      suggestion: "string"
    };
  }

  constructor(map?: { [key: string]: any }) {
    super(map);
  }
}


export default class Client {
  _endpoint: string;
  _protocol: string;
  _userAgent: string;
  _credential: Credential;

  constructor(config: Config) {
    if (Util.isUnset($tea.toMap(config))) {
      throw $tea.newError({
        name: "ParameterMissing",
        message: "'config' can not be unset"
      });
    }

    if (Util.empty(config.type)) {
      config.type = "access_key";
    }

    let credentialConfig = new $Credential.Config({
      accessKeyId: config.accessKeyId,
      type: config.type,
      accessKeySecret: config.accessKeySecret,
      securityToken: config.securityToken
    });
    this._credential = new Credential(credentialConfig);
    this._endpoint = config.endpoint;
    this._protocol = config.protocol;
    this._userAgent = config.userAgent;
  }

  async _request(method: string, pathname: string, query: { [key: string]: any }, headers: { [key: string]: string }, body: any, runtime: $Util.RuntimeOptions): Promise<{ [key: string]: any }> {
    const _runtime: { [key: string]: any } = {
      timeouted: "retry",
      readTimeout: runtime.readTimeout,
      connectTimeout: runtime.connectTimeout,
      httpProxy: runtime.httpProxy,
      httpsProxy: runtime.httpsProxy,
      noProxy: runtime.noProxy,
      maxIdleConns: runtime.maxIdleConns,
      retry: {
        retryable: runtime.autoretry,
        maxAttempts: Util.defaultNumber(runtime.maxAttempts, 3)
      },
      backoff: {
        policy: Util.defaultString(runtime.backoffPolicy, "no"),
        period: Util.defaultNumber(runtime.backoffPeriod, 1)
      },
      ignoreSSL: runtime.ignoreSSL
    };

    let _lastRequest = null;
    let _now = Date.now();
    let _retryTimes = 0;
    while ($tea.allowRetry(_runtime["retry"], _retryTimes, _now)) {
      if (_retryTimes > 0) {
        let _backoffTime = $tea.getBackoffTime(_runtime["backoff"], _retryTimes);
        if (_backoffTime > 0) {
          await $tea.sleep(_backoffTime);
        }
      }

      _retryTimes = _retryTimes + 1;
      try {
        let request_ = new $tea.Request();
        let accesskeyId = await this.getAccessKeyId();
        let accessKeySecret = await this.getAccessKeySecret();
        request_.protocol = Util.defaultString(this._protocol, "HTTP");
        request_.method = method;
        request_.pathname = pathname;
        request_.headers = {
          "user-agent": this.getUserAgent(),
          "Content-Type": "application/json",
          Date: OpenSearchUtil.getDate(),
          host: Util.defaultString(this._endpoint, `opensearch-cn-hangzhou.aliyuncs.com`),
          "X-Opensearch-Nonce": Util.getNonce(),
          ...headers
        };
        if (!Util.isUnset(query)) {
          request_.query = Util.stringifyMapValue(query);
        }

        if (!Util.isUnset(body)) {
          let reqBody = Util.toJSONString(body);
          request_.headers["Content-MD5"] = OpenSearchUtil.getContentMD5(reqBody);
          request_.body = new $tea.BytesReadable(reqBody);
        }

        request_.headers["Authorization"] = OpenSearchUtil.getSignature(request_, accesskeyId, accessKeySecret);
        _lastRequest = request_;
        let response_ = await $tea.doAction(request_, _runtime);

        let objStr = await Util.readAsString(response_.body);
        if (Util.is4xx(response_.statusCode) || Util.is5xx(response_.statusCode)) {
          throw $tea.newError({
            message: response_.statusMessage,
            data: objStr,
            code: response_.statusCode
          });
        }

        let obj = Util.parseJSON(objStr);
        let res = Util.assertAsMap(obj);
        return {
          body: res,
          headers: response_.headers
        };
      } catch (ex) {
        if ($tea.isRetryable(ex)) {
          continue;
        }
        throw ex;
      }
    }

    throw $tea.newUnretryableError(_lastRequest);
  }

  /**
   * 系统提供了丰富的搜索语法以满足用户各种场景下的搜索需求。
   */
  async searchEx(appName: string, request: SearchRequestModel, runtime: $Util.RuntimeOptions): Promise<SearchResponseModel> {
    return $tea.cast<SearchResponseModel>(await this._request("GET", `/v3/openapi/apps/${appName}/search`, $tea.toMap(request.query), request.headers, null, runtime), new SearchResponseModel({}));
  }

  /**
   * 系统提供了丰富的搜索语法以满足用户各种场景下的搜索需求。
   */
  async search(appName: string, request: SearchRequestModel): Promise<SearchResponseModel> {
    let runtime = new $Util.RuntimeOptions({
      connectTimeout: 5,
      autoretry: false,
      ignoreSSL: false,
      maxIdleConns: 50
    });
    return await this.searchEx(appName, request, runtime);
  }

  /**
   * 下拉提示是搜索服务的基础功能，在用户输入查询词的过程中，智能推荐候选query，减少用户输入，帮助用户尽快找到想要的内容。
   */
  async suggestEx(appName: string, modelName: string, request: SuggestRequestModel, runtime: $Util.RuntimeOptions): Promise<SuggestResponseModel> {
    return $tea.cast<SuggestResponseModel>(await this._request("GET", `/v3/openapi/apps/${appName}/suggest/${modelName}/search`, $tea.toMap(request.query), request.headers, null, runtime), new SuggestResponseModel({}));
  }

  /**
   * 下拉提示是搜索服务的基础功能，在用户输入查询词的过程中，智能推荐候选query，减少用户输入，帮助用户尽快找到想要的内容。
   */
  async suggest(appName: string, modelName: string, request: SuggestRequestModel): Promise<SuggestResponseModel> {
    let runtime = new $Util.RuntimeOptions({
      connectTimeout: 5,
      autoretry: false,
      ignoreSSL: false,
      maxIdleConns: 50
    });
    return await this.suggestEx(appName, modelName, request, runtime);
  }

  /**
   * 支持新增、更新、删除 等操作，以及对应批量操作
   */
  async pushDocumentEx(appName: string, tableName: string, request: PushDocumentRequestModel, runtime: $Util.RuntimeOptions): Promise<PushDocumentResponseModel> {
    return $tea.cast<PushDocumentResponseModel>(await this._request("POST", `/v3/openapi/apps/${appName}/${tableName}/actions/bulk`, null, request.headers, request.body, runtime), new PushDocumentResponseModel({}));
  }

  /**
   * 支持新增、更新、删除 等操作，以及对应批量操作
   */
  async pushDocument(appName: string, tableName: string, request: PushDocumentRequestModel): Promise<PushDocumentResponseModel> {
    let runtime = new $Util.RuntimeOptions({
      connectTimeout: 5,
      autoretry: false,
      ignoreSSL: false,
      maxIdleConns: 50
    });
    return this.pushDocumentEx(appName, tableName, request, runtime);
  }

  /**
   * 为了给客户提供更高质量的搜索效果，opensearch目前支持客户通过server端上传点击数据。
   */
  async collectDataEx(appName: string, collectorName: string, request: CollectDataRequestModel, runtime: $Util.RuntimeOptions): Promise<CollectDataResponseModel> {
    return $tea.cast<CollectDataResponseModel>(await this._request("POST", `/v3/openapi/app-groups/${appName}/data-collections/${collectorName}/actions/bulk`, null, request.headers, request.body, runtime), new CollectDataResponseModel({}));
  }

  /**
   * 为了给客户提供更高质量的搜索效果，opensearch目前支持客户通过server端上传点击数据。
   */
  async collectData(appName: string, collectorName: string, request: CollectDataRequestModel): Promise<CollectDataResponseModel> {
    let runtime = new $Util.RuntimeOptions({
      connectTimeout: 5,
      autoretry: false,
      ignoreSSL: false,
      maxIdleConns: 50
    });
    return await this.collectDataEx(appName, collectorName, request, runtime);
  }

  setUserAgent(userAgent: string): void {
    this._userAgent = userAgent;
  }

  appendUserAgent(userAgent: string): void {
    this._userAgent = `${this._userAgent} ${userAgent}`;
  }

  getUserAgent(): string {
    return Util.getUserAgent(this._userAgent);
  }

  async getAccessKeyId(): Promise<string> {
    if (Util.isUnset(this._credential)) {
      return "";
    }

    return this._credential.getAccessKeyId();
  }

  async getAccessKeySecret(): Promise<string> {
    if (Util.isUnset(this._credential)) {
      return "";
    }

    return this._credential.getAccessKeySecret();
  }

}
