package UnitTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.spring.dao.entities.bloc;
import tn.esprit.spring.dao.repositories.blocRepository;
import tn.esprit.spring.FoyerApplication;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@Slf4j
@SpringBootTest(classes = FoyerApplication.class)
@ActiveProfiles("test")


class BlocServiceTest {

    @Autowired
    private blocRepository blocrepository; // Use correct class name

    @BeforeEach
    void setUp() {
        blocrepository.deleteAll();

        // Sample data setup
        bloc bloc1 = new bloc();
        bloc1.setNomBloc("Bloc A");
        bloc1.setCapaciteBloc(100);
        bloc bloc2 = new bloc();
        bloc2.setNomBloc("Bloc B");
        bloc2.setCapaciteBloc(200);

        blocrepository.saveAll(Arrays.asList(bloc1, bloc2));
    }

    @Test
    void testFindAll() {
        List<bloc> blocs = blocrepository.findAll();
        assertNotNull(blocs, "blocs is null");
        assertFalse(blocs.isEmpty(), "blocs is empty");
        blocs.forEach(bloc -> log.info("bloc: {}", bloc));
    }
}
