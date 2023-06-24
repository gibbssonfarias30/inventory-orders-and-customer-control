package com.backfcdev.managementsystem.controller;

import com.backfcdev.managementsystem.dto.CategoryDTO;
import com.backfcdev.managementsystem.dto.OrderDTO;
import com.backfcdev.managementsystem.model.Order;
import com.backfcdev.managementsystem.service.IOrderService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final IOrderService orderService;
    private final ModelMapper mapper;

    @GetMapping
    ResponseEntity<Page<OrderDTO>> findAll(@PageableDefault(size = 5, sort = "client")
                                           Pageable pageable){
        return ResponseEntity.ok(orderService.findAll(pageable)
                .map(this::convertToDto));
    }

    // level 2 Richardson
    @PostMapping
    ResponseEntity<Void> save(@RequestBody OrderDTO orderDTO){
        Order order = orderService.save(convertToEntity(orderDTO));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(order.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    ResponseEntity<OrderDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok(convertToDto(orderService.findById(id)));
    }

    // Hateoas - level 3 Richardson
//    @GetMapping("/hateoas/{id}")
//    EntityModel<OrderDTO> findByIdHateoas(@PathVariable Integer id){
//        EntityModel<OrderDTO> resource = EntityModel.of(this.convertToDto(orderService.findById(id)));
//        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findById(id));
//        resource.add(link.withRel("order-info"));
//        return resource;
//    }

    @PutMapping("/{id}")
    ResponseEntity<OrderDTO> update(@PathVariable Integer id, @RequestBody OrderDTO orderDTO){
        Order order = orderService.update(id, convertToEntity(orderDTO));
        return ResponseEntity.ok(convertToDto(order));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Integer id){
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

    public OrderDTO convertToDto(Order dto){
        return mapper.map(dto, OrderDTO.class);
    }
    public Order convertToEntity(OrderDTO entity){
        return mapper.map(entity, Order.class);
    }
}
