package tn.esprit.spring.schedular;

import tn.esprit.spring.services.Chambre.IChambreService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class schedular {

     IChambreService iChambreService;
    @Scheduled(cron = "0 * * * * *")
   void service1() {
       iChambreService.listeChambresParBloc();
    }

//    @Scheduled(fixedRate = 30000)
//// 5 minutes = 300 secondes = 300000 millisecondes
//    void service2() {
//        iChambreService.pourcentageChambreParTypeChambre();
//    }
//
//    @Scheduled(fixedRate = 30000)
//    void service3() {
//        iChambreService.nbPlacesDisponibleParChambreAnneeEnCours();
//    }
//    @Scheduled(cron = "* * * 30 06 *")
//    void service4() {
//        iReservationService.annulerReservations();
//    }
}
