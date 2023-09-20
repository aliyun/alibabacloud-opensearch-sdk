package com.aliyun.opensearch.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.opensearch.sdk.generated.search.SearchParams;
import com.google.common.base.Optional;

/**
 * The Class ScrollingSubClausesBuilder.
 * config, query, filter, kvpairs.
 * @author Ken
 */
public class ScrollingSubClausesBuilder extends AbstractSubClausesBuilder {

	private static final Logger LOG = LoggerFactory.getLogger(ScrollingSubClausesBuilder.class);

	public ScrollingSubClausesBuilder(SearchParams params) {
		super(params);
	}

	@Override
	Optional<String> buildConfigClause() {
		return super.getDefaultConfigClause();
	}

	@Override
	Optional<String> buildQueryClause() {
		return super.getDefaulQueryClause();
	}

	@Override
	Optional<String> buildFilterClause() {
		return super.getDefaultFilterClause();
	}

	@Override
	Optional<String> buildSortClause() {
		return super.getDefaultSortClause();
	}

	@Override
	Optional<String> buildDistinctClause() {
		if (params.isSetDistincts()) {
			LOG.warn("not support distinct clause in scroll search mode.");
		}
		return super.buildDistinctClause();
	}

	@Override
	Optional<String> buildAggregateClause() {
		if (params.isSetAggregates()) {
			LOG.warn("not support aggregate clause in scroll search mode.");
		}
		return super.buildAggregateClause();
	}

	@Override
	Optional<String> buildKVpairsClause() {
		return super.getDefaultKVpairsClause();
	}
}
