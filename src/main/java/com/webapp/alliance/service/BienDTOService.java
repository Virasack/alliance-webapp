package com.webapp.alliance.service;

/**
 * Created by Nikos on 22/07/2018.
 */

import com.webapp.alliance.dto.BienDTO;
import com.webapp.alliance.exception.ResourceNotFoundException;
import com.webapp.alliance.mapperDTO.BienDTOMapper;
import com.webapp.alliance.mapperEntity.BienMapper;
import com.webapp.alliance.model.Bien;
import com.webapp.alliance.repository.BienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BienDTOService {

    @Autowired
    private BienRepository bienRepository;

    @Autowired
    private BienDTOMapper bienDTOMapper;

    @Autowired
    private BienMapper bienMapper;


    public Page<BienDTO> findAllWithPagedResults(Pageable pageable) {
        List<Bien> bienList = new ArrayList<Bien>();
        List<BienDTO> bienDTOList = new ArrayList<BienDTO>();
        bienList = bienRepository.findAll();

        for (Bien bien : bienList) {
            bienDTOList.add(bienDTOMapper.fill(bien));
        }

        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > bienDTOList.size() ? bienDTOList.size() : (start + pageable.getPageSize());

        return new PageImpl<BienDTO>(bienDTOList.subList(start, end), pageable, bienDTOList.size());
    }

    public BienDTO create(BienDTO bienDTO) {
        Bien bien = bienMapper.fill(bienDTO);
        bienRepository.save(bien);
        return bienDTO;
    }

    public void update(BienDTO bienDTO) {
        bienRepository.findById(bienDTO.getId())
                .map(bien -> {
                    bien.setPrix(bienDTO.getPrix());
                    bien.setDescription(bienDTO.getDescription());
                    bien.setVille(bienDTO.getVille());
                    bien.setSurface(bienDTO.getSurface());
                    return bienRepository.save(bien);
                }).orElseThrow(() -> new ResourceNotFoundException("Bien not found with id " + bienDTO.getId()));
    }

    public BienDTO findById(Long bienId) {
         Bien bien = bienRepository.findById(bienId)
                .orElseThrow(() -> new ResourceNotFoundException("Bien not found with id " + bienId));

         return bienDTOMapper.fill(bien);
    }

    public void delete(Long bienId) {
        bienRepository.findById(bienId)
                .map(bien -> {
                    bienRepository.delete(bien);
                    return ResponseEntity.ok().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException("Bien not found with id " + bienId));
    }

}
