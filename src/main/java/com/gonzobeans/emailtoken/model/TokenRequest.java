package com.gonzobeans.emailtoken.model;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class TokenRequest {

    @JsonProperty("applicationSecret")
    private String applicationSecret;

    @JsonProperty("emailAddress")
    private String emailAddress;

    @JsonProperty("tokenId")
    private String tokenId;

    public String getApplicationSecret() {
        return applicationSecret;
    }

    public String getTokenId() {
        return tokenId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
