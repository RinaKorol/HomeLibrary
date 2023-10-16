package com.example.labwithbase;

import java.time.Year;
import java.util.List;

public class Book {
    private int id;
    private String name;
    private List<Creation> creations;
    private String year;
    private String place;
    private int shelf_id;

    public Book(){
    }
    public Book(int id, String name, String year, String place, int shelf_id){
        this.id=id;
        this.name=name;
        this.year = year;
        this.place=place;
        this.shelf_id=shelf_id;
    }

    public void setShelf_id(int shelf_id) {
        this.shelf_id = shelf_id;
    }

    public int getShelf_id() {
        return shelf_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }

    public String getPlace() {
        return place;
    }
}
