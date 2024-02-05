package com.fullstackfidelity.campusconnect.controller;

import com.fullstackfidelity.campusconnect.ItemUpdateRequest;
import com.fullstackfidelity.campusconnect.entities.item.DonationItem;
import com.fullstackfidelity.campusconnect.entities.post.Post;
import com.fullstackfidelity.campusconnect.exception.ItemNotFoundException;
import com.fullstackfidelity.campusconnect.repositories.DonationItemRepository;
import com.fullstackfidelity.campusconnect.service.DonationItemService;
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
@RequestMapping("/v1/donate")
@CrossOrigin(origins = "http://localhost:3000")
public class DonationItemController {
    private final DonationItemService donationItemService;

    @Autowired
    public DonationItemController(DonationItemService donationItemService) {
        this.donationItemService = donationItemService;
    }
    @PostMapping("/post")
    public ResponseEntity<DonationItem> postItem(@RequestBody DonationItem item, @RequestParam long userId){
        DonationItem postedItem=donationItemService.postItem(item,userId);
        return new ResponseEntity<>(postedItem, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> retrieveItem(@PathVariable long id) {
        donationItemService.retrieveItem(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filter/{userId}")
    public ResponseEntity<Page<Post>> getAllPosts(@PathVariable long userId, @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        Page<Post> posts = donationItemService.getAllPosts(userId,page,size);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/retrieveAllItems")
    public List<DonationItem> retrieveAllItems(){
        return donationItemService.getAllItems();
    }
    @GetMapping("/itemById/{id}")
    public EntityModel<DonationItem> retrieveItemById(@PathVariable long id){
        Optional<DonationItem> item = donationItemService.getItemById(id);
        if(item.isEmpty())
        {
            throw new ItemNotFoundException("id"+id);
        }
        EntityModel<DonationItem> entityModel = EntityModel.of(item.get());
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllItems());
        entityModel.add(link.withRel("all-items"));
        return entityModel;
    }
//    @GetMapping("/itemByName/{itemName}")
//    public EntityModel<DonationItem> retrieveItemByName(@PathVariable String itemName){
//        Optional<DonationItem> item = donationItemService.getItemByName(itemName);
//        if(item.isEmpty())
//        {
//            throw new ItemNotFoundException("name"+itemName);
//        }
//        EntityModel<DonationItem> entityModel = EntityModel.of(item.get());
//        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllItems());
//        entityModel.add(link.withRel("all-items"));
//        return entityModel;
//    }
    @PutMapping("/update/{itemId}")
    public ResponseEntity<DonationItem> updateItem(@PathVariable(value = "itemId") long itemId,
                                                   @RequestBody ItemUpdateRequest itemDetails) {
        DonationItem updatedItem= donationItemService.updateItem(itemId,itemDetails);
        return ResponseEntity.ok(updatedItem);
    }
    @PutMapping("/donate/{itemId}")
    public ResponseEntity<DonationItem> receiveItem(@PathVariable(value = "itemId") long itemId, @RequestParam long userId) {
        donationItemService.receiveItem(itemId, userId);
        return ResponseEntity.ok().build();
    }
}
