import { assert } from "chai";
import { Client, Document } from "../src";

const { appName, appId, secret, endpoint } = process.env;

describe("Document", () => {
  it("pushDocument", async () => {
    // 创建请求用客户端实例
    const client = new Client({
      appName,
      endpoint,
      accessKeyId: appId,
      accessKeySecret: secret
    });
    const docs: Document[] = [
      {
        cmd: "add",
        fields: { sku: 123, env: "staging" }
      }
    ];
    const res = await client.pushDocument("products", docs);
    assert(res.status, "OK");
  });
});
