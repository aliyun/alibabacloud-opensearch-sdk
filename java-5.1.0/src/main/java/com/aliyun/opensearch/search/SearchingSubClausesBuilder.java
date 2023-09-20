package com.aliyun.opensearch.search;

import com.aliyun.opensearch.sdk.generated.search.SearchParams;
import com.google.common.base.Optional;

/**
 * Search Sub Clauses Builder for build: config, query, filter, kvpairs, sort, distinct, aggregate.
 *
 * @author Ken
 */
public class SearchingSubClausesBuilder extends AbstractSubClausesBuilder {

	public SearchingSubClausesBuilder(SearchParams params) {
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
	Optional<String> buildSortClause() {
		return super.getDefaultSortClause();
	}

	@Override
	Optional<String> buildFilterClause() {
		return super.getDefaultFilterClause();
	}

	@Override
	Optional<String> buildDistinctClause() {
		return super.getDefaultDistinctClause();
	}

	@Override
	Optional<String> buildAggregateClause() {
		return super.getDefaultAggregateClause();
	}

	@Override
	Optional<String> buildKVpairsClause() {
		return super.getDefaultKVpairsClause();
	}

    @Override
    Optional<String> buildFinalDistinctClause() {
        return super.getDefaultFinalDistinctClause();
    }

    @Override
    Optional<String> buildCustomClauses() {
        return super.getDefaultCustomClauses();
    }
}
