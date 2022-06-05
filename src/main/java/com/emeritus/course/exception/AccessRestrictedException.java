package com.emeritus.course.exception;

public class AccessRestrictedException extends RuntimeException{

    public AccessRestrictedException(String topic, String id) {
        super(String.format("%s is not allowed to perform %s", id, topic));
    }
}
