package com.example.apigateway;

import java.security.Principal;

public class UserPrincipal implements Principal {
    String userId;

    public UserPrincipal(String userId) {
        this.userId = userId;
    }

    @Override
    public String getName() {
        return userId;
    }

}
