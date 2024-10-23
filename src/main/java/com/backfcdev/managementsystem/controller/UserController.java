package com.backfcdev.managementsystem.controller;

import com.backfcdev.managementsystem.dto.request.UserRequest;
import com.backfcdev.managementsystem.dto.response.UserResponse;
import com.backfcdev.managementsystem.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService clientService;

    public UserController(IUserService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    ResponseEntity<Page<UserResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(clientService.findAll(pageable));
    }

    // level 2 Richardson
    @PostMapping
    ResponseEntity<Void> save(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = clientService.save(userRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(userResponse.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(clientService.findById(id));
    }

    // Hateoas - level 3 Richardson
    @GetMapping("/hateoas/{id}")
    EntityModel<UserResponse> findByIdHateoas(@PathVariable Long id) {
        EntityModel<UserResponse> resource = EntityModel.of(clientService.findById(id));
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findById(id));
        resource.add(link.withRel("user-info"));
        return resource;
    }

    @PutMapping("/{id}")
    ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(clientService.update(id, userRequest));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
