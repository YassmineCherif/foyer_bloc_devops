package UnitTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import tn.esprit.spring.dao.entities.bloc;
import tn.esprit.spring.dao.entities.Chambre;
import tn.esprit.spring.dao.repositories.blocRepository;
import tn.esprit.spring.dao.repositories.ChambreRepository;
import tn.esprit.spring.services.bloc.blocservice;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class BlocServiceMockitoTest {

    @InjectMocks
    private blocservice blocService;

    @Mock
    private blocRepository blocRepository;

    @Mock
    private ChambreRepository chambreRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test the addOrUpdate method with mock data
    @Test
    void testAddOrUpdate() {
        // Prepare test data
        bloc bloc = new bloc();
        bloc.setIdBloc(1);
        bloc.setNomBloc("Bloc test 1");
        bloc.setCapaciteBloc(500);

        Chambre chambre1 = new Chambre();
        chambre1.setNumeroChambre(101L);
        Chambre chambre2 = new Chambre();
        chambre2.setNumeroChambre(102L);
        bloc.setChambres(Arrays.asList(chambre1, chambre2));

        // Mock repository behavior
        Mockito.when(blocRepository.save(Mockito.any(bloc.class))).thenReturn(bloc);
        Mockito.when(chambreRepository.save(Mockito.any(Chambre.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the service method
        bloc savedBloc = blocService.addOrUpdate(bloc);

        // Validate results
        assertNotNull(savedBloc, "Saved bloc should not be null");
        assertEquals("Bloc test 1", savedBloc.getNomBloc());
        assertEquals(500, savedBloc.getCapaciteBloc());

        // Verify interactions
        Mockito.verify(blocRepository, Mockito.times(1)).save(bloc);
        Mockito.verify(chambreRepository, Mockito.times(2)).save(Mockito.any(Chambre.class));
    }

    @Test
    void testFindById() {
        // Prepare mock bloc data
        bloc bloc = new bloc();
        bloc.setIdBloc(1);
        bloc.setNomBloc("Bloc test 2");
        bloc.setCapaciteBloc(1358);

        // Mock repository behavior
        Mockito.when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));

        // Call the service method
        bloc foundBloc = blocService.findById(1L);

        // Validate results
        assertNotNull(foundBloc, "Found bloc should not be null");
        assertEquals("Bloc test 2", foundBloc.getNomBloc());
        assertEquals(1358, foundBloc.getCapaciteBloc());

        // Verify that findById was called correctly
        Mockito.verify(blocRepository).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        // Mock repository to return empty result
        Mockito.when(blocRepository.findById(99L)).thenReturn(Optional.empty());

        // Call service and expect exception
        assertThrows(NoSuchElementException.class, () -> blocService.findById(99L));
        Mockito.verify(blocRepository).findById(99L);
    }
}
