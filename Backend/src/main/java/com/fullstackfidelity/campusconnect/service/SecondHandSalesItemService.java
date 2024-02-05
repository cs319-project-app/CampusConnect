package com.fullstackfidelity.campusconnect.service;

import com.fullstackfidelity.campusconnect.ItemUpdateRequest;
import com.fullstackfidelity.campusconnect.entities.item.BorrowedItem;
import com.fullstackfidelity.campusconnect.entities.item.SecondHandSalesItem;
import com.fullstackfidelity.campusconnect.entities.post.Post;
import com.fullstackfidelity.campusconnect.entities.user.User;
import com.fullstackfidelity.campusconnect.exception.ItemNotFoundException;
import com.fullstackfidelity.campusconnect.repositories.BorrowedItemRepository;
import com.fullstackfidelity.campusconnect.repositories.SecondHandSalesItemRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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
public class SecondHandSalesItemService {
    private final SecondHandSalesItemRepository secondHandSalesItemRepository;
    @Autowired
    @Lazy
    private UserService userService;
    public SecondHandSalesItemService(SecondHandSalesItemRepository secondHandSalesItemRepository, @Lazy UserService userService) {
        this.secondHandSalesItemRepository = secondHandSalesItemRepository;
        this.userService = userService;
    }
    @Autowired
    public SecondHandSalesItemService(SecondHandSalesItemRepository secondHandSalesItemRepository) {
        this.secondHandSalesItemRepository = secondHandSalesItemRepository;
    }
    public SecondHandSalesItem postItem(SecondHandSalesItem item, long userId){
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        item.setUser(user);
        item.setPostDate(LocalDate.now());
        item.setUserIdValue(userId);
        item.setDealerId(user.getBilkentID());
        return secondHandSalesItemRepository.save(item);
    }
    public void retrieveItem(long id) {
        secondHandSalesItemRepository.deleteById(id);
    }

    public SecondHandSalesItem updateItem(long itemId, ItemUpdateRequest itemDetails) {
        SecondHandSalesItem item = secondHandSalesItemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item with ID " + itemId + " not found"));

        itemDetails.getItemName().ifPresent(item::setItemName);
        itemDetails.getImagePath().ifPresent(item::setImagePath);
        itemDetails.getDescription().ifPresent(item::setDescription);
        itemDetails.getItemAge().ifPresent(item::setItemAge);
        itemDetails.getItemPrice().ifPresent(item::setItemPrice);
        return secondHandSalesItemRepository.save(item);
    }
    @Transactional
    public void saleItem(long itemId, long userId) {
        SecondHandSalesItem item;
        Optional<SecondHandSalesItem> item1 = secondHandSalesItemRepository.findById(itemId);
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        if(item1.isEmpty())
        {
            throw new ItemNotFoundException("Item with id "+itemId+" not found!");
        }else{
            item = item1.get();
        }
        item.setRecipientId(user.getBilkentID());
        item.setStatus("sold");
        item.setSaleTime(LocalDate.now());
        secondHandSalesItemRepository.save(item);
        retrieveItem(itemId);
    }
    public List<SecondHandSalesItem> getAllItems() {
        return secondHandSalesItemRepository.findAll();
    }
    public Optional<SecondHandSalesItem> getItemById(long id) {return secondHandSalesItemRepository.findById(id);}
    public Optional<SecondHandSalesItem> getItemByName(String itemName) {
        return Optional.ofNullable(secondHandSalesItemRepository.findByItemName(itemName));
    }

    public Page<Post> getAllPosts(long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("creationTimestamp").descending());
        return secondHandSalesItemRepository.findPostsConsideringBlock(userId,pageable);
    }
}
