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
public class AppActivityResponse {

    @JsonProperty("daily_applications")
    private List<AppActivityDTO> activityDTOList;
}
