package com.aliyun.opensearch.auth.credential;

/**
 * 认证相关异常
 *
 * @author 隆宇
 */
public class CredentialsException extends RuntimeException {
    public CredentialsException(String message) {
        super(message);
    }

    public CredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}
