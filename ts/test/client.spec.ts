'use strict';

import Client, { Config, SearchRequestModel, SearchQuery } from "../src/client";
import * as $tea from "@alicloud/tea-typescript";
import { createServer, Server, IncomingMessage } from 'http';
import { Readable } from 'stream';
import { platform, arch } from 'os';
import { request } from 'httpx';
import 'mocha';
import assert from 'assert';
const pkg = require('../package.json');



describe('search is ok', function () {
  it('it should ok', async function () {
    const appId = process.env.APP_ID;
    const id = process.env.ID;
    const secret = process.env.SECRET;
    const url = process.env.URL;
    const query = `query=desc:'keywords'`;
    const q = [query, '', 'config=format:fulljson,start:0,hit:500'].filter(_ => _).join('&&');
    // 创建请求用客户端实例
    const config = new Config({
      endpoint: url,
      accessKeyId: id,
      accessKeySecret: secret,
    });
    const client = new Client(config);
    const searchResult = new SearchRequestModel();
    const queryString = new SearchQuery();
    queryString.query = q;
    searchResult.headers = {};
    searchResult.query = queryString;
    const response = await client.search(appId, searchResult);
    assert.strictEqual(response.body.status, 'OK');
  });
});
