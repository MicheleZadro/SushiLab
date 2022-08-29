package it.synclab.sushilab.classes;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuCompatto {
    private int id;
    private String nome;
    private List<Integer> piatti;
}
