package it.synclab.sushilab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.synclab.sushilab.entity.Blacklist;

@Repository
public interface BlacklistRepository extends JpaRepository<Blacklist, Integer> {
    
}
