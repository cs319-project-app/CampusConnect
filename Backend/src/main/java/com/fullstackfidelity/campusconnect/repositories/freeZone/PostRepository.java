package com.fullstackfidelity.campusconnect.repositories.freeZone;

import com.fullstackfidelity.campusconnect.entities.post.Post;
import com.fullstackfidelity.campusconnect.entities.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    //this is for filtering post
    @Query("SELECT p FROM Post p WHERE p.user.user_id NOT IN (SELECT ub.user_id FROM User u JOIN u.blockedUsers ub WHERE u.user_id = :userId) AND :userId NOT IN (SELECT ub.user_id FROM User u JOIN u.blockedUsers ub WHERE u.user_id = p.user.user_id)")
    Page<Post> findPostsConsideringBlock(@Param("userId") long userId, Pageable pageable);

    List<Post> findByUser(User user);

}
