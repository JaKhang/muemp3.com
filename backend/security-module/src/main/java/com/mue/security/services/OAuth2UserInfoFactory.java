package com.mue.security.services;


import com.mue.core.exception.OAuth2AuthenticationProcessingException;
import com.mue.enums.AuthProvider;
import com.mue.security.domain.FacebookOAuth2UserInfo;
import com.mue.security.domain.GoogleOAuthUserInfo;
import com.mue.security.domain.OAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) throws OAuth2AuthenticationProcessingException {
        if (registrationId.equalsIgnoreCase(AuthProvider.GOOGLE.toString())) {
            return new GoogleOAuthUserInfo(attributes);
        } else if(registrationId.equalsIgnoreCase(AuthProvider.FACEBOOK.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        }else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
