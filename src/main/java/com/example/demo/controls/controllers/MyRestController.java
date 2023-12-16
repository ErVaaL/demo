package com.example.demo.controls.controllers;

import com.example.demo.controls.service.MyRestService;
import com.example.demo.objects.Fox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class MyRestController {
    private final MyRestService myRestService;
    @Autowired
    public MyRestController(MyRestService myRestService) {
        this.myRestService = myRestService;
    }

    @GetMapping("/getAnimals")
    public List<Fox> getAllAnimals() {return myRestService.getAllAnimals();}

    @GetMapping("/getAnimal/{tails}")
    public List<Fox> findFoxByTails(@PathVariable("tails") int tails){return this.myRestService.getFoxByTails(tails);}

    @GetMapping("/filterAnimalByName/{name}")
    public List<Fox> filterFoxByName(@PathVariable("name") String name){return this.myRestService.filterFoxByName(name);}
    @GetMapping("/getAnimalById/{id}")
    public Fox findFoxById(@PathVariable("id") Long id){return this.myRestService.getFoxById(id);}

    @PutMapping("/updateAnimal")
    public void putAnimal(@RequestBody Fox animal){myRestService.putAnimal(animal);}

    @PostMapping("/addAnimal")
    public void postAnimal(@RequestBody Fox animal){
        myRestService.addAnimal(animal);
    }

    @DeleteMapping("/deleteAnimal")
    public void deleteAnimal(@RequestBody Fox animal) {
        myRestService.deleteAnimal(animal.getId());
    }

}
