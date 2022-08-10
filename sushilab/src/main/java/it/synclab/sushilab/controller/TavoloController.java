package it.synclab.sushilab.controller;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.synclab.sushilab.service.ClientService;
import it.synclab.sushilab.utility.Utility;

@RestController
@RequestMapping(value = "tavolo")
public class TavoloController {
    @Autowired
    private ClientService clienteService;


    /*  Crea Sessione POST http:/localhost:3000/tavolo   */

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createSession(){
        //Generate random value
        String value = Utility.generateString(7, 7, true, true, true);
        //Check if already exists
        boolean rtrn = false;
        int count = 0;
        while(rtrn == false){
            if(count >= 100)
                return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
            rtrn = clienteService.insertSessionCode(value);
            count++;
        }
            
        JSONObject body = new JSONObject();
        body.put("id", value);
        return new ResponseEntity<String>(body.toString(), HttpStatus.CREATED);
    }
    /*--------------------------------------------------- */

    /* Ottieni Sessione GET http:/localhost:3000/tavolo{idTavolo} Manca il ritorno del menù*/
    @GetMapping(path = "{idTavolo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSession(@PathVariable String idTavolo){
        //Check if exists
        boolean exist = clienteService.existSessionCode(idTavolo);
        if(!exist)
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        JSONObject body = new JSONObject();
        body.put("tavolo", 1);
        return new ResponseEntity<String>(body.toString(), HttpStatus.OK);
    }
    /*--------------------------------------------------- */

    /* Chiudi Sessione DELETE http:/localhost:3000/tavolo{idTavolo} */
    @DeleteMapping(path = "{idTavolo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteSession(@PathVariable String idTavolo){
        //Check if exists
        boolean exist = clienteService.existSessionCode(idTavolo);
        if(!exist)
            return new ResponseEntity<String>(HttpStatus.METHOD_NOT_ALLOWED);
        clienteService.deleteSession(idTavolo);
        return new ResponseEntity<String>(HttpStatus.OK);
    }




}
