package com.fullstackfidelity.campusconnect.service;

import com.fullstackfidelity.campusconnect.entities.item.IdCard;
import com.fullstackfidelity.campusconnect.entities.user.User;
import com.fullstackfidelity.campusconnect.repositories.IdCardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class IdCardService {

    private final IdCardRepository idCardRepository;
    @Autowired
    @Lazy
    private UserService userService;
    public IdCardService(IdCardRepository idCardRepository, @Lazy UserService userService) {
        this.idCardRepository = idCardRepository;
        this.userService = userService;
    }
    @Autowired
    public IdCardService(IdCardRepository idCardRepository){
        this.idCardRepository = idCardRepository;
    }
    public IdCard postIdCard(IdCard idCard, long userId){
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        idCard.setUser(user);
        idCard.setUserIdValue(userId);
        idCard.setPostDate(LocalDate.now());
        idCard.setFounderId(user.getBilkentID());
        return idCardRepository.save(idCard);
    }
    public void retrieveIdCard(long id) {
        idCardRepository.deleteById(id);
    }
    public List<IdCard> getAllItems() {
        return idCardRepository.findAll();
    }
    public Optional<IdCard> getItemById(long id) {return idCardRepository.findById(id);}
}
