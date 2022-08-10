package it.synclab.sushilab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.synclab.sushilab.entity.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, String>{
    
}
