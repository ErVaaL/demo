package com.application.test;

import com.application.controls.service.MyRestService;
import com.application.exceptions.FoxAlreadyExistsException;
import com.application.exceptions.FoxFailedToUpdateException;
import com.application.exceptions.FoxNotFoundException;
import com.application.objects.Fox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.web.client.MockServerRestClientCustomizer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest
public class MyRestServiceTest {

    MockServerRestClientCustomizer customizer = new MockServerRestClientCustomizer();
    RestClient.Builder builder = RestClient.builder();

    MyRestService service;
    @BeforeEach
    public void setup(){
        customizer.customize(builder);
        service = new MyRestService(builder.build());
    }

    @Test
    public void testGetAllAnimals() {

        customizer.getServer().expect(requestTo(MyRestService.API_URL + "/getAnimals"))
                .andRespond(withSuccess(
                        "[{\"id\":1,\"name\":\"Lumi\",\"tails\":9},{\"id\":2,\"name\":\"Alt\",\"tails\":9}]",
                        MediaType.APPLICATION_JSON));
        List<Fox> foxes = service.getAllAnimals();
        assertEquals(2, foxes.size());

    }

    @Test
    public void testGetFoxById() {
        customizer.getServer().expect(requestTo(MyRestService.API_URL + "/getAnimalById/1"))
                .andRespond(withSuccess(
                        "{\"id\":1,\"name\":\"Lumi\",\"tails\":9}",
                        MediaType.APPLICATION_JSON));
        Fox fox = service.getFoxById(1L);
        assertEquals(1L, fox.getId());
        assertEquals("Lumi", fox.getName());
        assertEquals(9, fox.getTails());
    }

    @Test
    public void testAddAnimal() {
        Fox fox = new Fox("Lumi", 9);
        customizer.getServer().expect(requestTo(MyRestService.API_URL + "/addAnimal"))
                .andRespond(withSuccess(
                        "{\"id\":1,\"name\":\"Lumi\",\"tails\":9}",
                        MediaType.APPLICATION_JSON));
        service.addAnimal(fox);
        assertEquals("Lumi", fox.getName());
        assertEquals(9, fox.getTails());
    }

    @Test
    public void testPutAnimal() {
        Fox fox = new Fox("Lumi", 3);
        fox.setId(2L);
        customizer.getServer().expect(requestTo(MyRestService.API_URL + "/getAnimalById/" + fox.getId()))
                .andRespond(withSuccess(
                        "{\"id\":1,\"name\":\"Lumi\",\"tails\":3}",
                        MediaType.APPLICATION_JSON));

        customizer.getServer().expect(requestTo(MyRestService.API_URL + "/updateAnimal"))
                .andRespond(withSuccess(
                        "{\"id\":1,\"name\":\"Lumi\",\"tails\":9}",
                        MediaType.APPLICATION_JSON));
        service.updateAnimal(fox);
        assertEquals("Lumi", fox.getName());
        assertEquals(3, fox.getTails());
    }

    @Test
    public void testDeleteAnimal() {
        Fox fox = new Fox("Lumi", 3);
        fox.setId(2L);
        customizer.getServer().expect(requestTo(MyRestService.API_URL + "/getAnimalById/" + fox.getId()))
                .andRespond(withSuccess(
                        "{\"id\":1,\"name\":\"Lumi\",\"tails\":3}",
                        MediaType.APPLICATION_JSON));
        customizer.getServer().expect(requestTo(MyRestService.API_URL + "/deleteAnimal/" + fox.getId()))
                .andRespond(withSuccess(
                        "{\"id\":1,\"name\":\"Lumi\",\"tails\":3}",
                        MediaType.APPLICATION_JSON));
        service.deleteAnimal(fox.getId());
        assertEquals("Lumi", fox.getName());
        assertEquals(3, fox.getTails());
    }
    @Test
    public void testDeleteAnimalThrowsException() {
    Long foxId = 2L;

     customizer.getServer().expect(requestTo(MyRestService.API_URL + "/getAnimalById/" + foxId))
            .andRespond(withStatus(HttpStatus.NOT_FOUND));

    assertThrows(HttpClientErrorException.NotFound.class, () -> service.deleteAnimal(foxId));
    }
    @Test
    public void testGetFoxByIdThrowsException() {
        Long foxId = 2L;
        customizer.getServer().expect(requestTo(MyRestService.API_URL + "/getAnimalById/" + foxId))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));
        assertThrows(HttpClientErrorException.NotFound.class, () -> service.getFoxById(foxId));
    }
    @Test
    public void testAddAnimalThrowsException() {
        Fox fox = new Fox("Lumi", 9);
        customizer.getServer().expect(requestTo(MyRestService.API_URL + "/addAnimal"))
                .andRespond(withStatus(HttpStatus.CONFLICT));
        assertThrows(HttpClientErrorException.Conflict.class, () -> service.addAnimal(fox));
    }
    @Test
    public void testUpdateAnimalThrowsException() {
        Fox fox = new Fox("Lumi", 9);
        fox.setId(2L);
        customizer.getServer().expect(requestTo(MyRestService.API_URL + "/getAnimalById/" + fox.getId()))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));
        assertThrows(HttpClientErrorException.NotFound.class, () -> service.updateAnimal(fox));
    }
    @Test
    public void testGetAllAnimalsThrowsException() {
        customizer.getServer().expect(requestTo(MyRestService.API_URL + "/getAnimals"))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));
        assertThrows(HttpClientErrorException.NotFound.class, () -> service.getAllAnimals());
    }
    

}
