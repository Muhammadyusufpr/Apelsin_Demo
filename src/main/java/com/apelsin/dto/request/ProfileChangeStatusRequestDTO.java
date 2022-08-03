package com.apelsin.dto.request;

import com.apelsin.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileChangeStatusRequestDTO {
    ProfileStatus status;
}
