package com.Integration.hubstaff.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;



@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrgResponse {
    @JsonProperty("organizations")
    private List<OrganizationDTO> orgList;

}
