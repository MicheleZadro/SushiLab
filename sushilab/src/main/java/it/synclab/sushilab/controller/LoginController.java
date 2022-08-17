package it.synclab.sushilab.controller;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.synclab.sushilab.entity.Utente;
import it.synclab.sushilab.service.ClientService;
import it.synclab.sushilab.utility.Utility;



@RestController
@RequestMapping(value = "login")
public class LoginController {
    @Autowired
    private ClientService clienteService;
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> eseguiLogin(@RequestBody Utente utente){
        if(clienteService.login(utente)) {
            //Genera valori
            Long expiresIn = Long.parseLong(Utility.generateString(10, 10, true, false, false));
            String idToken = Utility.generateString(18, 18, true, false, false);
            //Verifica se sono già presenti nel database, se non ci sono li inserisce
            
            boolean insert_value = clienteService.insertIdToken(utente, idToken);
            while(insert_value == false){
                expiresIn = Long.parseLong(Utility.generateString(10, 10, true, false, false));
                idToken = Utility.generateString(18, 18, true, false, false);
                //Verifica se sono già presenti nel database, se non ci sono li inserisce
                insert_value = clienteService.insertIdToken(utente, idToken);
            }
            JSONObject rtrnObject = new JSONObject();
            rtrnObject.put("expiresIn", expiresIn);
            rtrnObject.put("idToken", idToken);
            return new ResponseEntity<>(rtrnObject.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
