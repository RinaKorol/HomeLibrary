package com.example.labwithbase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Author {

   private int id;

   private  String name;


    private List<Creation> creations;

   public Author(){
       name="";
       id = 0;
   }

   public Author(int id, String name){
      this.id = id;
      this.name = name;
   }

    public void setId(int id) {
        this.id = id;
    }

    public  void setName(String name) {
        this.name = name;
    }

    public  int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /*public List<Author> getListAuthors(DataLoader loader){
       try{
           List<Author> authors = new ArrayList<>();
           ResultSet rs = loader.getRS("SELECT * FROM AUTHOR;");
           while (rs.next()) {
               int count = rs.getInt(1);
               String name = rs.getString(2);
               authors.add( new Author(count, name));

           }
           return authors;
       }
       catch (SQLException sqlEx){
           sqlEx.printStackTrace();
       }

       return null;

    }*/

}
