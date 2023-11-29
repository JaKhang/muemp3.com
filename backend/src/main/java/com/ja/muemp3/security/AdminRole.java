package com.ja.muemp3.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole(T(com.ja.muemp3.entities.constants.Role).ADMIN.toString())")
public @interface AdminRole {
}
