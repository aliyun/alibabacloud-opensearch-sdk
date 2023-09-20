package com.aliyun.opensearch.search;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aliyun.opensearch.sdk.generated.search.*;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import static com.aliyun.opensearch.sdk.generated.search.OpenSearchSearcherConstants.*;

/**
 * Abstract SubClausesBuilder
 *
 * @author Ken
 */
public class AbstractSubClausesBuilder {

	/** The Constant ConfigClauseKey. */
	public static final String ConfigClauseKey = "config";

	/** The Constant QueryClauseKey. */
	public static final String QueryClauseKey = "query";

	/** The Constant SortClauseKey. */
	public static final String SortClauseKey = "sort";

	/** The Constant DistinctClauseKey. */
	public static final String DistinctClauseKey = "distinct";

	/** The Constant AggregateClauseKey. */
	public static final String AggregateClauseKey = "aggregate";

	/** The Constant FilterClauseKey. */
	public static final String FilterClauseKey = "filter";

	/** The Constant KVpairsClause. */
	public static final String KVpairsClause = "kvpairs";

    /** The Constant FinalDistinctClauseKey. */
    public static final String FinalDistinctClauseKey = "final_distinct";

	/** The original params. */
	protected SearchParams params;

	/**
	 * Instantiates a new abstract sub clauses builder.
	 *
	 * @param params
	 *            the params
	 */
	public AbstractSubClausesBuilder(SearchParams params) {
		super();
		this.params = params;
	}

	/**
	 * Builds the config clause.
	 *
	 * @return the optional
	 */
	Optional<String> buildConfigClause() {
		return Optional.absent();
	}

	/**
	 * Builds the query clause.
	 *
	 * @return the optional
	 */
	Optional<String> buildQueryClause() {
		return Optional.absent();
	}

	/**
	 * Builds the sort clause: "+" or "-", and split by ";"
	 * sort=+fieldname1;-fielfname2 .
	 *
	 * @return the optional
	 */
	Optional<String> buildSortClause() {
		return Optional.absent();
	}

	/**
	 * Builds the filter clause.
	 *
	 * @return the optional
	 */
	Optional<String> buildFilterClause() {
		return Optional.absent();
	}

	/**
	 * Builds the distinct clause.
	 *
	 * @return the optional
	 */
	Optional<String> buildDistinctClause() {
		return Optional.absent();
	}

	/**
	 * Builds the aggregate clause.
	 *
	 * @return the optional
	 */
	Optional<String> buildAggregateClause() {
		return Optional.absent();
	}

	/**
	 * Builds the k vpairs clause.
	 *
	 * @return the optional
	 */
	Optional<String> buildKVpairsClause() {
		return Optional.absent();
	}

    /**
     * Build the final_distinct clause
     */
    Optional<String> buildFinalDistinctClause() {
        return Optional.absent();
    }

    /**
     * Build custom clauses
     */
    Optional<String> buildCustomClauses() {
        return Optional.absent();
    }

    /**
	 * Gets the clauses string.
	 *
	 * @return the clauses string
	 */
	public String getClausesString() {
		List<String> clauses = Lists.newLinkedList();
		clauses.add(buildConfigClause().orNull());
		clauses.add(buildQueryClause().orNull());
		clauses.add(buildSortClause().orNull());
		clauses.add(buildFilterClause().orNull());
		clauses.add(buildDistinctClause().orNull());
		clauses.add(buildAggregateClause().orNull());
		clauses.add(buildKVpairsClause().orNull());
		clauses.add(buildFinalDistinctClause().orNull());
		clauses.add(buildCustomClauses().orNull());
		return Joiner.on("&&").skipNulls().join(clauses);
	}

	/**
	 * Gets the default config clause.
	 *
	 * config=hit:20,start:0,rerank_size:200,format:json
	 *
	 * @return the default config clause
	 */
	final Optional<String> getDefaultConfigClause() {
		StringBuilder queryClause = new StringBuilder(ConfigClauseKey).append("=");
		List<String> configClauses = Lists.newArrayList();
		Config config = params.getConfig();
		Rank rank = params.getRank();
		int start = config.getStart();
		append(configClauses, CONFIG_CLAUSE_START, ":", start);
		int hits = config.getHits();
		append(configClauses, CONFIG_CLAUSE_HIT, ":", hits);
		int rerank_size = rank.getReRankSize();
		append(configClauses, CONFIG_CLAUSE_RERANK_SIZE, ":", rerank_size);
		SearchFormat searchFormat = config.getSearchFormat();
		append(configClauses, CONFIG_CLAUSE_FORMAT, ":", searchFormat.name().toLowerCase());
		if (config.isSetCustomConfig()) {
			configClauses.add(Joiner.on(",").join(config.getCustomConfig()));
		}
		return Optional.of(queryClause.append(Joiner.on(",").join(configClauses)).toString());
	}

	/**
	 * Gets the defaul query clause.
	 *
	 * @return the defaul query clause
	 */
	final Optional<String> getDefaulQueryClause() {
		StringBuilder queryClause = new StringBuilder(QueryClauseKey).append("=");
		String queryString = Optional.fromNullable(params.getQuery()).or("''");
		return Optional.of(queryClause.append(queryString).toString());
	}

	/**
	 * Gets the default sort clause.
	 *
	 * @return the default sort clause
	 */
	final Optional<String> getDefaultSortClause() {
		Optional<String> sortClause = Optional.absent();
		if (params.isSetSort()) {
			Sort sort = params.getSort();
			if (sort.isSetSortFields()) {
				StringBuilder sortBuilder = new StringBuilder(SortClauseKey).append("=");
				List<SortField> sortFields = sort.getSortFields();
				List<String> sortStrings = Lists.newArrayList();
				for (SortField sortField : sortFields) {
					String field = sortField.getField();
					Order orderEnum = sortField.getOrder();
					String order;
					switch (orderEnum) {
					case INCREASE:
						order = SORT_CLAUSE_INCREASE;
						break;
					case DECREASE:
						order = SORT_CLAUSE_DECREASE;
						break;
					default:
						throw new IllegalArgumentException("unknow order: " + orderEnum);
					}
					sortStrings.add(order + field);
				}
				sortClause = Optional.of(sortBuilder.append(Joiner.on(";").join(sortStrings)).toString());
			}
		}
		return sortClause;
	}

	/**
	 * Gets the default aggregate clause.
	 *
	 * @return the default aggregate clause
	 */
	Optional<String> getDefaultAggregateClause() {
		return buildKVSetClause(params.isSetAggregates(), AggregateClauseKey, params.getAggregates(),
				new OptionalStringListCreator<Aggregate>() {
					@Override
					public List<String> create(Aggregate target) {
						return createListOfAggregateStrings(target);
					}
				});
	}

	interface OptionalStringListCreator<T> {
		List<String> create(T target);
	}

	/**
	 * Builds the kv set clause.
	 * </p>
	 *
	 *
	 * k/v join with ',' as string, all k/v pairs join with ';' as string.
	 * </p>
	 * style: e.g: KEY=C1k1:C1v1,C1k2:C1v2;C2k1:C2v1,C2k2:C2v2
	 *
	 * </p>
	 *
	 * @param <T>
	 *            the generic type
	 * @param isSet
	 *            the is set
	 * @param keyName
	 *            the key name
	 * @param targets
	 *            the targets
	 * @param listCreator
	 *            the list creator
	 * @return the optional
	 */
	<T> Optional<String> buildKVSetClause(boolean isSet, String keyName, Set<T> targets,
			OptionalStringListCreator<T> listCreator) {
		Optional<String> clauseOptional = Optional.absent();
		if (isSet) {
			StringBuilder clauseString = new StringBuilder(keyName).append("=");
			List<String> listOfClauseString = Lists.newLinkedList();
			for (T target : targets) {
				List<String> strings = listCreator.create(target);
				listOfClauseString.add(Joiner.on(",").join(strings));
			}

			if (!listOfClauseString.isEmpty()) {
				clauseString.append(Joiner.on(";").join(listOfClauseString));
				clauseOptional = Optional.of(clauseString.toString());
			}
		}
		return clauseOptional;
	}

	/**
	 * Gets the default distinct clause.
	 *
	 * @return the default distinct clause
	 */
	Optional<String> getDefaultDistinctClause() {
		return buildKVSetClause(params.isSetDistincts(), DistinctClauseKey, params.getDistincts(),
				new OptionalStringListCreator<Distinct>() {
					@Override
					public List<String> create(Distinct target) {
						return createListOfDistinctStrings(target);
					}
				});
	}

	/**
	 * Gets the default filter clause.
	 *
	 * @return the default filter clause
	 */
	final Optional<String> getDefaultFilterClause() {
		Optional<String> filterClause = Optional.absent();
		if (params.isSetFilter()) {
			filterClause = Optional.of(concat(FilterClauseKey, "=", params.getFilter()));
		}
		return filterClause;
	}

	/**
	 * Gets the default k/v pairs clause.
	 * e.g: cluster=daogou&&kvpairs=name:company_name,price:new_price,title:short_title&&query=笔筒
	 * @return the default k vpairs clause
	 */
	final Optional<String> getDefaultKVpairsClause() {
		Optional<String> kVpairsClause = Optional.absent();
		Config config = params.getConfig();
		if (config.isSetKvpairs()) {
			kVpairsClause = Optional.of(concat(KVpairsClause, "=", config.getKvpairs()));
		}
		return kVpairsClause;
	}

    /**
     * Get the default distinct clause
     */
    Optional<String> getDefaultFinalDistinctClause() {
        if (!params.isSetFinalDistinct()) {
            return Optional.absent();
        }

        FinalDistinct finalDistinct = params.getFinalDistinct();

        List<String> kvPairs = Lists.newArrayList();
        append(kvPairs, DISTINCT_CLAUSE_DIST_TYPE, ":",
            finalDistinct.getType().name().toLowerCase());

        List<String> distKeyList = Lists.newArrayList();
        List<String> distCountList = Lists.newArrayList();
        for (FinalDistinctKey key : finalDistinct.getKeyList()) {
            distKeyList.add(key.getKey());
            distCountList.add(String.valueOf(key.getCount()));
        }
        String distKey = Joiner.on(";").join(distKeyList);
        String distCount = Joiner.on(";").join(distCountList);
        append(kvPairs, DISTINCT_CLAUSE_DIST_KEY, ":", distKey);
        append(kvPairs, DISTINCT_CLAUSE_DIST_COUNT, ":", distCount);

        if (finalDistinct.isSetSort()) {
            String distSort = Joiner.on(";").join(finalDistinct.getSort());
            append(kvPairs, DISTINCT_CLAUSE_DIST_SORT, ":", distSort);
        }

        if (finalDistinct.isSetSpecialCount()) {
            List<String> specialCountList = Lists.newArrayList();
            for (Map.Entry<String, Integer> entry : finalDistinct.getSpecialCount().entrySet()) {
                StringBuilder specialCountBuilder = new StringBuilder();
                specialCountBuilder.append(entry.getKey());
                specialCountBuilder.append("@");
                specialCountBuilder.append(entry.getValue());
                specialCountList.add(specialCountBuilder.toString());
            }
            String distSpecialCount = Joiner.on("|").join(specialCountList);
            append(kvPairs, DISTINCT_CLAUSE_DIST_SPECIAL_COUNT, ":", distSpecialCount);
        }

        if (finalDistinct.isSetCustomFinalDistinct()) {
            for (Map.Entry<String, String> entry : finalDistinct.getCustomFinalDistinct().entrySet()) {
                append(kvPairs, entry.getKey(), ":", entry.getValue());
            }
        }

        StringBuilder queryClause = new StringBuilder(FinalDistinctClauseKey).append("=");
        return Optional.of(queryClause.append(Joiner.on(",").join(kvPairs)).toString());
    }

    /**
     * Get the default custom clause
     */
    Optional<String> getDefaultCustomClauses() {
        if (!params.isSetCustomClause()) {
            return Optional.absent();
        }

        List<String> clauses = Lists.newArrayList();
        for (Map.Entry<String, String> entry : params.getCustomClause().entrySet()) {
            append(clauses, entry.getKey(), "=", entry.getValue());
        }
        return Optional.of(Joiner.on("&&").join(clauses));
    }

    /**
	 * Creates the list of aggregate strings.
	 *
	 * @param aggregate
	 *            the aggregate
	 * @return the list
	 */
	protected static List<String> createListOfAggregateStrings(Aggregate aggregate) {
		String groupKey = Preconditions.checkNotNull(aggregate.getGroupKey(), "groupKey is required.");
		String aggFun = aggregate.getAggFun();
		String range = aggregate.getRange();
		String maxGroup = aggregate.getMaxGroup();
		String aggFilter = aggregate.getAggFilter();
		String aggSamplerThresHold = aggregate.getAggSamplerThresHold();
		String aggSamplerStep = aggregate.getAggSamplerStep();

		List<String> aggregateString = Lists.newLinkedList();
		append(aggregateString, AGGREGATE_CLAUSE_GROUP_KEY, ":", groupKey);
		append(aggregateString, AGGREGATE_CLAUSE_AGG_FUN, ":", aggFun);
		append(aggregateString, AGGREGATE_CLAUSE_RANGE, ":", range);
		append(aggregateString, AGGREGATE_CLAUSE_MAX_GROUP, ":", maxGroup);
		append(aggregateString, AGGREGATE_CLAUSE_AGG_FILTER, ":", aggFilter);
		append(aggregateString, AGGREGATE_CLAUSE_AGG_SAMPLER_THRESHOLD, ":", aggSamplerThresHold);
		append(aggregateString, AGGREGATE_CLAUSE_AGG_SAMPLER_STEP, ":", aggSamplerStep);
		return aggregateString;
	}

	/**
	 * Creates the list of distinct strings.
	 *
	 * @param distinct
	 *            the distinct
	 * @return the list
	 */
	protected static List<String> createListOfDistinctStrings(Distinct distinct) {
		String key = Preconditions.checkNotNull(distinct.getKey(), "key is required.");
		int distCount = distinct.getDistCount();
		int distTimes = distinct.getDistTimes();
		boolean reserved = distinct.isReserved();
		String distFilter = distinct.getDistFilter();
		boolean updateTotalHit = distinct.isUpdateTotalHit();
		String grade = distinct.getGrade();

		List<String> distinctString = Lists.newLinkedList();
		append(distinctString, DISTINCT_CLAUSE_DIST_KEY, ":", key);
		append(distinctString, DISTINCT_CLAUSE_DIST_COUNT, ":", distCount);
		append(distinctString, DISTINCT_CLAUSE_DIST_TIMES, ":", distTimes);
		append(distinctString, DISTINCT_CLAUSE_RESERVED, ":", reserved);
		append(distinctString, DISTINCT_CLAUSE_DIST_FILTER, ":", distFilter);
		append(distinctString, DISTINCT_CLAUSE_UPDATE_TOTAL_HIT, ":", updateTotalHit);
		append(distinctString, DISTINCT_CLAUSE_GRADE, ":", grade);
		return distinctString;
	}

	/**
	 * Append key and value with splitter to stringlist.
	 *
	 * @param stringlist
	 *            the string list
	 * @param key
	 *            the key
	 * @param splitter
	 *            the splitter
	 * @param value
	 *            the value
	 */
	private static void append(List<String> stringlist, String key, String splitter, Object value) {
		if (null != value)
			stringlist.add(concat(key.trim(), splitter, value));
	}

	/**
	 * Concat key and value with splitter.
	 *
	 * @param key
	 *            the key
	 * @param splitter
	 *            the splitter
	 * @param value
	 *            the value
	 * @return the string
	 */
	private static String concat(String key, String splitter, Object value) {
		Preconditions.checkNotNull(key, "key was null.");
		return new StringBuilder().append(key.trim()).append(splitter).append(value).toString();
	}

}
