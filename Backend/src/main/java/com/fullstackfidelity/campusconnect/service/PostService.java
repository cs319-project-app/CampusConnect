package com.fullstackfidelity.campusconnect.service;
import com.fullstackfidelity.campusconnect.entities.post.Post;
import com.fullstackfidelity.campusconnect.entities.user.User;
import com.fullstackfidelity.campusconnect.exception.PostNotFoundException;
import com.fullstackfidelity.campusconnect.exception.UserNotFoundException;
import com.fullstackfidelity.campusconnect.repositories.UserRepository;
import com.fullstackfidelity.campusconnect.repositories.freeZone.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Data
public class PostService {
    @Autowired
    @Lazy
    private UserService userService;
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository, @Lazy UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }
    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    public boolean isPostLikedByUser(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));
        return post != null && post.getLikedByUsers().contains(userId);
    }
    public Post createPost(Post post, long userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        post.setUser(user);
        post.setUserIdValue(userId);
        return postRepository.save(post);
    }
    public List<Post> getPostsByUserId(long userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
        return postRepository.findByUser(user);
    }
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }
    public Page<Post> getAllPosts(long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size,Sort.by("creationTimestamp").descending());
        return postRepository.findPostsConsideringBlock(userId,pageable);
    }


    public void likePost(long postId, long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

        if (!post.getLikedByUsers().contains(userId)) {
            post.getLikedByUsers().add(userId);
            postRepository.save(post);
        }
    }


    public boolean toggleLike(long postId, long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        Set<Long> likes = post.getLikedByUsers();

        boolean isLiked;
        if (likes.contains(userId)) {
            likes.remove(userId);
            isLiked = false;
        } else {
            likes.add(userId);
            isLiked = true;
        }
        post.setLikedByUsers(likes);
        postRepository.save(post);
        return isLiked;
    }



    public void unlikePost(long postId, long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));

        if (post.getLikedByUsers().contains(userId)) {
            post.getLikedByUsers().remove(userId);
            postRepository.save(post);
        }
    }


    public Post updatePost(long postId,Post post) {
        Post original = getPostById(postId).orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));
        if(post.getImagePath()!=null)
            original.setImagePath(post.getImagePath());
        if(post.getContent()!=null)
            original.setContent(post.getContent());

        return
                postRepository.save(original);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

}
