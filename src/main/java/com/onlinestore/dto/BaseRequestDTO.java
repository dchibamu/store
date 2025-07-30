package com.onlinestore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

import java.time.Instant;

@Data
public abstract class BaseRequestDTO {

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            timezone = "Africa/Johannesburg")
    private Instant requestTimestamp = Instant.now();

    private String requestedBy;
}
