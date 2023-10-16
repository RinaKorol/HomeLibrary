package com.example.labwithbase;

public class Shelf {
    int id;
    String kind;

    public Shelf(int id, String kind){
        this.id=id;
        this.kind=kind;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getId() {
        return id;
    }

    public String getKind() {
        return kind;
    }
}
