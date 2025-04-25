package com.Integration.hubstaff.DTO;

import com.Integration.hubstaff.groups.Onfetch;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.security.auth.login.AccountNotFoundException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppDTO {


    private String applicationName;

    @NotNull(groups = {Onfetch.class}, message = "user id is required")
    private Integer userId;

    private Integer organizationId;
}
