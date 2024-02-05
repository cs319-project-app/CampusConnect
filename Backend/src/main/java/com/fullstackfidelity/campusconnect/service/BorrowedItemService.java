package com.fullstackfidelity.campusconnect.service;

import com.fullstackfidelity.campusconnect.ItemUpdateRequest;
import com.fullstackfidelity.campusconnect.entities.item.BorrowedItem;
import com.fullstackfidelity.campusconnect.entities.post.Post;
import com.fullstackfidelity.campusconnect.entities.user.User;
import com.fullstackfidelity.campusconnect.exception.ItemIsHoldException;
import com.fullstackfidelity.campusconnect.exception.ItemNotFoundException;
import com.fullstackfidelity.campusconnect.repositories.BorrowedItemRepository;
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
public class BorrowedItemService {
    private final BorrowedItemRepository borrowedItemRepository;
    @Autowired
    @Lazy
    private UserService userService;
    public BorrowedItemService(BorrowedItemRepository borrowedItemRepository, @Lazy UserService userService) {
        this.borrowedItemRepository = borrowedItemRepository;
        this.userService = userService;
    }
    @Autowired
    public BorrowedItemService(BorrowedItemRepository borrowedItemRepository) {
        this.borrowedItemRepository = borrowedItemRepository;
    }
    public BorrowedItem postItem(BorrowedItem item, long userId){
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        item.setUser(user);
        item.setUserIdValue(userId);
        item.setPostDate(LocalDate.now());
        item.setLenderId(user.getBilkentID());
        return borrowedItemRepository.save(item);
    }
    public void retrieveItem(long id) {
        borrowedItemRepository.deleteById(id);
    }
    public BorrowedItem updateItem(long itemId, ItemUpdateRequest itemDetails) {
         BorrowedItem item = borrowedItemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item with ID " + itemId + " not found"));

        itemDetails.getItemName().ifPresent(item::setItemName);
        itemDetails.getImagePath().ifPresent(item::setImagePath);
        itemDetails.getDescription().ifPresent(item::setDescription);
        return borrowedItemRepository.save(item);
    }
    @Transactional
    public void borrowItem(long itemId, long userId){
        BorrowedItem item;
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        Optional<BorrowedItem> item1 = borrowedItemRepository.findById(itemId);
        if(item1.isEmpty())
        {
            throw new ItemNotFoundException("Item with id "+itemId+" not found!");
        }else{
            item = item1.get();
        }
        item.setBorrowerId(user.getBilkentID());
        item.setStatus("borrowed");
        item.setTakenDate(LocalDate.now());
        borrowedItemRepository.save(item);
    }
    @Transactional
    public void returnItem(long itemId) {
        BorrowedItem item;
        Optional<BorrowedItem> item1 = borrowedItemRepository.findById(itemId);
        if(item1.isEmpty())
        {
            throw new ItemNotFoundException("Item with id "+itemId+" not found!");
        }else{
            item = item1.get();
        }
        item.setBorrowerId("");
        item.setStatus("returned");
        item.setRetrieveDate(LocalDate.now());
        borrowedItemRepository.save(item);
    }
    @Transactional
    public void holdItem(long itemId, long userId) throws ItemIsHoldException {
        BorrowedItem item;
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        Optional<BorrowedItem> item1 = borrowedItemRepository.findById(itemId);
        if(item1.isEmpty())
        {
            throw new ItemNotFoundException("Item with id "+itemId+" not found!");
        }else{
            item = item1.get();
        }
        if(!item.isHold()){
            item.setHold(true);
            item.setStatus("holded");
            item.setHolderId(user.getBilkentID());
        }
        else{
            throw new ItemIsHoldException("Item with id "+itemId+" is held, please try again later!");
        }
        borrowedItemRepository.save(item);
    }
    @Transactional
    public void addExtraTimeToBorrowItem(long itemId) throws ItemIsHoldException {
        BorrowedItem item;
        Optional<BorrowedItem> item1 = borrowedItemRepository.findById(itemId);
        if(item1.isEmpty())
        {
            throw new ItemNotFoundException("Item with id "+itemId+" not found!");
        }else{
            item = item1.get();
        }
        if(!item.isHold()){
            item.setExpectedReturnDate(item.getExpectedReturnDate().plusDays(item.getEXTRA_TIME()));
            borrowedItemRepository.save(item);
        }
        else{
            throw new ItemIsHoldException("Item with id "+itemId+" is held, you cannot extend the time!");
        }
    }
    public List<BorrowedItem> getAllItems() {
        return borrowedItemRepository.findAll();
    }
    public Optional<BorrowedItem> getItemById(long id) {return borrowedItemRepository.findById(id);}
    public Optional<BorrowedItem> getItemByName(String itemName) {
        return Optional.ofNullable(borrowedItemRepository.findByItemName(itemName));
    }

    public Page<Post> getAllPosts(long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("creationTimestamp").descending());
        return borrowedItemRepository.findPostsConsideringBlock(userId,pageable);
    }
}

