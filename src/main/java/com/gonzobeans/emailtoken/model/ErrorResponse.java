package com.gonzobeans.emailtoken.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {
    @JsonProperty("error")
    private final String error;

    @JsonProperty("message")
    private final String message;

    @JsonCreator
    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }
}
