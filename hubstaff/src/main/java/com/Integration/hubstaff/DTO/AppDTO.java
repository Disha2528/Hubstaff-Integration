package com.Integration.hubstaff.DTO;

import com.Integration.hubstaff.Groups.Onfetch;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppDTO {


    private String applicationName;

    @NotNull(groups = {Onfetch.class}, message = "user id is required")
    private Integer userId;

    private Integer organizationId;
}
