package it.synclab.sushilab.controller;

 
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.synclab.sushilab.classes.Ingredienti;
import it.synclab.sushilab.entity.Utente;
import it.synclab.sushilab.service.ClientService;

@RestController
@RequestMapping(value = "utente")
public class UtenteController {
    @Autowired
    private ClientService clienteService;
    
    /* Ottieni le informazioni dell'utente GET http://localhost:3000/utente/{idPersona} */
    @GetMapping(path = "/{idPersona}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ottieniUtente(@PathVariable String idPersona){
        Utente utente = clienteService.ottieniUtente(idPersona);
        if(utente == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        utente.setTavolo(null);
        utente.setIdPersona(null);
        //System.out.println(utente);
        JSONObject json = new JSONObject(utente);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    /* Registra Utente POST http://localhost:3000/utente */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registraUtente(@RequestBody Utente utente){
        if(clienteService.insert(utente)) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    
    /* Modifica stato preferiti POST http://localhost:3000/utente/{idPersona}/favorito/{idPiatto}*/
    @PostMapping(path = "{idPersona}/favorito/{idPiatto}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> modificaStatoPreferiti(@PathVariable String idPersona, @PathVariable String idPiatto, @RequestBody String param){
        JSONObject json = new JSONObject(param);
        if(!json.has("fav"))
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
            boolean value = json.getBoolean("fav");
            if(!clienteService.modificaStatoPreferiti(idPersona, Integer.parseInt(idPiatto), value))
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        
    /* Modifica valutazione  POST http://localhost:3000/utente/{idPersona}/rate/{idPiatto}*/
    @PostMapping(path = "{idPersona}/rate/{idPiatto}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> modificaValutazione(@PathVariable String idPersona, @PathVariable String idPiatto, @RequestBody String param){
        JSONObject json = new JSONObject(param);
        if(!json.has("rate"))
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        int value = json.getInt("rate");
        if(!clienteService.modificaValutazione(idPersona, Integer.parseInt(idPiatto), value))
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    /* Invia email per il recupero della password POST http://localhost:3000/utente/code */
    @PostMapping(path = "code", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> recuperoPassword(@RequestBody String param){
        JSONObject body = new JSONObject(param);
        if(!body.has("email"))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(!clienteService.recuperoPassword(body.getString("email")))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    /* Verifica codice recupero password POST http://localhost:3000/utente/verifyCode */
    @PostMapping(path = "verifyCode", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> verificaCodice(@RequestBody String param){
        JSONObject body = new JSONObject(param);
        if(!body.has("code"))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(!clienteService.verify(body.getString("code")))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    /* Reimposta la password dimenticata POST http://localhost:3000/utente/pass*/
    @PostMapping(path = "pass", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> reimpostaPassword(@RequestBody String body){
        try {
            JSONObject json = new JSONObject(body);
            if(!json.has("email") || !json.has("newpass")) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            if(clienteService.reimpostaPassword(json.getString("email"), json.getString("newpass"))) return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }   
    }

    /* Aggiorna blacklist POST http://localhost:3000/utente/{idPersona}/blacklist */
    @PostMapping(path = "{idPersona}/blacklist", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> aggiornaBlacklist(@PathVariable String idPersona, @RequestBody Ingredienti ingredienti){
        clienteService.aggiornaBlacklist(idPersona, ingredienti);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    /* Ottieni la blacklist GET http://localhost:3000/utente/{idPersona}/blacklist*/
    @GetMapping(path = "{idPersona}/blacklist", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ottieniBlacklist(@PathVariable String idPersona){
        List<String> list = clienteService.ottieniBlacklist(idPersona);
        if(list == null) 
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        Ingredienti ingredienti = new Ingredienti(list);
        JSONObject json = new JSONObject(ingredienti);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }
}
