// This file is auto-generated, don't edit it. Thanks.
package com.aliyun.opensearchutil;

import com.aliyun.tea.*;
import com.aliyun.tea.utils.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;


public class Client {

    /**
     * Append a string into a string arrat
     * @example append(["a", "b"], "c") => ["a", "b", "c"]
     * @param strings the string list
     * @param item the string to be appended
     * @return new array
     */
    public static java.util.List<String> append(java.util.List<String> strings, String item) throws Exception {
        strings.add(item);
        return strings;
    }

    /**
     * get the map's keys
     * @param m the map
     * @return new array
     */
    public static java.util.List<String> keys(java.util.Map<String, ?> m) throws Exception {
        return new ArrayList(m.keySet());
    }

    /**
     * Join array elements with a spearator string.
     * @example join(["a", "b"], ".") => "a.b"
     * @param strings the string list
     * @param separator the separator
     * @return the joined string
     */
    public static String join(java.util.List<String> strings, String separator) throws Exception {
        return String.join(separator, strings);
    }

    /**
     * Get date.
     * @example 2006-01-02T15:04:05Z
     * @return date string
     */
    public static String getDate() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(new SimpleTimeZone(0, "GMT"));
        return df.format(new Date());
    }

    /**
     * Get signature with request, accesskeyId, accessKeySecret.
     * @param request the object of tea request
     * @param accessKeyId accesskey id
     * @param accessKeySecret accesskey secret
     * @return signature string
     */
    public static String getSignature(TeaRequest request, String accessKeyId, String accessKeySecret) throws Exception {
        String signToString = getSignToString(request);
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec(accessKeySecret.getBytes("UTF-8"), "HmacSHA1"));
        byte[] signData = mac.doFinal(signToString.getBytes("UTF-8"));
        String signedStr = DatatypeConverter.printBase64Binary(signData);
        return "OPENSEARCH " + accessKeyId + ":" + signedStr;
    }

    /**
     * Get content MD5.
     * @param content the string which will be calculated
     * @return md5 string
     */
    public static String getContentMD5(String content) throws Exception {
        byte[] secretBytes;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(content.getBytes("UTF-8"));
        secretBytes = md.digest();
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    /**
     * Splice origin into string with sep.
     * @param origin the source map
     * @param sep the spearator
     * @return the spliced string
     */
    public static String mapToString(java.util.Map<String, String> origin, String sep) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry: origin.entrySet()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append(sep);
            stringBuilder.append(entry.getValue());
            stringBuilder.append(",");
        }
        return stringBuilder.deleteCharAt(stringBuilder.length()-1).toString();
    }

    public static String getCanonicalizedOpenSearchHeaders(TeaRequest request) {
        Map<String, String> temp = new HashMap<>();
        for (Map.Entry<String, String> entry : request.headers.entrySet()) {
            if (entry.getKey().toLowerCase().startsWith("x-opensearch")) {
                if (null != entry.getValue()) {
                    temp.put(entry.getKey().toLowerCase(), entry.getValue());
                }
            }
        }
        String[] sortedKeys = temp.keySet().toArray(new String[]{});
        Arrays.sort(sortedKeys);
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : sortedKeys) {
            stringBuilder.append(key);
            stringBuilder.append(":");
            stringBuilder.append(temp.get(key));
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public static String getSignToString(TeaRequest request) {
        String verb = request.method;
        String contentMd5 = request.headers.get("content-md5");
        String contentType = request.headers.get("content-type");
        String date = request.headers.get("date");
        String canonicalizedOpenSearchHeaders = getCanonicalizedOpenSearchHeaders(request);
        String canonicalizedResource = getCanonicalizedResource(request);
        if (null == contentType) {
            contentType = "";
        }
        if (null == contentMd5) {
            contentMd5 = "";
        }
        return verb + "\n" + contentMd5 + "\n" + contentType + "\n" + date + "\n" + canonicalizedOpenSearchHeaders + canonicalizedResource;
    }

    public static String getCanonicalizedResource(TeaRequest request) {
        StringBuilder resource = new StringBuilder(request.pathname);
        if (request.query.size() > 0) {
            resource.append("?");
        }
        for (Map.Entry<String, String> entry : request.query.entrySet()) {
            if (!StringUtils.isEmpty(entry.getValue()) && !"null".equals(entry.getValue())) {
                resource.append("&");
                resource.append(entry.getKey());
                resource.append("=");
                resource.append(entry.getValue());
            }
        }
        return resource.toString();
    }
}
