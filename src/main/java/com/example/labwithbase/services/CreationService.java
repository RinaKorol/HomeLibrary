package com.example.labwithbase.services;

import com.example.labwithbase.Book;
import com.example.labwithbase.Creation;
import com.example.labwithbase.DataLoader;
import com.example.labwithbase.Jenre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreationService {

    public List<Integer> booksByCreation(DataLoader loader, Creation creation){
        try {
            String query = "SELECT * FROM creation_book WHERE creation_id = "+ creation.getId();
            ResultSet rs = loader.getRS(query);
            List<Integer> books = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt(1);
                int creId = rs.getInt(2);
                books.add(id);
            }
            return books;
        }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }
        return null;
    }

    public void deleteCreation(DataLoader loader, Creation creation){
        String query = "DELETE FROM creation_jenre WHERE creation_id = "+ creation.getId();
        ResultSet rs = loader.getRS(query);
        String query2 = "DELETE FROM creation WHERE id = "+ creation.getId();
        rs = loader.getRS(query2);
    }
    public void addCreation(DataLoader loader, Creation creation, List<Integer> jenId){
        String queryAddBook = " INSERT INTO creation (id , name, author_id) VALUES (" +
                creation.getId()+" , '"+creation.getName()+ "' , " +creation.getAuthorId() + " )";
        ResultSet rs = loader.getRS(queryAddBook);
        StringBuilder queryCreBook ;
        ResultSet rsNew;
        for(Integer el : jenId){
            queryCreBook = new StringBuilder(" INSERT INTO creation_jenre (CREATION_ID, JENRE_ID) VALUES ( " + creation.getId() + " , ");
            queryCreBook.append(el).append(" )");
            rsNew= loader.getRS(queryCreBook.toString());
        }
    }
    public ObservableList<Creation> findCreationByName(DataLoader loader, String nameGiven){
        try{
            String query = "SELECT * FROM CREATION WHERE name LIKE '%" +nameGiven+ "%' ";
            ResultSet rs = loader.getRS(query);
            ObservableList<Creation> creations = FXCollections.observableArrayList();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int auId = rs.getInt(3);
                creations.add( new Creation(id,name,auId));
            }
            return creations;
        }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }
        return null;
    }
    public List<Creation> findAllCreations(DataLoader loader){
        try{
            Creation creation;
            List<Creation> creations = new ArrayList<>();
            ResultSet rs = loader.getRS("SELECT * FROM CREATION");
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

}
