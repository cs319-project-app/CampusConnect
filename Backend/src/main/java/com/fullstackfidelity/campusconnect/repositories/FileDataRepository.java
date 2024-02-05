package com.fullstackfidelity.campusconnect.repositories;


import com.fullstackfidelity.campusconnect.entities.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileDataRepository extends JpaRepository<FileData,Integer> {
    Optional<FileData> findByName(String fileName);
}
