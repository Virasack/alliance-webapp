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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BienDTOServiceUTest {

    private static final long BIEN_DTO_ID = 1L;
    private static final long BIEN_DTO_PRIX = 1500000L;
    private static final String BIEN_DTO_DESCRIPTION = "Description";
    private static final String BIEN_DTO_VILLE = "Paris";
    private static final long BIEN_DTO_SURFACE = 200L;
    private static final String MESSAGE_RESOURCE_NOT_FOUND_EXCEPTION = "Bien not found with id 1";

    @InjectMocks
    BienDTOService bienDTOService;

    @Mock
    private BienRepository bienRepository;

    @Mock
    private BienDTOMapper bienDTOMapper;

    @Mock
    private BienMapper bienMapper;

    @Mock
    private Pageable pageable;

    private Bien bien;
    private BienDTO bienDTO;

    @Before
    public void setUp() throws Exception {
        bien = new Bien();
        bienDTO = new BienDTO();
        bienDTO.setId(BIEN_DTO_ID);
        bienDTO.setPrix(BIEN_DTO_PRIX);
        bienDTO.setDescription(BIEN_DTO_DESCRIPTION);
        bienDTO.setVille(BIEN_DTO_VILLE);
        bienDTO.setSurface(BIEN_DTO_SURFACE);
    }

    @Test
    public void findAllWithPagedResults_shouldReturnExpectedBienDTOWithPagedResults_whenPageStartAtPageOne_withOneBienDTOOnEachPage() {
        // Given
        List<Bien> bienList = asList(bien, bien);
        List<BienDTO> bienDTOList = asList(bienDTO, bienDTO);

        when(bienRepository.findAll()).thenReturn(bienList);
        when(bienDTOMapper.fill(bien)).thenReturn(bienDTO);
        when(pageable.getOffset()).thenReturn(BIEN_DTO_ID);
        when(pageable.getPageSize()).thenReturn(1);

        Page<BienDTO> expectedbienDTOPageResult = new PageImpl<BienDTO>(bienDTOList.subList((int) pageable.getOffset(), bienDTOList.size()), pageable, bienDTOList.size());

        // When
        Page<BienDTO> bienDTOPageResult = bienDTOService.findAllWithPagedResults(pageable);

        // Then
        verify(bienRepository).findAll();
        verify(bienDTOMapper, atLeast(2)).fill(bien);
        assertThat(bienDTOPageResult).isEqualTo(expectedbienDTOPageResult);
    }

    @Test
    public void findAllWithPagedResults_shouldReturnExpectedBienDTOWithPagedResults_whenPageStartAtPageTwo_withOneBienDTOOnEachPage() {
        // Given
        List<Bien> bienList = asList(bien, bien, bien, bien);
        List<BienDTO> bienDTOList = asList(bienDTO, bienDTO, bienDTO, bienDTO);

        when(bienRepository.findAll()).thenReturn(bienList);
        when(bienDTOMapper.fill(bien)).thenReturn(bienDTO);
        when(pageable.getOffset()).thenReturn(2L);
        when(pageable.getPageSize()).thenReturn(1);

        Page<BienDTO> expectedbienDTOPageResult = new PageImpl<BienDTO>(bienDTOList.subList((int) pageable.getOffset(), (int) pageable.getOffset() + pageable.getPageSize()), pageable, bienDTOList.size());

        // When
        Page<BienDTO> bienDTOPageResult = bienDTOService.findAllWithPagedResults(pageable);

        // Then
        verify(bienRepository).findAll();
        verify(bienDTOMapper, atLeast(4)).fill(bien);
        assertThat(bienDTOPageResult).isEqualTo(expectedbienDTOPageResult);
    }

    @Test
    public void create_shouldSaveBienDTO_andReturnBienDTO() {
        // Given
        when(bienMapper.fill(bienDTO)).thenReturn(bien);

        // When
        BienDTO bienDTOResult = bienDTOService.create(bienDTO);

        // Then
        verify(bienMapper).fill(bienDTO);
        verify(bienRepository).save(bien);
        assertThat(bienDTOResult).isEqualTo(bienDTO);
    }

    @Test
    public void update_shouldUpdate_andSaveExpectedBien() {
        // Given
        when(bienRepository.findById(bienDTO.getId())).thenReturn(Optional.ofNullable(bien));
        when(bienRepository.save(bien)).thenReturn(bien);

        // When
        bienDTOService.update(bienDTO);

        // Then
        ArgumentCaptor<Bien> bienArgumentCaptor = ArgumentCaptor.forClass(Bien.class);
        verify(bienRepository).findById(bienDTO.getId());
        verify(bienRepository).save(bienArgumentCaptor.capture());
        assertThat(bienArgumentCaptor.getValue().getPrix()).isEqualTo(BIEN_DTO_PRIX);
        assertThat(bienArgumentCaptor.getValue().getDescription()).isEqualTo(BIEN_DTO_DESCRIPTION);
        assertThat(bienArgumentCaptor.getValue().getVille()).isEqualTo(BIEN_DTO_VILLE);
        assertThat(bienArgumentCaptor.getValue().getSurface()).isEqualTo(BIEN_DTO_SURFACE);
    }

    @Test
    public void update_shouldReturnResourceNotFoundException_whenErrorOccurred() {
        // Given
        Bien bien = new Bien();
        BienDTO bienDTO = new BienDTO();
        bienDTO.setId(BIEN_DTO_ID);

        // When
        Throwable thrown = catchThrowable(() -> bienDTOService.update(bienDTO));

        // Then
        assertThat(thrown).isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(MESSAGE_RESOURCE_NOT_FOUND_EXCEPTION);
    }

    @Test
    public void findById_shouldReturnExpectedBienDTO() {
        // Given
        when(bienRepository.findById(bienDTO.getId())).thenReturn(Optional.ofNullable(bien));
        when(bienDTOMapper.fill(bien)).thenReturn(bienDTO);

        // When
        BienDTO resultBienDTO = bienDTOService.findById(bienDTO.getId());

        // Then
        verify(bienRepository).findById(bienDTO.getId());
        verify(bienDTOMapper).fill(bien);
        assertThat(resultBienDTO).isEqualTo(bienDTO);
    }

    @Test
    public void findById_shouldReturnResourceNotFoundException_whenErrorOccurred() {
        // When
        Throwable thrown = catchThrowable(() -> bienDTOService.findById(bienDTO.getId()));

        // Then
        assertThat(thrown).isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(MESSAGE_RESOURCE_NOT_FOUND_EXCEPTION);
    }

    @Test
    public void delete_shouldDeleteExpectedBien() {
        // Given
        when(bienRepository.findById(bienDTO.getId())).thenReturn(Optional.ofNullable(bien));

        // When
        bienDTOService.delete(bienDTO.getId());

        // Then
        verify(bienRepository).findById(bienDTO.getId());
        verify(bienRepository).delete(bien);
    }

    @Test
    public void delete_shouldReturnResourceNotFoundException_whenErrorOccurred() {
        // When
        Throwable thrown = catchThrowable(() -> bienDTOService.delete(bienDTO.getId()));

        // Then
        assertThat(thrown).isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(MESSAGE_RESOURCE_NOT_FOUND_EXCEPTION);
    }
}