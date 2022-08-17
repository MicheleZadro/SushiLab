package it.synclab.sushilab.controller;


import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import it.synclab.sushilab.entity.IdToken;
import it.synclab.sushilab.entity.ListaOrdini;
import it.synclab.sushilab.entity.Menu;
import it.synclab.sushilab.entity.Ordine;
import it.synclab.sushilab.entity.OrdineDettaglio;
import it.synclab.sushilab.entity.Session;
import it.synclab.sushilab.entity.Utente;
import it.synclab.sushilab.service.ClientService;
import it.synclab.sushilab.utility.Utility;
import it.synclab.sushilab.entity.ListaOrdineDettaglio;

@RestController
@RequestMapping(value = "tavolo")
public class TavoloController {
    @Autowired
    private ClientService clienteService;


    /*  Crea Sessione POST http:/localhost:3000/tavolo   */

    @PostMapping(path = "persona/{idPersona}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> creaSessione(@PathVariable String idPersona){
        //Generate random value
        String value = Utility.generateString(7, 7, true, true, true);
        //Check if already exists
        boolean rtrn = false;
        int count = 0;
        while(rtrn == false){
            if(count >= 1000)
                return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
            rtrn = clienteService.insertSessionCode(value, idPersona);
            count++;
        }
        JSONObject body = new JSONObject();
        body.put("id", value);
        return new ResponseEntity<String>(body.toString(), HttpStatus.CREATED);
    }
    /*--------------------------------------------------- */

    /* Ottieni Sessione GET http:/localhost:3000/tavolo{idTavolo} Manca il ritorno del menù*/
    @GetMapping(path = "{idTavolo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ottieniSessione(@PathVariable String idTavolo){
        //Verifico se la sessione esiste
        boolean exist = clienteService.existSessionCode(idTavolo);
        if(!exist)
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        //Ricevo il menù dell'orario giusto
        Menu menu = clienteService.riceviMenu();
        if(menu == null) 
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        //Elimino ridondanze e ricorsione infinita
        for(int i = 0; i < menu.getFasce().size(); i++){
            menu.getFasce().get(i).setMenu(null);
        }
        for(int i = 0; i < menu.getMenu().size(); i++){
            menu.getMenu().get(i).setMenu(null);
            for (int j = 0; j < menu.getMenu().get(i).getPiatti().size(); j++){
                menu.getMenu().get(i).getPiatti().get(j).setSezionePreview(null);
            }
        }
        JSONObject body = new JSONObject(menu);
        return new ResponseEntity<String>(body.toString(), HttpStatus.OK);
    }
    /*--------------------------------------------------- */

    /* Chiudi Sessione DELETE http:/localhost:3000/tavolo{idTavolo} */
    @DeleteMapping(path = "{idTavolo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> chiudiSessione(@PathVariable String idTavolo){
        //Check if exists
        boolean exist = clienteService.existSessionCode(idTavolo);
        if(!exist)
            return new ResponseEntity<String>(HttpStatus.METHOD_NOT_ALLOWED);
        clienteService.deleteSession(idTavolo);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    /* Modifica ordine di una persona POST http://localhost:3000/tavolo/{idTavolo}/persona/{idPersona} */
    @GetMapping(path = "{idTavolo}/persona/{idPersona}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ottieniOrdiniPersona(@PathVariable String idTavolo, @PathVariable String idPersona){
        List<OrdineDettaglio> personali = clienteService.ottieniOrdini(idPersona, idTavolo);
        if(personali == null) return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        ListaOrdineDettaglio listaOrdineDettaglio = new ListaOrdineDettaglio(personali);
        JSONObject json = new JSONObject(listaOrdineDettaglio);
        System.out.println(listaOrdineDettaglio);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    /* Modifica ordine di una persona POST http://localhost:3000/tavolo/{idTavolo}/persona/{idPersona} */
    @PostMapping(path = "{idTavolo}/persona/{idPersona}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> modificaOrdiniPersona(@PathVariable String idTavolo, @PathVariable String idPersona, @RequestBody ListaOrdini ordini){
        //Inserisci ordini con tutte le verifiche
        if(!clienteService.inserisciOrdini(ordini.getOrdini(), idPersona, idTavolo)) return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
