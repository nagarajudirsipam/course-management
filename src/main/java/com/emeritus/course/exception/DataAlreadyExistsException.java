package com.emeritus.course.exception;

public class DataAlreadyExistsException extends RuntimeException{

    public DataAlreadyExistsException(String topic, String id) {
        super(String.format("%s with value %s already exists", topic, id));
    }
}
