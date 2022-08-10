package it.synclab.sushilab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.synclab.sushilab.entity.PiattoPreview;


@Repository
public interface PiattoPreviewRepository extends JpaRepository<PiattoPreview, Integer> {
    
}
