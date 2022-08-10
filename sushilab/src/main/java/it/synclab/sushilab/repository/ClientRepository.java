package it.synclab.sushilab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.synclab.sushilab.entity.Utente;

@Repository
public interface ClientRepository extends JpaRepository<Utente, String> {
    
}
