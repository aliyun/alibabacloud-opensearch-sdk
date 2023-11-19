package com.aliyun.opensearch.client;

import com.aliyun.opensearch.sdk.generated.commons.OpenSearchClientException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchException;

import java.util.Objects;

/**
 * OpenSearch响应消费器接口
 *
 * @param <T>
 */
@FunctionalInterface
public interface OpenSearchResponseConsumer<T> {
    void accept(T t) throws OpenSearchException, OpenSearchClientException;

    default OpenSearchResponseConsumer<T> andThen(OpenSearchResponseConsumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }
}
