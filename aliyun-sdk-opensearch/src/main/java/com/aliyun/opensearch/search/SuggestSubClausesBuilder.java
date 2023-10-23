package com.aliyun.opensearch.search;

import com.aliyun.opensearch.sdk.generated.search.SearchParams;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

/**
 * Sub clauses builder for Suggest.
 * 
 * @author ken
 */
public class SuggestSubClausesBuilder extends AbstractSubClausesBuilder {

	/**
	 * Instantiates a new suggest sub clauses builder.
	 *
	 * @param params
	 *            the params
	 */
	public SuggestSubClausesBuilder(SearchParams params) {
		super(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aliyun.opensearch.search.AbstractSubClausesBuilder#buildQueryClause()
	 */
	@Override
	Optional<String> buildQueryClause() {
		return Optional.of(Preconditions.checkNotNull(params.getQuery(), "query is required when Suggesting."));
	}

	@Override
	public String getClausesString() {
		return buildQueryClause().get();
	}

}
