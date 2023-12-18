package com.example.demo;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SeleniumTestPage {
public final String indexURL = "http://localhost:8080/index";
public WebDriver webDriver;

public SeleniumTestPage(WebDriver webDriver){
this.webDriver=webDriver;
    PageFactory.initElements(webDriver, this);
}
@FindBy(id = "foxAdding")
    WebElement foxAddingSubmit;
@FindBy(id = "foxEditing")
    WebElement foxEditingLink;
@FindBy(id = "foxDeleting")
    WebElement foxDeletingLink;
@FindBy(id = "name")
WebElement foxName;
@FindBy(id = "tails")
WebElement foxTails;
@FindBy(id = "saveFox")
WebElement saveFoxSubmit;
@FindBy(id = "deleteFox")
WebElement deleteFox;
public void open(){ webDriver.get(indexURL);}
public void addFox(){
foxAddingSubmit.click();
foxName.sendKeys("Bambik");
foxTails.sendKeys("5");
saveFoxSubmit.click();
}
public void editFox(){
   foxEditingLink.click();
   foxName.clear();
   foxTails.clear();
   foxName.sendKeys("Bambik");
   foxTails.sendKeys("7");
   saveFoxSubmit.click();

}
public void deleteFox(){
    foxDeletingLink.click();
    deleteFox.click();
}
}
