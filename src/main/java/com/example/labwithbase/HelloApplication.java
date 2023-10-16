package com.example.labwithbase;

import com.example.labwithbase.services.*;
import com.example.labwithbase.views.AuthorView;
import com.example.labwithbase.views.BookView;
import com.example.labwithbase.views.CreationView;
import com.example.labwithbase.views.JenreShelfView;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        DataLoader loader = new DataLoader();

        AuthorService authorService = new AuthorService();
        List<Author> au = authorService.findAllAuthors(loader);
        ObservableList<Author> authors = FXCollections.observableArrayList(au);

        BookService bookService = new BookService();
        List<Book> bo = bookService.findAllBooks(loader);
        ObservableList<Book> books = FXCollections.observableArrayList(bo);

        CreationService creationService = new CreationService();
        List<Creation> cr = creationService.findAllCreations(loader);
        ObservableList<Creation> creations = FXCollections.observableArrayList(cr);

        JenreService jenreService = new JenreService();
        List<Jenre> jen = jenreService.findAllJenres(loader);
        ObservableList<Jenre> jenres = FXCollections.observableArrayList(jen);

        ShelfService shelfService = new ShelfService();
        List<Shelf> sh = shelfService.findAllShelves(loader);
        ObservableList<Shelf> shelves = FXCollections.observableArrayList(sh);

        VBox auBox=new VBox(10);
        auBox.setAlignment(Pos.TOP_LEFT);
        AuthorView auView = new AuthorView(authors,authorService,loader);
        auBox.getChildren().add(auView.getPane());


        VBox bookBox = new VBox(10);
        bookBox.setAlignment(Pos.TOP_LEFT);
        BookView bookView = new BookView(books);
        bookBox.getChildren().add(bookView.getPane());

        VBox creationBox = new VBox(10);
        creationBox.setAlignment(Pos.TOP_LEFT);
        CreationView creationView = new CreationView(creations, creationService, loader);
        creationBox.getChildren().add(creationView.getPane());

        VBox jenreBox = new VBox(10);
        jenreBox.setAlignment(Pos.TOP_LEFT);
        JenreShelfView jenresShelvesView = new JenreShelfView(jenres,shelves, shelfService, jenreService);
        jenreBox.getChildren().add(jenresShelvesView.getPane());

        TabPane pane = new TabPane();
        Tab auTab = new Tab("authors", auBox);
        Tab bookTab = new Tab("books", bookBox);
        Tab creationTab = new Tab("creations", creationBox);
        Tab jenreShelfTab = new Tab("jenre/shelf", jenreBox);
        pane.getTabs().add(auTab);
        pane.getTabs().add(bookTab);
        pane.getTabs().add(creationTab);
        pane.getTabs().add(jenreShelfTab);

        Scene scene = new Scene(pane, 600,600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}