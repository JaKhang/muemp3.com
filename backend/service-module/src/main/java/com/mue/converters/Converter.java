package com.mue.converters;

public interface Converter<L, D, E, R> extends EntityConverter<E, R>, DetailsConverter<D,E>, LineConverter<L, E> {

}
