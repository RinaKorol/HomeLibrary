package com.example.labwithbase.services;

import com.example.labwithbase.Author;
import com.example.labwithbase.Creation;
import com.example.labwithbase.DataLoader;
import com.example.labwithbase.Jenre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorService {

    public ObservableList<String> findAllAuthorsName(DataLoader loader){
       try{ String query = "SELECT name FROM author";
        ResultSet rs = loader.getRS(query);
        ObservableList<String> names = FXCollections.observableArrayList();
        while (rs.next()) {
            String name = rs.getString(1);
            names.add(name);
        }
        return names;
    }
        catch (SQLException sqlEx){
        sqlEx.printStackTrace();
    }
        return null;

    }

    public List<Creation> creationsByAuthor(DataLoader loader, Author author){
        try {
            String query = "SELECT * FROM creation WHERE author_id = "+ author.getId();
            ResultSet rs = loader.getRS(query);
            Creation creation;
            List<Creation> creations = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int auId = rs.getInt(3);
                creation = new Creation(id,name,auId);
                creations.add(creation);

            }
            return creations;
        }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }
        return null;
    }
    public void addAuthor(DataLoader loader, Author author){

        String query = "INSERT INTO AUTHOR (id,name) VALUES ( " + author.getId()+", \' "+ author.getName() +" \' )";
        ResultSet rs = loader.getRS(query);

    }

    public void deleteAuthor(DataLoader loader, Author author){
        String query = "DELETE FROM AUTHOR WHERE id = " + author.getId();
        ResultSet rs = loader.getRS(query);
    }

    public void findAuthor(DataLoader loader, int id){
        try {
            String query = "SELECT * FROM AUTHOR WHERE id = " + id;
            ResultSet rs = loader.getRS(query);
            while (rs.next()) {
                int count = rs.getInt(1);
                String name = rs.getString(2);
            }
        }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }
    }
    public List<Author> findAllAuthors(DataLoader loader){
        try{
            Author author;
            List<Author> authors = new ArrayList<>();
            ResultSet rs = loader.getRS("SELECT * FROM AUTHOR");
            while (rs.next()) {
                int count = rs.getInt(1);
                String name = rs.getString(2);
                author = new Author(count,name);
                authors.add( author);

            }
            return authors;
        }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }


        return null;
    }

}
