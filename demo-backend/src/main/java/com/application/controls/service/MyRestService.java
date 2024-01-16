package com.application.controls.service;

import com.application.controls.repositories.FoxRepo;
import com.application.exceptions.FoxAlreadyExistsException;
import com.application.exceptions.FoxFailedToUpdateException;
import com.application.exceptions.FoxNotFoundException;
import com.application.objects.Fox;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MyRestService {
    FoxRepo repository;
    public MyRestService(FoxRepo repo){
       this.repository = repo;
        this.repository.save(new Fox("Kokos",1));
        this.repository.save(new Fox("Lumi",9));
        this.repository.save(new Fox("LumiAlt",9));

    }
    public List<Fox> getFoxByTails(int tails){
        return this.repository.findAllByTails(tails);
    }
    public Fox getFoxById(Long id){
        if(repository.findById(id).isEmpty()) throw new FoxNotFoundException();
        return this.repository
                .findById(id).orElse(new Fox("default",2));}
    public List<Fox> getAllAnimals(){
        return (List<Fox>) repository.findAll();
    }
    public Fox addAnimal(Fox animal){
        if(animal.getId() != null) throw new FoxAlreadyExistsException();
      return repository.save(animal);
    }
    public void putAnimal(Fox animal){
        if(!repository.existsById(animal.getId())) throw new FoxFailedToUpdateException();
        repository.save(animal);
    }
    public void deleteAnimal(Long id){
        if(repository.findById(id).isPresent())this.repository.deleteById(id);
        else throw new FoxNotFoundException();
    }

    public List<Fox> filterFoxByName(String name) {
        List<Fox> foxes = (List<Fox>)this.repository.findAll();
        return foxes.stream()
                .filter(x->x.getName().equals(name))
                .toList();
    }
}
