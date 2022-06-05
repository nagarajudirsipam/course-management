package com.emeritus.course.exception;

public class DataNotFoundException extends RuntimeException{

    public DataNotFoundException(String topic, String id) {
        super(String.format("%s with value %s doesn't exists", topic, id));
    }
}
