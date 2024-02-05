package com.fullstackfidelity.campusconnect.repositories;

import com.fullstackfidelity.campusconnect.entities.item.SecondHandSalesItem;
import com.fullstackfidelity.campusconnect.entities.post.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

@Transactional
public interface SecondHandSalesItemRepository extends ItemBaseRepository<SecondHandSalesItem> {

    @Query("SELECT p FROM SecondHandSalesItem p WHERE p.user.user_id NOT IN (SELECT ub.user_id FROM User u JOIN u.blockedUsers ub WHERE u.user_id = :userId) AND :userId NOT IN (SELECT ub.user_id FROM User u JOIN u.blockedUsers ub WHERE u.user_id = p.user.user_id)")
    Page<Post> findPostsConsideringBlock(long userId, Pageable pageable);
}