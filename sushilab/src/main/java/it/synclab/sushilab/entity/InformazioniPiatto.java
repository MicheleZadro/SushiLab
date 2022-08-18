package it.synclab.sushilab.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class InformazioniPiatto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonIgnore
    @OneToOne
    @JoinColumn
    private Utente utente;
    @JsonIgnore
    @OneToOne
    @JoinColumn
    private PiattoUpload piatto;
    private boolean preferito;
    private int valutazione;
    private String ultimoOrdine;
}
