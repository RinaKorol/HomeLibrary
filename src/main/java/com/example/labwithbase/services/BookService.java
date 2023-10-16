package com.example.labwithbase.services;

import com.example.labwithbase.Author;
import com.example.labwithbase.Book;
import com.example.labwithbase.DataLoader;
import com.example.labwithbase.Shelf;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class BookService {

    public void deleteBook(DataLoader loader, Book book){
        String queryDeleteCreBook = " DELETE FROM creation_book where book_id = "+book.getId();
        ResultSet rs = loader.getRS(queryDeleteCreBook);
        String queryDeleteBook = "DELETE FROM book WHERE id = "+book.getId();
        rs = loader.getRS(queryDeleteBook);
    }
    public void addBook(DataLoader loader, Book book, List<Integer> creId){
        String queryAddBook = " INSERT INTO BOOK (id , name, year, place, shelf_id) VALUES (" +
                book.getId()+" , '"+book.getName()+ "' , " +book.getYear()+ " , '" + book.getPlace()+ "' , "+ book.getShelf_id() + " )";
        ResultSet rs = loader.getRS(queryAddBook);
        StringBuilder queryCreBook ;
        ResultSet rsNew;
        for(Integer el : creId){
            queryCreBook = new StringBuilder(" INSERT INTO creation_book (BOOK_ID, CREATION_ID) VALUES ( " + book.getId() + " , ");
            queryCreBook.append(el).append(" )");
             rsNew= loader.getRS(queryCreBook.toString());
        }
    }

    public ObservableList<Book> findBookByName(DataLoader loader, String nameGiven){
        try{
        String query = "SELECT * FROM BOOK Join Shelf On book.shelf_id = shelf.id WHERE name LIKE '%" +nameGiven+ "%' ";
        ResultSet rs = loader.getRS(query);
            ObservableList<Book> books = FXCollections.observableArrayList();
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String year = rs.getString(3);
            String place = rs.getString(4);
            int shelf = rs.getInt(5);
            books.add( new Book(id, name,year,place, shelf));
        }
        return books;
        }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }
        return null;
    }

    public ObservableList<Book> findBookByJenre(DataLoader loader, String jenreGiven){
        try{
            String query = "SELECT DISTINCT book.id, book.name, book.year, book.place, shelf.id " +
                    "FROM BOOK JOIN creation_book ON book.id = creation_book.book_id "+
                    "JOIN creation ON creation_book.creation_id=creation.id " +
                    "JOIN creation_jenre ON creation.id = creation_jenre.creation_id " +
                    "JOIN jenre ON jenre.id = creation_jenre.jenre_id " +
                    "JOIN shelf ON book.shelf_id = shelf.id "+
                    "WHERE jenre.name LIKE ('%"+jenreGiven+ "%')";
            ResultSet rs = loader.getRS(query);
            ObservableList<Book> books = FXCollections.observableArrayList();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String year = rs.getString(3);
                String place = rs.getString(4);
                int shelf = rs.getInt(5);
                books.add( new Book(id, name,year,place,shelf));
            }
            return books;
        }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }
        return null;
    }


    public ObservableList<Book> findBookByAuthor(DataLoader loader, String authorGiven){
        try{
            String query = "SELECT DISTINCT book.id, book.name, book.year, book.place, shelf.id " +
                    "FROM BOOK JOIN creation_book ON book.id = creation_book.book_id "+
                    "JOIN creation ON creation_book.creation_id=creation.id " +
                    "JOIN author ON creation.author_id = author.id " +
                    "JOIN shelf ON shelf.id = book.shelf_id " +
                    "WHERE author.name LIKE ('%"+authorGiven+ "%')";
            ResultSet rs = loader.getRS(query);
            ObservableList<Book> books = FXCollections.observableArrayList();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String year = rs.getString(3);
                String place = rs.getString(4);
                int shelf = rs.getInt(5);
                books.add( new Book(id, name,year,place, shelf));
            }
            return books;
        }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }
        return null;
    }
    public List<Book> findAllBooks(DataLoader loader){
        try{
            Book book;
            List<Book> books = new ArrayList<>();
            ResultSet rs = loader.getRS("SELECT * FROM BOOK JOIN shelf ON book.shelf_id = shelf.id");
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String year = rs.getString(3);
                String place = rs.getString(4);
                int shelf = rs.getInt(5);
                book = new Book(id,name,year, place, shelf);
                books.add( book);

            }
            return books;
        }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }
        return null;
    }



}
