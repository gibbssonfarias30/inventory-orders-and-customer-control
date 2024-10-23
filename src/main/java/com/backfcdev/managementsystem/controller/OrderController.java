package com.backfcdev.managementsystem.controller;

import com.backfcdev.managementsystem.dto.request.OrderRequest;
import com.backfcdev.managementsystem.dto.response.OrderResponse;
import com.backfcdev.managementsystem.service.IOrderService;

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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final IOrderService orderService;


    @GetMapping
    ResponseEntity<Page<OrderResponse>> findAll(@PageableDefault(size = 5, sort = "user")
                                                Pageable pageable){
        return ResponseEntity.ok(orderService.findAll(pageable));
    }

    // level 2 Richardson
    @PostMapping
    ResponseEntity<Void> save(@RequestBody OrderRequest orderRequest){
        OrderResponse orderResponse = orderService.save(orderRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(orderResponse.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    ResponseEntity<OrderResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.findById(id));
    }

    // Hateoas - level 3 Richardson
    @GetMapping("/hateoas/{id}")
    EntityModel<OrderResponse> findByIdHateoas(@PathVariable Long id){
        EntityModel<OrderResponse> resource = EntityModel.of(orderService.findById(id));
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findById(id));
        resource.add(link.withRel("order-info"));
        return resource;
    }

    @PutMapping("/{id}")
    ResponseEntity<OrderResponse> update(@PathVariable Long id, @RequestBody OrderRequest orderRequest){
        return ResponseEntity.ok(orderService.update(id, orderRequest));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id){
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
