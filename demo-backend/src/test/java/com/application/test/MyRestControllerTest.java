package com.application.test;

import com.application.controls.controller.MyRestController;
import com.application.controls.exceptionhandle.MyRestEntityExceptionHandler;
import com.application.controls.service.MyRestService;
import com.application.exceptions.FoxNotFoundException;
import com.application.objects.Fox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MyRestControllerTest {
    private MockMvc mockMvc;
    @Mock
    private MyRestService service;
    @InjectMocks
    private MyRestController controller;
    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(
                new MyRestEntityExceptionHandler(),controller).build();
    }
    @Test
    public void getFoxByIdReturns404WhenFoxNotFound() throws Exception{
        when(service.getFoxById(7L)).thenThrow(FoxNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/getAnimalById/7"))
                .andExpect(status().isNotFound());
    }
    @Test
    public void getFoxByIdReturns200WhenFoxIsPresent() throws Exception {
        Fox testFox = new Fox("Lumi",9);
        when(service.getFoxById(2L)).thenReturn(testFox);

        mockMvc.perform(MockMvcRequestBuilders.get("/getAnimalById/2"))
                .andExpect(jsonPath("$.name").value("Lumi"))
                .andExpect(jsonPath("$.tails").value(9))
                .andExpect(status().isOk());
    }
    @Test
    public void testGetAllAnimals() throws Exception{
        var foxList = new ArrayList<Fox>();
        when(service.getAllAnimals()).thenReturn(foxList);

        mockMvc.perform(MockMvcRequestBuilders.get("/getAnimals"))
                .andExpect(status().isOk());
    }
    @Test
    public void testGetFoxByTails() throws Exception{
        var foxList = new ArrayList<Fox>();
        when(service.getFoxByTails(9)).thenReturn(foxList);

        mockMvc.perform(MockMvcRequestBuilders.get("/getAnimal/9"))
                .andExpect(status().isOk());
    }
    @Test
    public void testFilterFoxByName() throws Exception{
        var foxList = new ArrayList<Fox>();
        when(service.filterFoxByName("Lumi")).thenReturn(foxList);

        mockMvc.perform(MockMvcRequestBuilders.get("/filterAnimalByName/Lumi"))
                .andExpect(status().isOk());
    }
    @Test
    public void testPutAnimal() throws Exception{
        var fox = new Fox("Lumi",9);
        mockMvc.perform(MockMvcRequestBuilders.put("/updateAnimal")
                .contentType("application/json")
                .content("{\"name\":\"Lumi\",\"tails\":9}"))
                .andExpect(status().isOk());
    }
    @Test
    public void testPostAnimal() throws Exception{
        var fox = new Fox("Lumi",9);
        when(service.addAnimal(ArgumentMatchers.any(Fox.class))).thenReturn(fox);

        mockMvc.perform(MockMvcRequestBuilders.post("/addAnimal")
                .contentType("application/json")
                .content("{\"name\":\"Lumi\",\"tails\":9}"))
                .andExpect(status().isOk());
    }
    @Test
    public void testDeleteAnimal() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteAnimal/1"))
                .andExpect(status().isOk());
    }

}
