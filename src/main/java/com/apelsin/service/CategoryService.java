package com.apelsin.service;

import com.apelsin.dto.request.CategoryRequestDTO;
import com.apelsin.dto.response.CategoryResponseDTO;
import com.apelsin.entity.CategoryEntity;
import com.apelsin.exception.ItemNotFoundException;
import com.apelsin.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryResponseDTO create(CategoryRequestDTO requestDTO) {
        CategoryEntity entity = new CategoryEntity();
        entity.setNameEn(requestDTO.getNameEn());
        entity.setNameUz(requestDTO.getNameUz());
        entity.setNameUz(requestDTO.getNameUz());
        entity.setKey(requestDTO.getKey());
        categoryRepository.save(entity);
        return toDTO(entity);
    }

    public CategoryResponseDTO getById(String id) {
        CategoryEntity entity = categoryRepository.findByIdAndVisible(id, true).orElseThrow(() -> {
            log.info("Category id not found");
            throw new ItemNotFoundException("Category id not found");
        });
        return toDTO(entity);
    }

    public List<CategoryResponseDTO> getAll() {
        List<CategoryResponseDTO> responseDTOS = new LinkedList<>();
        categoryRepository.findAllByVisible(true).forEach(entity -> {
            responseDTOS.add(toDTO(entity));
        });
        return responseDTOS;
    }

    public Boolean delete(String id) {
        int n = categoryRepository.deleteStatus(false, id);
        return n > 0;
    }

    private CategoryResponseDTO toDTO(CategoryEntity entity) {
        CategoryResponseDTO responseDTO = new CategoryResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setNameUz(entity.getNameUz());
        responseDTO.setNameRu(entity.getNameRu());
        responseDTO.setNameEn(entity.getNameEn());
        responseDTO.setKey(entity.getKey());
        responseDTO.setCreatedDate(entity.getCreatedDate());
        return responseDTO;
    }
}
