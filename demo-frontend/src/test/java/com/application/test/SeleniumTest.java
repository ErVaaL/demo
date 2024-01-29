package com.application.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class SeleniumTest {
    WebDriver webDriver;
    @BeforeEach
    public void setUp(){webDriver = new EdgeDriver();}
    @Test
    public void addFoxTest(){
        SeleniumTestPage page = new SeleniumTestPage(webDriver);
        streamTestPage(page);
    }

    private static void streamTestPage(SeleniumTestPage page) {
        page.open();

        page.addFox();
        page.testGoBackForAddingFox();
        page.editFox();
        page.testGoBackForEditingFox();
        page.deleteFox();
        page.testGoBackForDeletingFox();


        page.close();
    }
}