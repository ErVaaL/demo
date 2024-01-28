package com.application.test;

import com.application.controls.controller.MyFoxController;
import com.application.controls.exceptionhandle.MyRestEntityExceptionHandler;
import com.application.controls.service.MyRestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class MyFoxControllerTest {
    @Mock
    private MyRestService service;

    @InjectMocks
    private MyFoxController controller;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(
                new MyRestEntityExceptionHandler(),
                controller).build();
    }

    @Test
    public void testGetIndexView() throws Exception{
        mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}
