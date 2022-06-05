package com.emeritus.course.model.login;

import lombok.Data;

@Data
public class AccessResponse {

    private String userName;
    private String accessToken;

}
