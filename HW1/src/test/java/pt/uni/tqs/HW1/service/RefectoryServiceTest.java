package pt.uni.tqs.HW1.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pt.uni.tqs.HW1.model.Refectory;
import pt.uni.tqs.HW1.repository.RefectoryRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RefectoryServiceTest {

    @Mock
    private RefectoryRepository refectoryRepository;

    @InjectMocks
    private RefectoryService refectoryService;

    private Refectory r1, r2;

    @BeforeEach
    void setUp() {
        r1 = new Refectory("Moliceiro", "Avenida A", 100);
        r1.setId(1L);
        r2 = new Refectory("Barlavento", "Praça Central", 80);
        r2.setId(2L);

        List<Refectory> allRefectories = Arrays.asList(r1, r2);

        when(refectoryRepository.findAll()).thenReturn(allRefectories);
        when(refectoryRepository.findById(1L)).thenReturn(Optional.of(r1));
        when(refectoryRepository.findById(2L)).thenReturn(Optional.of(r2));
        when(refectoryRepository.findById(-1L)).thenReturn(Optional.empty());
    }

    @Test
    void whenGetAllRefectories_thenReturnAll() {
        List<Refectory> result = refectoryService.getAllRefectorys();

        assertThat(result).hasSize(2).extracting(Refectory::getName)
                .containsExactly("Moliceiro", "Barlavento");

        verifyFindAllCalledOnce();
    }

    @Test
    void whenGetValidId_thenReturnRefectory() {
        Refectory refectory = refectoryService.getRefectoryById(1L);

        assertThat(refectory).isNotNull();
        assertThat(refectory.getName()).isEqualTo("Moliceiro");

        verifyFindByIdCalledOnce(1L);
    }

    @Test
    void whenGetInvalidId_thenThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            refectoryService.getRefectoryById(-1L);
        });

        assertThat(exception.getMessage()).isEqualTo("Refectory with ID -1 not found.");
        verifyFindByIdCalledOnce(-1L);
    }

    private void verifyFindAllCalledOnce() {
        Mockito.verify(refectoryRepository, VerificationModeFactory.times(1)).findAll();
    }

    private void verifyFindByIdCalledOnce(Long id) {
        Mockito.verify(refectoryRepository, VerificationModeFactory.times(1)).findById(id);
    }
}
