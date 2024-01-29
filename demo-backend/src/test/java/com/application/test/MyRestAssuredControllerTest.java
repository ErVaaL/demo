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

    @BeforeAll
    public static void setUp(){

    }
    @AfterAll
    public static void tearDown(){

    }

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
    @Test
    public void testFoxAddingWhenExists(){
        Fox fox = new Fox("Kokos",1);
        fox.setId(1L);
        with()
                .body(fox)
                .contentType("application/json")
                .post(URI + "/addAnimal")
                .then()
                .statusCode(400);
    }
    @Test
    public void testFoxEditing(){
        Fox fox = new Fox("testFox", 7);
        fox.setId(4L);
        with()
                .body(fox)
                .contentType("application/json")
                .put(URI+"/updateAnimal")
                .then()
                .statusCode(200);
    }
    @Test
    public void testFoxEditingFoxUpdateFail(){
        Fox fox = new Fox("testFox", 7);
        fox.setId(21312L);
        with()
                .body(fox)
                .contentType("application/json")
                .put(URI+"/updateAnimal")
                .then()
                .statusCode(400);
    }
    @Test
    public void testFoxDeleting(){
        when()
                .delete(URI + "/deleteAnimal/5")
                .then()
                .statusCode(200);
    }
    @Test
    public void testFoxDeletingNotFound(){
        when()
                .delete(URI + "/deleteAnimal/2022")
                .then()
                .statusCode(404);
    }
}
