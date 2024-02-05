package com.fullstackfidelity.campusconnect.service;

import com.fullstackfidelity.campusconnect.ItemUpdateRequest;
import com.fullstackfidelity.campusconnect.entities.item.DonationItem;
import com.fullstackfidelity.campusconnect.entities.post.Post;
import com.fullstackfidelity.campusconnect.entities.user.User;
import com.fullstackfidelity.campusconnect.exception.ItemNotFoundException;
import com.fullstackfidelity.campusconnect.repositories.DonationItemRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
public class DonationItemService {
    private final DonationItemRepository donationItemRepository;
    @Autowired
    @Lazy
    private UserService userService;
    public DonationItemService(DonationItemRepository donationItemRepository, @Lazy UserService userService) {
        this.donationItemRepository = donationItemRepository;
        this.userService = userService;
    }
    @Autowired
    public DonationItemService(DonationItemRepository donationItemRepository) {
        this.donationItemRepository = donationItemRepository;
    }
    public DonationItem postItem(DonationItem item, long userId){
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        item.setUser(user);
        item.setUserIdValue(user.getUser_id());
        item.setPostDate(LocalDate.now());
        item.setDonatorId(user.getBilkentID());
        return donationItemRepository.save(item);
    }
    public void retrieveItem(long id) {
        donationItemRepository.deleteById(id);
    }
    public DonationItem updateItem(long itemId, ItemUpdateRequest itemDetails) {
        DonationItem item = donationItemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item with ID " + itemId + " not found"));

        itemDetails.getItemName().ifPresent(item::setItemName);
        itemDetails.getImagePath().ifPresent(item::setImagePath);
        itemDetails.getDescription().ifPresent(item::setDescription);
        return donationItemRepository.save(item);
    }
    @Transactional
    public void receiveItem(long itemId, long userId) {
        DonationItem item;
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        Optional<DonationItem> item1 = donationItemRepository.findById(itemId);
        if(item1.isEmpty())
        {
            throw new ItemNotFoundException("Item with id "+itemId+" not found!");
        }else{
            item = item1.get();
        }
        item.setRecipientId(user.getBilkentID());
        item.setStatus("donated");
        item.setTakenDate(LocalDate.now());
        donationItemRepository.save(item);
    }
    public List<DonationItem> getAllItems() {
        return donationItemRepository.findAll();
    }
    public Optional<DonationItem> getItemById(long id) {
        return donationItemRepository.findById(id);
    }
    public Optional<DonationItem> getItemByName(String itemName) {
        return Optional.ofNullable(donationItemRepository.findByItemName(itemName));
    }

    public Page<Post> getAllPosts(long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("creationTimestamp").descending());
        return donationItemRepository.findPostsConsideringBlock(userId,pageable);
    }
}
