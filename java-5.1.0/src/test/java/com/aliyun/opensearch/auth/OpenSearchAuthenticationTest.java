package com.aliyun.opensearch.auth;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.Maps;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

/**
 *
 * @author Ken
 */
@RunWith(DataProviderRunner.class)
public class OpenSearchAuthenticationTest {

	String method;
	String content_md5;
	String content_type;
	String date;
	TreeMap<String, String> opensearch_headers;
	String request_path;
	TreeMap<String, String> query_params;

	String secret;

	private String expectToSignContents;
	private String expectContentsSign;

	@Before
	public void setUp() throws Exception {

		secret = "ytE24oQRDSAv7mYUSZSlgYfngfaWjo";
		expectContentsSign = "bfBdIYC8w+BGKVZZRlTm7MbT5TU=";

		method = "POST";
		content_md5 = "048e6eab63d11a8f8dc647d7a40151cb";
		content_type = "text/json";
		date = "2016-09-12T04:58:57Z";
		opensearch_headers = Maps.newTreeMap();
		opensearch_headers.put("X-Opensearch-Nonce", "14736563378308720");
		request_path = "/v3/openapi/apps/ICBU_PRODUCT_FULL_TEXT_SEARCH/main/actions/bulk";
		query_params = Maps.newTreeMap();

		StringBuilder b = new StringBuilder();
		b.append("POST").append("\n");
		b.append("048e6eab63d11a8f8dc647d7a40151cb").append("\n");
		b.append("text/json").append("\n");
		b.append("2016-09-12T04:58:57Z").append("\n");
		b.append("x-opensearch-nonce:14736563378308720").append("\n");
		b.append("/v3/openapi/apps/ICBU_PRODUCT_FULL_TEXT_SEARCH/main/actions/bulk");
		expectToSignContents = b.toString();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateToSignContents_request_path_casesensitive() {
		request_path = "/v3/openapi/apps/icbu_product_full_text_search/main/actions/bulk";
		String toSignContents = OpenSearchAuthentication.createToSignContents(method, content_md5, content_type, date,
				opensearch_headers, request_path, query_params);
		assertNotEquals(this.expectToSignContents, toSignContents);
	}

	@Test
	public void testCreateToSignContents() {
		String toSignContents = OpenSearchAuthentication.createToSignContents(method, content_md5, content_type, date,
				opensearch_headers, request_path, query_params);
		assertEquals(this.expectToSignContents, toSignContents);
	}

	@Test
	public void testSignContents() {
		String toSignContents = OpenSearchAuthentication.createToSignContents(method, content_md5, content_type, date,
				opensearch_headers, request_path, query_params);
		String signature = OpenSearchAuthentication.signature(toSignContents, secret);
		assertEquals(this.expectContentsSign, signature);
	}

	@DataProvider
    public static Object[][] createOpenSearchHeadersSecurityTokenDataProvider() {
	    return new Object[][] {
            {"baz", true, "baz"},
            {null, false, null}
        };
    }

	@Test
    @UseDataProvider("createOpenSearchHeadersSecurityTokenDataProvider")
    public void createOpenSearchHeadersSecurityToken(String securityToken, boolean containsHeader, String expected) {
	    String baseUri = "http://foo.bar";
	    String accessKey = "foo";
	    String secret = "bar";
	    long expireTime = 1234;
	    String headerKey = "X-Opensearch-Security-Token";

	    OpenSearchAuthentication openSearchAuthentication
            = new OpenSearchAuthentication(baseUri, accessKey, secret, securityToken);
	    Map<String, String> headers = openSearchAuthentication.createOpenSearchHeaders(expireTime);
	    assertEquals(containsHeader, headers.containsKey(headerKey));
	    assertEquals(expected, headers.get(headerKey));
    }
}
