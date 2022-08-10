package it.synclab.sushilab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.synclab.sushilab.entity.FasciaOraria;
import it.synclab.sushilab.entity.Menu;
import it.synclab.sushilab.entity.PiattoUpload;
import it.synclab.sushilab.repository.MenuRepository;
import it.synclab.sushilab.repository.PiattoUploadRepository;

@Service
@Transactional
public class GestoreService {
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    PiattoUploadRepository piattoUploadRepository;

    /* Metodi Piatto Upload */
    
    public void inserisciPiattoUpload(PiattoUpload piattoUpload) {
        piattoUploadRepository.save(piattoUpload);
    }

    
    public boolean rimuoviPiattoUpload(int id) {
        if (!piattoUploadRepository.existsById(id))
            return false;
        piattoUploadRepository.deleteById(id);
        return true;
    }

    
    public boolean aggiornaPiattoUpload(int id, PiattoUpload piattoUpload) {
        if(!piattoUploadRepository.existsById(id)) return false;
        PiattoUpload vecchioPiattoUpload = piattoUploadRepository.getReferenceById(id);
        if(vecchioPiattoUpload == null)
            return false;
        piattoUpload.setId(vecchioPiattoUpload.getId());
        piattoUploadRepository.save(piattoUpload);
        return true;
    }

    
    public PiattoUpload riceviPiattoUpload(int piattoUploadId) {
        if(!piattoUploadRepository.existsById(piattoUploadId)) return null;
        return piattoUploadRepository.findById(piattoUploadId).get();
    }


    /* Metodi menu */
    
    public int inserisciMenu(String nome) {
        Menu menu = new Menu();
        menu.setNome(nome);
        menuRepository.save(menu);
        return menu.getId();
    }

    
    public boolean aggiornaMenu(int id, Menu menu) {
        if(!menuRepository.existsById(id)) return false;
        Menu oldMenu = menuRepository.findById(id).get();
        if(oldMenu == null) return false;
        //Verifico tutti i piatti che esistano già in piattoUploadRepository
        for(int i = 0; i < menu.getMenu().size(); i++){
            for(int j = 0; j < menu.getMenu().get(i).getPiatti().size(); j++){
                if(!piattoUploadRepository.existsById(menu.getMenu().get(i).getPiatti().get(j).getId()))
                    return false;
                    menu.getMenu().get(i).getPiatti().get(j).setPiattoUpload(piattoUploadRepository.findById(menu.getMenu().get(i).getPiatti().get(j).getId()).get());
            }
        }
        //Verifico le fasce orarie
        for(int i = 0; i < menu.getFasce().size(); i++){
            if(!verificaFasciaOraria(menu.getFasce().get(i))) return false;
        }
        menu.setId(oldMenu.getId());
        menuRepository.save(menu);
        return true;
    }

    
    public boolean eliminaMenu(int id) {
        if(!menuRepository.existsById(id)) return false;
        menuRepository.deleteById(id);
        return true;
    }

    
    private boolean verificaFasciaOraria(FasciaOraria fasciaOraria) {
        String s = fasciaOraria.getGiorno();
        if(s.compareTo("Lun") != 0 && 
            s.compareTo("Mar") != 0 && 
            s.compareTo("Mer") != 0 && 
            s.compareTo("Gio") != 0 && 
            s.compareTo("Ven") != 0 && 
            s.compareTo("Sab") != 0 && 
            s.compareTo("Dom") != 0 && 
            s.compareTo("All") != 0 && 
            s.compareTo("Week") != 0 && 
            s.compareTo("End") != 0)
            return false;
        if (fasciaOraria.getOraFine() < fasciaOraria.getOraInizio()) return false;
        return true;
    }
    
    

    


}
