package com.football_manager.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerDtoResponse {

    private Integer id;

    private String firstName;

    private String lastName;

    private Integer age;

    private Integer experienceMonths;
}