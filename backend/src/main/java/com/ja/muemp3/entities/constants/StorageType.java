package com.ja.muemp3.entities.constants;

public enum StorageType {
    OTHER(""),
    LOCAL(""),
    GOOGLE_DRIVE("https://drive.google.com/uc?export=view&id=%s");

    StorageType(String template) {
        this.template = template;
    }

    private final String template;
    public String urlTemplate() {
        return template;
    }
}
