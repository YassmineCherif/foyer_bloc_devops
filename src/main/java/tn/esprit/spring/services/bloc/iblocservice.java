package tn.esprit.spring.services.bloc;

import tn.esprit.spring.dao.entities.bloc;

import java.util.List;

public interface iblocservice {
    bloc addOrUpdate(bloc b);
    bloc addOrUpdate2(bloc b);

    List<bloc> findAll();

    bloc findById(long id);

    void deleteById(long id);

    void delete(bloc b);

    bloc affecterChambresABloc(List<Long> numChambre, String nomBloc);
    bloc affecterBlocAFoyer( String nomBloc,  String nomFoyer) ;


}
