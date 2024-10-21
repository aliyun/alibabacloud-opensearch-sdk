package com.aliyun.opensearch.auth.credential;

/**
 * 基于Bearer Token的认证凭证
 *
 * @author lyk
 */
public class BearerTokenCredentials implements Credentials{

    private String bearerToken;

    public BearerTokenCredentials(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    public String getBearerToken() {
        return bearerToken;
    }

    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }
}
