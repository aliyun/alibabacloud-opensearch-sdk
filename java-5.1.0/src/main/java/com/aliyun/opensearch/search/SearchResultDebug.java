package com.aliyun.opensearch.search;

import java.net.URL;
import java.util.List;

import org.apache.http.Header;

import com.aliyun.opensearch.sdk.generated.search.general.SearchResult;

/**
 * SearchResult with Debug.
 *
 * @author ken
 */
public class SearchResultDebug {

	/** The search result. */
	private SearchResult searchResult;

	/** The request url. */
	private URL requestUrl;

	/** The request headers. */
	private List<Header> requestHeaders;

	/** The response headers. */
	private List<Header> responseHeaders;

	/** The status code. */
	private int statusCode;

	/** The reason phrase. */
	private String reasonPhrase;

	/**
	 * Instantiates a new search result debug.
	 *
	 * @param searchResult
	 *            the search result
	 * @param requestUrl
	 *            the request url
	 * @param requestHeaders
	 *            the request headers
	 * @param responseHeaders
	 *            the response headers
	 * @param statusCode
	 *            the status code
	 * @param reasonPhrase
	 *            the reason phrase
	 */
	public SearchResultDebug(SearchResult searchResult, URL requestUrl, List<Header> requestHeaders,
			List<Header> responseHeaders, int statusCode, String reasonPhrase) {
		super();
		this.searchResult = searchResult;
		this.requestUrl = requestUrl;
		this.requestHeaders = requestHeaders;
		this.responseHeaders = responseHeaders;
		this.statusCode = statusCode;
		this.reasonPhrase = reasonPhrase;
	}

	/**
	 * Gets the search result.
	 *
	 * @return the search result
	 */
	public SearchResult getSearchResult() {
		return searchResult;
	}

	/**
	 * Gets the request url.
	 *
	 * @return the request url
	 */
	public URL getRequestUrl() {
		return requestUrl;
	}

	/**
	 * Gets the request headers.
	 *
	 * @return the request headers
	 */
	public List<Header> getRequestHeaders() {
		return requestHeaders;
	}

	/**
	 * Gets the response headers.
	 *
	 * @return the response headers
	 */
	public List<Header> getResponseHeaders() {
		return responseHeaders;
	}

	/**
	 * Gets the status code.
	 *
	 * @return the status code
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * Gets the reason phrase.
	 *
	 * @return the reason phrase
	 */
	public String getReasonPhrase() {
		return reasonPhrase;
	}

}
