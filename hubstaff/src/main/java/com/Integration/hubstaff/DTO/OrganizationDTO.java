package com.Integration.hubstaff.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDTO {

    @JsonProperty("id")
    private Integer organizationId;

    @JsonProperty("name")
    private String organizationName;

    private String status;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("invite_url")
    private String inviteUrl;
}
