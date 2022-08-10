package it.synclab.sushilab.entity;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Piatto {
    @Id
    private int id;
    private int numero;
    private String variante;
    private String nome;
    private float prezzo;
    @ElementCollection(targetClass=String.class)
    private List<String> allergeni;
    @ElementCollection(targetClass=String.class)
    private List<String> ingredienti;
    private int limite;
    private int valutazioneMedia;
    private int valutazioneUtente;
    private boolean preferito;
    private String ultimoOrdine;
    private boolean popolare;
    private boolean consigliato;
    private String immagine;
    private String alt;
}
