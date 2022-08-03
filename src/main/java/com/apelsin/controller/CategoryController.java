package com.apelsin.controller;

import com.apelsin.dto.request.CategoryRequestDTO;
import com.apelsin.service.CategoryService;
import com.apelsin.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Api(tags = "Category")
@RestController
@RequestMapping("/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    @ApiOperation(value = "Create ", notes = "Method: Create Category")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody @Valid CategoryRequestDTO requestDTO, HttpServletRequest request) {
        log.info("Create: {}", requestDTO);
        JwtUtil.getIdFromHeader(request);
        return ResponseEntity.ok(categoryService.create(requestDTO));
    }

    @ApiOperation(value = "Get by id", notes = "Method: by Category id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        log.info("Get by id: {}", id);
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @ApiOperation(value = "Get all", notes = "Method: Category get all")
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        log.info("Get all: {}");
        return ResponseEntity.ok(categoryService.getAll());
    }

    @ApiOperation(value = "Delete by id", notes = "Method: Delete by Category id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id,HttpServletRequest request) {
        log.info("Delete by id: {}", id);
        JwtUtil.getIdFromHeader(request);
        return ResponseEntity.ok(categoryService.delete(id));
    }


}
