package com.mue.converters;

import com.mue.entities.AbstractEntity;
import com.mue.payload.response.ObjectResponse;

public interface ObjectConverter {
    ObjectResponse convert(AbstractEntity abstractEntity);
}
