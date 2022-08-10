package it.synclab.sushilab.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
/* Il Code serve per il recupero della password */
public class Code{
    @Id
    private String email;
    @Column(name="code", nullable = false)
    private String code;
}
