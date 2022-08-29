package it.synclab.sushilab.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"id"})
public class FasciaOraria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String giorno;

    private int oraInizio;

    private int oraFine;



    @ManyToOne
    @JoinColumn(name = "menu", referencedColumnName = "id")
    @Getter(value = AccessLevel.NONE)
    //@JsonIgnore
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //@JsonBackReference(value = "fasceReference")
    private Menu menu;

    //@JsonIgnore
    public Menu getMenu() {
        return menu;
    }
    public int getId(){
        return id;
    }
    
}
