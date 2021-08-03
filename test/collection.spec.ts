import { assert } from "chai";
import { Client, SearchQuery } from "../src";
import { CollectionFields } from "../types";

const { appId, secret, endpoint, appName } = process.env;

describe("collection", () => {
  it("collect", async () => {
    const config = {
      appName,
      endpoint,
      accessKeyId: appId,
      accessKeySecret: secret
    };
    const client = new Client(config);
    const search: SearchQuery = {
      query: `query=keywords:'土豆'`,
      raw_query: "土豆",
      config: {
        hit: 1,
        start: 0,
        format: "fulljson"
      },
      fetch_fields: ["id", "sku"]
    };
    const res = await client.search(search);
    const [item] = res.result.items;
    const fields: CollectionFields = {
      user_id: "1",
      rn: res.request_id,
      trace_info: res.ops_request_misc,
      biz_id: "1",
      bhv_type: "click",
      bhv_time: "1",
      bhv_detail: "",
      item_id: item.fields.id,
      item_type: "goods"
    };
    const body = await client.collection("BEHAVIOR", [fields]);
    assert.isTrue(body.result);
  });
});
