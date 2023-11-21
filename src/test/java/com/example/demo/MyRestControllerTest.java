package com.example.demo;

import com.example.demo.controls.MyRestController;
import com.example.demo.controls.MyRestEntityExceptionHandler;
import com.example.demo.controls.MyRestService;
import com.example.demo.exceptions.FoxNotFoundException;
import com.example.demo.objects.Fox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
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
        when(service.getFoxById(2L)).thenReturn(Optional.of(testFox));

        mockMvc.perform(MockMvcRequestBuilders.get("/getAnimalById/2"))
                .andExpect(jsonPath("$.name").value("Lumi"))
                .andExpect(jsonPath("$.tails").value(9))
                .andExpect(status().isOk());
    }
    @Test
    public void filterFoxesByName() throws Exception{
        List<Fox> testFox = new ArrayList<>();
        testFox.add(new Fox("Lumi", 9));
        testFox.add(new Fox("LumiAlt",9));
        when(service.filterFoxByName("Lumi")).thenReturn(testFox);

        mockMvc.perform(MockMvcRequestBuilders.get("/filterAnimalByName/Lumi"))
                .andExpect(jsonPath("$.[0].name").value("Lumi"))
                .andExpect(jsonPath("$.[1].name").value("LumiAlt"))
                .andExpect(status().isOk());
    }
}
