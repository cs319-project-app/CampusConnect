package com.fullstackfidelity.campusconnect.controller;

import com.fullstackfidelity.campusconnect.entities.user.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fullstackfidelity.campusconnect.entities.post.Post;
import com.fullstackfidelity.campusconnect.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/v1/posts")
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create/")
    public ResponseEntity<Post> createPost(@RequestBody Post post, @RequestParam long userId) {
        Post createdPost = postService.createPost(post, userId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
    @GetMapping("/isLike/{postId}")
    public ResponseEntity<Boolean> isPostLikedByUser(@PathVariable Long postId, @RequestParam Long userId) {
        boolean isLiked = postService.isPostLikedByUser(postId, userId);
        return ResponseEntity.ok(isLiked);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return postService.getPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/filter/{userId}")
    public ResponseEntity<Page<Post>> getAllPosts(@PathVariable long userId,@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        Page<Post> posts = postService.getAllPosts(userId,page,size);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getUserPosts(@PathVariable long userId) {
        List<Post> userPosts = postService.getPostsByUserId(userId);
        return ResponseEntity.ok(userPosts);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
        Post updatedPost = postService.updatePost(id,post);
        return ResponseEntity.ok(updatedPost);
    }

//    @PutMapping("/{post_id}/totalLikes")
//    public void updateLikePost(@PathVariable long post_id, @RequestBody long userId) {
//        postService.likePost(post_id, userId);
//    }
//
//    @PutMapping("/{post_id}/totalLikes")
//    public void updateDislikePost(@PathVariable long post_id, @RequestBody long userId) {
//        postService.unlikePost(post_id, userId);
//    }

    @PostMapping("/{post_id}/like")
    public ResponseEntity<?> toggleLike(@PathVariable long post_id, @RequestParam long userId) {
        boolean isLiked = postService.toggleLike(post_id, userId);
        return ResponseEntity.ok(isLiked);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }

}
