package com.webapp.alliance.mapperEntity;

/**
 * Created by Nikos on 22/07/2018.
 */

import com.webapp.alliance.dto.BienDTO;
import com.webapp.alliance.model.Bien;
import org.springframework.stereotype.Component;

@Component
public class BienMapper {

    public Bien fill(BienDTO bienDTO){
        Bien bien = new Bien();
        bien.setId(bienDTO.getId());
        bien.setSurface(bienDTO.getSurface());
        bien.setVille(bienDTO.getVille());
        bien.setDescription(bienDTO.getDescription());
        bien.setPrix(bienDTO.getPrix());

        return bien;
    }
}
