package com.ecommerce.common.error;

public class RoleNotFound extends RuntimeException {
    public RoleNotFound(String message) {
        super(message);
    }
}
