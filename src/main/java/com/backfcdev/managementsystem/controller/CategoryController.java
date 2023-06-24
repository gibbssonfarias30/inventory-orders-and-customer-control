package com.backfcdev.managementsystem.controller;

import com.backfcdev.managementsystem.dto.CategoryDTO;
import com.backfcdev.managementsystem.model.Category;
import com.backfcdev.managementsystem.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final ICategoryService categoryService;
    private final ModelMapper mapper;


    @GetMapping
    ResponseEntity<List<CategoryDTO>> findAll() {
        return ResponseEntity.ok(categoryService.findAll()
                .stream()
                .map(this::convertToDto)
                .toList());
    }

    // level 2 Richardson
    @PostMapping
    ResponseEntity<Void> save(@RequestBody CategoryDTO categoryDTO) {
        Category category = categoryService.save(convertToEntity(categoryDTO));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    ResponseEntity<CategoryDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(convertToDto(categoryService.findById(id)));
    }

    // Hateoas - level 3 Richardson
    @GetMapping("/hateoas/{id}")
    EntityModel<CategoryDTO> findByIdHateoas(@PathVariable Integer id) {
        EntityModel<CategoryDTO> resource = EntityModel.of(this.convertToDto(categoryService.findById(id)));
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findById(id));
        resource.add(link.withRel("category-info"));
        return resource;
    }

    @PutMapping("/{id}")
    ResponseEntity<CategoryDTO> update(@PathVariable Integer id, @RequestBody CategoryDTO categoryDTO) {
        categoryDTO.setId(id);
        Category category = categoryService.update(id, convertToEntity(categoryDTO));
        return ResponseEntity.ok(convertToDto(category));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Integer id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }


    public CategoryDTO convertToDto(Category dto) {
        return mapper.map(dto, CategoryDTO.class);
    }
    public Category convertToEntity(CategoryDTO entity) {
        return mapper.map(entity, Category.class);
    }
}
