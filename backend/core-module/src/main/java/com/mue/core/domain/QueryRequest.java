package com.mue.core.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mue.core.utilities.StringUtils.*;

@Data
public class QueryRequest implements ApiQuery {
    private String key;
    private QueryOperation operation;
    private Object value;
    private boolean or;

    public static List<ApiQuery> from(String search) {
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");

        List<ApiQuery> queryRequests = new ArrayList<>();
        while (matcher.find()) {
            QueryRequest apiQuery = new QueryRequest();
            apiQuery.setKey(matcher.group(1));
            apiQuery.setOperation(QueryOperation.from(matcher.group(2)));
            apiQuery.setValue(getValue(matcher.group(3)));
            queryRequests.add(apiQuery);
        }

        return queryRequests;
    }

    private static Object getValue(String s) {
        if (isInteger(s)) {
            return Integer.parseInt(s);
        } else if (isBoolean(s)) {
            return Boolean.parseBoolean(s);
        } else if (isDouble(s)) {
            return Double.parseDouble(s);
        } else {
            return s;
        }

    }

    public static void main(String[] args) {
        String search = "id:123,isBrand:true";
        System.out.println(from(search));
    }

    public static final String EQUAL = "=";
    public static final String GREATER = ">";
    public static final String LESSER = "<";
}
