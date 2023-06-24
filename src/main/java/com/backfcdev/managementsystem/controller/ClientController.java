package com.backfcdev.managementsystem.controller;

import com.backfcdev.managementsystem.dto.ClientDTO;
import com.backfcdev.managementsystem.model.Client;
import com.backfcdev.managementsystem.service.IClientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {
    private final IClientService clientService;
    private final ModelMapper mapper;

    @GetMapping
    ResponseEntity<List<ClientDTO>> findAll() {
        return ResponseEntity.ok(clientService.findAll()
                .stream()
                .map(this::convertToDto)
                .toList());
    }

    // level 2 Richardson
    @PostMapping
    ResponseEntity<Void> save(@RequestBody ClientDTO clientDTO) {
        Client client = clientService.save(convertToEntity(clientDTO));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(client.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    ResponseEntity<ClientDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok(convertToDto(clientService.findById(id)));
    }

    // Hateoas - level 3 Richardson
    @GetMapping("/hateoas/{id}")
    EntityModel<ClientDTO> findByIdHateoas(@PathVariable Integer id) {
        EntityModel<ClientDTO> resource = EntityModel.of(this.convertToDto(clientService.findById(id)));
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findById(id));
        resource.add(link.withRel("client-info"));
        return resource;
    }

    @PutMapping("/{id}")
    ResponseEntity<ClientDTO> update(@PathVariable Integer id, @RequestBody ClientDTO clientDTO) {
        clientDTO.setId(id);
        Client client = clientService.update(id, convertToEntity(clientDTO));
        return ResponseEntity.ok(convertToDto(client));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Integer id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }


    public ClientDTO convertToDto(Client dto) {
        return mapper.map(dto, ClientDTO.class);
    }
    public Client convertToEntity(ClientDTO entity) {
        return mapper.map(entity, Client.class);
    }
}
