package com.aliyun.opensearch.search;

import java.util.Iterator;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.opensearch.SearcherClient;
import com.aliyun.opensearch.sdk.generated.search.SearchParams;
import com.aliyun.opensearch.sdk.generated.search.general.SearchResult;
import com.google.common.base.Preconditions;

/**
 * <p>DeepPageing Iterator.</p>
 *
 * <p>Provide a easy way to paging and retrieve itmes on every page.</p>
 *
 * NOTE: default paging intervals is 100 ms.
 *
 * @author Ken
 */
public class DeepPageingIterator implements Iterator<String> {

	private static final Logger LOG = LoggerFactory.getLogger(DeepPageingIterator.class);

	/** The searcher client. */
	private SearcherClient searcherClient;

	/** The search params. */
	private SearchParams searchParams;

	/** The cursor mark. */
	private String cursorMark;

	/** The remain. */
	private int remain;

	/** The next. */
	private String next;

	/** The paging intervals. */
	private long pagingIntervals = 100l;

	/**
	 * Instantiates a new deep pageing iterator.
	 *
	 * @param searcherClient the searcher client
	 * @param searchParams the search params
	 */
	public DeepPageingIterator(SearcherClient searcherClient, SearchParams searchParams) {
		super();
		this.searcherClient = searcherClient;
		this.searchParams = new SearchParams(searchParams);
	}

	/**
	 * Sets the paging intervals. this can be efficiently limit paging frequent.
	 *
	 * @param pageIntervals the new page intervals in milliseconds.
	 */
	public void setPagingIntervals(long pageIntervals) {
		this.pagingIntervals = pageIntervals;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		if (null == cursorMark) {
			SearchResult searchResult;
			try {
				searchResult = searcherClient.execute(searchParams);
				String result = searchResult.getResult();
				JSONObject json = new JSONObject(result);
				String status = json.getString("status");
				JSONObject resultPart = (JSONObject) json.get("result");
				int total = resultPart.getInt("total");
				String next_scroll_id = resultPart.getString("scroll_id");

				this.remain = total;
				if ("OK".equals(status)) {
					if (remain == 0) {
						LOG.warn("no result found.");
					} else {
						cursorMark = next_scroll_id;
					}
				} else {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
		}

		if (remain == 0)
			return false;

		try {
			Thread.sleep(this.pagingIntervals);
		} catch (InterruptedException e1) {
		}

		// retrieve current result and perpare for next.
		Preconditions.checkNotNull(cursorMark);
		searchParams.getDeepPaging().setScrollId(cursorMark);
		SearchResult searchResult;
		try {
			searchResult = searcherClient.execute(searchParams);
			String result = searchResult.getResult();
			JSONObject json = new JSONObject(result);
			String status = json.getString("status");
			JSONObject resultPart = (JSONObject) json.get("result");
			int total = resultPart.getInt("total");
			int num = resultPart.getInt("num");
			String next_scroll_id = resultPart.getString("scroll_id");

			this.remain = total;
			this.next = result;
			if ("OK".equals(status)) {
				if (remain == 0) {
					LOG.warn("no result remain for next paging.");
					return false;
				} else if (num == 0) {
					LOG.warn("result found, but shows nothing.");
					return false;
				} else {
					cursorMark = next_scroll_id;
					return true;
				}
			} else {
				remain = -1;
				return true;
			}
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	@Override
	public String next() {
		Preconditions.checkNotNull(next);
		return next;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove() {
	}

}
