package com.application.test;
import com.application.controls.repositories.FoxRepo;
import com.application.controls.service.MyRestService;
import com.application.exceptions.FoxAlreadyExistsException;
import com.application.exceptions.FoxFailedToUpdateException;
import com.application.exceptions.FoxNotFoundException;
import com.application.objects.Fox;
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
    public void testGetFoxById() {
        Long id = 1L;
        var testFox = new Fox("Kokos", 1);
        when(repo.findById(id)).thenReturn(Optional.of(testFox));
        assertEquals(testFox, service.getFoxById(id));

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
        Mockito.verify(repo, Mockito.times(7))
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
    @Test
    public void testFoxDeleteById() {
        Fox testFox1 = new Fox("TestFox", 5);
        testFox1.setId(4L);
        when(repo.findById(testFox1.getId())).thenReturn(Optional.of(testFox1));

        service.deleteAnimal(4L);
        verify(repo, times(1)).deleteById(4L);
    }
    @Test
    public void testFoxDeleteByIdThrowsNotFound () {
        assertThrows(FoxNotFoundException.class, () -> {
            service.deleteAnimal(5L);
        });
    }
    @Test
    public void testFoxUpdateById() {
        Fox testFox1 = new Fox("TestFox", 5);
        testFox1.setId(4L);
        when(repo.existsById(testFox1.getId())).thenReturn(true);

        service.updateAnimal(testFox1);
        verify(repo, times(1)).save(testFox1);
    }
    @Test
    public void testFoxUpdateByIdThrowsNotFound () {
        Fox testFox1 = new Fox("TestFox", 5);
        testFox1.setId(4L);
        when(repo.existsById(testFox1.getId())).thenReturn(false);

        assertThrows(FoxFailedToUpdateException.class, () -> {
            service.updateAnimal(testFox1);
        });
    }
    @Test
    public void testFoxFilterByName() {
        Fox testFox1 = new Fox("TestFox", 2);
        Fox testFox2 = new Fox("TestFox", 3);
        Fox testFox3 = new Fox("TestFox", 5);
        List<Fox> foxes = new ArrayList<>();
        foxes.add(testFox1);
        foxes.add(testFox2);
        foxes.add(testFox3);
        when(repo.findAll()).thenReturn(foxes);

        List<Fox> result = service.filterFoxByName("TestFox");
        assertEquals(foxes, result);
    }
}



