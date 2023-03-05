package com.example.if_else.Servises;


import com.example.if_else.Models.Animal;
import com.example.if_else.Models.VisitsLocation;
import com.example.if_else.Reposiories.AnimalRepository;
import com.example.if_else.utils.SerchingParametrs.AmimalSerchParameters;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final EntityManager entityManager;

    public ResponseEntity<Animal> getAnimalById(@Valid @Min(1) @NotNull Long animalId) {

        if (animalId == null || animalId <= 0) {
            return ResponseEntity.status(400).body(null);
        }

        //todo вернуть проекцию, а не объект
        Optional<Animal> account = animalRepository.findById(animalId);

        if (account.isPresent()) {
            return ResponseEntity.status(200).body(account.get());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    public ResponseEntity<List<Animal>> findAnimals(@Valid AmimalSerchParameters param) {

        List<Animal> animals = animalRepository.findAnimalByParams(
                param.getStartDateTime(),
                param.getEndDateTime(),
                param.getChippingLocationId(),
                param.getLifeStatus(),
                param.getGender(),
                param.getFrom(),
                param.getSize());

        return ResponseEntity.status(200).body(animals);
    }



    public ResponseEntity<List<VisitsLocation>> getLocation(@Valid @Min(1) @NotNull Long animalId,
                                                            @Valid AmimalSerchParameters param) {




        Optional<Animal> animal = animalRepository.findById(animalId);
        if (animal.isEmpty()) {return ResponseEntity.status(404).body(null);}

        List<VisitsLocation> accounts = getVisitsLocations(animalId, param);

        return ResponseEntity.status(200).body(accounts);
    }

    private List<VisitsLocation> getVisitsLocations(@Valid @Min(1) @NotNull Long animalId,
                                                    @Valid AmimalSerchParameters param) {
        Query query = entityManager.createQuery(
                "SELECT visit " +
                        "FROM Animal animal " +
                        "JOIN animal.visitedLocations visit  " +
                        "WHERE :animalId = animal.id " +
                        "AND (:startDateTime is null or visit.dateTimeOfVisitLocationPoint >= :startDateTime) " +
                        "AND (:endDateTime is null or visit.dateTimeOfVisitLocationPoint <= :endDateTime) ");

        query.setParameter("animalId", animalId);
        query.setParameter("startDateTime", param.getStartDateTime());
        query.setParameter("endDateTime", param.getEndDateTime());
        query.setFirstResult(param.getFrom());
        query.setMaxResults(param.getSize());
        List<VisitsLocation> accounts = query.getResultList();

        //todo переделать на страницы
        //animalRepository.findAll(PageRequest.of(0, 10));

        return accounts;
    }
}
