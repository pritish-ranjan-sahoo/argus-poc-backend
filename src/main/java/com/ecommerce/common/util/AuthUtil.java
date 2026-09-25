package com.ecommerce.common.util;

import com.ecommerce.common.error.RoleNotFoundException;
import com.ecommerce.user.RoleType;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    public RoleType getRole(String role) {
        role = role.toUpperCase().trim();

        if(role.equals("CUSTOMER")) {
            return RoleType.CUSTOMER;
        } else if(role.equals("SELLER")) {
            return RoleType.SELLER;
        } else if(role.equals("ADMIN")) {
            return RoleType.ADMIN;
        } else {
            throw new RoleNotFoundException("Received invalid role type: "+ role);
        }
    }
}
