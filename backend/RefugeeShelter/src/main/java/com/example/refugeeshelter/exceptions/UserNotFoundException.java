package com.example.refugeeshelter.exceptions;

import com.example.refugeeshelter.dto.ApiResponse;

public class UserNotFoundException extends RuntimeException {
    private transient ApiResponse apiResponse;


    public UserNotFoundException() {
        super();
        String message = "Incorrect login or password please check...";
        this.apiResponse = new ApiResponse(Boolean.FALSE, message);
    }

    public ApiResponse getApiResponse() {
        return apiResponse;
    }
}
