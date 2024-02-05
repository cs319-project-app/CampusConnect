package com.fullstackfidelity.campusconnect.controller;

import com.fullstackfidelity.campusconnect.entities.comment.Comment;
import com.fullstackfidelity.campusconnect.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/comments")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{commentId}/replies")
    public ResponseEntity<List<Comment>> getRepliesByCommentId(@PathVariable Long commentId) {
        List<Comment> replies = commentService.getRepliesByCommentId(commentId);
        return ResponseEntity.ok(replies);
    }
    @PostMapping("/{parentCommentId}/replies/{userId}")
    public ResponseEntity<Comment> addReplyToComment(@PathVariable Long parentCommentId, @RequestBody Comment reply,@PathVariable long userId) {
        Comment createdReply = commentService.addReplyToComment(parentCommentId, reply,userId);
        return ResponseEntity.ok(createdReply);
    }
    @PostMapping("/create")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment,@RequestParam long postId,@RequestParam long userId)
    {
        Comment created = commentService.createComment(comment,postId,userId);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/like/{commentId}")
    public ResponseEntity<?> likeComment(@PathVariable long commentId){
       commentService.likeComment(commentId);
        return ResponseEntity.ok().build();
    }

}
