package com.application.controls.service;

import com.application.exceptions.FoxAlreadyExistsException;
import com.application.exceptions.FoxFailedToUpdateException;
import com.application.exceptions.FoxNotFoundException;
import com.application.objects.Fox;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class MyRestService {
    public static final String API_URL = "http://localhost:8080";
    private final RestClient restClient;
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

    public Fox getFoxById(Long id){
       var fox = restClient.get()
                .uri(API_URL + "/getAnimalById/"+id)
                .retrieve()
                .body(new ParameterizedTypeReference<Fox>() {
                });
        if(fox == null) throw new FoxNotFoundException();
        return fox;
    }

    public void addAnimal(Fox animal){
        if(animal.getId() != null) throw new FoxAlreadyExistsException();
        restClient.post()
                .uri(API_URL + "/addAnimal")
                .contentType(MediaType.APPLICATION_JSON)
                .body(animal)
                .retrieve()
                .toBodilessEntity();
    }
    
    public void putAnimal(Fox animal){
        if(this.getFoxById(animal.getId()) == null) throw new FoxFailedToUpdateException();
        restClient.put()
                .uri(API_URL + "/updateAnimal")
                .contentType(MediaType.APPLICATION_JSON)
                .body(animal)
                .retrieve()
                .toBodilessEntity();
    }

    public void deleteAnimal(Long id){
        var foundFox = getFoxById(id);
        if(foundFox == null ) throw new FoxNotFoundException();
        restClient.delete()
                 .uri(API_URL + "/deleteAnimal/"+id)
                .retrieve()
                .toBodilessEntity();
    }

//    public List<Fox> filterFoxByName(String name) {
//        var foxes = (List<Fox>)this.repository.findAll();
//        return foxes.stream()
//                .filter(x->x.getName().equals(name))
//                .toList();
//    }
}