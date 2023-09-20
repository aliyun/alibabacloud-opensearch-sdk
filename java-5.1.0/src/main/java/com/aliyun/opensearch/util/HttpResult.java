package com.aliyun.opensearch.util;
/**
 * Created by dengwx on 16/9/8.
 */

import java.net.URI;
import java.util.List;

import org.apache.http.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResult {
    private static final Logger LOG = LoggerFactory.getLogger(HttpResult.class);

  private int code;
  private String reason = "";
  private String result = "";

  // debug info
  private URI requestUri;
  private List<Header> requestHeaders;
  private List<Header> responseHeaders;

  public HttpResult() {
  }

  public HttpResult(int code, String reason, String result) {
   this(code,reason,result,null,null,null);
  }

  /**
   * http result with http request &amp; response detail.
   *
 * @param code              response code, eg:200
 * @param reason            response reason, eg: OK
 * @param result            response result
 * @param requestUri        requestUri
 * @param requestHeaders    requestHeaders
 * @param responseHeaders   responseHeaders
 */
public HttpResult(int code, String reason, String result,URI requestUri,
		  List<Header> requestHeaders,List<Header> responseHeaders) {
	    this.code = code;
	    this.reason = reason;
	    this.result = result;
	    this.requestUri = requestUri;
	    this.requestHeaders = requestHeaders;
	    this.responseHeaders = responseHeaders;
  }

  public int getCode() {
    return code;
  }

  public String getReason() {
    return reason;
  }

  public String getResult() {
    return result;
  }

  public URI getRequestUri() {
	return requestUri;
  }

	public List<Header> getRequestHeaders() {
		return requestHeaders;
	}

	public List<Header> getResponseHeaders() {
		return responseHeaders;
	}

@Override
  public String toString() {
    return "HttpResult{" +
            "code=" + code +
            ", reason='" + reason + '\'' +
            ", result='" + result + '\'' +
            '}';
  }
}
