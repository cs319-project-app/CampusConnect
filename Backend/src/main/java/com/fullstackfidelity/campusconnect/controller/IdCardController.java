package com.fullstackfidelity.campusconnect.controller;

import com.fullstackfidelity.campusconnect.entities.item.IdCard;
import com.fullstackfidelity.campusconnect.exception.ItemNotFoundException;
import com.fullstackfidelity.campusconnect.service.IdCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/idCard")
public class IdCardController {
    private final IdCardService idCardService;

    @Autowired
    public IdCardController(IdCardService idCardService) {
        this.idCardService = idCardService;
    }

    @PostMapping("/post")
    public ResponseEntity<IdCard> postIdCard(@RequestBody IdCard idCard, @RequestParam long userId){
        IdCard foundedId=idCardService.postIdCard(idCard,userId);
        return new ResponseEntity<>(foundedId, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> retrieveIdCard(@PathVariable long id) {
        idCardService.retrieveIdCard(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/retrieveAllItems")
    public List<IdCard> retrieveAllItems(){
        return idCardService.getAllItems();
    }
    @GetMapping("/{id}")
    public EntityModel<IdCard> retrieveItemById(@PathVariable long id){
        Optional<IdCard> item = idCardService.getItemById(id);
        if(item.isEmpty())
        {
            throw new ItemNotFoundException("id"+id);
        }
        EntityModel<IdCard> entityModel = EntityModel.of(item.get());
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllItems());
        entityModel.add(link.withRel("all-items"));
        return entityModel;
    }
}
