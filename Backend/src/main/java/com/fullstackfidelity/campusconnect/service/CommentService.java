package com.fullstackfidelity.campusconnect.service;

import com.fullstackfidelity.campusconnect.entities.comment.Comment;
import com.fullstackfidelity.campusconnect.entities.post.Post;
import com.fullstackfidelity.campusconnect.entities.user.User;
import com.fullstackfidelity.campusconnect.exception.CommentNotFoundException;
import com.fullstackfidelity.campusconnect.exception.UserNotFoundException;
import com.fullstackfidelity.campusconnect.repositories.freeZone.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class CommentService {
    private final CommentRepository commentRepository;
    @Autowired
    @Lazy
    private final UserService userService;
    @Autowired
    @Lazy
    private final PostService postService;
    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService){
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }
    public List<Comment> getCommentsByPostId(long postId) {
        return commentRepository.findByFreeZonePost_PostId(postId);
    }
    public List<Comment> getRepliesByCommentId(long commentId) {
        return commentRepository.findByParentComment_CommentId(commentId);
    }
    public Comment likeComment(long commentId){
        Comment comment =commentRepository.findById(commentId).orElseThrow(()-> new CommentNotFoundException("Comment With  ID " + commentId+ " not found"));
        comment.setLikes(comment.getLikes()+1);
        commentRepository.save(comment);
        return comment;
    }
    @Transactional
    public Comment createComment(Comment comment,long postId,long userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
        comment.setUser(user);
        comment.setUserIdValue(userId);
        Post post = postService.getPostById(postId).orElseThrow(() -> new UserNotFoundException("Post with ID " + postId + " not found"));
        comment.setFreeZonePost(post);
        System.out.println(post);
        return commentRepository.save(comment);
    }

    public void deleteReply(long id)
    {
        commentRepository.deleteById(id);
    }
    public Optional<Comment> updateComment(long commentId, Comment updatedComment) {
        return commentRepository.findById(commentId).map(comment -> {
            comment.setCommentContent(updatedComment.getCommentContent());
            return commentRepository.save(comment);
        });
    }
    public Comment addReplyToComment(long parentCommentId, Comment reply,long userId) {
        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new RuntimeException("Parent comment not found"));
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
        reply.setParentComment(parentComment);
        Post post = parentComment.getFreeZonePost();
        Post nPosr= postService.getPostById(post.getPostId()).orElseThrow(()-> new RuntimeException("No post founc"));
        reply.setFreeZonePost(nPosr);
        reply.setUser(user);
        reply.setUserIdValue(user.getUser_id());
        return commentRepository.save(reply);
    }
}
