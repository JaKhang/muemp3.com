package com.mue.converters;

public interface DetailsConverter<D, E> {
    D convertToDetails(E e);
}
