package com.fullstackfidelity.campusconnect.controller;
import com.fullstackfidelity.campusconnect.DTO.LostAndFoundItemDTO;
import com.fullstackfidelity.campusconnect.ItemUpdateRequest;
import com.fullstackfidelity.campusconnect.entities.item.BorrowedItem;
import com.fullstackfidelity.campusconnect.entities.item.LostAndFoundItem;
import com.fullstackfidelity.campusconnect.entities.post.Post;
import com.fullstackfidelity.campusconnect.exception.ItemNotFoundException;
import com.fullstackfidelity.campusconnect.service.LostAndFoundItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("/v1/lost-and-found")
public class LostAndFoundItemController {

    private final LostAndFoundItemService lostAndFoundItemService;

    @Autowired
    public LostAndFoundItemController(LostAndFoundItemService lostAndFoundItemService) {
        this.lostAndFoundItemService = lostAndFoundItemService;
    }

    @GetMapping("/filter/{userId}")
    public ResponseEntity<Page<Post>> getAllPosts(@PathVariable long userId, @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        Page<Post> posts = lostAndFoundItemService.getAllPosts(userId,page,size);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/post")
    public ResponseEntity<LostAndFoundItem> postItem(@RequestBody LostAndFoundItem item, @RequestParam long userId){
        LostAndFoundItem postedItem=lostAndFoundItemService.postItem(item,userId);
        return new ResponseEntity<>(postedItem, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> retrieveItem(@PathVariable long id) {
        lostAndFoundItemService.retrieveItem(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/update/{itemId}")
    public ResponseEntity<LostAndFoundItem> updateItem(@PathVariable(value = "itemId") long itemId,
                                                   @RequestBody ItemUpdateRequest itemDetails) {
        LostAndFoundItem updatedItem= lostAndFoundItemService.updateItem(itemId,itemDetails);
        return ResponseEntity.ok(updatedItem);
    }
    @GetMapping("/getAllItems")
    public List<LostAndFoundItem> getAllLostAndFoundItems() {
        return lostAndFoundItemService.getAllItems();
    }

    @GetMapping("/getAllItemsById/{id}")
    public ResponseEntity<LostAndFoundItem> getLostAndFoundItemById(@PathVariable Long id) {
        return lostAndFoundItemService.getItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
//    @GetMapping("/{itemName}")
//    public EntityModel<LostAndFoundItem> getLostAndFoundItemByName(@PathVariable String itemName){
//        Optional<LostAndFoundItem> item = lostAndFoundItemService.getItemByName(itemName);
//        if(item.isEmpty())
//        {
//            throw new ItemNotFoundException("name"+itemName);
//        }
//        EntityModel<LostAndFoundItem> entityModel = EntityModel.of(item.get());
//        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllLostAndFoundItems());
//        entityModel.add(link.withRel("all-items"));
//        return entityModel;
//    }

}
