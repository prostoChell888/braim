package com.example.if_else.Servises;


import com.example.if_else.Models.Animal;
import com.example.if_else.Models.VisitsLocation;
import com.example.if_else.Reposiories.AnimalRepository;
import com.example.if_else.utils.SerchingParametrs.AmimalSerchParameters;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final EntityManager entityManager;

    public AnimalService(AnimalRepository animalRepository, EntityManager entityManager) {
        this.animalRepository = animalRepository;
        this.entityManager = entityManager;
    }

    public ResponseEntity<Animal> getAnimalById(Long animalId) {

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

    public ResponseEntity<List<Animal>> findAnimals(AmimalSerchParameters param) {
        if (param.getSize() <= 0 || param.getFrom() < 0) {
            return ResponseEntity.status(400).body(null);
        }

        if ("LIVE".equals(param.getLifeStatus()) || "DEAD".equals(param.getLifeStatus())) {
            return ResponseEntity.status(400).body(null);
        }

        if ("MALE".equals(param.getGender()) || "FEMALE".equals(param.getGender())
                || "OTHER".equals(param.getGender())) {
            return ResponseEntity.status(400).body(null);
        }
        List<Animal> animals = getAnimals(param);

        return ResponseEntity.status(200).body(animals);
    }

    private List<Animal> getAnimals(AmimalSerchParameters param) {

        List<Animal> res = animalRepository.findAnimalByParams(
                param.getStartDateTime(),
                param.getEndDateTime(),
                param.getChippingLocationId(),
                param.getLifeStatus(),
                param.getGender());


        return res.stream().
                skip(param.getFrom()).
                limit(param.getSize()).
                collect(Collectors.toList());
    }

    public ResponseEntity<List<VisitsLocation>> getLocation(Long animalId, AmimalSerchParameters param) {

        //todo проверка на соответстви формату времени
        if (animalId == null || param.getSize() <= 0 || param.getFrom() < 0 || animalId <= 0)
        {return ResponseEntity.status(400).body(null);}

        Optional<Animal> animal = animalRepository.findById(animalId);
        if (animal.isEmpty()) {return ResponseEntity.status(404).body(null);}

        List<VisitsLocation> accounts = getVisitsLocations(animalId, param);

        return ResponseEntity.status(200).body(accounts);
    }

    private List<VisitsLocation> getVisitsLocations(Long animalId, AmimalSerchParameters param) {
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

        animalRepository.findAll(PageRequest.of(0, 10));

        return accounts;
    }
}
