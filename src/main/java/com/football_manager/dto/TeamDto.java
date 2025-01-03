package com.football_manager.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamDto {

    private Integer id;

    private String name;

    private Double balance;

    private Integer commissionPercentage;
}
