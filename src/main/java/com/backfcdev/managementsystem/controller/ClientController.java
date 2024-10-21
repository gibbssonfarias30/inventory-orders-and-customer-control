package com.backfcdev.managementsystem.controller;

import com.backfcdev.managementsystem.dto.request.ClientRequest;
import com.backfcdev.managementsystem.dto.response.ClientResponse;
import com.backfcdev.managementsystem.service.IClientService;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/clients")
public class ClientController {

    private final IClientService clientService;


    @GetMapping
    ResponseEntity<Page<ClientResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(clientService.findAll(pageable));
    }

    // level 2 Richardson
    @PostMapping
    ResponseEntity<Void> save(@RequestBody ClientRequest clientRequest) {
        ClientResponse clientResponse = clientService.save(clientRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(clientResponse.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    ResponseEntity<ClientResponse> findById(@PathVariable Integer id){
        return ResponseEntity.ok(clientService.findById(id));
    }

    // Hateoas - level 3 Richardson
    @GetMapping("/hateoas/{id}")
    EntityModel<ClientResponse> findByIdHateoas(@PathVariable Integer id) {
        EntityModel<ClientResponse> resource = EntityModel.of(clientService.findById(id));
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findById(id));
        resource.add(link.withRel("client-info"));
        return resource;
    }

    @PutMapping("/{id}")
    ResponseEntity<ClientResponse> update(@PathVariable Integer id, @RequestBody ClientRequest clientRequest) {
        return ResponseEntity.ok(clientService.update(id, clientRequest));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Integer id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
