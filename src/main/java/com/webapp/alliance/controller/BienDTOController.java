package com.webapp.alliance.controller;

/**
 * Created by Nikos on 21/07/2018.
 */

import com.webapp.alliance.dto.BienDTO;
import com.webapp.alliance.service.BienDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BienDTOController {

    @Autowired
    private BienDTOService bienDTOService;

    @GetMapping("/biens")
    public ResponseEntity<Page<BienDTO>> getAllBiensWithPagedResults(Pageable pageable) {
        Page<BienDTO> bienDTOPage = bienDTOService.findAllWithPagedResults(pageable);

        if (bienDTOPage == null) {
            return new ResponseEntity<Page<BienDTO>>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<Page<BienDTO>>(bienDTOPage, HttpStatus.OK);
        }
    }

    @GetMapping("/bien/{id}")
    public ResponseEntity<BienDTO> getBien(@PathVariable Long id) {
        BienDTO bienDTO = bienDTOService.findById(id);

        if (bienDTO == null) {
            return new ResponseEntity<BienDTO>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<BienDTO>(bienDTO, HttpStatus.OK);
        }
    }

    @PostMapping("/bien")
    public ResponseEntity<Void> createBien(@RequestBody BienDTO bienDTO) {
        if (bienDTO.getId() != null && bienDTOService.findById(bienDTO.getId()) != null) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        } else {
            bienDTOService.create(bienDTO);
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        }
    }

    @PutMapping("/bien/{id}")
    public ResponseEntity<BienDTO> updateBien(@PathVariable Long id,
                                              @RequestBody BienDTO bienDTO) {
        BienDTO currentBienDTO = bienDTOService.findById(id);

        if (currentBienDTO == null) {
            return new ResponseEntity<BienDTO>(HttpStatus.NOT_FOUND);
        } else {
            bienDTOService.update(bienDTO);
            currentBienDTO = bienDTOService.findById(currentBienDTO.getId());
            return new ResponseEntity<BienDTO>(currentBienDTO, HttpStatus.OK);
        }
    }

    @DeleteMapping("/bien/{id}")
    public ResponseEntity<BienDTO> deleteBien(@PathVariable Long id) {
        BienDTO bienDTO = bienDTOService.findById(id);

        if (bienDTO == null) {
            return new ResponseEntity<BienDTO>(HttpStatus.NOT_FOUND);
        }

        bienDTOService.delete(id);
        return new ResponseEntity<BienDTO>(HttpStatus.NO_CONTENT);
    }


}
