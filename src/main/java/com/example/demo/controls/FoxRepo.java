package com.example.demo.controls;

import com.example.demo.objects.Fox;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoxRepo extends CrudRepository<Fox,Long> {
    Fox findByTails(int tails);
    List<Fox> findAllByTails(int tails);
    Fox findByName(String name);
}
