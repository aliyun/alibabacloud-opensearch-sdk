package com.aliyun.opensearch.search;

import java.util.Map;
import java.util.Set;

import com.aliyun.opensearch.sdk.generated.search.Config;
import com.aliyun.opensearch.sdk.generated.search.Distinct;
import com.aliyun.opensearch.sdk.generated.search.SearchParams;
import com.aliyun.opensearch.sdk.generated.search.Summary;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * Convenience util to build some complex search params.
 *
 * @author Ken
 */
public class SearchParamsBuilder {

	/** The search params. */
	private SearchParams searchParams;

	/**
	 * Creates a new SearchParamsBuilder onject.
	 *
	 * @param config the config
	 * @return the search params builder
	 */
	public static SearchParamsBuilder create(Config config) {
		return new SearchParamsBuilder(config);
	}

	/**
	 * Creates a new SearchParamsBuilder onject with other SearchParams.
	 *
	 * @param otherSearchParams the other search params
	 * @return the search params builder
	 */
	public static SearchParamsBuilder create(SearchParams otherSearchParams) {
		return new SearchParamsBuilder(otherSearchParams);
	}

	/**
	 * Instantiates a new search params builder.
	 */
	private SearchParamsBuilder(Config config) {
		this.searchParams = new SearchParams(config);
	}

	/**
	 * Instantiates a new search params builder.
	 *
	 * @param otherSearchParams the other search params
	 */
	private SearchParamsBuilder(SearchParams otherSearchParams) {
		super();
		this.searchParams = otherSearchParams;
	}

	/**
	 * Adds the custom param. <p>
	 * Both key and value are not accept null value.
	 * @param key the key
	 * @param value the value
     * @return the search params builder
	 */
	public SearchParamsBuilder addCustomParam(String key, String value) {
		Preconditions.checkNotNull(key);
		Preconditions.checkNotNull(value);
		Map<String, String> customParam;
		if (this.searchParams.isSetCustomParam()) {
			customParam = this.searchParams.getCustomParam();
			Preconditions.checkArgument(!customParam.containsKey(key), "the CustomParam is already added. key: " + key);
		} else {
			customParam = Maps.newHashMap();
		}
		customParam.put(key, value);
		this.searchParams.setCustomParam(customParam);
		return this;
	}

	/**
	 * 增加过滤规则(filter)(1).
	 *
	 * @param filter 过滤规则，例如fieldName &lt;= 1。
	 * @param operator 操作符，可以为 AND OR。默认为“AND”
     * @return the search params builder
	 */
	public SearchParamsBuilder addFilter(String filter, String operator) {
		if (operator == null) {
			operator = "AND";
		}
		if (searchParams.isSetFilter()) {
			String f = searchParams.getFilter();
			filter = new StringBuilder(f).append(" ").append(operator).append(" ").append(filter).toString();
		}
		searchParams.setFilter(filter);
		return this;
	}

	/**
	 * 增加过滤规则(filter)(2).
	 *
	 * @param filter 过滤规则。
     * @return the search params builder
	 */
	public SearchParamsBuilder addFilter(String filter) {
		this.addFilter(filter, "AND");
		return this;
	}

	/**
	 * 添加一条动态摘要(summary)信息(1)
	 *
	 * 增加了此内容后，fieldName字段可能会被截断、飘红等。.
	 *
	 * @param fieldName 指定的生效的字段。此字段必需为可分词的text类型的字段。
	 * @param len 指定结果集返回的词字段的字节长度，一个汉字为2个字节。
	 * @param element 指定命中的query的标红标签，可以为em等。
	 * @param ellipsis 指定用什么符号来标注未展示完的数据，例如“...”。
	 * @param snippet 指定query命中几段summary内容。
	 * @return the search params builder
	 */
	public SearchParamsBuilder addSummary(String fieldName, Integer len, String element, String ellipsis,
			Integer snippet) {
		if (fieldName == null || fieldName.equals("")) {
			return this;
		}

		final Map<String, Summary> summariesMap = Maps.newLinkedHashMap();
		if (searchParams.isSetSummaries()) {
			Set<Summary> _summaries = searchParams.getSummaries();
			for (Summary summary : _summaries) {
				summariesMap.put(summary.getSummary_field(), summary);
			}
			Preconditions.checkArgument(!summariesMap.containsKey(fieldName),
					"the Summaries is already added. fieldName: " + fieldName);
		} else {
			summariesMap.put(fieldName, new Summary(fieldName));
		}
		searchParams.setSummaries(Sets.newLinkedHashSet(summariesMap.values()));
		return this;
	}

	/**
	 * 关闭某些功能模块(disable)
	 *
	 * 有如下场景需要考虑：
	 * 1、如果要关闭整个qp的功能，则指定disableValue="qp"。
	 * 2、要指定某个索引关闭某个功能，则可以指定disableValue="qp:function_name:index_names",
	 *   其中index_names可以用“|”分隔，可以为index_name1|index_name2...
	 * 3、如果要关闭多个function可以用“,”分隔，例如：disableValue="qp:function_name1:index_name1,qp:function_name2:index_name1"
	 *
	 * qp有如下模块：
	 * 1、spell_check: 检查用户查询串中的拼写错误，并给出纠错建议。
	 * 2、term_weighting: 分析查询中每个词的重要程度，并将其量化成权重，权重较低的词可能不会参与召回。
	 * 3、stop_word: 根据系统内置的停用词典过滤查询中无意义的词
	 * 4、synonym: 根据系统提供的通用同义词库和语义模型，对查询串进行同义词扩展，以便扩大召回。
	 *
	 * example:
	 * "qp" 标示关闭整个qp
	 * "qp:spell_check" 标示关闭qp的拼音纠错功能。
	 * "qp:stop_word:index_name1|index_name2" 标示关闭qp中index_name1和index_name2上的停用词功能。
	 *
	 * @param functionName 需要禁用的函数名称
	 * @param value 待禁用函数的详细说明
	 * @return the search params builder
	 */
	public SearchParamsBuilder addDisableFunction(String functionName, String value) {
		Preconditions.checkNotNull(functionName, "functionName should not be null.");
		Preconditions.checkNotNull(value, "value should not be null.");
		Map<String, String> disableFunctions;
		if (this.searchParams.isSetDisableFunctions()) {
			disableFunctions = this.searchParams.getDisableFunctions();
			Preconditions.checkArgument(!disableFunctions.containsKey(functionName),
					"the DisableFunction is already added. functionName: " + functionName);
		} else {
			disableFunctions = Maps.newLinkedHashMap();
		}
		disableFunctions.put(functionName, value);
		this.searchParams.setDisableFunctions(disableFunctions);
		return this;
	}

	/**
	 * 添加聚合打散条件(distinct)(1)
	 *
	 * 例如：检索关键词“手机”共获得10个结果，分别为：doc1，doc2，doc3，doc4，doc5，doc6，
	 * doc7，doc8，doc9，doc10。其中前三个属于用户A，doc4-doc6属于用户B，剩余四个属于
	 * 用户C。如果前端每页仅展示5个商品，则用户C将没有展示的机会。但是如果按照user_id进行抽
	 * 取，每轮抽取1个，抽取2次，并保留抽取剩余的结果，则可以获得以下文档排列顺序：doc1、
	 * doc4、doc7、doc2、doc5、doc8、doc3、doc6、doc9、doc10。可以看出，通过distinct
	 * 排序，各个用户的 商品都得到了展示机会，结果排序更趋于合理。
	 *
	 * @param key 为用户用于做distinct抽取的字段，该字段要求为可过滤字段。
	 * @param distCount 为一次抽取的document数量，默认值为1。
	 * @param distTimes 为抽取的次数，默认值为1。
	 * @param reserved 为是否保留抽取之后剩余的结果，true为保留，false则丢弃，丢弃时totalHits的个数会减去被distinct而丢弃的个数，但这个结果不一定准确，默认为true。
	 * @param distFilter 为过滤条件，被过滤的doc不参与distinct，只在后面的 排序中，这些被过滤的doc将和被distinct出来的第一组doc一起参与排序。默认是全部参与distinct。
	 * @param updateTotalHit 当reserved为false时，设置update_total_hit为true，则最终total_hit会减去被distinct丢弃的的数目（不一定准确），为false则不减； 默认为false。
	 * @param grade 指定档位划分阈值。
	 * @return the search params builder
	 *
	 */
	public SearchParamsBuilder addDistinct(String key, int distCount, int distTimes, boolean reserved,
			String distFilter, boolean updateTotalHit, double grade) {
		Preconditions.checkNotNull(key, "key should not be null.");
		Map<String, Distinct> distincts = Maps.newLinkedHashMap();
		if (this.searchParams.isSetDistincts()) {
			Set<Distinct> distinctsSet = this.searchParams.getDistincts();
			for (Distinct distinct : distinctsSet) {
				distincts.put(distinct.getKey(), distinct);
			}
			Preconditions.checkArgument(!distincts.containsKey(key), "the Distinct is already added. key: " + key);
		}
		Distinct distinct = new Distinct(key);
		distinct.setDistCount(distCount);
		distinct.setDistTimes(distTimes);
		distinct.setReserved(reserved);
		distinct.setDistFilter(distFilter);
		distinct.setUpdateTotalHit(updateTotalHit);
		distinct.setGrade(Double.toString(grade));
		distincts.put(key, distinct);
		this.searchParams.setDistincts(Sets.newLinkedHashSet(distincts.values()));
		return this;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SearchParamsBuilder [" + searchParams + "]";
	}

	/**
	 * Builds the final SearchParams.
	 *
	 * @return the search params
	 */
	public SearchParams build() {
		return this.searchParams;
	}

}
