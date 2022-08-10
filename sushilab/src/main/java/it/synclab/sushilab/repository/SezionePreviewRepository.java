package it.synclab.sushilab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.synclab.sushilab.entity.SezionePreview;

@Repository
public interface SezionePreviewRepository extends JpaRepository<SezionePreview, Integer>{
    
}
