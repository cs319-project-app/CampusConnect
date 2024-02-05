package com.fullstackfidelity.campusconnect.repositories.freeZone;

import com.fullstackfidelity.campusconnect.entities.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByFreeZonePost_PostId(long postId);

    List<Comment> findByParentComment_CommentId(long commentId);
}
