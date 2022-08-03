package com.apelsin.dto.response;


import com.apelsin.dto.request.CardRequestDTO;
import com.apelsin.enums.CardStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardResponseDTO extends CardRequestDTO {
    private LocalDateTime createdDate;
    private CardStatus status;
    private ProfileResponseDTO profile;
    private String phone;
}
