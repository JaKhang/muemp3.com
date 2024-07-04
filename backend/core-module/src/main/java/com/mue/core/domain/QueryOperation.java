package com.mue.core.domain;

import com.mue.core.exception.BadRequestException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum QueryOperation {
    EQUAL(":"),
    GREATER(">"),
    LESS("<");

    private final String symbol;


    QueryOperation(String symbol) {
        this.symbol = symbol;
    }

    public static QueryOperation from(String symbol){
        return Arrays.stream(QueryOperation.values()).filter(queryOperation -> queryOperation.getSymbol().equals(symbol)).findFirst().orElseThrow(() -> new BadRequestException("Query operation invalid !"));
    }
}
