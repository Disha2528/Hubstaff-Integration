package com.Integration.hubstaff.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntegrationDTO {
    private String clientId;
    private String clientSecret;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("expires_in")
    private String tokenExpiry;
    private String status;
    private String lastSyncedAt;
    private String createdAt;
    private String updatedAt;

}
