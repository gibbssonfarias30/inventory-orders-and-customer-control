package com.backfcdev.managementsystem.controller;


import com.backfcdev.managementsystem.dto.ProductDTO;
import com.backfcdev.managementsystem.model.Product;
import com.backfcdev.managementsystem.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final IProductService productService;
    private final ModelMapper mapper;

    /**
     * Search for products, either by name or price, or both
     * Example Endpoints:
     * localhost:8777/api/products?name=ora
     * localhost:8777/api/products?price=80
     * localhost:8777/api/products?name=o&price=78

     * Example Pagination:
     * localhost:8777/api/products?name=o&page=1
     */
    @GetMapping
    ResponseEntity<Page<ProductDTO>> findAllProductsByDistinctArgs(@RequestParam Optional<String> name, @RequestParam Optional<Double> price, @PageableDefault(page = 0, size = 5) Pageable pageable) {
        return ResponseEntity.ok(
                productService.findByArgs(name, price, pageable).map(this::convertToDto)
        );
    }

    // level 2 Richardson
    @PostMapping
    ResponseEntity<Void> save(@RequestBody ProductDTO productDTO) {
        Product product = productService.save(convertToEntity(productDTO));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    ResponseEntity<ProductDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok(convertToDto(productService.findById(id)));
    }

    // Hateoas - level 3 Richardson
    @GetMapping("/hateoas/{id}")
    EntityModel<ProductDTO> findByIdHateoas(@PathVariable Integer id) {
        EntityModel<ProductDTO> resource = EntityModel.of(this.convertToDto(productService.findById(id)));
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findById(id));
        resource.add(link.withRel("product-info"));
        return resource;
    }

    @PutMapping("/{id}")
    ResponseEntity<ProductDTO> update(@PathVariable Integer id, @RequestBody ProductDTO productDTO) {
        productDTO.setId(id);
        Product product = productService.update(id, convertToEntity(productDTO));
        return ResponseEntity.ok(convertToDto(product));
    }

    @GetMapping("/stock-less-than/{amount}")
    ResponseEntity<List<ProductDTO>> getProductsByStockLessThan(@PathVariable int amount) {
        return ResponseEntity.ok(productService.findByStockLessThan(amount)
                .stream()
                .map(this::convertToDto)
                .toList());
    }

    @GetMapping("/best-selling/{amount}")
    ResponseEntity<List<ProductDTO>> getBestSellingProducts(@PathVariable int amount) {
        return ResponseEntity.ok(productService.getBestSellingProducts(amount)
                .stream()
                .map(this::convertToDto)
                .toList());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Integer id ){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }


    public ProductDTO convertToDto(Product dto) {
        return mapper.map(dto, ProductDTO.class);
    }
    public Product convertToEntity(ProductDTO entity) {
        return mapper.map(entity, Product.class);
    }
}
