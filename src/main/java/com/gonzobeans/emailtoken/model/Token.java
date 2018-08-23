package com.gonzobeans.emailtoken.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;


@Entity
@Table(name = "tokens")
@JsonIgnoreProperties
@JsonInclude(Include.NON_NULL)
public class Token {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonIgnore
    @Column(name = "secret_hash")
    private String secretHash;

    @JsonProperty("emailAddress")
    @Column(name = "email_address", nullable = false)
    private String emailAddress;

    @JsonProperty("usageId")
    @Column(name = "usage_id")
    private String usageId;

    @JsonProperty("token")
    @Column(name = "token")
    private String token;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSecretHash() {
        return secretHash;
    }

    public void setSecretHash(String secretHash) {
        this.secretHash = secretHash;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUsageId() {
        return usageId;
    }

    public void setUsageId(String usageId) {
        this.usageId = usageId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static TokenBuilder Builder() {
        return new TokenBuilder();
    }

    public static final class TokenBuilder {
        private String secretHash;
        private String emailAddress;
        private String usageId;
        private String token;

        private TokenBuilder() {
        }

        public TokenBuilder withSecretHash(String secretHash) {
            this.secretHash = secretHash;
            return this;
        }

        public TokenBuilder withEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public TokenBuilder withUsageId(String usageId) {
            this.usageId = usageId;
            return this;
        }

        public TokenBuilder withToken(String token) {
            this.token = token;
            return this;
        }

        public Token build() {
            Token t = new Token();
            t.setSecretHash(secretHash);
            t.setEmailAddress(emailAddress);
            t.setUsageId(usageId);
            t.setToken(token);
            return t;
        }
    }
}
