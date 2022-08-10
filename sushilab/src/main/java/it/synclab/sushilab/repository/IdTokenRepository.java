package it.synclab.sushilab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.synclab.sushilab.entity.IdToken;

@Repository
public interface IdTokenRepository extends JpaRepository<IdToken, String> {
    
}
