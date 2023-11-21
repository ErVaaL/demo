package com.example.demo;

import com.example.demo.controls.FoxRepo;
import com.example.demo.controls.MyRestService;
import com.example.demo.exceptions.FoxAlreadyExistsException;
import com.example.demo.exceptions.FoxNotFoundException;
import com.example.demo.objects.Fox;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        when(repo.findAllByTails(tails)).thenReturn(foxesList);

       List<Fox> result = service.getFoxByTails(tails);
       assertEquals(foxesList,result);
    }
    @Test
    public void getAllAnimals(){
        List<Fox> listOfFoxes = new ArrayList<>();
        when(repo.findAll()).thenReturn(listOfFoxes);

        List<Fox> result = service.getAllAnimals();
        assertEquals(listOfFoxes,result);
    }
    @Test
    public void testFoxAdding(){
        Fox testFox1 = new Fox("TestFox", 5);
        ArgumentCaptor<Fox> captor = ArgumentCaptor.forClass(Fox.class);
        when(repo.save(captor.capture())).thenReturn(testFox1);

        service.addAnimal(testFox1);
        verify(repo, times(4))
                .save(Mockito.any());
        Fox foxFromSave = captor.getValue();
        assertEquals(testFox1,foxFromSave);
    }
    @Test
    public void testFoxAddingThrowsException(){
        Fox testFox1 = new Fox("TestFox", 5);
        testFox1.setId(2L);

        when(repo.findById(2L)).thenReturn(Optional.of(testFox1));
        assertThrows(FoxAlreadyExistsException.class,()-> service.addAnimal(testFox1));
    }
    @Test
    public void testFoxFindById(){
        Fox testFox1 = new Fox("TestFox", 5);
        testFox1.setId(4L);
        when(repo.findById(testFox1.getId())).thenReturn(Optional.of(testFox1));

        Optional<Fox> result = service.getFoxById(4L);
        assertEquals(Optional.of(testFox1),result);
    }
    @Test
    public void testFoxFindByIdThrowsNotFound(){
        assertThrows(FoxNotFoundException.class, ()-> service.getFoxById(5L));

    }
    @Test
    public void testFoxFilterByName(){
        var fox1 = new Fox("Lumi", 9);
        var fox2 = new Fox("LumiAlt", 9);
        when(repo.findAll()).thenReturn(List.of(fox1,fox2));

        var result = service.filterFoxByName("Lumi");
        assertEquals(List.of(fox1),result);
    }
}
