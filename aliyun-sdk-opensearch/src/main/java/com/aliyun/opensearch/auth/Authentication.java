package com.aliyun.opensearch.auth;

import java.util.Map;
import java.util.TreeMap;

public interface Authentication {

	TreeMap<String, String> createOpenSearchHeaders(long expireTime);

	TreeMap<String, Object> createSignParameters(String method, String request_path,
			TreeMap<String, String> opensearch_headers, Map<String, String> params);

	String createAliyunSign(TreeMap<String, Object> signParameters);

	Map<String, String> createHttpHeaders(TreeMap<String, String> opensearch_headers,
			TreeMap<String, Object> signParameters, String aliyunSignature);

}
