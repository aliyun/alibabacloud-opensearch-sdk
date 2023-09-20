package com.aliyun.opensearch.sdk;

import java.util.Random;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.aliyun.opensearch.sdk.generated.app.AppService;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchResult;
import com.aliyun.opensearch.sdk.generated.document.DocumentService;
import com.aliyun.opensearch.sdk.generated.search.OpenSearchSearcherService;
import com.aliyun.opensearch.util.JsonUtil;

public class SdkThriftClient {

	public static void main(String[] args) throws TException, InterruptedException {
		TTransport transport = new TSocket("127.0.0.1", 8181);
		TProtocol protocol = new TBinaryProtocol(transport);

		TMultiplexedProtocol documentServiceProto = new TMultiplexedProtocol(protocol, "documentServiceProcessor");
		TMultiplexedProtocol opensearchSearcherServiceProto = new TMultiplexedProtocol(protocol,
				"opensearchSearcherServiceProcessor");
		DocumentService.Client documentServiceClient = new DocumentService.Client(documentServiceProto);
		OpenSearchSearcherService.Client searcherServiceClient = new OpenSearchSearcherService.Client(
				opensearchSearcherServiceProto);
		TMultiplexedProtocol appServiceProto = new TMultiplexedProtocol(protocol, "appServiceProcessor");
		AppService.Client appServiceClient = new AppService.Client(appServiceProto);

		transport.open();

		OpenSearchResult openSearchResult = appServiceClient.getById("100031803");
		System.out.println(JsonUtil.toPrettyJson(openSearchResult));
		System.out.println(openSearchResult.getResult());

		//List<App> apps = appServiceClient.findAll(new Pageable().setPage(1).setSize(10));
		//System.out.println(JsonUtil.toPrettyJson(apps));

//		App app2 = new App();
//		app2.setQuota(new Quota().setDoc_size(0.2));
//		System.out.println(JsonUtilWrapper.toJson(app2));
//
//		App app3 = appServiceClient.updateById("100031803", app2);
//		System.out.println(JsonUtil.toPrettyJson(app3));

//		appServiceClient.reindexById("100031803");
		Random rand = new Random();
		int id = rand.nextInt(Integer.MAX_VALUE);
		String appName = "myblog";
//		String tableName = "blog_entries";
//		String data = "[{\"cmd\":\"ADD\",\"fields\":{\"content\":\"测试.\",\"id\":" + id
//						+ ",\"title\":\"搜索引擎事业部简介\",\"auth\":\"濒湖\"}}]";
//		documentServiceClient.push(data, appName, tableName);
//
//		Thread.sleep(5 * 1000);
//

//		Config config = new Config(0, 30);
//		config.setSearchFormat(SearchFormat.JSON);
//		SearchParams params = new SearchParams(Lists.newArrayList("appName"), config);
//		params.setQuery("id:'" + id + "'");
//		SearchResult result = searcherServiceClient.execute(params);
//		System.out.println(result.getResult());

		transport.close();
		System.out.println("client closed.");
	}
}