package it.synclab.sushilab.classes;

import java.util.List;

import it.synclab.sushilab.entity.Ordine;
import lombok.Data;


@Data
public class ListaOrdini {
    private List<Ordine> ordini;
}
