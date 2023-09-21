package com.aliyun.opensearch.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;

import com.aliyun.opensearch.OpenSearchClient;
import org.apache.http.params.BasicHttpParams;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * The static Utils.
 *
 * @author Ken
 */
public class Utils {

	private static Random rand = new Random();

	public static int randInt(int min, int max) {
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	public static String getHTTPParamsAsUrlStr(String method, Map<String, String> params) {
		if (params == null || params.size() <= 0) {
			return "";
		}

        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.putAll(params);
        queryParams.remove(OpenSearchClient.POST_BODY_PARAM_KEY);
		List<String> paramstring = Lists.newLinkedList();
		for (Entry<String, String> entry : queryParams.entrySet()) {
			String key = percentEncode(entry.getKey());
			String value = percentEncode(entry.getValue());
			StringBuilder sb = new StringBuilder();
			paramstring.add(sb.append(key).append("=").append(value).toString());
		}
		return "?" + Joiner.on("&").join(paramstring);
	}

	public static String urlEncodeWithUTF8(String value) {
		if (null == value)
			return null;
		String encode = "UTF-8";
		try {
			return URLEncoder.encode(value, encode);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("JVM DOES NOT SUPPORT " + encode + "?");
		}
	}

	public static String percentEncode(String value) {
		if (null == value)
			return null;
		return urlEncodeWithUTF8(value).replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
	}

	public static String normalize(String str) {
		StringBuilder sb = new StringBuilder();
		sb.append(str.endsWith("/") ? str.substring(0, str.length() - 1) : str);
		return sb.toString();
	}


    /**
     * httpParams to map.
     *
     * @param httpParams the http params
     * @return the map
     */
    public static Map<String, String> toMap(BasicHttpParams httpParams) {
        Map<String, String> map = Maps.newLinkedHashMap();
        Set<String> names = httpParams.getNames();
        for (String name : names) {
            map.put(name, httpParams.getParameter(name).toString());
        }
        return map;
    }
}
