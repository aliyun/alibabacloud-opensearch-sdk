package com.aliyun.opensearch.auth.credential.provider;

import com.aliyun.opensearch.auth.credential.Credentials;
import com.aliyun.opensearch.auth.credential.CredentialsException;

/**
 * 认证凭证服务商
 *
 * @author 隆宇
 */
public interface CredentialsProvider {
    /**
     * 获取认证凭证
     *
     * @return 由认证服务商提供的认证凭证
     * @throws CredentialsException 出现任何错误时抛出
     */
    Credentials getCredentials() throws CredentialsException;
}
