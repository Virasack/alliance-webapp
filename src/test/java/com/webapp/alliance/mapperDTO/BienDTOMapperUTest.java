package com.webapp.alliance.mapperDTO;

/**
 * Created by Nikos on 22/07/2018.
 */

import com.webapp.alliance.dto.BienDTO;
import com.webapp.alliance.model.Bien;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BienDTOMapperUTest {
    private static final long BIEN_ID = 1L;
    private static final long BIEN_PRIX = 1500000L;
    private static final String BIEN_DESCRIPTION = "Description";
    private static final String BIEN_VILLE = "Paris";
    private static final long BIEN_SURFACE = 200L;

    @InjectMocks
    private BienDTOMapper bienDTOMapper;

    @Mock
    private Bien bien;

    private Date dateCreation;
    private Date dateUpdate;

    @Test
    public void fill_shouldReturnExpectedBienId() {
        // Given
        when(bien.getId()).thenReturn(BIEN_ID);

        // When
        BienDTO bienDTO = bienDTOMapper.fill(bien);

        // Then
        assertThat(bienDTO.getId()).isEqualTo(BIEN_ID);
    }

    @Test
    public void fill_shouldReturnExpectedBienPrix() {
        // Given
        when(bien.getPrix()).thenReturn(BIEN_PRIX);

        // When
        BienDTO bienDTO = bienDTOMapper.fill(bien);

        // Then
        assertThat(bienDTO.getPrix()).isEqualTo(BIEN_PRIX);
    }

    @Test
    public void fill_shouldReturnExpectedBienDescription() {
        // Given
        when(bien.getDescription()).thenReturn(BIEN_DESCRIPTION);

        // When
        BienDTO bienDTO = bienDTOMapper.fill(bien);

        // Then
        assertThat(bienDTO.getDescription()).isEqualTo(BIEN_DESCRIPTION);
    }

    @Test
    public void fill_shouldReturnExpectedBienVille() {
        // Given
        when(bien.getVille()).thenReturn(BIEN_VILLE);

        // When
        BienDTO bienDTO = bienDTOMapper.fill(bien);

        // Then
        assertThat(bienDTO.getVille()).isEqualTo(BIEN_VILLE);
    }

    @Test
    public void fill_shouldReturnExpectedBienSurface() {
        // Given
        when(bien.getSurface()).thenReturn(BIEN_SURFACE);

        // When
        BienDTO bienDTO = bienDTOMapper.fill(bien);

        // Then
        assertThat(bienDTO.getSurface()).isEqualTo(BIEN_SURFACE);
    }

    @Test
    public void fill_shouldReturnExpectedBienDateCreation() {
        // Given
        when(bien.getCreatedAt()).thenReturn(dateCreation);

        // When
        BienDTO bienDTO = bienDTOMapper.fill(bien);

        // Then
        assertThat(bienDTO.getDateCreation()).isEqualTo(dateCreation);
    }

    @Test
    public void fill_shouldReturnExpectedBienDateUpdate() {
        // Given
        when(bien.getUpdatedAt()).thenReturn(dateUpdate);

        // When
        BienDTO bienDTO = bienDTOMapper.fill(bien);

        // Then
        assertThat(bienDTO.getDateUpdate()).isEqualTo(dateUpdate);
    }

}