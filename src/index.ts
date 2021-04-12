import Credential, * as $Credential from "@alicloud/credentials";
import OpenSearchUtil from "./util";
import * as $tea from "@alicloud/tea-typescript";
import Util, * as $Util from "@alicloud/tea-util";
import { SearchQuery, SearchResponse } from "./interface/search";
import { SuggestQuery, SuggestResponse } from "./interface/suggest";
import { Behavior } from "./interface/collect";
import { Document } from "./interface/document";
import { HotQuery, HotResponse } from "./interface/hot";
import { Config, Response } from "./interface/base";
// import { snakeCase } from "lodash";

export * from "./interface/collect";
export * from "./interface/hot";
export * from "./interface/document";
export * from "./interface/search";
export * from "./interface/suggest";
export * from "./interface/base";

export class Client {
  readonly #endpoint: string;
  readonly #protocol: string;
  readonly #userAgent: string;
  readonly #credential: Credential;
  readonly #appName: string;

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

    const credentialConfig = new $Credential.Config({
      accessKeyId: config.accessKeyId,
      type: config.type,
      accessKeySecret: config.accessKeySecret,
      securityToken: config.securityToken
    });
    this.#credential = new Credential(credentialConfig);
    this.#endpoint = config.endpoint;
    this.#protocol = config.protocol;
    this.#userAgent = config.userAgent;
    this.#appName = config.appName;
  }

  private async request(method: string, pathname: string, query: { [key: string]: any }, body: any, runtime: $Util.RuntimeOptions): Promise<{ [key: string]: any }> {
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
        let accessKeyId = await this.getAccessKeyId();
        let accessKeySecret = await this.getAccessKeySecret();
        request_.protocol = Util.defaultString(this.#protocol, "HTTP");
        request_.method = method;
        request_.pathname = pathname;
        request_.headers = {
          "user-agent": this.getUserAgent(),
          "Content-Type": "application/json",
          "X-Opensearch-Nonce": Util.getNonce(),
          Date: new Date().toISOString().replace(/\.\d+/, ""),
          host: Util.defaultString(this.#endpoint, `opensearch-cn-hangzhou.aliyuncs.com`)
        };
        if (!Util.isUnset(query)) {
          request_.query = query;
        }

        if (!Util.isUnset(body)) {
          let reqBody = Util.toJSONString(body);
          request_.headers["Content-MD5"] = OpenSearchUtil.getContentMD5(reqBody);
          request_.body = new $tea.BytesReadable(reqBody);
        }

        request_.headers["Authorization"] = OpenSearchUtil.getSignature(request_, accessKeyId, accessKeySecret);
        _lastRequest = request_;
        const res = await $tea.doAction(request_, _runtime);

        let objStr = await Util.readAsString(res.body);
        if (Util.is4xx(res.statusCode) || Util.is5xx(res.statusCode)) {
          throw new Error(res.statusMessage);
        }

        let obj = Util.parseJSON(objStr);
        return {
          headers: res.headers,
          body: Util.assertAsMap(obj)
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

  /*
   * 系统提供了丰富的搜索语法以满足用户各种场景下的搜索需求。
   */
  async searchEx(search: SearchQuery, runtime: $Util.RuntimeOptions): Promise<SearchResponse> {
    const { query, config = { start: 0, hit: 20, format: "fulljson" } } = search;
    //Config子句
    const conf = Object.entries(config).map(([k, v]) => `${k}=${v}`).join(",");
    search.query = [query, `config=${conf}`].join("&&");
    delete search.config;
    const { body } = await this.request("GET", `/v3/openapi/apps/${this.#appName}/search`, search, null, runtime);
    return body as SearchResponse;
  }

  /*
   * 系统提供了丰富的搜索语法以满足用户各种场景下的搜索需求。
   */
  async search(query: SearchQuery): Promise<SearchResponse> {
    let runtime = new $Util.RuntimeOptions({
      connectTimeout: 5,
      autoretry: false,
      ignoreSSL: false,
      maxIdleConns: 50
    });
    return this.searchEx(query, runtime);
  }

  /*
   * 下拉提示是搜索服务的基础功能，在用户输入查询词的过程中，智能推荐候选query，减少用户输入，帮助用户尽快找到想要的内容。
   */
  async suggestEx(modelName: string, query: SuggestQuery, runtime: $Util.RuntimeOptions): Promise<SuggestResponse> {
    const { body } = await this.request("GET", `/v3/openapi/apps/${this.#appName}/suggest/${modelName}/search`, query, null, runtime);
    return body as SuggestResponse;
  }

  /*
   * 下拉提示是搜索服务的基础功能，在用户输入查询词的过程中，智能推荐候选query，减少用户输入，帮助用户尽快找到想要的内容。
   */
  async suggest(modelName: string, query: SuggestQuery): Promise<SuggestResponse> {
    let runtime = new $Util.RuntimeOptions({
      connectTimeout: 5,
      autoretry: false,
      ignoreSSL: false,
      maxIdleConns: 50
    });
    return await this.suggestEx(modelName, query, runtime);
  }

  /*
  * 热搜列表
  * */
  async hot(query: HotQuery): Promise<HotResponse> {
    let runtime = new $Util.RuntimeOptions({
      connectTimeout: 5,
      autoretry: false,
      ignoreSSL: false,
      maxIdleConns: 50
    });

    const res = await this.request("GET", `/v3/openapi/apps/${this.#appName}/actions/hot`, query, null, runtime);
    return res?.body as HotResponse;
  }

  /*
   * 支持新增、更新、删除 等操作，以及对应批量操作
   */
  async pushDocumentEx(tableName: string, data: Document[], runtime: $Util.RuntimeOptions): Promise<Response> {
    const res = await this.request("POST", `/v3/openapi/apps/${this.#appName}/${tableName}/actions/bulk`, null, data, runtime);
    console.log(res);
    return res?.body as Response;
  }

  /*
   * 支持新增、更新、删除 等操作，以及对应批量操作
   */
  async pushDocument(tableName: string, data: Document[]): Promise<Response> {
    let runtime = new $Util.RuntimeOptions({
      connectTimeout: 5,
      autoretry: false,
      ignoreSSL: false,
      maxIdleConns: 50
    });
    return this.pushDocumentEx(tableName, data, runtime);
  }

  /*
   * 为了给客户提供更高质量的搜索效果，opensearch目前支持客户通过server端上传点击数据。
   */
  async collectDataEx(collectorName: string, data: Behavior, runtime: $Util.RuntimeOptions): Promise<Response> {
    const { body } = await this.request("POST", `/v3/openapi/app-groups/${this.#appName}/data-collections/${collectorName}/actions/bulk`, null, data, runtime);
    return body as Response;
  }

  /*
   * 为了给客户提供更高质量的搜索效果，opensearch目前支持客户通过server端上传点击数据。
   */
  async collectData(collectorName: string, data: Behavior): Promise<Response> {
    let runtime = new $Util.RuntimeOptions({
      connectTimeout: 5,
      autoretry: false,
      ignoreSSL: false,
      maxIdleConns: 50
    });
    return await this.collectDataEx(collectorName, data, runtime);
  }

  getUserAgent(): string {
    return Util.getUserAgent(this.#userAgent);
  }

  async getAccessKeyId(): Promise<string> {
    if (Util.isUnset(this.#credential)) {
      return "";
    }

    return this.#credential.getAccessKeyId();
  }

  async getAccessKeySecret(): Promise<string> {
    if (Util.isUnset(this.#credential)) {
      return "";
    }

    return this.#credential.getAccessKeySecret();
  }

}
