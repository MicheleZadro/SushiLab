package it.synclab.sushilab.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    @OneToMany(mappedBy = "menu", orphanRemoval=true, cascade = {CascadeType.ALL})
    @Setter(value = AccessLevel.NONE)
    private List<SezionePreview> menu;
    @OneToMany(mappedBy = "menu", orphanRemoval=true, cascade = {CascadeType.ALL})
    @Setter(value = AccessLevel.NONE)
    private List<FasciaOraria> fasce;

    public void setMenu(List<SezionePreview> menu){
        this.menu = menu;
        for(int i = 0; i < this.menu.size(); i++){
            this.menu.get(i).setMenu(this);
        }
    }

    public void setFasce(List<FasciaOraria> fasce){
        this.fasce = fasce;
        for(int i = 0; i < this.fasce.size(); i++){
            this.fasce.get(i).setMenu(this);
        }
    }

}
