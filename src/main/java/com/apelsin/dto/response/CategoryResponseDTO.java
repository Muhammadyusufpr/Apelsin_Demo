package com.apelsin.dto.response;

import com.apelsin.dto.request.CategoryRequestDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryResponseDTO   extends CategoryRequestDTO {
    private  String id;
    private  LocalDateTime createdDate;
    private  Boolean visible;
}
