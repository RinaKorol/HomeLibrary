package com.example.labwithbase;


import java.util.List;

public class Creation {

    private int id;

    private String name;

    private List<Jenre> jenres;
    private int authorId;


    public Creation(int id, String name, int author){
        this.id = id;
        this.name = name;
        this.authorId=author;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setJenres(List<Jenre> jenres) {
        this.jenres = jenres;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Jenre> getJenres() {
        return jenres;
    }

}
