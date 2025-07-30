package com.onlinestore.dto;

import lombok.Data;

import java.time.Instant;

@Data
public abstract class BaseResponseDTO {
    private Long id;
    private Instant createdDate;
    private Instant lastModifiedDate;
    private String createdBy;
    private String lastModifiedBy;
}
