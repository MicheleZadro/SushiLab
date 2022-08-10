package it.synclab.sushilab.entity;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PiattoUpload {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int numero;
    private String variante;
    private String nome;
    private int prezzo;
    private String immagine;
    private String alt;
    @ElementCollection(targetClass=String.class)
    private List<String> allergeni;
    @ElementCollection(targetClass=String.class)
    private List<String> ingredienti;
}
