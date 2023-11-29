package com.ja.muemp3.entities.constants;

public enum ThumbnailSize {
    SMALL("sm", 94),
    MEDIUM("md", 240),
    LARGE("lg", 600);

    public final String key;
    public final int size;

    ThumbnailSize(String key, int size) {
        this.key = key;
        this.size = size;
    }

    @Override
    public String toString() {
        return key;
    }
}
