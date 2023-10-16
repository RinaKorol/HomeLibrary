package com.example.labwithbase.services;

import com.example.labwithbase.Book;
import com.example.labwithbase.DataLoader;
import com.example.labwithbase.Jenre;
import com.example.labwithbase.Shelf;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class ShelfHasBooksException extends Exception{
        public ShelfHasBooksException(String message){
            System.out.println(message);
        }
}
public class ShelfService {

    public List<Book> booksByShelf(DataLoader loader, int shelfId){
        try {

            String queryBook = " SELECT * FROM BOOK WHERE shelf_id = " + shelfId;
            ResultSet rs = loader.getRS(queryBook);
            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String year = rs.getString(3);
                String place = rs.getString(4);
                int shelf = rs.getInt(5);
                books.add(new Book(id, name, year, place, shelf));
            }
            return books;
        }catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }
        return null;
    }

    public void deleteShelf(DataLoader loader,Shelf shelf) {
             String  query = " DELETE FROM Shelf WHERE id = "+ shelf.getId();
            ResultSet rs = loader.getRS(query);

    }
    public void addShelf(DataLoader loader, Shelf shelf){
        String query = " INSERT INTO Shelf (id, kind) VALUES ( "+ shelf.getId() +" , '"+ shelf.getKind()+"' )";
        ResultSet rs = loader.getRS(query);
    }
    public List<Shelf> findAllShelves(DataLoader loader){
        try{
            Shelf shelf;
            List<Shelf> shelves = new ArrayList<>();
            ResultSet rs = loader.getRS("SELECT * FROM SHELF order by id");
            while (rs.next()) {
                int count = rs.getInt(1);
                String kind = rs.getString(2);
                shelf = new Shelf(count,kind);
                shelves.add( shelf);

            }
            return shelves;
        }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }


        return null;
    }

    public ObservableList<Integer> findAllShelvesId(DataLoader loader){
        try{
            ObservableList<Integer> shelvesId = FXCollections.observableArrayList();
            ResultSet rs = loader.getRS("SELECT id FROM SHELF");
            while (rs.next()) {
                int idRead = rs.getInt(1);
                shelvesId.add( idRead);

            }
            return shelvesId;
        }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }


        return null;
    }
}
