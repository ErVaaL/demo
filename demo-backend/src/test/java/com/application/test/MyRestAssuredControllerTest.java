package com.application.test;

import com.application.objects.Fox;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyRestAssuredControllerTest {
    private static final String URI = "http://localhost:8080";

//    @BeforeAll
//    public void setUp(){
//        with()
//                .body(new Fox("testFox",5))
//                .contentType("application/json")
//                .port(URI + "/addAnimal");
//    }
//    @AfterAll
//    public void tearDown(){
//        with()
//                .body(new Fox("testFox",5))
//                .contentType("application/json")
//                .delete(URI + "/deleteAnimal");
//    }

    @Test
    public void testGetFox(){
       Fox fox = when()
                .get(URI + "/getAnimalById/1")
                .then()
                .statusCode(200)
                .extract()
                .as(Fox.class);
       assertEquals(1, fox.getId());
       assertEquals("Kokos", fox.getName());
       assertEquals(1, fox.getTails());
    }
    @Test
    public void testAddFox(){
        with()
                .body(new Fox("testFox",5))
                .contentType("application/json")
                .request("POST",URI + "/addAnimal")
                .then()
                .assertThat()
                .body("id",greaterThan(3))
                .body("name", equalTo("testFox"))
                .body("tails",equalTo(5))
                .statusCode(200);

    }
}
