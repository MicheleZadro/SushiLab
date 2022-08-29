package it.synclab.sushilab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdineMerge {
    private int idPiatto;
    private int count;
    private String note;
    private String idPersona;
}
