package com.apelsin.dto.response;

import com.apelsin.dto.request.TransactionsRequestDTO;
import com.apelsin.enums.TranStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionsResponseDTO extends TransactionsRequestDTO {
    private String id;
    private LocalDateTime createdDate;
    private TranStatus status;
    private CardResponseDTO fromCard;
    private CardResponseDTO toCard;
    private CardResponseDTO cardTranId;

}
