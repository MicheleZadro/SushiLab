package it.synclab.sushilab.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.synclab.sushilab.entity.Ordine;
import it.synclab.sushilab.entity.IdToken;

@Repository
public interface OrdineRepository extends JpaRepository<Ordine, Integer> {
    
}
