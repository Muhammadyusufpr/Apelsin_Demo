package com.apelsin.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CardRequestDTO {
    private String name;
    private String number;
    private String expDate;
}
