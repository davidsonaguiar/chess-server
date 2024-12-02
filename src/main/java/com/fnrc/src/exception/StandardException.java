package com.fnrc.src.exception;

public class StandardException extends RuntimeException {
    long timestamp = System.currentTimeMillis();

    public StandardException(String message) {
        super(message);
    }

    public long getTimestamp() {
        return this.timestamp;
    }
}
