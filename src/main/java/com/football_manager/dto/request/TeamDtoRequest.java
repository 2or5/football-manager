package com.football_manager.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TeamDtoRequest {

    @NotBlank(message = "The team name cannot be blank. Please provide a valid name.")
    private String name;

    @NotNull(message = "Balance cannot be null. Please specify a balance.")
    @Min(value = 0, message = "Balance must be at least 0.")
    private Double balance;

    @NotNull(message = "Commission percentage cannot be null. Please specify a value between 0 and 100.")
    @Min(value = 0, message = "Commission percentage must be at least 0.")
    @Max(value = 100, message = "Commission percentage cannot exceed 100.")
    private Integer commissionPercentage;
}
