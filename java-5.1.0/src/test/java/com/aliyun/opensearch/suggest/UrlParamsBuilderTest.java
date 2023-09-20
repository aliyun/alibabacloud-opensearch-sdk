package com.aliyun.opensearch.suggest;

import java.util.HashMap;
import java.util.Map;

import com.aliyun.opensearch.sdk.generated.search.Suggest;
import com.aliyun.opensearch.sdk.generated.suggestion.ReSearch;
import com.aliyun.opensearch.sdk.generated.suggestion.SuggestParams;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(DataProviderRunner.class)
public class UrlParamsBuilderTest {

    @DataProvider
    public static Object[][] setQueryDataProvider() {
        return new Object[][] {
            {"foo", "foo"},
            {"阿里巴巴", "阿里巴巴"},
            {null, null}
        };
    }
    @Test
    @UseDataProvider("setQueryDataProvider")
    public void setQuery(String query, String expected) {
        String paramKey = "query";

        // Directly
        UrlParamsBuilder urlParamsBuilder = new UrlParamsBuilder();
        urlParamsBuilder.setQuery(query);
        String actual = (String)urlParamsBuilder.getHttpParams().getParameter(paramKey);
        assertEquals(expected, actual);

        // via SuggestParams
        SuggestParams suggestParams = new SuggestParams();
        suggestParams.setQuery(query);
        urlParamsBuilder = new UrlParamsBuilder(suggestParams);
        actual = (String)urlParamsBuilder.getHttpParams().getParameter(paramKey);
        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] setHitsDataProvider() {
        return new Object[][] {
            {123, 123}
        };
    }
    @Test
    @UseDataProvider("setHitsDataProvider")
    public void setHits(int hits, int expected) {
        String paramKey = "hit";

        // Directly
        UrlParamsBuilder urlParamsBuilder = new UrlParamsBuilder();
        urlParamsBuilder.setHits(hits);
        int actual = Integer.valueOf(urlParamsBuilder.getHttpParams().getParameter(paramKey).toString());
        assertEquals(expected, actual);

        // via SuggestParams
        SuggestParams suggestParams = new SuggestParams();
        suggestParams.setHits(hits);
        urlParamsBuilder = new UrlParamsBuilder(suggestParams);
        actual = Integer.valueOf(urlParamsBuilder.getHttpParams().getParameter(paramKey).toString());
        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] setUserIdDataProvider() {
        return new Object[][] {
            {"foo", "foo"},
            {"阿里巴巴", "阿里巴巴"},
            {null, null}
        };
    }
    @Test
    @UseDataProvider("setUserIdDataProvider")
    public void setUserId(String userId, String expected) {
        String paramKey = "user_id";

        // Directly
        UrlParamsBuilder urlParamsBuilder = new UrlParamsBuilder();
        urlParamsBuilder.setUserId(userId);
        String actual = (String)urlParamsBuilder.getHttpParams().getParameter(paramKey);
        assertEquals(expected, actual);

        // via SuggestParams
        SuggestParams suggestParams = new SuggestParams();
        suggestParams.setUserId(userId);
        urlParamsBuilder = new UrlParamsBuilder(suggestParams);
        actual = (String)urlParamsBuilder.getHttpParams().getParameter(paramKey);
        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] setReSearchDataProvider() {
        return new Object[][] {
            {ReSearch.HOMONYM, "homonym"},
            {ReSearch.DISABLE, "disable"},
            {null, null}
        };
    }
    @Test
    @UseDataProvider("setReSearchDataProvider")
    public void setReSearch(ReSearch reSearch, String expected) {
        String paramKey = "re_search";

        // Directly
        UrlParamsBuilder urlParamsBuilder = new UrlParamsBuilder();
        urlParamsBuilder.setReSearch(reSearch);
        String actual = (String)urlParamsBuilder.getHttpParams().getParameter(paramKey);
        assertEquals(expected, actual);

        // via SuggestParams
        SuggestParams suggestParams = new SuggestParams();
        suggestParams.setReSearch(reSearch);
        urlParamsBuilder = new UrlParamsBuilder(suggestParams);
        if (expected == null) {
            assertFalse(urlParamsBuilder.getHttpParams().isParameterSet(paramKey));
        } else {
            actual = (String)urlParamsBuilder.getHttpParams().getParameter(paramKey);
            assertEquals(expected, actual);
        }
    }
    @DataProvider
    public static Object[][] setCustomParamDataProvider() {
        return new Object[][] {
            {"bar", "foo", "foo"},
            {"baz", "阿里巴巴", "阿里巴巴"},
            {"bax", null, null}
        };
    }
    @Test
    @UseDataProvider("setCustomParamDataProvider")
    public void setCustomParam(final String paramKey, final String customParam, String expected) {
        // Directly
        UrlParamsBuilder urlParamsBuilder = new UrlParamsBuilder();
        urlParamsBuilder.setCustomParam(paramKey, customParam);
        String actual = (String)urlParamsBuilder.getHttpParams().getParameter(paramKey);
        assertEquals(expected, actual);

        // via SuggestParams
        Map<String, String> params = new HashMap<String, String>() {{
            put(paramKey, customParam);
        }};
        SuggestParams suggestParams = new SuggestParams();
        suggestParams.setCustomParams(params);
        urlParamsBuilder = new UrlParamsBuilder(suggestParams);
        if (expected == null) {
            assertFalse(urlParamsBuilder.getHttpParams().isParameterSet(paramKey));
        } else {
            actual = (String)urlParamsBuilder.getHttpParams().getParameter(paramKey);
            assertEquals(expected, actual);
        }
    }
}
