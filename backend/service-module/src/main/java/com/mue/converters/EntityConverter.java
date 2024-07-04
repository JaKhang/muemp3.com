package com.mue.converters;

public interface EntityConverter<E, R> {
    E convertToEntity(R r);
}
