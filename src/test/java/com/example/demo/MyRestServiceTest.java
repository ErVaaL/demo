package com.example.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.EnableMBeanExport;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyRestServiceTest {
    @Mock
    private FoxRepo repo;
    private AutoCloseable openMocks;
    private MyRestService service;

    @BeforeEach
    public void init() {
       openMocks = MockitoAnnotations.openMocks(this);
        service = new MyRestService(repo);
    }

    @AfterEach
    public void teardown() throws Exception
    {
        openMocks.close();
    }
    @Test
    public void findAnimalsByTails(){
        int tails = 9;
        List<Fox> foxesList = new ArrayList<>();
        Mockito.when(repo.findAllByTails(tails)).thenReturn(foxesList);

       List<Fox> result = service.getFoxByTails(tails);
       assertEquals(foxesList,result);
    }
    @Test
    public void getAllAnimals(){
        List<Fox> listOfFoxes = new ArrayList<>();
        Mockito.when(repo.findAll()).thenReturn(listOfFoxes);

        List<Fox> result = service.getAllAnimals();
        assertEquals(listOfFoxes,result);
    }
    @Test
    public void testSaving(){
        String name = "TestFox";
        int tails = 5;
        Fox testFox1 = new Fox(name, tails);
        ArgumentCaptor<Fox> captor = ArgumentCaptor.forClass(Fox.class);
        Mockito.when(repo.save(captor.capture())).thenReturn(testFox1);

        service.addAnimal(testFox1);
        Mockito.verify(repo, Mockito.times(4))
                .save(Mockito.any());
        Fox foxFromSave = captor.getValue();
        assertEquals(testFox1,foxFromSave);
    }
    @Test
    public void testDeleting(){
        long id = 1;

    }
}
