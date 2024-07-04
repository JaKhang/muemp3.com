package com.mue.enums;

public enum AuthProvider {
    LOCAL, GOOGLE, FACEBOOK;

    public static AuthProvider fromString(String value){
        return AuthProvider.valueOf(value.toUpperCase());
    }

}
