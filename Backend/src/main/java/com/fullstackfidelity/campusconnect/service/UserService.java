package com.fullstackfidelity.campusconnect.service;

import com.fullstackfidelity.campusconnect.ForgetPassword;
import com.fullstackfidelity.campusconnect.UserUpdateRequest;
import com.fullstackfidelity.campusconnect.entities.user.User;
import com.fullstackfidelity.campusconnect.exception.UserNotFoundException;
import com.fullstackfidelity.campusconnect.exception.WrongCodeException;
import com.fullstackfidelity.campusconnect.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Data
public class UserService {
    private UserRepository userRepository;
    public UserService(UserRepository userRepository) { //@Lazy BorrowedItemService itemService(bu şekilde bir service yok)
        this.userRepository = userRepository;
    }
    public String generateUniqueCode() {
        String code;
        do {
            code = generateRandomCode();
        } while (userRepository.existsByCode(code));

        return code;
    }

    private String generateRandomCode() {
        return UUID.randomUUID().toString().substring(0, 8);
    }



    public User createUser(User user) {
        user.setCode(generateUniqueCode());
        User savedUser =userRepository.save(user);
        return savedUser;
    }
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @PutMapping("/v1/forgetPassword")
    public ResponseEntity<?> forgetPassword(@RequestBody ForgetPassword userDetails)
    {
        String mail= userDetails.getBilkentMail();
        String code = userDetails.getCode();
        Optional<User> user1 = userRepository.findUserByBilkentMail(mail);
        if(user1.isEmpty())
        {
            throw new UserNotFoundException("There is no such mail"+mail);
        }else{
            if(user1.get().getCode().equals(code))
            {
                user1.get().setPassword(userDetails.getPassword());
                userRepository.save(user1.get());
                return ResponseEntity.ok().build();
            }else{
                throw new WrongCodeException("You entered wrong code");
            }
        }
    }
    public User updateUser(Long userId, UserUpdateRequest userDetails) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

            userDetails.getUsername().ifPresent(user::setUsername);

            userDetails.getPassword().ifPresent(user::setPassword);

            userDetails.getProfilePath().ifPresent(user::setUserPhotoPath);
            System.out.println(userDetails.toString());


        return userRepository.save(user);
    }

    @Transactional
    public void blockUser(long userId, long blockedUserId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
        User blockedUser = userRepository.findById(blockedUserId)
                .orElseThrow(() -> new UserNotFoundException("Blocked user with ID " + blockedUserId + " not found"));

        user.getBlockedUsers().add(blockedUser);
        userRepository.save(user);
    }

    @Transactional
    public void unblockUser(long userId, long blockedUserId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
        User blockedUser = userRepository.findById(blockedUserId)
                .orElseThrow(() -> new UserNotFoundException("Blocked user with ID " + blockedUserId + " not found"));

        user.getBlockedUsers().remove(blockedUser);
        userRepository.save(user);
    }

    public boolean isUserBlocked(long userId, long checkBlockedUserId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
        return user.getBlockedUsers().stream().anyMatch(b -> b.getUser_id() == checkBlockedUserId);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }
    public Optional<User> getUserByUserName(String name) {
        return userRepository.findByUsername(name);
    }
    public Optional<User> getUserByBilkentId(String id) {
        return userRepository.findByBilkentID(id);
    }
    public Optional<User> getUserByBilkentMail(String mail ) {
        return userRepository.findUserByBilkentMail(mail);
    }
}
