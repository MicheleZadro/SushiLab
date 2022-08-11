package it.synclab.sushilab.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
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

    @ManyToOne
    @JoinColumn
    @Getter(value = AccessLevel.NONE)
    //@JsonIgnore
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private PiattoUpload piattoUpload;

    @ManyToOne
    @JoinColumn
    @Getter(value = AccessLevel.NONE)
    //@JsonIgnore
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //@JsonBackReference(value = "piattiReference")
    private SezionePreview sezionePreview;

    //@JsonIgnore
    public PiattoUpload getPiattoUpload() {
        return piattoUpload;
    }

    //@JsonIgnore
    public SezionePreview getSezionePreview() {
        return sezionePreview;
    }

    
}
