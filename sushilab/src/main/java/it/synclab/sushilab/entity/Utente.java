package it.synclab.sushilab.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Utente{
    @Id
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @JsonProperty("isGestore")
    private Boolean isGestore = false;
    @JsonIgnore
    @OneToOne
    @JoinColumn(referencedColumnName = "id_token")
    private IdToken idPersona = null;
    @JsonIgnore
    @OneToOne
    @JoinColumn(referencedColumnName = "id_table")
    private Session tavolo = null;
}
