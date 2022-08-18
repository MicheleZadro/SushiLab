package it.synclab.sushilab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.synclab.sushilab.entity.InformazioniPiatto;

@Repository
public interface InformazioniPiattoRepository extends JpaRepository<InformazioniPiatto, Integer> {
    
}
