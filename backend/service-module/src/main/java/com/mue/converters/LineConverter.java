package com.mue.converters;

public interface LineConverter<L, E> {
    L convertToLine(E e);
}
