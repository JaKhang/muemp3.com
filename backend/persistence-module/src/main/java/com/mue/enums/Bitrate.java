package com.mue.enums;

public enum Bitrate {
    LOW("128k"),
    HIGH("320k");

    private String bitrate;

    Bitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    @Override
    public String toString() {
        return bitrate;
    }
}
