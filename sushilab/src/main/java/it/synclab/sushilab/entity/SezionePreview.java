package it.synclab.sushilab.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SezionePreview {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    @OneToMany(mappedBy = "sezionePreview", cascade = CascadeType.ALL)
    @Setter(value = AccessLevel.NONE)
    private List<PiattoPreview> piatti;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Menu menu;
    
    public void setPiatti(List<PiattoPreview> piatti){
        this.piatti = piatti;
        for(int i = 0; i < piatti.size(); i++){
            piatti.get(i).setSezionePreview(this);
        }
    }

    // public SezionePreview(int id, String nome, Menu menu, List<PiattoPreview> piatti){
    //     this.id = id;
    //     this.nome = nome;
    //     this.menu = menu;
    //     this.piatti = piatti;
    //     for(int i = 0; i < piatti.size(); i++){
    //         piatti.get(i).setSezionePreview(this);
    //     }
    // } 
}
