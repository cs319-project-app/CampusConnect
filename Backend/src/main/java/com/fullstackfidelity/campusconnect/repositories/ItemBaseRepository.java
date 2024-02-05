package com.fullstackfidelity.campusconnect.repositories;

import com.fullstackfidelity.campusconnect.entities.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemBaseRepository<T extends Item> extends JpaRepository<T, Long> {
    T findByItemName(String itemName);

    T findByItemId(long id);
}

