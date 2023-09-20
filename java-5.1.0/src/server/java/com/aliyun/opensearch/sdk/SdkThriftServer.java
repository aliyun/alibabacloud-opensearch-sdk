package com.aliyun.opensearch.sdk;

import java.net.InetSocketAddress;

import com.aliyun.opensearch.AppClient;
import com.aliyun.opensearch.sdk.generated.app.AppService;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportFactory;

import com.aliyun.opensearch.DocumentClient;
import com.aliyun.opensearch.OpenSearchClient;
import com.aliyun.opensearch.exceptions.InvalidParameterException;
import com.aliyun.opensearch.sdk.generated.OpenSearch;
import com.aliyun.opensearch.sdk.generated.OpenSearchService;
import com.aliyun.opensearch.sdk.generated.document.DocumentService;
import com.aliyun.opensearch.sdk.generated.search.OpenSearchSearcherService;
import com.aliyun.opensearch.SearcherClient;

public class SdkThriftServer {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) throws InvalidParameterException {

		TMultiplexedProcessor multiplexedProcessor = new TMultiplexedProcessor();
		if (args.length < 4) {
			throw new RuntimeException("accessKey secret host port");
		}
		//String accessKey = "Z2S85EGADatXoHwG";
		//String secret = "2q3e1lj5Dsx0ISsXhQM7Ad1FTbykdm";
		//String host = "http://106.11.30.131";

		String accessKey = args[0];
		String secret = args[1];
		String host = args[2];
		int port = Integer.valueOf(args[3]);

		OpenSearch opensearch = new OpenSearch(accessKey, secret, host);
		OpenSearchClient openSearchClient = new OpenSearchClient(opensearch);

		OpenSearchService.Processor opensearchServiceprocessor = new OpenSearchService.Processor(openSearchClient);

		SearcherClient searcherClient = new SearcherClient(openSearchClient);
		OpenSearchSearcherService.Processor opensearchSearcherServiceProcessor = new OpenSearchSearcherService.Processor(searcherClient);

		DocumentClient documentClient = new DocumentClient(openSearchClient);
		DocumentService.Processor documentServiceprocessor = new DocumentService.Processor(documentClient);

		AppClient appClient = new AppClient(openSearchClient);
		AppService.Processor appServiceProcessor = new AppService.Processor(appClient);
		multiplexedProcessor.registerProcessor("opensearchServiceProcessor", opensearchServiceprocessor);
		multiplexedProcessor.registerProcessor("opensearchSearcherServiceProcessor", opensearchSearcherServiceProcessor);
		multiplexedProcessor.registerProcessor("documentServiceProcessor", documentServiceprocessor);
		multiplexedProcessor.registerProcessor("appServiceProcessor", appServiceProcessor);

		try {
			TServerTransport serverTransport = new TServerSocket(new InetSocketAddress("0.0.0.0", port));
			Args trArgs = new Args(serverTransport);
			trArgs.processor(multiplexedProcessor);
			trArgs.protocolFactory(new TBinaryProtocol.Factory(true, true));
			trArgs.transportFactory(new TTransportFactory());
			TServer server = new TThreadPoolServer(trArgs);
			System.out.println("starting server...");
			server.serve();
			server.stop();
		} catch (Exception e) {
			throw new RuntimeException("index thrift server start failed!!" + "/n" + e.getMessage());
		}
	}
}