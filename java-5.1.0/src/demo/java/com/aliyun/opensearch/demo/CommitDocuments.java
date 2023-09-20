package com.aliyun.opensearch.demo;

import java.util.Map;
import java.util.Random;

import com.aliyun.opensearch.DocumentClient;
import com.aliyun.opensearch.OpenSearchClient;
import com.aliyun.opensearch.sdk.generated.OpenSearch;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchClientException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchException;
import com.google.common.collect.Maps;

/**
 * The demo CommitDocuments.
 *
 * @author Ken
 */
public class CommitDocuments {

	private String appName = "";
	private String tableName = "";
	private String accesskey = "";
	private String secret = "";
	private String host = "";

	void addAndCommit() {

		// 用基础信息创建OpenSearch
		OpenSearch openSearch = new OpenSearch(accesskey, secret, host);

		// 创建OpenSearchClient
		OpenSearchClient serviceClient = new OpenSearchClient(openSearch);

		// 创建DocumentClient
		DocumentClient documentClient = new DocumentClient(serviceClient);

		// 创建第1个document
		Map<String, Object> doc1 = Maps.newLinkedHashMap();
		doc1.put("id", new Random().nextInt(Integer.MAX_VALUE));
		doc1.put("title", "开放搜索简介");
		doc1.put("auth", "Michael");
		doc1.put("content", "开放搜索（OpenSearch）是一款结构化数据搜索托管服务.");

		// 把doc1加入缓存
		documentClient.add(doc1);

		// 创建第1个document
		Map<String, Object> doc2 = Maps.newLinkedHashMap();
		doc2.put("id", new Random().nextInt(Integer.MAX_VALUE));
		doc2.put("title", "阿里云简介");
		doc2.put("auth", "Jean");
		doc2.put("content", "阿里云，阿里巴巴集团旗下云计算品牌，全球卓越的云计算技术和服务提供商.");

		// 把doc2加入缓存
		documentClient.add(doc2);

		// 提交缓存的所有documents.
		try {
			documentClient.commit(appName, tableName);
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
		CommitDocuments push = new CommitDocuments();
		push.addAndCommit();
	}

}
