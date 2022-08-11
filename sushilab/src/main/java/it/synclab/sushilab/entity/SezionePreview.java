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


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
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
    //@JsonManagedReference(value = "piattiReference")
    private List<PiattoPreview> piatti;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @Getter(value = AccessLevel.NONE)
    //@JsonIgnore
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //@JsonBackReference(value = "sezionePreviewReference")
    private Menu menu;
    
    public void setPiatti(List<PiattoPreview> piatti){
        this.piatti = piatti;
        for(int i = 0; i < piatti.size(); i++){
            piatti.get(i).setSezionePreview(this);
        }
    }

    //@JsonIgnore
    public Menu getMenu() {
        return menu;
    }

    

}
