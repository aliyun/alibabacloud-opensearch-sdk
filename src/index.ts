import Credential, * as $Credential from "@alicloud/credentials";
import { Util } from "./util";
import { SearchQuery, SearchResponse } from "./interface/search";
import { SuggestQuery, SuggestResponse } from "./interface/suggest";
import { CollectionFields, CollectionRes, CollectionType } from "./interface/collection";
import { Document } from "./interface/document";
import { HotQuery, HotResponse } from "./interface/hot";
import { Config, Response } from "./interface/base";
import axios, { AxiosRequestConfig, Method } from "axios";

export * from "./interface/collection";
export * from "./interface/hot";
export * from "./interface/document";
export * from "./interface/search";
export * from "./interface/suggest";
export * from "./interface/base";

export class Client {
  readonly #axios = axios;
  readonly #appName: string;
  readonly #credential: Credential;

  constructor(config: Config) {
    this.#appName = config.appName;
    this.#axios.defaults.baseURL = config.endpoint;
    if (config.timeout) {
      this.#axios.defaults.timeout = config.timeout;
    }

    const credentialConfig = new $Credential.Config({
      type: "access_key",
      accessKeyId: config.accessKeyId,
      accessKeySecret: config.accessKeySecret,
      securityToken: config.securityToken
    });
    this.#credential = new Credential(credentialConfig);
  }

  private async request<T>(method: Method, url: string, options: Pick<AxiosRequestConfig, "params" | "data">): Promise<T> {
    const accessKeyId = await this.#credential.getAccessKeyId();
    const accessKeySecret = await this.#credential.getAccessKeySecret();

    const { params = {}, data } = options;
    const conf: AxiosRequestConfig = {
      url,
      data,
      params,
      method,
      headers: {
        "Content-Type": "application/json",
        "X-Opensearch-Nonce": Util.genNonce(),
        Date: new Date().toISOString().replace(/\.\d+/, "")
      }
    };

    if (data) {
      conf.headers["Content-MD5"] = Util.getContentMD5(data);
    }

    conf.headers.Authorization = Util.getSignature(conf, accessKeyId, accessKeySecret);
    const res = await this.#axios.request<T>(conf);
    return res?.data;
  }

  /*
   * 系统提供了丰富的搜索语法以满足用户各种场景下的搜索需求。
   */
  async searchEx(search: SearchQuery): Promise<SearchResponse> {
    const { fetch_fields, query, config = { start: 0, hit: 20, format: "fulljson" } } = search;
    //Config子句
    const conf = Object.entries(config).map(([k, v]) => `${k}:${v}`).join(",");
    search.query = [query, `config=${conf}`].join("&&");
    delete search.config;
    // fetch_fields
    if (Array.isArray(fetch_fields)) {
      search.fetch_fields = fetch_fields.join(";");
    }

    return this.request<SearchResponse>("GET", `/v3/openapi/apps/${this.#appName}/search`, { params: search });
  }

  /*
   * 系统提供了丰富的搜索语法以满足用户各种场景下的搜索需求。
   */
  async search(query: SearchQuery): Promise<SearchResponse> {
    return this.searchEx(query);
  }

  /*
   * 下拉提示是搜索服务的基础功能，在用户输入查询词的过程中，智能推荐候选query，减少用户输入，帮助用户尽快找到想要的内容。
   */
  async suggestEx(modelName: string, params: SuggestQuery): Promise<SuggestResponse> {
    return this.request<SuggestResponse>("GET", `/v3/openapi/apps/${this.#appName}/suggest/${modelName}/search`, { params });
  }

  /*
   * 下拉提示是搜索服务的基础功能，在用户输入查询词的过程中，智能推荐候选query，减少用户输入，帮助用户尽快找到想要的内容。
   */
  async suggest(modelName: string, query: SuggestQuery): Promise<SuggestResponse> {
    return this.suggestEx(modelName, query);
  }

  /*
  * 热搜列表
  * */
  async hot(params: HotQuery): Promise<HotResponse> {
    return this.request<HotResponse>("GET", `/v3/openapi/apps/${this.#appName}/actions/hot`, { params });
  }

  /*
   * 支持新增、更新、删除 等操作，以及对应批量操作
   */
  async pushDocumentEx(tableName: string, data: Document[]): Promise<Response> {
    return this.request<Response>("POST", `/v3/openapi/apps/${this.#appName}/${tableName}/actions/bulk`, { data });
  }

  /*
   * 支持新增、更新、删除 等操作，以及对应批量操作
   */
  async pushDocument(tableName: string, data: Document[]): Promise<Response> {
    return this.pushDocumentEx(tableName, data);
  }

  /*
   * 为了给客户提供更高质量的搜索效果，OpenSearch目前支持客户通过server端上传点击数据。
   */
  async collection(type: CollectionType, docs: CollectionFields[]): Promise<CollectionRes> {
    const data = docs.map(fields => {
      fields.trace_id = fields.trace_id || "Alibaba";
      return { cmd: "ADD", fields };
    });

    return this.request<CollectionRes>("POST", `/v3/openapi/app-groups/${this.#appName}/data-collections/${this.#appName}/data-collection-type/${type}/actions/bulk`, { data });
  }
}
