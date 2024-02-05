package com.fullstackfidelity.campusconnect.controller;

import com.fullstackfidelity.campusconnect.ItemUpdateRequest;
import com.fullstackfidelity.campusconnect.entities.item.BorrowedItem;
import com.fullstackfidelity.campusconnect.entities.item.SecondHandSalesItem;
import com.fullstackfidelity.campusconnect.entities.post.Post;
import com.fullstackfidelity.campusconnect.exception.ItemNotFoundException;
import com.fullstackfidelity.campusconnect.repositories.SecondHandSalesItemRepository;
import com.fullstackfidelity.campusconnect.service.BorrowedItemService;
import com.fullstackfidelity.campusconnect.service.SecondHandSalesItemService;
import com.fullstackfidelity.campusconnect.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@RestController
@RequestMapping("/v1/second-hand-sales")
@CrossOrigin(origins = "http://localhost:3000")
public class SecondHandSalesItemController {

    private final SecondHandSalesItemService secondHandSalesItemService;

    @Autowired
    public SecondHandSalesItemController(SecondHandSalesItemService secondHandSalesItemService) {
        this.secondHandSalesItemService = secondHandSalesItemService;
    }
    @PostMapping("/post")
    public ResponseEntity<SecondHandSalesItem> postItem(@RequestBody SecondHandSalesItem item, @RequestParam long userId){
        SecondHandSalesItem postedItem=secondHandSalesItemService.postItem(item,userId);
        return new ResponseEntity<>(postedItem, HttpStatus.CREATED);
    }

    @GetMapping("/filter/{userId}")
    public ResponseEntity<Page<Post>> getAllPosts(@PathVariable long userId, @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        Page<Post> posts = secondHandSalesItemService.getAllPosts(userId,page,size);
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> retrieveItem(@PathVariable long id) {
        secondHandSalesItemService.retrieveItem(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/retrieveAllItems")
    public List<SecondHandSalesItem> retrieveAllItems(){
        return secondHandSalesItemService.getAllItems();
    }
    @GetMapping("/itemById/{id}")
    public EntityModel<SecondHandSalesItem> retrieveItemById(@PathVariable long id){
        Optional<SecondHandSalesItem> item = secondHandSalesItemService.getItemById(id);
        if(item.isEmpty())
        {
            throw new ItemNotFoundException("id"+id);
        }
        EntityModel<SecondHandSalesItem> entityModel = EntityModel.of(item.get());
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllItems());
        entityModel.add(link.withRel("all-items"));
        return entityModel;
    }
//    @GetMapping("/itemByName/{itemName}")
//    public EntityModel<SecondHandSalesItem> retrieveItemByName(@PathVariable String itemName){
//        Optional<SecondHandSalesItem> item = secondHandSalesItemService.getItemByName(itemName);
//        if(item.isEmpty())
//        {
//            throw new ItemNotFoundException("name"+itemName);
//        }
//        EntityModel<SecondHandSalesItem> entityModel = EntityModel.of(item.get());
//        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllItems());
//        entityModel.add(link.withRel("all-items"));
//        return entityModel;
//    }
    @PutMapping("/update/{itemId}")
    public ResponseEntity<SecondHandSalesItem> updateItem(@PathVariable(value = "itemId") long itemId,
                                                   @RequestBody ItemUpdateRequest itemDetails) {
        SecondHandSalesItem updatedItem= secondHandSalesItemService.updateItem(itemId,itemDetails);
        return ResponseEntity.ok(updatedItem);
    }
    @PutMapping("/sale/{itemId}")
    public ResponseEntity<SecondHandSalesItem> saleItem(@PathVariable(value = "itemId") long itemId, @RequestParam long userId) {
        secondHandSalesItemService.saleItem(itemId, userId);
        return ResponseEntity.ok().build();
    }
}
