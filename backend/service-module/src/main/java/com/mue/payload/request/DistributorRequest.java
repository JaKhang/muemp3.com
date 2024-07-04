package com.mue.payload.request;

import lombok.Data;

import java.util.UUID;

@Data
public class DistributorRequest {
    private String name;
    private String alias;
    private UUID coverId;
}
