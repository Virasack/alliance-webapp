package com.webapp.alliance.mapperEntity;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BienMapperUTest {

    private static final long BIEN_ID = 1L;
    private static final long BIEN_PRIX = 1500000L;
    private static final String BIEN_DESCRIPTION = "Description";
    private static final String BIEN_VILLE = "Paris";
    private static final long BIEN_SURFACE = 200L;

    @InjectMocks
    private BienMapper bienMapper;

    @Mock
    private BienDTO bienDTO;

    @Test
    public void fill_shouldReturnExpectedBienDTOId() {
        // Given
        when(bienDTO.getId()).thenReturn(BIEN_ID);

        // When
        Bien bien = bienMapper.fill(bienDTO);

        // Then
        assertThat(bien.getId()).isEqualTo(BIEN_ID);
    }

    @Test
    public void fill_shouldReturnExpectedBienDTOPrix() {
        // Given
        when(bienDTO.getPrix()).thenReturn(BIEN_PRIX);

        // When
        Bien bien = bienMapper.fill(bienDTO);

        // Then
        assertThat(bien.getPrix()).isEqualTo(BIEN_PRIX);
    }

    @Test
    public void fill_shouldReturnExpectedBienDTODescription() {
        // Given
        when(bienDTO.getDescription()).thenReturn(BIEN_DESCRIPTION);

        // When
        Bien bien = bienMapper.fill(bienDTO);

        // Then
        assertThat(bien.getDescription()).isEqualTo(BIEN_DESCRIPTION);
    }

    @Test
    public void fill_shouldReturnExpectedBienDTOVille() {
        // Given
        when(bienDTO.getVille()).thenReturn(BIEN_VILLE);

        // When
        Bien bien = bienMapper.fill(bienDTO);

        // Then
        assertThat(bien.getVille()).isEqualTo(BIEN_VILLE);
    }

    @Test
    public void fill_shouldReturnExpectedBienDTOSurface() {
        // Given
        when(bienDTO.getSurface()).thenReturn(BIEN_SURFACE);

        // When
        Bien bien = bienMapper.fill(bienDTO);

        // Then
        assertThat(bien.getSurface()).isEqualTo(BIEN_SURFACE);
    }
}