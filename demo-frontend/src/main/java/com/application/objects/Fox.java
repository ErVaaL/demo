package com.application.objects;

public class Fox {

    private Long id;
    private String name;
    private int tails;

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    protected Fox(){}

    public Fox(String name, int tails){
        this.name=name;
        this.tails=tails;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getTails() {
        return tails;
    }

    public void setTails(int tails) {
        this.tails = tails;
    }

    public Fox(String name) {
        this.name = name;
    }
}
