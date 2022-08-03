package com.apelsin.dto.response;

import com.apelsin.dto.request.ProfileRequestDTO;
import com.apelsin.enums.ProfileStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProfileResponseDTO extends ProfileRequestDTO {
    private String id;
    private LocalDateTime createdDate;
    private ProfileStatus status;

    private String jwt;
}
