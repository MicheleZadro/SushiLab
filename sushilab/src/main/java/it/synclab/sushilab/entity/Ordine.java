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
@AllArgsConstructor
@NoArgsConstructor
public class Ordine {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int idPiatto;
    private int count;
    private String note;
    @JsonIgnore
    @OneToOne
    @JoinColumn
    private IdToken idPersona;
    @JsonIgnore
    @OneToOne
    @JoinColumn
    private Session idTavolo;
}
