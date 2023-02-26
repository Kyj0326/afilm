package com.afilm.security.config.oauth.provider;

import java.util.Map;

public class GoogleUserInfo implements OAuth2Userinfo {

    private Map<String, Object> attributes; // oauth2User.getAttribute()를 받는다

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String)attributes.get("sub");
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getEmail() {
        return (String)attributes.get("email");
    }

    @Override
    public String getName() {
        return (String)attributes.get("name");
    }
}
