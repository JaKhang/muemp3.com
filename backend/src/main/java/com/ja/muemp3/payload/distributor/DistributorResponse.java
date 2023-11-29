package com.ja.muemp3.payload.distributor;

import lombok.Data;

import java.util.UUID;

@Data
public class DistributorResponse {
    private String name;
    private UUID id;
}
