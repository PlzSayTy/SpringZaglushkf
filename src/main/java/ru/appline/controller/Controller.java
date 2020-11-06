package ru.appline.controller;

import org.springframework.web.bind.annotation.*;
import ru.appline.logic.Pet;
import ru.appline.logic.PetModel;

import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {

    private static final PetModel petModel = PetModel.getInstance();
    private static final AtomicInteger newId = new AtomicInteger(1);

    @PostMapping(value = "/createPet", consumes = "application/json")
    public String createPet(@RequestBody Pet pet){
        petModel.add(pet,newId.getAndIncrement());
        if (newId.get() == 2){
            return "Congratulations with your first pet!";
        }else{
            return "Pet created";
        }
    }

    @GetMapping(value = "/getAll", consumes = "application/json", produces = "application/json")
    public Map<Integer, Pet> getAll(){
        return petModel.getAll();
    }

    @GetMapping(value = "/getPet", consumes = "application/json", produces = "application/json")
    public Pet getPet(@RequestBody Map<String, Integer> id){
        return petModel.getFromList(id.get("id"));
    }

    @DeleteMapping(value = "/deletePet", consumes = "application/json", produces = "application/json")
    public Map<Integer, Pet> deletePet(@RequestBody Map<String, Integer> id){
        petModel.getAll().remove(id.get("id"));
        return petModel.getAll();
    }

    @PutMapping(value = "/putPet", consumes = "application/json", produces = "application/json")
    public Map<Integer, Pet> putPet(@RequestBody Pet pet){
        petModel.getFromList(pet.getId()).setName(pet.getName());
        petModel.getFromList(pet.getId()).setType(pet.getType());
        petModel.getFromList(pet.getId()).setAge(pet.getAge());
        return petModel.getAll();
    }
}
