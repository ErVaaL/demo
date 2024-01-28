package com.application.controls.controller;

import com.application.controls.service.MyRestService;
import com.application.objects.Fox;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyRestController {
    private final MyRestService myRestService;

    @GetMapping("/getAnimals")
    public List<Fox> getAllAnimals() {return myRestService.getAllAnimals();}

    @GetMapping("/getAnimal/{tails}")
    public List<Fox> findFoxByTails(@PathVariable("tails") int tails){return this.myRestService.getFoxByTails(tails);}

    @GetMapping("/filterAnimalByName/{name}")
    public List<Fox> filterFoxByName(@PathVariable("name") String name){return this.myRestService.filterFoxByName(name);}
    @GetMapping("/getAnimalById/{id}")
    public Fox findFoxById(@PathVariable("id") Long id){return this.myRestService.getFoxById(id);}

    @PutMapping("/updateAnimal")
    public void putAnimal(@RequestBody Fox animal){myRestService.updateAnimal(animal);}

    @PostMapping("/addAnimal")
    public Fox postAnimal(@RequestBody Fox animal){
        return myRestService.addAnimal(animal);
    }

    @DeleteMapping("/deleteAnimal/{id}")
    public void deleteAnimal(@PathVariable("id") Long id) {
        myRestService.deleteAnimal(id);
    }

}