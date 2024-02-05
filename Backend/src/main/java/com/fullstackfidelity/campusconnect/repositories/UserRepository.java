package com.fullstackfidelity.campusconnect.repositories;

import com.fullstackfidelity.campusconnect.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findUserByBilkentMail(String bilkentMail);
    Optional<User> findByBilkentID(String bilkentId);
    boolean existsByCode(String code);
}
