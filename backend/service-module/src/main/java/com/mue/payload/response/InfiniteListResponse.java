package com.mue.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class InfiniteListResponse<T> {
    private int total;
    private List<T> content;
    private boolean hasMore;
}
