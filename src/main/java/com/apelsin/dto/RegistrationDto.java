package com.apelsin.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@ToString
public class RegistrationDto {
    @NotNull
    @Size(min = 3, max = 25, message = "name required")
    private String name;
    @NotNull
    @Size(min = 3, max = 25, message = "surname required")
    private String surname;
    @NotNull
    @NotBlank(message = "phone required")
    @Size(min = 12, max = 12, message = "phone required")
    private String phone;
    @NotBlank(message = "password required")
    @Size(min = 6, max = 20, message = "password required")
    private String password;

}

