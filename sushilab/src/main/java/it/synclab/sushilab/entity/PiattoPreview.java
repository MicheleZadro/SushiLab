package it.synclab.sushilab.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PiattoPreview {
    @Id
    private int id;
    private int numero;
    private String variante;
    private String nome;
    private Boolean consigliato;
    private int limite;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private PiattoUpload piattoUpload;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private SezionePreview sezionePreview;
}
