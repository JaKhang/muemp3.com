package com.mue.payload.request;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ActionRequest {

    private List<UUID> ids;

}
