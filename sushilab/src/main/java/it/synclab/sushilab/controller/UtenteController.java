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

import it.synclab.sushilab.entity.Ingredienti;
import it.synclab.sushilab.entity.Utente;
import it.synclab.sushilab.service.ClientService;

@RestController
@RequestMapping(value = "utente")
public class UtenteController {
    @Autowired
    private ClientService clienteService;
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registraUtente(@RequestBody String param){
        JSONObject body = new JSONObject(param);
        if(!body.has("email") || !body.has("password"))
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        Utente client = new Utente();
        if(!body.has("isGestore")){
            client.setEmail(body.getString("email"));
            client.setPassword(body.getString("password"));
        }
        else{
            client.setEmail(body.getString("email"));
            client.setPassword(body.getString("password"));
            client.setIsGestore(body.getBoolean("isGestore"));
        }
        if(clienteService.insert(client)) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    @PostMapping(path = "/code", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> recuperoPassword(@RequestBody String param){
        JSONObject body = new JSONObject(param);
        if(!body.has("email"))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(clienteService.recover(body.getString("email")))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping(path = "/verifyCode", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> verificaCodice(@RequestBody String param){
        JSONObject body = new JSONObject(param);
        if(!body.has("code"))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(clienteService.verify(body.getString("code")))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping(path = "/{idPersona}/favorito/{idPiatto}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> modificaStatoPreferiti(@PathVariable String idPersona, @PathVariable String idPiatto, @RequestBody String param){
        JSONObject json = new JSONObject(param);
        if(!json.has("fav"))
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        boolean value = json.getBoolean("fav");
        if(!clienteService.modificaStatoPreferiti(idPersona, Integer.parseInt(idPiatto), value))
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/{idPersona}/rate/{idPiatto}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> modificaValutazione(@PathVariable String idPersona, @PathVariable String idPiatto, @RequestBody String param){
        JSONObject json = new JSONObject(param);
        if(!json.has("rate"))
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        int value = json.getInt("rate");
        if(!clienteService.modificaValutazione(idPersona, Integer.parseInt(idPiatto), value))
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /* Aggiorna BlackList */
    @PostMapping(path = "/{idPersona}/blacklist", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> aggiornaBlacklist(@PathVariable String idPersona, @RequestBody Ingredienti ingredienti){
        clienteService.aggiornaBlacklist(idPersona, ingredienti);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /* Ottieni BlackList */
    @GetMapping(path = "/{idPersona}/blacklist", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ottieniBlacklist(@PathVariable String idPersona){
        List<String> list = clienteService.ottieniBlacklist(idPersona);
        if(list == null) 
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        Ingredienti ingredienti = new Ingredienti(list);
        return new ResponseEntity<>(ingredienti.toString(), HttpStatus.OK);
    }
}
