package ru.javawebinar.topjava.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(Integer id) {
        super("Приёма пищи не существует", id);
    }
}
