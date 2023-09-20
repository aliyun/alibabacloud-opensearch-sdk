package com.aliyun.opensearch.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.aliyun.opensearch.sdk.generated.search.DeepPaging;
import com.aliyun.opensearch.sdk.generated.search.Rank;
import com.aliyun.opensearch.sdk.generated.search.RankType;
import com.aliyun.opensearch.sdk.generated.search.SearchFormat;
import com.aliyun.opensearch.sdk.generated.search.SearchParams;
import com.aliyun.opensearch.sdk.generated.search.Summary;
import com.aliyun.opensearch.sdk.generated.search.Summary._Fields;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.SyncBasicHttpParams;

import static com.aliyun.opensearch.sdk.generated.search.OpenSearchSearcherConstants.*;

/**
 * UrlParamsBuilder.
 *
 * @author Ken
 */
public class UrlParamsBuilder {

	/** The Constant QUERY. */
	private static final String QUERY = "query";

	/** The Constant FORMAT. */
	private static final String FORMAT = "format";

	/** The Constant FIRST_RANK_NAME. */
	private static final String FIRST_RANK_NAME = "first_rank_name";

	/** The Constant SECOND_RANK_NAME. */
	private static final String SECOND_RANK_NAME = "second_rank_name";

	/** The Constant SECOND_RANK_TYPE. */
	private static final String SECOND_RANK_TYPE = "second_rank_type";

	/** The Constant SUMMARY. */
	private static final String SUMMARY = "summary";

	/** The Constant FETCH_FIELDS. */
	private static final String FETCH_FIELDS = "fetch_fields";

	/** The Constant QP. */
	private static final String QP = "qp";

	/** The Constant DISABLE. */
	private static final String DISABLE = "disable";

	/** The Constant PROTOBUF. */
	private static final String PROTOBUF = "protobuf";

	/** The Constant ROUTE_VALUE. */
	private static final String ROUTE_VALUE = "route_value";

	/** The Constant SCROLL_EXPIRE. */
	private static final String SCROLL_EXPIRE = "scroll";

	/** The Constant SCROLL_ID. */
	private static final String SCROLL_ID = "scroll_id";

	/** The Constant SEARCH_TYPE. */
	private static final String SEARCH_TYPE = "search_type";

	/** The Constant VALUE_SPLITTER_FETCHFIELDS. */
	private static final String VALUE_SPLITTER_FETCHFIELDS = ";";

	/** The Constant VALUE_SPLITTER_QP. */
	private static final String VALUE_SPLITTER_QP = ";";

	/** The Constant VALUE_SPLITTER_DISABLEFUNCTIONS. */
	private static final String VALUE_SPLITTER_DISABLEFUNCTIONS = ";";

	/** The Constant VALUE_SPLITTER_DISABLEFUNCTIONS_KEYVALUE. */
	private static final String VALUE_SPLITTER_DISABLEFUNCTIONS_KEYVALUE = ":";

	/** The Constant SEARCH_TYPE_SCAN. */
	private static final String SEARCH_TYPE_SCAN = "scan";

    /** The Constant ABTEST. */
    private static final String ABTEST = "abtest";

	/** The http params. */
	private SyncBasicHttpParams httpParams;

	/**
	 * Instantiates a new url params builder.
	 *
	 * @param searchParams
	 *            the search params
	 */
	public UrlParamsBuilder(SearchParams searchParams) {
		this();
		init(searchParams);
	}

	/**
	 * Instantiates a new url params builder.
	 */
	public UrlParamsBuilder() {
		this.httpParams = new SyncBasicHttpParams();
	}

	/**
	 * Inits the builder with SearchParams.
	 *
	 * @param searchParams
	 *            the search params
	 */
	private void init(SearchParams searchParams) {
		initQuery(searchParams);
		initScroll(searchParams);
		initRank(searchParams);
		initSuggest(searchParams);
		initFormat(searchParams);
		initFetchFields(searchParams);
		initSummary(searchParams);
		initRouteValue(searchParams);
		initCustomParams(searchParams);
		initQueryProcessorNames(searchParams);
		initDisableFunctions(searchParams);

        initAbtest(searchParams);
        initUserId(searchParams);
        initRawQuery(searchParams);
	}

	/**
	 * e.g: /v3/openapi/suggests/jcj_jcfenci/jname/search?hit=5&query=%27%E4%B8%AD%27&format=json
	 *
	 * @param searchParams the search params
	 */
	private void initSuggest(SearchParams searchParams) {
		if (searchParams.isSetSuggest()) {
            setCustomParam("hit", Integer.toString(searchParams.getConfig().getHits()));
        }
	}

	/**
	 * Inits the ranks: FirstRank and SecondRank.
	 *
	 * @param searchParams
	 *            the search params
	 */
	private void initRank(SearchParams searchParams) {
		initFirstRankName(searchParams);
        initSecondRankName(searchParams);
        initSecondRankType(searchParams);
	}

	/**
	 * Inits the scroll.
	 *
	 * @param searchParams
	 *            the search params
	 */
	private void initScroll(SearchParams searchParams) {
		if (searchParams.isSetDeepPaging()) {
			setSearchType(SEARCH_TYPE_SCAN);
			DeepPaging deepPaging = searchParams.getDeepPaging();
			setScrollExpire(deepPaging.getScrollExpire());
			setScrollId(deepPaging.getScrollId());
		}
	}

	/**
	 * Inits the format.
	 *
	 * @param searchParams
	 *            the search params
	 */
	private void initFormat(SearchParams searchParams) {
		SearchFormat format = searchParams.getConfig().getSearchFormat();
		Preconditions.checkArgument(null != format, "SearchFormat not specified.");
		setFormat(format.toString().toLowerCase());
	}

	interface QueryUrlParamBuilder {
		String build(SearchParams searchParams);
	}

	/**
	 * Inits the query.
	 *
	 * @param searchParams
	 *            the search params
	 */
	private void initQuery(SearchParams searchParams) {
		AbstractSubClausesBuilder subClausesBuilder;
		if (searchParams.isSetDeepPaging()) {
			subClausesBuilder = new ScrollingSubClausesBuilder(searchParams);
		} else if (searchParams.isSetSuggest()) {
			subClausesBuilder = new SuggestSubClausesBuilder(searchParams);
		} else {
			subClausesBuilder = new SearchingSubClausesBuilder(searchParams);
		}
		setQuery(subClausesBuilder.getClausesString());
	}

	/**
	 * Inits the first rank name.
	 *
	 * @param searchParams
	 *            the search params
	 */
	private void initFirstRankName(SearchParams searchParams) {
		if (searchParams.isSetRank()) {
			Rank rank = searchParams.getRank();
			if (rank.isSetFirstRankName()) {
                setFirstRankName(rank.getFirstRankName());
            }
		}
	}

	/**
	 * Inits the second rank name.
	 *
	 * @param searchParams
	 *            the search params
	 */
	private void initSecondRankName(SearchParams searchParams) {
		if (searchParams.isSetRank()) {
			Rank rank = searchParams.getRank();
			if (rank.isSetSecondRankName()) {
                setSecondRankName(rank.getSecondRankName());
            }
		}
    }

    /**
     * Inits the second rank type.
     *
     * @param searchParams
     */
    private void initSecondRankType(SearchParams searchParams) {
		if (searchParams.isSetRank()) {
			Rank rank = searchParams.getRank();
			if (rank.isSetSecondRankType()) {
                setSecondRankType(rank.getSecondRankType().toString().toLowerCase());
            }
		}
    }

	/**
	 * Inits the fetch fields.
	 *
	 * @param searchParams
	 *            the search params
	 */
	void initFetchFields(SearchParams searchParams) {
		if (searchParams.getConfig().isSetFetchFields()) {
			List<String> fetchFields = searchParams.getConfig().getFetchFields();
			setFetchFields(Joiner.on(VALUE_SPLITTER_FETCHFIELDS).join(fetchFields));
		}
	}

	/**
	 * Inits the query processor names.
	 *
	 * @param searchParams
	 *            the search params
	 */
	void initQueryProcessorNames(SearchParams searchParams) {
		if (searchParams.isSetQueryProcessorNames()) {
			setQueryProcessorNames(Joiner.on(VALUE_SPLITTER_QP).join(searchParams.getQueryProcessorNames()));
		}
	}

	/**
	 * Inits the disable functions.
	 *
	 * @param searchParams
	 *            the search params
	 */
	void initDisableFunctions(SearchParams searchParams) {
		if (searchParams.isSetDisableFunctions()) {
			Map<String, String> disableFunctions = searchParams.getDisableFunctions();
			setDisableFunctions(Joiner.on(VALUE_SPLITTER_DISABLEFUNCTIONS)
					.withKeyValueSeparator(VALUE_SPLITTER_DISABLEFUNCTIONS_KEYVALUE).join(disableFunctions));
		}
	}

	/**
	 * Inits the summary.
	 *
	 * @param searchParams
	 *            the search params
	 */
	private void initSummary(SearchParams searchParams) {
		if (searchParams.isSetSummaries()) {
			Set<Summary> summaries = searchParams.getSummaries();
			List<String> summariesStrings = Lists.newLinkedList();
			for (Summary summary : summaries) {
				List<String> summaryStrings = Lists.newLinkedList();
				Set<_Fields> summaryFieldsSet = Summary.metaDataMap.keySet();
				for (_Fields _Fields : summaryFieldsSet) {
					Object fieldValue = summary.getFieldValue(_Fields);
					if (null != fieldValue) {
						summaryStrings.add(_Fields.getFieldName() + ":" + fieldValue);
					}
				}
				summariesStrings.add(Joiner.on(",").join(summaryStrings));
			}
			setSummary(Joiner.on(";").join(summariesStrings));
		}
	}

    /**
     * 初始化 abtest 参数
     * @param searchParams
     */
    private void initAbtest(SearchParams searchParams) {
        if (searchParams.isSetAbtest()) {
            List<String> abtestParams = new ArrayList<String>();

            if (searchParams.getAbtest().isSetSceneTag()) {
                abtestParams.add(String.format("%s:%s", ABTEST_PARAM_SCENE_TAG, searchParams.getAbtest().getSceneTag()));
            }

            if (searchParams.getAbtest().isSetFlowDivider()) {
                abtestParams.add(String.format("%s:%s", ABTEST_PARAM_FLOW_DIVIDER,  searchParams.getAbtest().getFlowDivider()));
            }

            if (!abtestParams.isEmpty()) {
                setAbtest(StringUtils.join(abtestParams, ","));
            }
        }
    }

    /**
     * 初始化 user_id 参数
     * user_id：终端用户的id，用来统计uv信息
     *
     * @param searchParams
     */
    private void initUserId(SearchParams searchParams) {
        if (searchParams.isSetUserId()) {
            setUserId(searchParams.getUserId());
        }
    }

    /**
     * 初始化 raw_query 参数
     * raw_query：终端用户输入的query, value的值urlencode
     *
     * @param searchParams
     */
    private void initRawQuery(SearchParams searchParams) {
        if (searchParams.isSetRawQuery()) {
            setRawQuery(searchParams.getRawQuery());
        }
    }

	/**
	 * Inits the route value.
	 *
	 * @param searchParams
	 *            the search params
	 */
	private void initRouteValue(SearchParams searchParams) {
		if (searchParams.getConfig().isSetRouteValue()) {
			setRouteValue(searchParams.getConfig().getRouteValue());
		}
	}

	/**
	 * Inits the custom params.
	 *
	 * @param searchParams
	 *            the search params
	 */
	private void initCustomParams(SearchParams searchParams) {
		if (searchParams.isSetCustomParam()) {
			Map<String, String> customParam = searchParams.getCustomParam();
			for (Entry<String, String> entry : customParam.entrySet()) {
				setCustomParam(entry.getKey(), entry.getValue());
			}
		}
	}

	/**
	 * Gets the http params.
	 *
	 * @return the http params
	 */
	public BasicHttpParams getHttpParams() {
		return this.httpParams;
	}

	/**
	 * Adds the ha3Query.
	 *
	 * @param queryParamValue
	 *            the query param value
	 */
	public void setQuery(String queryParamValue) {
		httpParams.setParameter(QUERY, queryParamValue);
	}

	/**
	 * Sets the format.
	 *
	 * @param formatParamValue
	 *            the new format
	 */
	public void setFormat(String formatParamValue) {
		httpParams.setParameter(FORMAT, formatParamValue);
	}

	/**
	 * Sets the first rank name.
	 *
	 * @param firstRankNameParamValue
	 *            the new first rank name
	 */
	public void setFirstRankName(String firstRankNameParamValue) {
		httpParams.setParameter(FIRST_RANK_NAME, firstRankNameParamValue);
	}

	/**
	 * Sets the second rank name.
	 *
	 * @param secondRankNameParamValue
	 *            the new second rank name
	 */
	public void setSecondRankName(String secondRankNameParamValue) {
		httpParams.setParameter(SECOND_RANK_NAME, secondRankNameParamValue);
	}

	/**
	 * Sets the second rank type.
	 *
	 * @param secondRankTypeParamValue
	 *            the new second rank name
	 */
	public void setSecondRankType(String secondRankTypeParamValue) {
		httpParams.setParameter(SECOND_RANK_TYPE, secondRankTypeParamValue);
    }

	/**
	 * Sets the summary.
	 *
	 * @param summaryParamValue
	 *            the new summary
	 */
	public void setSummary(String summaryParamValue) {
		httpParams.setParameter(SUMMARY, summaryParamValue);
	}

    public void setAbtest(String abtestParamValue) {
        httpParams.setParameter(ABTEST, abtestParamValue);
    }

    public void setUserId(String userId) {
        httpParams.setParameter(USER_ID, userId);
    }

    public void setRawQuery(String rawQuery) {
        httpParams.setParameter(RAW_QUERY, rawQuery);
    }

	/**
	 * Sets the fetch fields, each fetch field value is splitted by ';' .
	 *
	 * @param fetchFieldsParamValue
	 *            the new fetch fields
	 */
	public void setFetchFields(String fetchFieldsParamValue) {
		httpParams.setParameter(FETCH_FIELDS, fetchFieldsParamValue);
	}

	/**
	 * Sets the query processor names.
	 *
	 * @param queryProcessorNamesParamValue
	 *            the new query processor names
	 */
	public void setQueryProcessorNames(String queryProcessorNamesParamValue) {
		httpParams.setParameter(QP, queryProcessorNamesParamValue);
	}

	/**
	 * Sets the disable functions.
	 *
	 * @param disableFunctionsParamValue
	 *            the new disable functions
	 */
	public void setDisableFunctions(String disableFunctionsParamValue) {
		httpParams.setParameter(DISABLE, disableFunctionsParamValue);
	}

	/**
	 * Sets the route value.
	 *
	 * @param routeValue
	 *            the new route value
	 */
	public void setRouteValue(String routeValue) {
		httpParams.setParameter(ROUTE_VALUE, routeValue);
	}

	/**
	 * Sets the scroll expire.
	 *
	 * @param scrollExpireValue
	 *            the new scroll expire
	 */
	public void setScrollExpire(String scrollExpireValue) {
		httpParams.setParameter(SCROLL_EXPIRE, scrollExpireValue);
	}

	/**
	 * Sets the scroll id.
	 *
	 * @param scrollIdValue
	 *            the new scroll id
	 */
	public void setScrollId(String scrollIdValue) {
		httpParams.setParameter(SCROLL_ID, scrollIdValue);
	}

	/**
	 * Sets the search type.
	 *
	 * @param searchTypeValue
	 *            the new search type
	 */
	public void setSearchType(String searchTypeValue) {
		httpParams.setParameter(SEARCH_TYPE, searchTypeValue);
	}

	/**
	 * Sets the custom param.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public void setCustomParam(String key, String value) {
		httpParams.setParameter(key, value);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		BasicHttpParams httpParams = this.getHttpParams();
		Set<String> names = httpParams.getNames();
		StringBuilder b = new StringBuilder();
		for (String name : names) {
			b.append(StringUtils.rightPad(name, 15, " ") + "=" + httpParams.getParameter(name)).append("\n");
		}
		return b.toString();
	}
}
