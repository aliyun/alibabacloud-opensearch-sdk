package com.aliyun.opensearch.auth;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SimpleTimeZone;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.aliyun.opensearch.OpenSearchClient;
import com.aliyun.opensearch.auth.credential.AccessKeyCredentials;
import com.aliyun.opensearch.auth.credential.Credentials;
import com.aliyun.opensearch.auth.credential.StsCredentials;
import com.aliyun.opensearch.auth.credential.provider.CredentialsProvider;
import com.aliyun.opensearch.auth.credential.provider.StaticCredentialsProvider;
import com.aliyun.opensearch.util.Utils;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The OpenSearch Authentication.
 *
 * @author Ken
 */
public class OpenSearchAuthentication implements Authentication {

    private static final Logger LOG = LoggerFactory.getLogger(OpenSearchAuthentication.class);

    private String baseURI;

    private CredentialsProvider credentialsProvider;

    /**
     * Instantiates a new open search authentication.
     *
     * @param baseURI   the base uri
     * @param accessKey 用户阿里云网站中的accesskey,keyYype为ALIYUN使用 此信息阿里云网站中提供
     * @param secret    用户阿里云网站中的secret,keyYype为ALIYUN使用 此信息阿里云网站中提供
     */
    public OpenSearchAuthentication(String baseURI, String accessKey, String secret) {
        this(baseURI, accessKey, secret, null);
    }

    /**
     * Instantiates a new open search authentication.
     *
     * @param securityToken 临时安全令牌
     */
    public OpenSearchAuthentication(String baseURI, String accessKey, String secret,
                                    String securityToken) {
        this.baseURI = baseURI;
        credentialsProvider = new StaticCredentialsProvider(accessKey, secret, securityToken);
    }

    public OpenSearchAuthentication(String baseURI, CredentialsProvider credentialsProvider) {
        this.baseURI = baseURI;
        this.credentialsProvider = credentialsProvider;
    }

    @Override
    public TreeMap<String, String> createOpenSearchHeaders(long expireTime) {
        return createOpenSearchHeaders(expireTime, credentialsProvider.getCredentials());
    }

    @Override
    public TreeMap<String, String> createOpenSearchHeaders(long expireTime, Credentials credentials) {
        TreeMap<String, String> opensearch_headers = new TreeMap<String, String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        opensearch_headers.put("X-Opensearch-Nonce", getNonce());

        if (expireTime > 0) {
            opensearch_headers.put("X-Opensearch-Expire", String.valueOf(expireTime));
        }

        if (credentials instanceof StsCredentials) {
            opensearch_headers.put("X-Opensearch-Security-Token",
                ((StsCredentials)credentials).getSecurityToken());
        }

        return opensearch_headers;
    }

    @Override
    public TreeMap<String, Object> createSignParameters(String method, String request_path,
                                                        TreeMap<String, String> opensearch_headers,
                                                        Map<String, String> params) {
        TreeMap<String, String> sortedQueryParameters = sortParametersByKey(params);
        TreeMap<String, Object> signParameters = new TreeMap<String, Object>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        String content_md5 = "";
        signParameters.put("method", method);
        signParameters.put("request_path", request_path);
        signParameters.put("content_md5", content_md5);
        signParameters.put("content_type", "application/json; charset=utf-8");
        signParameters.put("date", formatIso8601Date(new Date()));//
        signParameters.put("opensearch_headers", opensearch_headers);

        if (method.equals(OpenSearchClient.METHOD_GET)) {
            signParameters.put("query_params", sortedQueryParameters);
        } else {
            if (params != null && !params.isEmpty()) {
                String body = params.get(OpenSearchClient.POST_BODY_PARAM_KEY);
                LOG.debug("BODY_CONTENT: ", body);
                // signParameters.put("content_md5", DigestUtils.md5Hex(body));
                signParameters.put("content_md5", Hashing.md5().hashString(body, Charset.forName("UTF-8")).toString());
            }
        }
        return signParameters;
    }

    @Override
    public String createAliyunSign(TreeMap<String, Object> sortMap) {
        return createAliyunSign(sortMap, credentialsProvider.getCredentials());
    }

    @Override
    public String createAliyunSign(TreeMap<String, Object> sortMap, Credentials credentials) {
        String method = (String)sortMap.get("method");
        String content_md5 = (String)sortMap.get("content_md5");
        String content_type = (String)sortMap.get("content_type");
        String date = (String)sortMap.get("date");
        String request_path = (String)sortMap.get("request_path");
        TreeMap<String, String> query_params = paramsFilter(
            get_query_params((TreeMap<String, String>)sortMap.get("query_params")));
        TreeMap<String, String> opensearch_headers = paramsFilter(
            (TreeMap<String, String>)sortMap.get("opensearch_headers"));

        String string_to_sign = createToSignContents(method, content_md5, content_type, date, opensearch_headers,
            request_path, query_params);
        LOG.debug("\n---------Content to sign: ----------\n", string_to_sign
            , "\n------------------------------------");

        String signature = signature(string_to_sign, getAccessKeySecret(credentials));
        LOG.debug("signature: ", signature);

        return signature;
    }

    private static String getAccessKeySecret(Credentials credentials) {
        AccessKeyCredentials accessKeyCredentials = (AccessKeyCredentials)credentials;
        return accessKeyCredentials.getAccessKeySecret();
    }

    private static String getAccessKeyId(Credentials credentials) {
        AccessKeyCredentials accessKeyCredentials = (AccessKeyCredentials)credentials;
        return accessKeyCredentials.getAccessKeyId();
    }

    /**
     * Creates the to sign contents.
     * public FOR TESTING .
     *
     * @param method             the method
     * @param content_md5        the content_md5
     * @param content_type       the content_type
     * @param date               the date
     * @param request_path       the request_path
     * @param query_params       the query_params
     * @param opensearch_headers the opensearch_headers
     * @return the string
     */
    @VisibleForTesting
    protected static String createToSignContents(String method, String content_md5, String content_type, String date,
                                                 TreeMap<String, String> opensearch_headers, String request_path,
                                                 TreeMap<String, String> query_params) {
        StringBuilder string_to_sign = new StringBuilder();
        string_to_sign.append(method.toUpperCase()).append("\n");
        string_to_sign.append(content_md5).append("\n");
        string_to_sign.append(content_type).append("\n");
        string_to_sign.append(date).append("\n");

        for (Entry<String, String> entry : opensearch_headers.entrySet()) {
            String header_key = entry.getKey().toLowerCase();
            String header_value = entry.getValue();
            string_to_sign.append(header_key).append(":").append(header_value).append("\n");
        }

        String signable_resource = Utils.percentEncode(request_path).replaceAll("%2F", "/");
        List<String> query_params_kvs = Lists.newLinkedList();
        for (Entry<String, String> entry : query_params.entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue();
            String encodeKey = Utils.percentEncode(k);
            String encodeValue = Utils.percentEncode(v);
            StringBuilder s = new StringBuilder();
            query_params_kvs.add(s.append(encodeKey).append("=").append(encodeValue).toString());
        }

        String query_params_string = Joiner.on('&').join(query_params_kvs);
        String canonicalizedResource = signable_resource;
        if (!query_params_string.isEmpty()) {
            canonicalizedResource += "?" + query_params_string;
        }
        string_to_sign.append(canonicalizedResource);
        return string_to_sign.toString();
    }

    @Override
    public Map<String, String> createHttpHeaders(TreeMap<String, String> opensearch_headers,
                                                 TreeMap<String, Object> signParameters, String signature) {
        return createHttpHeaders(opensearch_headers, signParameters, signature, credentialsProvider.getCredentials());
    }

    public Map<String, String> createHttpHeaders(TreeMap<String, String> opensearch_headers,
                                                 TreeMap<String, Object> signParameters, String signature,
                                                 Credentials credentials) {
        Map<String, String> headers = Maps.newLinkedHashMap();
        headers.putAll(opensearch_headers);
        headers.put("Content-Type", (String)signParameters.get("content_type"));
        headers.put("Date", (String)signParameters.get("date"));
        headers.put("Accept-Language", "zh-cn");
        headers.put("Content-Md5", (String)signParameters.get("content_md5"));
        String authorization = "OPENSEARCH " + getAccessKeyId(credentials) + ":" + signature;
        headers.put("Authorization", authorization);
        return headers;
    }

    public CredentialsProvider getCredentialsProvider() {
        return credentialsProvider;
    }

    private static TreeMap<String, String> sortParametersByKey(Map<String, String> params) {
        TreeMap<String, String> sortedParameters = new TreeMap<String, String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        if (params != null) {
            sortedParameters.putAll(params);
        }
        return sortedParameters;
    }

    @VisibleForTesting
    protected static String signature(String toSignContent, String secret) {
        Preconditions.checkNotNull(secret);
        byte[] keyBytes;
        try {
            keyBytes = secret.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("JVM NOT SUPPORT UTF-8?");
        }
        SecretKey secretKeySpec = new SecretKeySpec(keyBytes, "HmacSHA1");
        Mac mac;
        try {
            mac = Mac.getInstance("HmacSHA1");
        } catch (NoSuchAlgorithmException e1) {
            throw new RuntimeException("JVM NOT SUPPORT Algorithm: HmacSHA1?");
        }
        try {
            mac.init(secretKeySpec);
        } catch (InvalidKeyException e1) {
            throw new RuntimeException(e1);
        }
        byte[] text;
        try {
            text = toSignContent.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("JVM NOT SUPPORT UTF-8?");
        }
        return BaseEncoding.base64().encode(mac.doFinal(text));
    }

    private static TreeMap<String, String> paramsFilter(TreeMap<String, String> params) {
        if (params.containsKey("Signature")) { params.remove("Signature"); }
        List<String> emptyValueKeys = new LinkedList<String>();
        for (Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if ("".equals(value)) { emptyValueKeys.add(key); }
        }
        for (String key : emptyValueKeys) {
            params.remove(key);
        }
        return params;
    }

    private static TreeMap<String, String> get_query_params(TreeMap<String, String> object) {
        if (null == object) { return new TreeMap<String, String>(); }
        return (TreeMap<String, String>)object;
    }

    /**
     * 生成当前的nonce值。
     *
     * @return 返回生成的nonce串。
     */
    private static String getNonce() {
        long timestamp = System.nanoTime();
        int randInt = Utils.randInt(10000, 99999999);
        return new StringBuilder().append(timestamp).append(randInt).toString();
    }

    private static final String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * 生成符合规范的TimeStamp字符串
     *
     * @param date 时间
     * @return
     */
    private static String formatIso8601Date(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(ISO8601_DATE_FORMAT);
        df.setTimeZone(new SimpleTimeZone(0, "GMT"));
        return df.format(date);
    }
}
