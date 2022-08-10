package it.synclab.sushilab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.synclab.sushilab.entity.FasciaOraria;

@Repository
public interface FasciaOrariaRepository extends JpaRepository<FasciaOraria, Integer> {
    
}
