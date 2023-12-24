package dev.ozkan.ratingapp.core.jwt;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.Objects;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefaultJWTPayload {

    @JsonProperty("jti")
    private String id;

    @JsonProperty("iss")
    private String issuer;

    @JsonProperty("sub")
    private String subject;

    @JsonProperty("aud")
    private String audience;

    @JsonIgnore
    private Instant issuedAt;

    @JsonProperty
    private Instant expiration;

    public DefaultJWTPayload() {
    }

    public DefaultJWTPayload(DefaultJWTPayload cf) {
        this.id = cf.id;
        this.issuer = cf.issuer;
        this.subject = cf.subject;
        this.audience = cf.audience;
        this.issuedAt = cf.issuedAt;
        this.expiration = cf.expiration;
    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonIgnore
    public String getIssuer() {
        return issuer;
    }

    @JsonIgnore
    public String getSubject() {
        return subject;
    }

    @JsonIgnore
    public String getAudience() {
        return audience;
    }

    @JsonIgnore
    public Instant getIssuedAt() {
        return issuedAt;
    }

    @JsonIgnore
    public Instant getExpiration() {
        return expiration;
    }

    @JsonProperty("iat")
    public Integer getIssuedAtInSeconds() {
        if (this.issuedAt == null) return null;
        return (int) issuedAt.getEpochSecond();
    }

    @JsonProperty("exp")
    public Integer getExpirationInSeconds() {
        if (expiration == null) return null;
        return (int) expiration.getEpochSecond();
    }

    public DefaultJWTPayload setId(String id) {
        this.id = id;
        return this;
    }

    public DefaultJWTPayload setIssuer(String issuer) {
        this.issuer = issuer;
        return this;
    }

    public DefaultJWTPayload setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public DefaultJWTPayload setAudience(String audience) {
        this.audience = audience;
        return this;
    }

    public DefaultJWTPayload setIssuedAt(Instant issuedAt) {
        this.issuedAt = issuedAt;
        return this;
    }

    @JsonProperty("iat")
    public DefaultJWTPayload setIssuedAt(int issuedAt) {
        this.issuedAt = Instant.ofEpochSecond(issuedAt);
        return this;
    }

    public DefaultJWTPayload setExpiration(Instant expiration) {
        this.expiration = expiration;
        return this;
    }

    @JsonProperty("exp")
    public DefaultJWTPayload setExpiration(int expiration) {
        this.expiration = Instant.ofEpochSecond(expiration);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultJWTPayload that = (DefaultJWTPayload) o;
        return Objects.equals(id, that.id)
                && Objects.equals(issuer, that.issuer)
                && Objects.equals(subject, that.subject)
                && Objects.equals(audience, that.audience)
                && Objects.equals(getIssuedAtInSeconds(), that.getIssuedAtInSeconds())
                && Objects.equals(getExpirationInSeconds(), that.getExpirationInSeconds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, issuer, subject, audience, issuedAt, expiration);
    }

    @Override
    public String toString() {
        return "DefaultJWTPayload{" +
                "id='" + id + '\'' +
                ", issuer='" + issuer + '\'' +
                ", subject='" + subject + '\'' +
                ", audience='" + audience + '\'' +
                ", issuedAt=" + issuedAt +
                ", expiration=" + expiration +
                '}';
    }
}
