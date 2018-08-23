package com.gonzobeans.emailtoken.model;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class TokenRequest {

    @JsonProperty("applicationSecret")
    private String applicationSecret;

    @JsonProperty("emailAddress")
    private String emailAddress;

    @JsonProperty("usageId")
    private String usageId;

    public String getApplicationSecret() {
        return applicationSecret;
    }

    public String getUsageId() {
        return usageId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
