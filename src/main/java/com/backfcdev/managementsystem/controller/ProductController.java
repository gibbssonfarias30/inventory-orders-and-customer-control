package com.backfcdev.managementsystem.controller;


import com.backfcdev.managementsystem.dto.request.ProductRequest;
import com.backfcdev.managementsystem.dto.response.ProductResponse;
import com.backfcdev.managementsystem.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final IProductService productService;

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
    ResponseEntity<Page<ProductResponse>> findAllProductsByDistinctArgs(@RequestParam Optional<String> name, @RequestParam Optional<Double> price, @PageableDefault(page = 0, size = 5) Pageable pageable) {
        return ResponseEntity.ok(productService.findByArgs(name, price, pageable));
    }

    // level 2 Richardson
    @PostMapping
    ResponseEntity<Void> save(@RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.save(productRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(productResponse.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    ResponseEntity<ProductResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(productService.findById(id));
    }

    // Hateoas - level 3 Richardson
    @GetMapping("/hateoas/{id}")
    EntityModel<ProductResponse> findByIdHateoas(@PathVariable Long id) {
        EntityModel<ProductResponse> resource = EntityModel.of(productService.findById(id));
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findById(id));
        resource.add(link.withRel("product-info"));
        return resource;
    }

    @PutMapping("/{id}")
    ResponseEntity<ProductResponse> update(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.update(id, productRequest));
    }

    @GetMapping("/stock-less-than/{amount}")
    ResponseEntity<List<ProductResponse>> getProductsByStockLessThan(@PathVariable int amount) {
        return ResponseEntity.ok(productService.findByStockLessThan(amount));
    }

    @GetMapping("/best-selling/{amount}")
    ResponseEntity<List<ProductResponse>> getBestSellingProducts(@PathVariable int amount) {
        return ResponseEntity.ok(productService.getBestSellingProducts(amount));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id ){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
