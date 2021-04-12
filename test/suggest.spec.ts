import { assert } from "chai";
import { Client } from "../src";

const { appName, appId, secret, endpoint } = process.env;

describe("suggest", () => {
  it("suggest", async () => {
    // 创建请求用客户端实例
    const config = {
      appName,
      endpoint,
      accessKeyId: appId,
      accessKeySecret: secret
    };
    const client = new Client(config);
    const query = { query: "bc", hit: 10 };
    const res = await client.suggest("Goods", query);
    assert.hasAllKeys(res, ["request_id", "searchtime", "suggestions"]);
  });
});
