package com.fullstackfidelity.campusconnect.controller;
import com.fullstackfidelity.campusconnect.LoginRequest;
import com.fullstackfidelity.campusconnect.UserUpdateRequest;
import com.fullstackfidelity.campusconnect.entities.user.User;
import com.fullstackfidelity.campusconnect.exception.UserNotFoundException;
import com.fullstackfidelity.campusconnect.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@org.springframework.web.bind.annotation.RestController
@AllArgsConstructor
@RequestMapping("/v1/user/")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserService userService;
    @GetMapping("retrieveAllUsers")
    public List<User> retrieveAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("create")
    public ResponseEntity<User> registerUser(@RequestBody User user){
        User savedUser = userService.createUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedUser.getUser_id()).toUri();
        return ResponseEntity.created(location).body(savedUser);
    }
    @PutMapping("/{user_id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "user_id") Long userId,
                                           @RequestBody UserUpdateRequest userDetails) {
        User updatedUser = userService.updateUser(userId, userDetails);
        return ResponseEntity.ok(updatedUser);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("{id}")
    public EntityModel<User> retrieveUser(@PathVariable long id){
        Optional<User> user = userService.getUserById(id);
        if(user.isEmpty())
        {
            throw new UserNotFoundException("id"+id);
        }
        EntityModel<User> entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }
    @PostMapping("login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
        // Find user by username or email.
        Optional<User> optionalUser = userService.getUserByUserName(loginRequest.getUsernameOrEmail());
        Optional<User> optionalUser1 = userService.getUserByBilkentMail(loginRequest.getUsernameOrEmail());
        boolean passwordMatches = true;
        User user;
        if (!optionalUser.isPresent() && !optionalUser1.isPresent()) {
            return ResponseEntity.badRequest().body(null); // or another suitable error response.
        }else if(optionalUser1.isPresent())
        {
            user = optionalUser1.get();
        }else{
            user = optionalUser.get();
        }
        if(!loginRequest.getPassword().equals(user.getPassword()))
        {
            passwordMatches = false;
        }

        if (!passwordMatches) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("/{userId}/block/{blockedUserId}")
    public ResponseEntity<?> blockUser(@PathVariable long userId, @PathVariable long blockedUserId) {
        userService.blockUser(userId, blockedUserId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/unblock/{blockedUserId}")
    public ResponseEntity<?> unblockUser(@PathVariable long userId, @PathVariable long blockedUserId) {
        userService.unblockUser(userId, blockedUserId);
        return ResponseEntity.ok().build();
    }
}
