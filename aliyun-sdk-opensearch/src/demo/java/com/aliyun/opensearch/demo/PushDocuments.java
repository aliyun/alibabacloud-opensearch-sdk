package com.aliyun.opensearch.demo;

import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import com.aliyun.opensearch.DocumentClient;
import com.aliyun.opensearch.OpenSearchClient;
import com.aliyun.opensearch.sdk.generated.OpenSearch;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchClientException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchException;
import com.aliyun.opensearch.sdk.generated.document.Command;
import com.aliyun.opensearch.sdk.generated.document.DocumentConstants;
import com.google.common.collect.Maps;

/**
 * The demo PushDocuments.
 *
 * @author Ken
 */
public class PushDocuments {

	private String appName = "";
	private String tableName = "";
	private String accesskey = "";
	private String secret = "";
	private String host = "";

	void push() {

		//创建第1个document
		Map<String, Object> doc1 = Maps.newLinkedHashMap();
		doc1.put("id", new Random().nextInt(Integer.MAX_VALUE));
		doc1.put("title", "开放搜索简介");
		doc1.put("auth", "Michael");
		doc1.put("content", "开放搜索（OpenSearch）是一款结构化数据搜索托管服务.");

		JSONObject json1 = new JSONObject();
		json1.put(DocumentConstants.DOC_KEY_CMD, Command.ADD.toString());
		json1.put(DocumentConstants.DOC_KEY_FIELDS, doc1);

		//创建第1个document
		Map<String, Object> doc2 = Maps.newLinkedHashMap();
		doc2.put("id", new Random().nextInt(Integer.MAX_VALUE));
		doc2.put("title", "阿里云简介");
		doc2.put("auth", "Jean");
		doc2.put("content", "阿里云，阿里巴巴集团旗下云计算品牌，全球卓越的云计算技术和服务提供商.");

		JSONObject json2 = new JSONObject();
		json2.put(DocumentConstants.DOC_KEY_CMD, Command.ADD.toString());
		json2.put(DocumentConstants.DOC_KEY_FIELDS, doc2);

		JSONArray docs = new JSONArray();
		docs.put(json1);
		docs.put(json2);
		String docsJson = docs.toString();

		// 用基础信息创建OpenSearch
		OpenSearch openSearch = new OpenSearch(accesskey, secret, host);

		// 创建OpenSearchClient
		OpenSearchClient serviceClient = new OpenSearchClient(openSearch);

		// 创建DocumentClient
		DocumentClient documentClient = new DocumentClient(serviceClient);

		try {
			documentClient.push(docsJson, appName, tableName);
		} catch (OpenSearchException e) {
			e.printStackTrace();
		} catch (OpenSearchClientException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PushDocuments pushDocuments = new PushDocuments();
		pushDocuments.push();
	}

}
