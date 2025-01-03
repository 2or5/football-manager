package com.football_manager.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TeamDtoResponse {

    private Integer id;

    private String name;

    private Double balance;

    private Integer commissionPercentage;

    private List<PlayerDtoResponse> players;
}
