package com.football_manager.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PlayerDtoRequest {

    @NotNull(message = "Birth date cannot be null.")
    @Past(message = "Birth date must be in the past.")
    private LocalDate birthDate;

    @NotNull(message = "Experience cannot be null.")
    @Min(value = 0, message = "Experience must be at least 0.")
    private Integer experienceMonths;

    @NotBlank(message = "The first name cannot be blank. Please provide a valid first name.")
    private String firstName;

    @NotBlank(message = "The last name cannot be blank. Please provide a valid last name.")
    private String lastName;

    @NotNull(message = "Team Id cannot be null.")
    private Integer teamId;
}
