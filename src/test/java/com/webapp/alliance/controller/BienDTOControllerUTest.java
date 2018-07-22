package com.webapp.alliance.controller;

/**
 * Created by Nikos on 22/07/2018.
 */

import com.webapp.alliance.dto.BienDTO;
import com.webapp.alliance.service.BienDTOService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BienDTOControllerUTest {

    @InjectMocks
    private BienDTOController bienDTOController;

    @Mock
    private BienDTOService bienDTOService;

    private Pageable pageable;
    private UriComponentsBuilder uriComponentsBuilder;
    private Page<BienDTO> bienDTOPage;
    private List<BienDTO> bienDTOList;
    private BienDTO bienDTO;

    @Before
    public void setUp() throws Exception {
        bienDTOList = new ArrayList<BienDTO>();
        bienDTOPage = new PageImpl<BienDTO>(bienDTOList);
        bienDTO = new BienDTO();
        bienDTO.setId(1L);
    }

    @Test
    public void getAllBiensWithPagedResults_shouldReturnResponseEntityBodyWithBienDTOPageResults_andHttpStatusOk() {
        // Given
        when(bienDTOService.findAllWithPagedResults(pageable)).thenReturn(bienDTOPage);

        // When
        ResponseEntity<Page<BienDTO>> responseEntity = bienDTOController.getAllBiensWithPagedResults(pageable);

        // Then
        verify(bienDTOService).findAllWithPagedResults(pageable);
        assertThat(responseEntity.getBody()).isEqualTo(bienDTOPage);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getAllBiensWithPagedResults_shouldReturnHttpStatusNoContent_whenBienDTOPageResultsIsNull() {
        // Given
        when(bienDTOService.findAllWithPagedResults(pageable)).thenReturn(null);

        // When
        ResponseEntity<Page<BienDTO>> responseEntity = bienDTOController.getAllBiensWithPagedResults(pageable);

        // Then
        verify(bienDTOService).findAllWithPagedResults(pageable);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void getBien_shouldReturnResponseEntityBodyWithBienDTO_andHttpStatusOk_whenBienDTOExists() {
        // Given
        when(bienDTOService.findById(1L)).thenReturn(bienDTO);

        // When
        ResponseEntity<BienDTO> responseEntity = bienDTOController.getBien(bienDTO.getId());

        // Then
        verify(bienDTOService).findById(bienDTO.getId());
        assertThat(responseEntity.getBody()).isEqualTo(bienDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getBien_shouldReturnHttpStatusNotFound_whenBienDTOSearchedIsNull() {
        // Given
        when(bienDTOService.findById(1L)).thenReturn(null);

        // When
        ResponseEntity<BienDTO> responseEntity = bienDTOController.getBien(bienDTO.getId());

        // Then
        verify(bienDTOService).findById(bienDTO.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void createBien_shouldReturnHttpStatusCreated_whenBienDTOIsNew() {
        // Given
        when(bienDTOService.findById(bienDTO.getId())).thenReturn(null);
        when(bienDTOService.create(bienDTO)).thenReturn(bienDTO);

        // When
        ResponseEntity<Void> responseEntity = bienDTOController.createBien(bienDTO);

        // Then
        verify(bienDTOService).findById(bienDTO.getId());
        verify(bienDTOService).create(bienDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void createBien_shouldReturnHttpStatusConflict_whenBienDTOAlreadyExists() {
        when(bienDTOService.findById(bienDTO.getId())).thenReturn(bienDTO);
        when(bienDTOService.create(bienDTO)).thenReturn(bienDTO);

        // When
        ResponseEntity<Void> responseEntity = bienDTOController.createBien(bienDTO);

        // Then
        verify(bienDTOService).findById(bienDTO.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    public void updateBien_shouldReturnHttpStatusOK_whenBienDTOHasBeenUpdated() {
        // Given
        when(bienDTOService.findById(bienDTO.getId())).thenReturn(bienDTO);

        // When
        ResponseEntity<BienDTO>  responseEntity = bienDTOController.updateBien(1L, bienDTO);

        // Then
        verify(bienDTOService , atLeast(2)).findById(bienDTO.getId());
        verify(bienDTOService).update(bienDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }



    @Test
    public void updateBien_shouldReturnHttpStatusNotFound_whenExpectedBienDTOToUpdateDoesNotExists() {
        when(bienDTOService.findById(bienDTO.getId())).thenReturn(null);

        // When
        ResponseEntity<BienDTO>  responseEntity = bienDTOController.updateBien(1L, bienDTO);

        // Then

        verify(bienDTOService).findById(bienDTO.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void deleteBien_shouldReturnHttpStatusNoContent_whenBienDTOHasBeenDeleted() {
        // Given
        when(bienDTOService.findById(bienDTO.getId())).thenReturn(bienDTO);

        // When
        ResponseEntity<BienDTO>  responseEntity = bienDTOController.deleteBien(bienDTO.getId());

        // Then
        verify(bienDTOService).findById(bienDTO.getId());
        verify(bienDTOService).delete(bienDTO.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }



    @Test
    public void deleteBien_shouldReturnHttpStatusNotFound_whenBienDTODoesNotExists() {
        // Given
        when(bienDTOService.findById(bienDTO.getId())).thenReturn(null);

        // When
        ResponseEntity<BienDTO>  responseEntity = bienDTOController.deleteBien(bienDTO.getId());

        // Then
        verify(bienDTOService).findById(bienDTO.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


}