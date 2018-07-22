package com.webapp.alliance.mapperDTO;

/**
 * Created by Nikos on 22/07/2018.
 */

import com.webapp.alliance.dto.BienDTO;
import com.webapp.alliance.model.Bien;
import org.springframework.stereotype.Component;

@Component
public class BienDTOMapper {

    public BienDTO fill(Bien bien){
        BienDTO bienDTO = new BienDTO();
        bienDTO.setId(bien.getId());
        bienDTO.setDescription(bien.getDescription());
        bienDTO.setVille(bien.getVille());
        bienDTO.setPrix(bien.getPrix());
        bienDTO.setSurface(bien.getSurface());
        bienDTO.setDateCreation(bien.getCreatedAt());
        bienDTO.setDateUpdate(bien.getUpdatedAt());

        return  bienDTO;
    }
}
