package it.synclab.sushilab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.synclab.sushilab.entity.PiattoUpload;

@Repository
public interface PiattoUploadRepository extends JpaRepository<PiattoUpload, Integer> {
    
}
