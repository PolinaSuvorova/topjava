package ru.javawebinar.topjava.exception;

public class StorageException extends RuntimeException {
    private final Integer id;

    public StorageException(String message, Integer id) {
        super(message);
        this.id = id;
    }

    public StorageException(String message, Integer id, Exception exception) {
        super(message, exception);
        this.id = id;
    }

    public StorageException(Exception e) {
        this(e.getMessage(), 0, e);
    }

    public Integer getId() {
        return id;
    }
}