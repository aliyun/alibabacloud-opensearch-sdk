package com.aliyun.opensearch.auth;

import com.aliyun.opensearch.auth.credential.Credentials;

import java.util.Map;
import java.util.TreeMap;

public interface Authentication {

	TreeMap<String, String> createOpenSearchHeaders(long expireTime);

    /**
     * 使用指定的认证凭证构建OpenSearch请求头
     */
    default TreeMap<String, String> createOpenSearchHeaders(long expireTime, Credentials credentials) {
        return createOpenSearchHeaders(expireTime);
    }

	TreeMap<String, Object> createSignParameters(String method, String request_path,
			TreeMap<String, String> opensearch_headers, Map<String, String> params);

	String createAliyunSign(TreeMap<String, Object> signParameters);

    /**
     * 使用指定的认证凭证构建签名字符串
     */
    default String createAliyunSign(TreeMap<String, Object> signParameters, Credentials credentials) {
        return createAliyunSign(signParameters);
    }

	Map<String, String> createHttpHeaders(TreeMap<String, String> opensearch_headers,
			TreeMap<String, Object> signParameters, String aliyunSignature);

    /**
     * 使用指定的认证凭证构建请求头
     */
    default Map<String, String> createHttpHeaders(TreeMap<String, String> opensearch_headers,
                                          TreeMap<String, Object> signParameters, String aliyunSignature,
                                          Credentials credentials) {
        return createHttpHeaders(opensearch_headers, signParameters, aliyunSignature);
    }

}
