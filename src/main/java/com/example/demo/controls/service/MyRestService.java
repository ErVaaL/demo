package com.example.demo.controls.service;

import com.example.demo.exceptions.FoxAlreadyExistsException;
import com.example.demo.exceptions.FoxFailedToUpdateException;
import com.example.demo.objects.Fox;
import com.example.demo.exceptions.FoxNotFoundException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
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
        var fox = this.getFoxById(id);
        if(this.getFoxById(id) != null ){
         restClient.delete();

        }
        else throw new FoxNotFoundException();
    }

//    public List<Fox> filterFoxByName(String name) {
//        var foxes = (List<Fox>)this.repository.findAll();
//        return foxes.stream()
//                .filter(x->x.getName().equals(name))
//                .toList();
//    }
}
