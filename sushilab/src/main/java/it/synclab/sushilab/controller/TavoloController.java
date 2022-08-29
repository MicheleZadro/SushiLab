package it.synclab.sushilab.controller;


import java.util.ArrayList;
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


import it.synclab.sushilab.entity.InArrivo;
import it.synclab.sushilab.entity.ListaOrdini;
import it.synclab.sushilab.entity.Menu;
import it.synclab.sushilab.entity.MenuCompatto;
import it.synclab.sushilab.entity.MenuCompattoSessione;
import it.synclab.sushilab.entity.Ordine;
import it.synclab.sushilab.entity.OrdineDettaglio;
import it.synclab.sushilab.service.ClientService;
import it.synclab.sushilab.utility.Utility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import it.synclab.sushilab.entity.ListaOrdineDettaglio;
import it.synclab.sushilab.entity.ListaOrdineDettaglioTavolo;
import it.synclab.sushilab.entity.ListaOrdineMerge;

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
        MenuCompatto menuCompatto = new MenuCompatto();
        if(menu == null) 
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        menuCompatto.setId(menu.getId());
        menuCompatto.setNome(menu.getNome());
        List<Integer> piatti = new ArrayList<>();
        for(int i = 0; i < menu.getMenu().size(); i++){
            for(int j = 0; j < menu.getMenu().get(i).getPiatti().size(); j++){
                piatti.add(menu.getMenu().get(i).getPiatti().get(j).getId());
            }
        }
        menuCompatto.setPiatti(piatti);

        MenuCompattoSessione menuRisposta = new MenuCompattoSessione(menuCompatto);
        System.out.println(menuRisposta);
        JSONObject json = new JSONObject(menuRisposta);
        System.out.println(json);
        return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
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

    /* Ottieni ordini di una persona Get http://localhost:3000/tavolo/{idTavolo}/persona/{idPersona} */
    @GetMapping(path = "{idTavolo}/persona/{idPersona}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ottieniOrdiniPersona(@PathVariable String idTavolo, @PathVariable String idPersona){
        List<OrdineDettaglio> personali = clienteService.ottieniOrdiniPersonali(idPersona, idTavolo);
        if(personali == null) return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        ListaOrdineDettaglio listaOrdineDettaglio = new ListaOrdineDettaglio(personali);
        JSONObject json = new JSONObject(listaOrdineDettaglio);
        System.out.println(listaOrdineDettaglio);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }
    
    /* Modifica gli ordini di una persona POST http://localhost:3000/tavolo/{idTavolo}/persona/{idPersona} */
    @PostMapping(path = "{idTavolo}/persona/{idPersona}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> modificaOrdiniPersona(@PathVariable String idTavolo, @PathVariable String idPersona, @RequestBody ListaOrdini ordini){
        //Inserisci ordini con tutte le verifiche
        if(!clienteService.inserisciOrdini(ordini.getOrdini(), idPersona, idTavolo)) return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /* Ottieni ordini di un tavolo Get http://localhost:3000/tavolo/{idTavolo}/persona/{idPersona}/ordini */
    @GetMapping(path = "{idTavolo}/persona/{idPersona}/ordini", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ottieniOrdiniTavolo(@PathVariable String idTavolo, @PathVariable String idPersona){
        List<OrdineDettaglio> ordini = clienteService.ottieniOrdiniTavolo(idPersona, idTavolo);
        if(ordini == null) return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        ListaOrdineDettaglioTavolo listaOrdineDettaglioTavolo = new ListaOrdineDettaglioTavolo(ordini);
        JSONObject json = new JSONObject(listaOrdineDettaglioTavolo);
        //System.out.println(listaOrdineDettaglio);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }
    
    /* Ottieni gli ordini in arrivo Get http://localhost:3000/tavolo/{idTavolo}/inarrivo/{idPersona} */
    @GetMapping(path = "{idTavolo}/inarrivo/{idPersona}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ottieniGliOrdiniInArrivo(@PathVariable String idTavolo, @PathVariable String idPersona){
        List<OrdineDettaglio> list = clienteService.ottieniGliOrdiniInArrivo(idTavolo, idPersona);
        if(list == null) return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        InArrivo inArrivo = new InArrivo(list);
        JSONObject json = new JSONObject(inArrivo);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }
    
    /* Sposta gli ordini a in arrivo POST http://localhost:3000/tavolo/{idTavolo}/inarrivo */
    @PostMapping(path = "{idTavolo}/inarrivo")
    public ResponseEntity<String> spostaGliOrdiniInArrivo(@PathVariable String idTavolo){
        if(!clienteService.spostaGliOrdiniInArrivo(idTavolo)) return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /* Mergia 2 sessioni di tavolo */
    @PostMapping(path = "merge/{idTavolo}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> mergeTavolo(@PathVariable String idTavolo, @RequestBody ListaOrdineMerge body){
        if(!clienteService.mergeTavolo(idTavolo, body)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
