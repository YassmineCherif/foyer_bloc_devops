package tn.esprit.spring.services.bloc;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.spring.dao.entities.bloc;
import tn.esprit.spring.dao.entities.Chambre;
import tn.esprit.spring.dao.entities.Foyer;
import tn.esprit.spring.dao.repositories.blocRepository;
import tn.esprit.spring.dao.repositories.ChambreRepository;
import tn.esprit.spring.dao.repositories.FoyerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class blocservice implements iblocservice {
    blocRepository repo;
    ChambreRepository chambreRepository;
    blocRepository blocRepository;
    FoyerRepository foyerRepository;

    @Override
    public bloc addOrUpdate2(bloc b) { //Cascade
        List<Chambre> chambres= b.getChambres();
        for (Chambre c: chambres) {
            c.setBloc(b);
            chambreRepository.save(c);
        }
        return b;
    }

    @Override
    public bloc addOrUpdate(bloc b) {
        List<Chambre> chambres= b.getChambres();
        b= repo.save(b);
        for (Chambre chambre: chambres) {
            chambre.setBloc(b);
            chambreRepository.save(chambre);
        }
        return b;
    }

    @Override
    public List<bloc> findAll() {
        List<bloc> blocs = repo.findAll();
        for (bloc bloc : blocs) {
            log.info("Bloc fetched: {}", bloc);

        }
        return blocs;
    }

    @Override
    public bloc findById(long id) {
        Optional<bloc> optionalBloc = repo.findById(id);

        if (optionalBloc.isPresent()) {
            bloc bloc = optionalBloc.get();
            log.info("Bloc fetched: {}", bloc);
            return bloc;
        } else {
            log.warn("Bloc not found with id: {}", id);
            throw new NoSuchElementException("Bloc not found with id: " + id);
        }
    }


    @Override
    public void deleteById(long id) {
        repo.deleteById(id);
    }

    @Override
    public void delete(bloc b) {
        List<Chambre> chambres= b.getChambres();
        for (Chambre chambre: chambres) {
            chambreRepository.delete(chambre);
        }
        repo.delete(b);
    }

    @Override
    public bloc affecterChambresABloc(List<Long> numChambre, String nomBloc) {
        //1
        bloc b = repo.findByNomBloc(nomBloc);
        List<Chambre> chambres= new ArrayList<>();
        for (Long nu: numChambre) {
            Chambre chambre=chambreRepository.findByNumeroChambre(nu);
            chambres.add(chambre);
        }
        // Keyword (2ème méthode)
        //2 Parent==>Chambre  Child==> Bloc
        for (Chambre cha : chambres) {
            //3 On affecte le child au parent
                cha.setBloc(b);
            //4 save du parent
                chambreRepository.save(cha);
        }
        return b;
    }

    @Override
    public bloc affecterBlocAFoyer(String nomBloc, String nomFoyer) {
        bloc b = blocRepository.findByNomBloc(nomBloc); //Parent
        Foyer f = foyerRepository.findByNomFoyer(nomFoyer); //Child
        //On affecte le child au parent
        b.setFoyer(f);
        return blocRepository.save(b);
    }
}
