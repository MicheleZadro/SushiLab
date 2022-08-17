package it.synclab.sushilab.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ListaOrdineDettaglio {
    private List<OrdineDettaglio> personale;
}
