package com.aliyun.opensearch.auth.credential.provider;

import com.aliyun.opensearch.auth.credential.*;

/**
 * 通过Bearer Token构建静态认证凭证服务商
 *
 * @author lyk
 */
public class BearerTokenCredentialsProvider implements CredentialsProvider {
    private String bearerToken;

    /**
     * 构造函数
     *
     * @param bearerToken
     */
    public BearerTokenCredentialsProvider(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    @Override
    public Credentials getCredentials() throws CredentialsException {
        return new BearerTokenCredentials(bearerToken);
    }
}
