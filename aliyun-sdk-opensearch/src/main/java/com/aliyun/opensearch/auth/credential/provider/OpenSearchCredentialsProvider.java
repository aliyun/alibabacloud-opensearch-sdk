package com.aliyun.opensearch.auth.credential.provider;

import com.aliyun.opensearch.auth.credential.Credentials;
import com.aliyun.opensearch.auth.credential.CredentialsException;
import com.aliyun.opensearch.sdk.generated.OpenSearch;

/**
 * 通过{@link OpenSearch}对象构建静态认证凭证服务商
 *
 * @author 隆宇
 */
public class OpenSearchCredentialsProvider implements CredentialsProvider {
    private CredentialsProvider delegate;

    /**
     * 构造函数
     *
     * @param openSearch {@link OpenSearch}通用配置对象
     */
    public OpenSearchCredentialsProvider(OpenSearch openSearch) {
        if (openSearch.isSetBearerToken()) {
            delegate = new BearerTokenCredentialsProvider(openSearch.getBearerToken());
        } else {
            delegate = new StaticCredentialsProvider(openSearch.getAccessKey(), openSearch.getSecret(), openSearch.getSecurityToken());
        }
    }

    @Override
    public Credentials getCredentials() throws CredentialsException {
        return delegate.getCredentials();
    }
}
