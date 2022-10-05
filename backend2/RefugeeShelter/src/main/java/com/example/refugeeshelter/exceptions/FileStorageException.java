package com.example.refugeeshelter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad request!")
public class FileStorageException extends RuntimeException {
    public FileStorageException(String msg) {
        super(msg);
    }
}