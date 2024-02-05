package com.fullstackfidelity.campusconnect.repositories;

import com.fullstackfidelity.campusconnect.entities.item.IdCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IdCardRepository extends JpaRepository<IdCard,Long> {
    Optional<IdCard> findByLosedIdNo(String losedIdNo);
    Optional<IdCard> findByIdLoserName(String idLoserName);
    Optional<IdCard> findByIdLoserDepartment(String idLoserDepartment);
}
