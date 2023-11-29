package com.ja.muemp3.services.storage;

public class StorageFileNotFoundException extends StorageException {
    public StorageFileNotFoundException(String s) {
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StorageFileNotFoundException(Throwable cause) {
        super(cause);
    }

    public StorageFileNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public StorageFileNotFoundException() {
    }
}
