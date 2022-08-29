package it.synclab.sushilab.classes;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdineDettaglio {
    private Piatto piatto;
    private int molteplicità;
    private List<String> note;
}
