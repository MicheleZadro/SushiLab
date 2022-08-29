package it.synclab.sushilab.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.synclab.sushilab.classes.ArraySezione;
import it.synclab.sushilab.classes.Fasce;
import it.synclab.sushilab.classes.Preferiti;
import it.synclab.sushilab.entity.FasciaOraria;
import it.synclab.sushilab.service.ClientService;

@RestController
@RequestMapping(value = "menu")
public class MenuController {
    @Autowired
    ClientService clientService;

    /* Ottieni menu GET http://localhost:3000/menu/{idMenu}/persona/{idPersona} */
    @GetMapping(path = "{idMenu}/persona/{idPersona}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ottieniMenu(@PathVariable int idMenu, @PathVariable String idPersona){
        ArraySezione arraySezione = clientService.ottieniMenu(idMenu, idPersona);
        if(arraySezione == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        JSONObject json = new JSONObject(arraySezione);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }
    /* Ottieni la lista delle fasce di validità GET http://localhost:3000/menu/{idMenu}/fasce */
    @GetMapping(path = "{idMenu}/fasce", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ottieniListaFasce(@PathVariable int idMenu){
        Fasce fasce = clientService.ottieniFasce(idMenu);
        if(fasce == null) return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        List<FasciaOraria> list = fasce.getFasce();
        for(int i = 0; i < list.size(); i++){
            list.get(i).setMenu(null);
        }
        JSONObject json = new JSONObject(fasce);
        System.out.println(json.toString());
        JSONArray jsonArray = json.getJSONArray("fasce");
        for(int i = 0; i < jsonArray.length(); i++){
            jsonArray.getJSONObject(i).remove("id");
        }
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }
    /* Ottieni la lista dei preferiti GET http://localhost:3000/menu/{idMenu}/persona/{idPersona}/preferiti */
    @GetMapping(path = "{idMenu}/persona/{idPersona}/preferiti", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ottieniListaPreferiti(@PathVariable int idMenu, @PathVariable String idPersona){
        Preferiti preferiti = clientService.ottieniListaPreferiti(idMenu, idPersona);
        if(preferiti == null) return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        JSONObject json = new JSONObject(preferiti);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }
    /* Ottieni il tema del menu GET http://localhost:3000/menu/{idMenu}/stile */
    @GetMapping(path = "{idMenu}/stile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ottieniTema(@PathVariable String idMenu){
        String s = "{\"test\":\"test\"}";
        return new ResponseEntity<>(s, HttpStatus.OK);
    }
}
