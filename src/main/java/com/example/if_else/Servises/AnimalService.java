package com.example.if_else.Servises;


import com.example.if_else.Models.*;
import com.example.if_else.Reposiories.AccountRepository;
import com.example.if_else.Reposiories.AnimalRepository;
import com.example.if_else.Reposiories.AnimalTypeRepository;
import com.example.if_else.Reposiories.LocationRepository;
import com.example.if_else.mapers.AnimalProjection;
import com.example.if_else.mapers.ChangeTypeDto;
import com.example.if_else.utils.SerchingParametrs.AmimalSerchParameters;
import com.example.if_else.utils.SerchingParametrs.AnimalCreateParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.*;

@Service
@Validated
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final EntityManager entityManager;

    private final AnimalTypeRepository animalTypeRepository;

    private final AccountRepository accountRepository;

    private final LocationRepository locationRepository;

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


        Page<Animal> animals = animalRepository.findAnimalByParams(
                param.getStartDateTime(),
                param.getEndDateTime(),
                param.getChippingLocationId(),
                param.getLifeStatus(),
                param.getGender(),
                PageRequest.of(param.getFrom(), param.getSize(), Sort.Direction.ASC, "id"));

        return ResponseEntity.status(200).body(animals.getContent());
    }


    public ResponseEntity<List<VisitsLocation>> getLocation(@Valid @Min(1) @NotNull Long animalId,
                                                            @Valid AmimalSerchParameters param) {


        Optional<Animal> animal = animalRepository.findById(animalId);
        if (animal.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }

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

    public ResponseEntity<AnimalProjection> createAnimal(@Valid AnimalCreateParam params) {
        // проверка наличия id аккаунта
        Optional<Account> accountOptional = accountRepository.findById(params.getChipperId());
        if (accountOptional.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        //проверка на уникльность элементов
        Set<Long> set = new HashSet<>(params.getAnimalTypes());
        if (set.size() != params.getAnimalTypes().size()) {
            return ResponseEntity.status(409).body(null);
        }

        //проверка налчия в БД id animalTypes
        List<AnimalType> animalTypes = animalTypeRepository.findAllById(params.getAnimalTypes());
        if (animalTypes.size() != params.getAnimalTypes().size()) {
            return ResponseEntity.status(404).body(null);
        }
// проверка наличия chipingId
        Optional<Location> locationOptional = locationRepository.findById(params.getChippingLocationId());
        if (locationOptional.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }

        Animal animal = Animal.builder()
                .animalTypes(animalTypes)
                .weight(params.getWeight())
                .length(params.getLength())
                .height(params.getHeight())
                .gender(params.getGender())
                .chipperId(accountOptional.get())
                .chippingLocationId(locationOptional.get())
                .build();

        Animal newAnimal = animalRepository.save(animal);
        AnimalProjection animalProjection = animalRepository.getAnimalProjectionById(newAnimal.getId());
        return ResponseEntity.status(201).body(animalProjection);
    }

    public ResponseEntity<AnimalProjection> updateAnimal(@Valid @Min(1) @NotNull Long animalId, @Valid AnimalCreateParam param) {



        Optional<Animal> optionalAnimal = animalRepository.findById(animalId);

        if (optionalAnimal.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }

        Optional<Location> optionalLocation = locationRepository.findById(param.getChippingLocationId());
        if (optionalLocation.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }

        Optional<Account> accountOptional = accountRepository.findById(param.getChipperId());
        if (accountOptional.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }

        Animal updatinAnimal = optionalAnimal.get();

        if (updatinAnimal.getVisitedLocations().size() != 0
                && Objects.equals(param.getChippingLocationId(), updatinAnimal.getVisitedLocations().get(0).getId())) {
            return ResponseEntity.status(400).body(null);
        }
        updatinAnimal.setWeight(param.getWeight());
        updatinAnimal.setLength(param.getLength());
        updatinAnimal.setHeight(param.getHeight());
        updatinAnimal.setGender(param.getGender());
        updatinAnimal.setLifeStatus(param.getLifeStatus());
        updatinAnimal.setChipperId(accountOptional.get());
        updatinAnimal.setChippingLocationId(optionalLocation.get());

        animalRepository.save(updatinAnimal);

        AnimalProjection animalProjection = animalRepository.getAnimalProjectionById(updatinAnimal.getId());

        return ResponseEntity.status(201).body(animalProjection);
    }

    public ResponseEntity<Object> deleteAnimal(@Valid @Min(1) @NotNull Long animalId) {
        Optional<Animal> optionalAnimal = animalRepository.findById(animalId);

        if (optionalAnimal.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        Animal updatinAnimal = optionalAnimal.get();

        if (updatinAnimal.getVisitedLocations().size() > 0) {
            return ResponseEntity.status(400).body(null);
        }
        animalRepository.deleteById(animalId);

        return ResponseEntity.status(200).body(null);
    }

    public ResponseEntity<AnimalProjection> addTypeToAnimal(@Valid @Min(1) @NotNull Long animalId,
                                                            @Valid @Min(1) @NotNull Long typeId) {
        Optional<Animal> optionalAnimal = animalRepository.findById(animalId);
        if (optionalAnimal.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }

        Optional<AnimalType> animalTypeOption = animalTypeRepository.findById(typeId);
        if (animalTypeOption.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        Animal animal = optionalAnimal.get();
        AnimalType animalType = animalTypeOption.get();
        if (animal.getAnimalTypes().contains(animalType)){
            return ResponseEntity.status(409).body(null);
        }

        animal.getAnimalTypes().add(animalType);
        AnimalProjection animalProjection = animalRepository.getAnimalProjectionById(animalId);

        return ResponseEntity.status(201).body(animalProjection);
    }

    public ResponseEntity<AnimalProjection> deleteTypeFromAnimal(@Valid @Min(1) @NotNull  Long animalId,
                                                                 @Valid @Min(1) @NotNull  Long typeId) {

        Optional<Animal> optionalAnimal = animalRepository.findById(animalId);

        if (optionalAnimal.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }

        Optional<AnimalType> animalTypeOption = animalTypeRepository.findById(typeId);
        if (animalTypeOption.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        Animal animal = optionalAnimal.get();
        animal.getAnimalTypes().remove(animalTypeOption.get());

        AnimalProjection animalProjection = animalRepository.getAnimalProjectionById(animalId);

        return ResponseEntity.status(201).body(animalProjection);
    }

    public ResponseEntity<AnimalProjection> changeTypeFromAnimal(@Valid @Min(1) @NotNull Long animalId,
                                                                 @Valid  ChangeTypeDto changeTypeDto) {
        Optional<AnimalType> newAnimalTypeOption = animalTypeRepository.findById(changeTypeDto.getNewTypeId());
        if (newAnimalTypeOption.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }

        Optional<AnimalType> oldAnimalTypeOption = animalTypeRepository.findById(changeTypeDto.getOldTypeId());
        if (oldAnimalTypeOption.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }

        Optional<Animal> optionalAnimal = animalRepository.findById(animalId);

        if (optionalAnimal.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        Animal animal = optionalAnimal.get();

        Collections.replaceAll(animal.getAnimalTypes(), oldAnimalTypeOption.get(), newAnimalTypeOption.get());

        animalRepository.save(animal);

        AnimalProjection animalProjection = animalRepository.getAnimalProjectionById(animal.getId());

        return ResponseEntity.status(200).body(animalProjection);
    }
}

