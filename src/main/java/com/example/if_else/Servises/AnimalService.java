package com.example.if_else.Servises;


import com.example.if_else.Models.*;
import com.example.if_else.Reposiories.*;
import com.example.if_else.enums.LifeStatus;
import com.example.if_else.mapers.AnimalProjection;
import com.example.if_else.mapers.ChangeTypeDto;
import com.example.if_else.mapers.VisitsLocationProjection;
import com.example.if_else.request.LocationUpdateRequest;
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
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;

    private final EntityManager entityManager;
    private final VisitLocationRepository visitLocationRepository;
    private final AnimalTypeRepository animalTypeRepository;

    private final AccountRepository accountRepository;

    private final LocationRepository locationRepository;
    private final VisitLocationRepository visitsLocationRepository;

    public ResponseEntity<AnimalProjection> getAnimalById(@Valid @Min(1) @NotNull Long animalId) {

        if (animalId == null || animalId <= 0) {
            return ResponseEntity.status(400).body(null);
        }

        //todo вернуть проекцию, а не объект
        AnimalProjection animal = animalRepository.getAnimalProjectionById(animalId);

        if (animal != null) {
            return ResponseEntity.status(200).body(animal);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    public ResponseEntity<List<AnimalProjection>> findAnimals(@Valid AmimalSerchParameters param) {


        Page<Animal> animals = animalRepository.findAnimalByParams(
                param.getStartDateTime(),
                param.getEndDateTime(),
                param.getChippingLocationId(),
                param.getLifeStatus(),
                param.getGender(),
                PageRequest.of(param.getFrom(), param.getSize(), Sort.Direction.ASC, "id"));
        List<AnimalProjection> animalProjections = animalRepository.getAllById(animals.getContent().stream().
                map(Animal::getId)
                .collect(Collectors.toList()));


        return ResponseEntity.status(200).body(animalProjections);
    }


    public ResponseEntity<List<VisitsLocationProjection>> getLocation(@Valid @Min(1) @NotNull Long animalId,
                                                            @Valid AmimalSerchParameters param) {

        Optional<Animal> animal = animalRepository.findById(animalId);
        if (animal.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }

        Page<VisitsLocationProjection> visitsLocations = visitLocationRepository.findLocatiomByParam(animalId,
                param.getStartDateTime(),
                param.getEndDateTime(),
                PageRequest.of(param.getFrom(), param.getSize(), Sort.Direction.ASC, "id"));

        List<VisitsLocationProjection> accounts = visitsLocations.getContent();

        return ResponseEntity.status(200).body(accounts);
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
        Animal animal = optionalAnimal.get();

        if (!animal.getVisitedLocations().isEmpty()) {
            return ResponseEntity.status(400).body(optionalAnimal.get());
        }

        animal.getAnimalTypes().clear();
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
        if (animal.getAnimalTypes().contains(animalType)) {
            return ResponseEntity.status(409).body(null);
        }

        animal.getAnimalTypes().add(animalType);
        AnimalProjection animalProjection = animalRepository.getAnimalProjectionById(animalId);

        return ResponseEntity.status(201).body(animalProjection);
    }

    public ResponseEntity<AnimalProjection> deleteTypeFromAnimal(@Valid @Min(1) @NotNull Long animalId,
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
        animal.getAnimalTypes().remove(animalTypeOption.get());

        AnimalProjection animalProjection = animalRepository.getAnimalProjectionById(animalId);

        return ResponseEntity.status(200).body(animalProjection);
    }

    public ResponseEntity<AnimalProjection> changeTypeFromAnimal(@Valid @Min(1) @NotNull Long animalId,
                                                                 @Valid ChangeTypeDto changeTypeDto) {
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

    public ResponseEntity<VisitsLocationProjection> addLocateToAnimal(@Valid @Min(1) @NotNull Long animalId,
                                                                      @Valid @Min(1) @NotNull Long pointId) {

        Optional<Location> locationOptional = locationRepository.findById(pointId);
        if (locationOptional.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }

        Optional<Animal> animalOptional = animalRepository.findById(animalId);
        if (animalOptional.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }

        //todo вынести отдельно
        Animal animal = animalOptional.get();
        if (animal.getLifeStatus().equals(LifeStatus.DEAD)) {
            return ResponseEntity.status(400).body(null);
        }

        if (locationOptional.get().equals(animal.getChippingLocationId())) {
            return ResponseEntity.status(400).body(null);
        }

        VisitsLocation visitsLocation = VisitsLocation.builder()
                .locationPointId(locationOptional.get())
                .build();
        animal.getVisitedLocations().add(visitsLocation);

        visitsLocationRepository.save(visitsLocation);
        VisitsLocationProjection anser = visitsLocationRepository.getVisitsLocationById(visitsLocation.getId());

        return ResponseEntity.status(201).body(anser);
    }

    public ResponseEntity<AnimalProjection> changeLocateToAnimal(@Valid @Min(1) @NotNull Long animalId,
                                                                 @Valid LocationUpdateRequest oldNewLacate) {


        Optional<Location> locationOptional = locationRepository.findById(oldNewLacate.getLocationPointId());
        if (locationOptional.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }

        Optional<Animal> animalOptional = animalRepository.findById(animalId);
        if (animalOptional.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }

        Animal animal = animalOptional.get();
        if (animal.getChippingLocationId().getId().equals(oldNewLacate.getLocationPointId())) {
            return ResponseEntity.status(400).body(null);
        }

        List<Long> listLacateId = animal.getVisitedLocations().stream()
                .map(a -> a.getLocationPointId().getId())
                .collect(Collectors.toList());

        int indexOfChangPoint = listLacateId.indexOf(oldNewLacate.getLocationPointId());
        if (indexOfChangPoint == -1) {
            return ResponseEntity.status(404).body(null);
        }

        if (listLacateId.size() - 1 != indexOfChangPoint
                && !Objects.equals(listLacateId.get(indexOfChangPoint + 1), oldNewLacate.getLocationPointId())) {
            return ResponseEntity.status(400).body(null);
        }

        if (0 != indexOfChangPoint
                && !Objects.equals(listLacateId.get(indexOfChangPoint - 1), oldNewLacate.getLocationPointId())) {
            return ResponseEntity.status(400).body(null);
        }
        animal.getVisitedLocations()
                .get(indexOfChangPoint)
                .setLocationPointId(locationOptional.get());

        animalRepository.save(animal);
        AnimalProjection animalProjection = animalRepository.getAnimalProjectionById(animal.getId());

        return ResponseEntity.status(200).body(animalProjection);

    }


    public ResponseEntity<Object> deleteLocateFromAnimal(Long animalId, Long visitedPointId) {
        Optional<Animal> animalOptional = animalRepository.findById(animalId);
        if (animalOptional.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }

        Optional<VisitsLocation> locationOptional = visitsLocationRepository.findById(visitedPointId);
        if (locationOptional.isPresent()) {
            return ResponseEntity.status(404).body(null);
        }
        VisitsLocation location = locationOptional.get();

        Animal animal = animalOptional.get();
        if (!animal.getVisitedLocations().remove(location)) {
            return ResponseEntity.status(404).body(null);
        }

        animalRepository.save(animal);

        return ResponseEntity.ok().body(null);
    }
}

