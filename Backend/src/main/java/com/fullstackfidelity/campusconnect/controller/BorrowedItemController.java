package com.fullstackfidelity.campusconnect.controller;

import com.fullstackfidelity.campusconnect.ItemUpdateRequest;
import com.fullstackfidelity.campusconnect.entities.item.BorrowedItem;
import com.fullstackfidelity.campusconnect.entities.post.Post;
import com.fullstackfidelity.campusconnect.exception.ItemIsHoldException;
import com.fullstackfidelity.campusconnect.exception.ItemNotFoundException;
import com.fullstackfidelity.campusconnect.repositories.BorrowedItemRepository;
import com.fullstackfidelity.campusconnect.service.BorrowedItemService;
import com.fullstackfidelity.campusconnect.service.PostService;
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
@RequestMapping("/v1/borrow")
@CrossOrigin(origins = "http://localhost:3000")
public class BorrowedItemController {
    private final BorrowedItemService borrowedItemService;

    @Autowired
    public BorrowedItemController(BorrowedItemService borrowedItemService) {
        this.borrowedItemService = borrowedItemService;
    }
    @PostMapping("/post")
    public ResponseEntity<BorrowedItem> postItem(@RequestBody BorrowedItem item, @RequestParam long userId){
        BorrowedItem postedItem=borrowedItemService.postItem(item,userId);
        return new ResponseEntity<>(postedItem, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> retrieveItem(@PathVariable long id) {
        borrowedItemService.retrieveItem(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filter/{userId}")
    public ResponseEntity<Page<Post>> getAllPosts(@PathVariable long userId, @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        Page<Post> posts = borrowedItemService.getAllPosts(userId,page,size);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/retrieveAllItems")
    public List<BorrowedItem> retrieveAllItems(){
        return borrowedItemService.getAllItems();
    }
    @GetMapping("/itemById/{id}")
    public EntityModel<BorrowedItem> retrieveItemById(@PathVariable long id){
        Optional<BorrowedItem> item = borrowedItemService.getItemById(id);
        if(item.isEmpty())
        {
            throw new ItemNotFoundException("id"+id);
        }
        EntityModel<BorrowedItem> entityModel = EntityModel.of(item.get());
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllItems());
        entityModel.add(link.withRel("all-items"));
        return entityModel;
    }
//    @GetMapping("/itemByName/{itemName}")
//    public EntityModel<BorrowedItem> retrieveItemByName(@PathVariable String itemName){
//        Optional<BorrowedItem> item = borrowedItemService.getItemByName(itemName);
//        if(item.isEmpty())
//        {
//            throw new ItemNotFoundException("name"+itemName);
//        }
//        EntityModel<BorrowedItem> entityModel = EntityModel.of(item.get());
//        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllItems());
//        entityModel.add(link.withRel("all-items"));
//        return entityModel;
//    }
    @PutMapping("/update/{itemId}")
    public ResponseEntity<BorrowedItem> updateItem(@PathVariable(value = "itemId") long itemId,
                                                   @RequestBody ItemUpdateRequest itemDetails) {
        BorrowedItem updatedItem= borrowedItemService.updateItem(itemId,itemDetails);
        return ResponseEntity.ok(updatedItem);
    }
    @PutMapping("/borrow/{itemId}")
    public ResponseEntity<BorrowedItem> borrowItem(@PathVariable(value = "itemId") long itemId,  @RequestParam long userId) {
        borrowedItemService.borrowItem(itemId, userId);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/return/{itemId}")
    public ResponseEntity<BorrowedItem> returnItem(@PathVariable(value = "itemId") long itemId) {
        borrowedItemService.returnItem(itemId);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/hold/{itemId}")
    public ResponseEntity<BorrowedItem> holdItem(@PathVariable(value = "itemId") long itemId, @RequestParam long userId) throws ItemIsHoldException {
        borrowedItemService.holdItem(itemId, userId);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/extraTime/{itemId}")
    public ResponseEntity<BorrowedItem> addExtraTimeToBorrowItem(@PathVariable(value = "itemId") long itemId) throws ItemIsHoldException {
       borrowedItemService.addExtraTimeToBorrowItem(itemId);
       return ResponseEntity.ok().build();
    }
}

