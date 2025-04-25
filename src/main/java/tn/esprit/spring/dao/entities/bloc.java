package tn.esprit.spring.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "T_BLOC")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class bloc implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idBloc;
    String nomBloc;
    long capaciteBloc;
    @ManyToOne
    @JsonIgnore
    Foyer foyer;
    @OneToMany(mappedBy = "bloc", fetch = FetchType.EAGER)
    @JsonIgnore
    List<Chambre> chambres= new ArrayList<>();
    //test webhook jenkins detection test
    //test webhook jenkins detection test
    //test webhook jenkins detection test
    //test webhook jenkins detection test

    @Override
    public String toString() {
        return "Bloc{idBloc =" + idBloc + ", nomBloc ='" + nomBloc + "', capaciteBloc ='" + capaciteBloc + "' }";
    }
// test test
    //test test

}

