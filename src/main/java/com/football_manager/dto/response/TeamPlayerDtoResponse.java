package com.football_manager.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class TeamPlayerDtoResponse {

    private Integer id;

    private String name;

    private Double balance;

    private Double commissionPercentage;

    private List<PlayerResponse> players;
}
