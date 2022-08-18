package it.synclab.sushilab.controller;

import java.util.List;

import org.json.JSONException;
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

import it.synclab.sushilab.entity.ListaMenu;
import it.synclab.sushilab.entity.ListaPiatti;
import it.synclab.sushilab.entity.Menu;
import it.synclab.sushilab.entity.PiattoUpload;
import it.synclab.sushilab.service.ClientService;
import it.synclab.sushilab.service.GestoreService;

@RestController
@RequestMapping(value = "gestore")
public class GestoreController {
    @Autowired
    GestoreService gestoreService;
    @Autowired
    private ClientService clienteService;

    /* Nuovo piatto */
    @PostMapping(path = "{idPersona}/piatto", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> nuovoPiatto(@PathVariable String idPersona, @RequestBody PiattoUpload piattoUpload){
        //Verifico idPersona corrisponde a un gestore
        if(!clienteService.isGestore(idPersona))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        //try {
            gestoreService.inserisciPiattoUpload(piattoUpload);
        //} catch (Exception e) {
           // return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        //}
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    /* Elimina piatto */
    @DeleteMapping(path = "{idPersona}/piatto/{idPiatto}")
    public ResponseEntity<String> eliminaPiatto(@PathVariable String idPersona, @PathVariable String idPiatto){
        //Verifico che idPiatto sia intero
        int id;
        try {
            id = (Integer.parseInt(idPiatto));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        //Verifico idPersona corrisponde a un gestore
        if(!clienteService.isGestore(idPersona))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        //Verifico che l'id del piatto esista
        if(!gestoreService.rimuoviPiattoUpload(id))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /* Aggiorna Piatto */
    @PostMapping(path = "{idPersona}/piatto/{idPiatto}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> aggiornaPiatto(@PathVariable String idPersona, @PathVariable String idPiatto, @RequestBody PiattoUpload piattoUpload){

        //Verifico che idPiatto sia intero
        int id;
        try {
            id = (Integer.parseInt(idPiatto));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        //Verifico idPersona corrisponde a un gestore
        if(!clienteService.isGestore(idPersona))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        //Verifico che idPiatto esista e se c'è effettuo l'update
        if(!gestoreService.aggiornaPiattoUpload(id, piattoUpload))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /* Ottieni piatto */
    @GetMapping(path = "{idPersona}/piatto/{idPiatto}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ottieniPiatto(@PathVariable String idPersona, @PathVariable String idPiatto){
        //Verifico che idPiatto sia intero
        int id;
        try {
            id = (Integer.parseInt(idPiatto));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        //Verifico idPersona corrisponde a un gestore
        if(!clienteService.isGestore(idPersona))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        JSONObject json;
        //Verifico che l'id del piatto esista e ritorno
        try {
            PiattoUpload rtrn = gestoreService.riceviPiattoUpload(id);
            if(rtrn == null)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            json = new JSONObject(rtrn);
            json.remove("id");
            return new ResponseEntity<>(json.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /* Ottieni Lista Piatti */
    @GetMapping(path = "{idPersona}/piatti", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ottieniListaPiatti(@PathVariable String idPersona){
        //Verifico idPersona corrisponde a un gestore
        if(!clienteService.isGestore(idPersona)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        //Ricevo lista piatti
        ListaPiatti listaPiatti = gestoreService.ottieniListaPiatti();
        JSONObject json = new JSONObject(listaPiatti);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }



    /* Nuovo Menu */
    @PostMapping(path = "{idPersona}/menu", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> nuovoMenu(@PathVariable String idPersona, @RequestBody String name){
        //Verifico idPersona se è gestore
        if(!clienteService.isGestore(idPersona))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        
        //Creo nuovo menù
        JSONObject body;
        try{
            body = new JSONObject(name);
        }catch(JSONException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if(!body.has("name"))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        int idMenu = gestoreService.inserisciMenu(body.getString("name"));
        JSONObject rtrnJsonObject = new JSONObject();
        rtrnJsonObject.put("id", idMenu);
        return new ResponseEntity<>(rtrnJsonObject.toString(), HttpStatus.OK);
    }

    /* Aggiorna Menu */
    @PostMapping(path = "{idPersona}/menu/{idMenu}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> aggiornaMenu(@PathVariable String idPersona, @PathVariable String idMenu, @RequestBody Menu menu){
        //Verifico che idPiatto sia intero
        int id;
        try {
            id = (Integer.parseInt(idMenu));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        //Verifica idPersona se è gestore
        if(!clienteService.isGestore(idPersona))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        
        //Aggiorna menu
        if(!gestoreService.aggiornaMenu(id, menu)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /* Elimina Menu */
    @DeleteMapping(path = "{idPersona}/menu/{idMenu}")
    public ResponseEntity<String> eliminaMenu(@PathVariable String idPersona, @PathVariable String idMenu){
        //Verifico che idPiatto sia intero
        int id;
        try {
            id = (Integer.parseInt(idMenu));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        //Verifica idPersona se è gestore
        if(!clienteService.isGestore(idPersona))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        //Elimina menu
        if(!gestoreService.eliminaMenu(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /* Ottieni lista menù */
    @GetMapping(path = "{idPersona}/menu", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ottieniListaMenu(@PathVariable String idPersona){
        //Verifico idPersona corrisponde a un gestore
        if(!clienteService.isGestore(idPersona))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        ListaMenu menu = gestoreService.ottieniListaMenu();
        JSONObject json = new JSONObject(menu);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
        
    }

}
