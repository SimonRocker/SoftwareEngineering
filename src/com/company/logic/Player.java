package com.company.logic;

public class Player {
    private String name;
    private Integer id;

    public Player(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getStartField() {
        return this.id * 12 ;
    }
}
