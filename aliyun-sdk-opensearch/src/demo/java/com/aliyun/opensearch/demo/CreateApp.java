package com.aliyun.opensearch.demo;

import com.aliyun.opensearch.AppClient;
import com.aliyun.opensearch.OpenSearchClient;
import com.aliyun.opensearch.sdk.generated.OpenSearch;
import com.aliyun.opensearch.sdk.generated.app.App;
import com.aliyun.opensearch.sdk.generated.app.Quota;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchResult;
import com.aliyun.opensearch.util.JsonUtilWrapper;

public class CreateApp {

	private String accesskey = "";
	private String secret = "";
	private String host = "";

	void create() throws Throwable {
		OpenSearch openSearch = new OpenSearch(accesskey, secret, host);
		OpenSearchClient serviceClient = new OpenSearchClient(openSearch);

		AppClient appClient = new AppClient(serviceClient);
		App app = new App();
		app.setName("appName");
		app.setQuota(new Quota().setDoc_size(100).setCompute_resource(6).setSpec("opensearch.share.common"));

		// create app
		OpenSearchResult openSearchResult = appClient.save(JsonUtilWrapper.toJson(app));

		// result to App object
		App resultApp = JsonUtilWrapper.fromJson(openSearchResult.getResult(), App.class);
	}

	/**
	 * @param args
	 * @throws Throwable
	 */
	public static void main(String[] args) throws Throwable {
		CreateApp creator = new CreateApp();
		creator.create();
	}

}
