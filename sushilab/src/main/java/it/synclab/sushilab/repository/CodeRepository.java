package it.synclab.sushilab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.synclab.sushilab.entity.Code;

@Repository
public interface CodeRepository extends JpaRepository<Code, String> {
    public Code findByCode(String code);
}
