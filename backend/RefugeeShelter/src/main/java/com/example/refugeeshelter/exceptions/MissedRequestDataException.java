package com.example.refugeeshelter.exceptions;

import com.example.refugeeshelter.dto.ApiResponse;

public class MissedRequestDataException extends RuntimeException {
    private transient ApiResponse apiResponse;

    public MissedRequestDataException() {
        super();
        String message = "roomId field should not be null and should be integer";
        this.apiResponse = new ApiResponse(Boolean.FALSE, message);
    }

    public ApiResponse getApiResponse() {
        return apiResponse;
    }

}
