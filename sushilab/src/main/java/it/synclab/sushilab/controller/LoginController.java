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



@RestController
@RequestMapping(value = "login")
public class LoginController {
    @Autowired
    private ClientService clienteService;
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> eseguiLogin(@RequestBody String param){
        JSONObject body = new JSONObject(param);
        if(!body.has("email") || !body.has("password"))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Utente client = new Utente();
        client.setEmail(body.getString("email"));
        client.setPassword(body.getString("password"));
        if(clienteService.login(client)) {
            RandomStringGenerator randomStringGenerator = new RandomStringGenerator.Builder()
                .withinRange('0', '9')
                .filteredBy(/*CharacterPredicates.LETTERS, */CharacterPredicates.DIGITS)
                .build();
            //Genera valori
            Long expiresIn = Long.parseLong(randomStringGenerator.generate(10));
            String idToken = randomStringGenerator.generate(18);
            //Verifica se sono già presenti nel database, se non ci sono li inserisce
            boolean insert_value = clienteService.insertIdToken(client, idToken);
            while(insert_value == false){
                expiresIn = Long.parseLong(randomStringGenerator.generate(10));
                idToken = randomStringGenerator.generate(18);
                //Verifica se sono già presenti nel database, se non ci sono li inserisce
                insert_value = clienteService.insertIdToken(client, idToken);
            }
            JSONObject rtrnObject = new JSONObject();
            rtrnObject.put("expiresIn", expiresIn);
            rtrnObject.put("idToken", idToken);
            return new ResponseEntity<>(rtrnObject.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
