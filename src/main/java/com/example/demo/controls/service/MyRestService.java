package com.example.demo.controls.service;

import com.example.demo.exceptions.FoxAlreadyExistsException;
import com.example.demo.exceptions.FoxFailedToUpdateException;
import com.example.demo.objects.Fox;
import com.example.demo.exceptions.FoxNotFoundException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class MyRestService {
    public static final String API_URL = "http://localhost:8080";
    private RestClient restClient;
    public MyRestService(){
     this.restClient = RestClient.create();
    }
    public List<Fox> getAllAnimals(){
        return restClient
                .get()
                .uri(API_URL + "/getAnimals")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
//    public List<Fox> getFoxByTails(int tails){return (List<Fox>) this.repository.findAllByTails(tails);}

//    public Fox getFoxById(Long id){
//        if(repository.findById(id).isEmpty()) throw new FoxNotFoundException();
//        return this.repository
//                .findById(id).orElse(new Fox("default",2));}

//    public void addAnimal(Fox animal){
//        if(animal.getId() != null) throw new FoxAlreadyExistsException();
//        repository.save(animal);
//    }
    
//    public void putAnimal(Fox animal){
//        if(!repository.existsById(animal.getId())) throw new FoxFailedToUpdateException();
//        repository.save(animal);
//    }

//    public void deleteAnimal(Long id){
//        if(repository.findById(id).isPresent())this.repository.deleteById(id);
//        else throw new FoxNotFoundException();
//    }

//    public List<Fox> filterFoxByName(String name) {
//        var foxes = (List<Fox>)this.repository.findAll();
//        return foxes.stream()
//                .filter(x->x.getName().equals(name))
//                .toList();
//    }
}
