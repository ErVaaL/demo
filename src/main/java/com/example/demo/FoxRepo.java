package com.example.demo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoxRepo extends CrudRepository<Fox,Long> {
    public Fox findByTails(int tails);
    public List<Fox> findAllByTails(int tails);
    public Fox findByName(String name);
}
