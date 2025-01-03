package com.football_manager.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamDtoResponse {

    private Integer id;

    private String name;

    private Double balance;

    private Integer commissionPercentage;
}
