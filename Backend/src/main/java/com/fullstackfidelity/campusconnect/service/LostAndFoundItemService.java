package com.fullstackfidelity.campusconnect.service;

import com.fullstackfidelity.campusconnect.ItemUpdateRequest;
import com.fullstackfidelity.campusconnect.entities.item.LostAndFoundItem;
import com.fullstackfidelity.campusconnect.entities.post.Post;
import com.fullstackfidelity.campusconnect.entities.user.User;
import com.fullstackfidelity.campusconnect.exception.ItemNotFoundException;
import com.fullstackfidelity.campusconnect.repositories.LostAndFoundItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class LostAndFoundItemService {
    private final LostAndFoundItemRepository lostAndFoundItemRepository;
    @Autowired
    @Lazy
    private UserService userService;
    public LostAndFoundItemService(LostAndFoundItemRepository lostAndFoundItemRepository, @Lazy UserService userService) {
        this.lostAndFoundItemRepository = lostAndFoundItemRepository;
        this.userService = userService;
    }
    @Autowired
    public LostAndFoundItemService(LostAndFoundItemRepository lostAndFoundItemRepository) {
        this.lostAndFoundItemRepository = lostAndFoundItemRepository;
    }
    public LostAndFoundItem postItem(LostAndFoundItem item, long userId){
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        item.setUser(user);
        item.setUserIdValue(userId);
        item.setPostDate(LocalDate.now());
        item.setFounderId(user.getBilkentID());
        item.setStatus("found");
        return lostAndFoundItemRepository.save(item);
    }
    public void retrieveItem(long id) {
        lostAndFoundItemRepository.deleteById(id);
    }
    public LostAndFoundItem updateItem(long itemId, ItemUpdateRequest itemDetails) {
        LostAndFoundItem item = lostAndFoundItemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item with ID " + itemId + " not found"));

        itemDetails.getItemName().ifPresent(item::setItemName);
        itemDetails.getImagePath().ifPresent(item::setImagePath);
        itemDetails.getDescription().ifPresent(item::setDescription);
        return lostAndFoundItemRepository.save(item);
    }
    public List<LostAndFoundItem> getAllItems() {
        return lostAndFoundItemRepository.findAll();
    }
    public Optional<LostAndFoundItem> getItemById(long id) {return lostAndFoundItemRepository.findById(id);}
    public Optional<LostAndFoundItem> getItemByName(String itemName) {
        return Optional.ofNullable(lostAndFoundItemRepository.findByItemName(itemName));
    }

    public Page<Post> getAllPosts(long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("creationTimestamp").descending());
        return lostAndFoundItemRepository.findPostsConsideringBlock(userId,pageable);
    }
}
