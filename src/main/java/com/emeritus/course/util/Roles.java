package com.emeritus.course.util;

public enum Roles {
    SYSTEM_ADMIN("SYSTEM_ADMIN"), STUDENT("STUDENT"), INSTRUCTOR("INSTRUCTOR");

    String role;

    Roles(String role) {
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }
}
