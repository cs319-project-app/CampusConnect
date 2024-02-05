package com.fullstackfidelity.campusconnect.repositories;

import com.fullstackfidelity.campusconnect.entities.item.Item;
import jakarta.transaction.Transactional;

@Transactional
public interface ItemRepository extends ItemBaseRepository<Item> {

}

