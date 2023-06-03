package ru.javawebinar.topjava.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(Integer id) {
        super("Приём пищи " + id + " уже существует", id);
    }
}
