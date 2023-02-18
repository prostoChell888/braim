package com.example.if_else.Servises;


import com.example.if_else.Models.Account;
import com.example.if_else.Models.Animal;
import com.example.if_else.Reposiories.AnimalRepository;
import com.example.if_else.utils.SerchingParametrs.AmimalSerchParameters;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final EntityManager entityManager;

    public AnimalService(AnimalRepository animalRepository, EntityManager entityManager) {
        this.animalRepository = animalRepository;
        this.entityManager = entityManager;
    }

    public ResponseEntity<Animal> getAnimalById(Long animalId) {

        if (animalId == null || animalId < 0) {
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
        if (param.size <= 0 || param.from < 0) {
            return ResponseEntity.status(400).body(null);
        }

        if ("LIVE".equals(param.lifeStatus)||"DEAD".equals(param.lifeStatus)) {
            return ResponseEntity.status(400).body(null);
        }

        if ("MALE".equals(param.gender) ||"FEMALE".equals(param.gender) ||"OTHER".equals(param.gender)) {
            return ResponseEntity.status(400).body(null);
        }

        List<Animal> animals = getAnimals(param);

        return ResponseEntity.status(200).body(animals);
    }

    private List<Animal> getAnimals(AmimalSerchParameters param) {
        Query query = entityManager.createQuery(
                "SELECT animals" +
                        " FROM Animal animals " +
                        "WHERE :startDateTime is null or animals.chippingDateTime >= :startDateTime " +
                        "AND :endDateTime is null or animals.chippingDateTime <= :endDateTime " +
                        "AND :chippingLocationId is null or animals.chippingLocationId.id = :chippingLocationId " +
                        "AND :lifeStatus is null  or animals.lifeStatus = :lifeStatus " +
                        "AND :gender is null or animals.gender = :gender");

        query.setParameter("startDateTime", param.startDateTime);
        query.setParameter("endDateTime", param.endDateTime);
        query.setParameter("chippingLocationId", param.chippingLocationId);
        query.setParameter("lifeStatus", param.lifeStatus);
        query.setParameter("gender", param.gender);
        query.setFirstResult(param.from);
        query.setMaxResults(param.size);

        return query.getResultList();

    }
}
