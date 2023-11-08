import { assert } from "chai";
import { Client, SearchQuery } from "../src";

const { appName, appId, secret, endpoint } = process.env;

describe("search", function () {
  it("it should ok", async function () {
    const query = `query=keywords:'白菜'`;
    // 创建请求用客户端实例
    const config = {
      appName,
      endpoint,
      accessKeyId: appId,
      accessKeySecret: secret
    };
    const client = new Client(config);
    const search: SearchQuery = {
      query,
      raw_query: "白菜",
      config: {
        hit: 500,
        start: 0,
        format: "fulljson"
      }
    };
    const res = await client.search(search);
    assert(res.status, "OK");
  });
});
