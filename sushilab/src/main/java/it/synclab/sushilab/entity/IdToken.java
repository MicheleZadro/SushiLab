package it.synclab.sushilab.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
/* IdToken verrà usato come IdPersona per identificare i vari clienti */
public class IdToken {
    @Id
    @Column(name = "id_token")
    private String idToken;
    @OneToOne
    @JoinColumn(name = "cliente", referencedColumnName = "email", nullable = false, unique = true)
    private Utente cliente;
}
