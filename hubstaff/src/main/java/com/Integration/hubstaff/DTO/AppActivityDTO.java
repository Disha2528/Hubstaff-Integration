package com.Integration.hubstaff.DTO;

import com.Integration.hubstaff.Groups.Onfetch;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppActivityDTO {

    @JsonProperty("id")
    private Long activityId;

    @JsonProperty("name")
    @NotBlank(groups = {Onfetch.class}, message = "application name cannot be blank")
    private String applicationName;

    @JsonProperty("user_id")
    @NotNull(groups = {Onfetch.class}, message = "user Id is required")
    private Integer userId;

    private Integer organizationId;

    @JsonProperty("date")
    private String activityDate;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("tracked")
    private Integer tracked;

}
