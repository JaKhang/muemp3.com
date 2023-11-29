package com.ja.muemp3.entities.constants;

public enum AuthProvider {
    LOCAL, GOOGLE, FACEBOOK;

    public static AuthProvider fromString(String value){
        return AuthProvider.valueOf(value.toUpperCase());
    }

}
