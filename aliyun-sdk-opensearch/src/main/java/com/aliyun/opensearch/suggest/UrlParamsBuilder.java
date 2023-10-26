package com.aliyun.opensearch.suggest;

import java.util.Map;

import com.aliyun.opensearch.sdk.generated.suggestion.ReSearch;
import com.aliyun.opensearch.sdk.generated.suggestion.SuggestParams;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.SyncBasicHttpParams;

/**
 * Query string builder for Suggest
 *
 * @author 隆宇 &lt;yanan.yn@alibaba-inc.com&gt;
 */
public class UrlParamsBuilder {
    private static final String QUERY       = "query";
    private static final String HITS        = "hits";
    private static final String USER_ID     = "user_id";
    private static final String RE_SEARCH   = "re_search";

    /**
     * The http params.
     */
    private SyncBasicHttpParams httpParams;

    public UrlParamsBuilder() {
        this.httpParams = new SyncBasicHttpParams();
    }

    public UrlParamsBuilder(SuggestParams suggestParams) {
        this();
        init(suggestParams);
    }

    public void setQuery(String query) {
        httpParams.setParameter(QUERY, query);
    }

    public void setHits(int hits) {
        httpParams.setParameter(HITS, hits);
    }

    public void setUserId(String userId) {
        httpParams.setParameter(USER_ID, userId);
    }

    public void setReSearch(ReSearch reSearchType) {
        if (reSearchType == null) {
            return;
        }
        String value = reSearchType.toString().toLowerCase();
        httpParams.setParameter(RE_SEARCH, value);
    }

    public void setCustomParam(String key, String value) {
        httpParams.setParameter(key, value);
    }

    /**
     * Get the http params.
     *
     * @return the http params
     */
    public BasicHttpParams getHttpParams() {
        return this.httpParams;
    }

    private void init(SuggestParams suggestParams) {
        initQuery(suggestParams);
        initHits(suggestParams);
        initUserId(suggestParams);
        initReSearch(suggestParams);
        initCustomParams(suggestParams);
    }

    private void initQuery(SuggestParams suggestParams) {
        if (!suggestParams.isSetQuery()) {
            return;
        }
        setQuery(suggestParams.getQuery());
    }
    private void initHits(SuggestParams suggestParams) {
        if (!suggestParams.isSetHits()) {
            return;
        }
        setHits(suggestParams.getHits());
    }
    private void initUserId(SuggestParams suggestParams) {
        if (!suggestParams.isSetUserId()) {
            return;
        }
        setUserId(suggestParams.getUserId());
    }
    private void initReSearch(SuggestParams suggestParams) {
        if (!suggestParams.isSetReSearch()) {
            return;
        }
        setReSearch(suggestParams.getReSearch());
    }
    private void initCustomParams(SuggestParams suggestParams) {
        if (suggestParams.isSetCustomParams()) {
            Map<String, String> customParams = suggestParams.getCustomParams();
            for (Map.Entry<String, String> entry : customParams.entrySet()) {
                setCustomParam(entry.getKey(), entry.getValue());
            }
        }
    }
}
