import { assert } from "chai";
import { Client, HotQuery } from "../src";

const { appId, secret, endpoint, appName } = process.env;

describe("hot", () => {
  it("list", async () => {
    const config = {
      appName,
      endpoint,
      accessKeyId: appId,
      accessKeySecret: secret
    };
    const client = new Client(config);
    const query: HotQuery = {
      user_id: "1",
      model_name: "Goods"
    };
    const body = await client.hot(query);
    assert.hasAllKeys(body, ["request_id", "searchtime", "result"]);
  });
});
