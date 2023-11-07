package com.example.demo.controls;

import com.example.demo.exceptions.FoxFailedToUpdateException;
import com.example.demo.exceptions.FoxIllegalArgumentException;
import com.example.demo.objects.Fox;
import com.example.demo.exceptions.FoxNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MyRestService {
    FoxRepo repository;
    public MyRestService(FoxRepo repo){
       this.repository = repo;
        this.repository.save(new Fox("Kokos",1));
        this.repository.save(new Fox("Lumi",9));
        this.repository.save(new Fox("Alt",9));

    }
    public List<Fox> getFoxByTails(int tails){return (List<Fox>) this.repository.findAllByTails(tails);}
    public Optional<Fox> getFoxById(Long id){
        if(!repository.existsById(id)) throw new FoxNotFoundException();
        return this.repository.findById(id);}
    public List<Fox> getAllAnimals(){
        return (List<Fox>) repository.findAll();
    }
    public void addAnimal(Fox animal){
        repository.save(animal);
    }
    public void putAnimal(Fox animal){
        if(!repository.existsById(animal.getId())) throw new FoxFailedToUpdateException();
        repository.save(animal);
    }
    public void deleteAnimal(Long id){
        this.repository.deleteById(id);
    }
}
