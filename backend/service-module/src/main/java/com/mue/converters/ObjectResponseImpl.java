package com.mue.converters;

import com.mue.entities.AbstractEntity;
import com.mue.payload.response.ObjectResponse;
import org.springframework.stereotype.Component;

@Component
public class ObjectResponseImpl implements ObjectConverter {
    @Override
    public ObjectResponse convert(AbstractEntity abstractEntity) {
        if (abstractEntity == null)
            return null;
        ObjectResponse objectResponse = new ObjectResponse();
        objectResponse.setId(abstractEntity.getId());
        objectResponse.setName(abstractEntity.getName());
        objectResponse.setAlias(abstractEntity.getAlias());
        return objectResponse;
    }
}
