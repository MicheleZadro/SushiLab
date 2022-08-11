package it.synclab.sushilab.service;

import java.util.List;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.synclab.sushilab.constants.Constants;
import it.synclab.sushilab.entity.Utente;
import it.synclab.sushilab.entity.Code;
import it.synclab.sushilab.entity.IdToken;
import it.synclab.sushilab.entity.Menu;
import it.synclab.sushilab.entity.Session;
import it.synclab.sushilab.repository.ClientRepository;
import it.synclab.sushilab.repository.CodeRepository;
import it.synclab.sushilab.repository.IdTokenRepository;
import it.synclab.sushilab.repository.MenuRepository;
import it.synclab.sushilab.repository.SessionRepository;
import it.synclab.sushilab.utility.Utility;

@Service
@Transactional
public class ClientService{

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    IdTokenRepository idTokenRepository;
    @Autowired
    CodeRepository codeRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    MenuRepository menuRepository;

    
    public List<Utente> getAllClients() {
        return clientRepository.findAll();
    }

    
    public boolean insert(Utente client) {
        if(!clientRepository.existsById(client.getEmail())){
            clientRepository.save(client);
            return true;
        }
        return false;
    }

    
    public boolean login(Utente client) {
        if(clientRepository.existsById(client.getEmail()))
            if(clientRepository.findById(client.getEmail()).get().getPassword().compareTo(client.getPassword()) == 0)
                return true;
        return false;
    }

    
    public boolean insertIdToken(Utente client, String idToken) {
        if(idTokenRepository.existsById(idToken))
            return false;
        IdToken id_token = new IdToken(idToken, client);
        List<IdToken> list = idTokenRepository.findAll();
        for(int index = 0; index < list.size(); index++){
            if(list.get(index).getCliente().getEmail().toLowerCase().compareTo(client.getEmail().toLowerCase()) == 0){
                idTokenRepository.deleteById(list.get(index).getIdToken());
                return false;
            }
        }
        idTokenRepository.save(id_token);
        return true;
    }

    
    public boolean recover(String email) {
        //Cerco cliente già registrato nel database
        Boolean existClient = clientRepository.existsById(email);
        //Se non ho trovato il cliente non esiste quindi ritorno 0
        if(!existClient)
            return false;
        //Genero il codice
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator.Builder()
                .withinRange('0', 'Z')
                .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
                .build();
        String s = randomStringGenerator.generate(Constants.codeLength);
        codeRepository.save(new Code(email, s));
        return true;
    }

    
    public boolean verify(String code) {
        if(codeRepository.findByCode(code) == null)
            return false;
        return true;
    }
    /*Da modificare: il fatto che alla creazione della sessione deve già inserirci dentro l'host */
    
    public boolean insertSessionCode(String code) {
        boolean exist = sessionRepository.existsById(code);
        if(!exist){
            Session s = new Session();
            s.setIdTable(code);
            sessionRepository.save(s);
            return true;
        }
        return false;
    }

    
    public boolean existSessionCode(String idTavolo) {
        return sessionRepository.existsById(idTavolo);
    }

    
    public void deleteSession(String idTavolo) {
        sessionRepository.deleteById(idTavolo);        
    }

    
    public boolean isGestore(String idPersona) {
        if(!idTokenRepository.existsById(idPersona))
            return false;
        if(idTokenRepository.findById(idPersona).get().getCliente().getIsGestore())
            return true;
        return false;
    }

    public Menu riceviMenu(){
        List<Menu> menu = menuRepository.findAll();
        if(menu == null) return null;
        for(int i = 0; i < menu.size(); i++){
            for(int j = 0; j < menu.get(i).getFasce().size(); j++){
                String giorno = menu.get(i).getFasce().get(j).getGiorno();
                int oraInizio = menu.get(i).getFasce().get(j).getOraInizio();
                int oraFine = menu.get(i).getFasce().get(j).getOraFine();
                if(Utility.verifyDate(giorno, oraInizio, oraFine)) {
                    Menu rtrn = menu.get(i);
                    
                    return rtrn;
                }
            }
        }
        return null;
    }
    
}
