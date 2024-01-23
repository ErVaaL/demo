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
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
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

//        RestClient.RequestHeadersUriSpec uriSpecMock = mock(RestClient.RequestHeadersUriSpec.class);
//        RestClient.RequestHeadersSpec retrieveSpecMock = mock(RestClient.RequestHeadersSpec.class);
//        RestClient.ResponseSpec responseSpecMock = mock(RestClient.ResponseSpec.class);
//
//        when(restClient.get()).thenReturn(uriSpecMock);
//        when(uriSpecMock.uri(anyString())).thenReturn(retrieveSpecMock);
//        when(retrieveSpecMock.retrieve()).thenReturn(responseSpecMock);
//        when(responseSpecMock.body(any(ParameterizedTypeReference.class))).thenReturn(Arrays.asList(new Fox(), new Fox()));
//
//        List<Fox> actual = myRestService.getAllAnimals();
//
//        assertEquals(2, actual.size());
    }

    @Test
    public void testGetFoxById() {
//        Long foxId = 1L;
//        Fox expectedFox = new Fox();
//        expectedFox.setId(foxId);
//
//        RestClient.RequestHeadersUriSpec uriSpecMock = mock(RestClient.RequestHeadersUriSpec.class);
//        RestClient.RequestHeadersSpec retrieveSpecMock = mock(RestClient.RequestHeadersSpec.class);
//        RestClient.ResponseSpec responseSpecMock = mock(RestClient.ResponseSpec.class);
//
//        when(restClient.get()).thenReturn(uriSpecMock);
//        when(uriSpecMock.uri(MyRestService.API_URL + "/getAnimalById/" + foxId)).thenReturn(retrieveSpecMock);
//        when(retrieveSpecMock.retrieve()).thenReturn(responseSpecMock);
//        when(responseSpecMock.body(any(ParameterizedTypeReference.class))).thenReturn(expectedFox);
//
//        Fox actualFox = myRestService.getFoxById(foxId);
//
//        assertEquals(expectedFox, actualFox);
    }

    @Test
    public void testAddAnimal() {

    }

    @Test
    public void testPutAnimal() {

    }

    @Test
    public void testDeleteAnimal() {

    }
}
