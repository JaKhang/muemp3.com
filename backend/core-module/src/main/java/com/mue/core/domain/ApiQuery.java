package com.mue.core.domain;

public interface ApiQuery {

    String getKey();

    Object getValue();

    QueryOperation getOperation();

    boolean isOr();
}
