package com.application.test;
import com.application.controls.repositories.FoxRepo;
import com.application.controls.service.MyRestService;
import com.application.exceptions.FoxAlreadyExistsException;
import com.application.exceptions.FoxNotFoundException;
import com.application.objects.Fox;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.OptionalDataException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    public void teardown() throws Exception {
        openMocks.close();
    }

    @Test
    public void findAnimalsByTails() {
        int tails = 9;
        List<Fox> foxesList = new ArrayList<>();
        Mockito.when(repo.findAllByTails(tails)).thenReturn(foxesList);
        when(repo.findAllByTails(tails)).thenReturn(foxesList);

        List<Fox> result = service.getFoxByTails(tails);
        assertEquals(foxesList, result);
    }

    @Test
    public void getAllAnimals() {
        List<Fox> listOfFoxes = new ArrayList<>();
        when(repo.findAll()).thenReturn(listOfFoxes);

        List<Fox> result = service.getAllAnimals();
        assertEquals(listOfFoxes, result);
    }

    @Test
    public void testFoxAdding () {
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
    public void testFoxAddingThrowsException() {
            Fox testFox1 = new Fox("TestFox", 5);
            testFox1.setId(2L);

            when(repo.findById(2L)).thenReturn(Optional.of(testFox1));
            assertThrows(FoxAlreadyExistsException.class, () -> {
                service.addAnimal(testFox1);
            });
        }
    @Test
    public void testFoxFindById() {
                Fox testFox1 = new Fox("TestFox", 5);
                testFox1.setId(4L);
                when(repo.findById(testFox1.getId())).thenReturn(Optional.of(testFox1));

                Fox result = service.getFoxById(4L);
                assertEquals(testFox1, result);
            }
            @Test
            public void testFoxFindByIdThrowsNotFound () {
                assertThrows(FoxNotFoundException.class, () -> {
                    service.getFoxById(5L);
                });
            }
}



