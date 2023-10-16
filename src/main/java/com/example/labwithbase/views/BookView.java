package com.example.labwithbase.views;

import com.example.labwithbase.Author;
import com.example.labwithbase.Book;
import com.example.labwithbase.DataLoader;
import com.example.labwithbase.services.BookService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class BookView {
    private ObservableList<Book> books ;
    private GridPane grid;
    private BookService bookService = new BookService();;
    private TableView<Book> table;
    private DataLoader loader = new DataLoader();
    private Boolean filerFlag = false;
    private TextField filterText = new TextField("Input value");

    public void createPane(){

        grid = new GridPane();
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

         TextField search = new TextField();
        grid.add(search,1,1);
        Button searchBookBtn = new Button("Search");
        searchBookBtn.setOnAction(event -> {
            String serchName = search.getText();
            ObservableList<Book> searchedBooks = bookService.findBookByName(loader,serchName);
            setInfo(searchedBooks);
        });
        grid.add(searchBookBtn,2,1);
        Button backSearchBookBtn = new Button("Back");
        backSearchBookBtn.setOnAction(event -> {
            setInfo(books);
        });
        grid.add(backSearchBookBtn,2,2);


        table = new TableView<Book>(books);
        table.setPrefWidth(430);
        table.setPrefHeight(200);

        TableColumn<Book, String> idColumn = new TableColumn<Book,String>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("id"));
        table.getColumns().add(idColumn);

        TableColumn<Book,String> nameColumn = new TableColumn<Book,String>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("name"));
        table.getColumns().add(nameColumn);

        TableColumn<Book, String> yearColumn = new TableColumn<Book,String>("Year");
        yearColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("year"));
        table.getColumns().add(yearColumn);

        TableColumn<Book,String> placeColumn = new TableColumn<Book,String>("Place");
        placeColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("place"));
        table.getColumns().add(placeColumn);

        TableColumn<Book,String> shelfColumn = new TableColumn<Book,String>("Shelf");
        shelfColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("shelf_id"));
        table.getColumns().add(shelfColumn);

        grid.add(table,1,3);


        Button filter = new Button("filter");
        Button backFilterBookBtn = new Button("Back");
        backFilterBookBtn.setOnAction(event -> {
            setInfo(books);
        });

        ObservableList<String> filters = FXCollections.observableArrayList("Jenres", "Authors");
        ComboBox<String> chooseFilter = new ComboBox<>(filters);
        chooseFilter.setValue("choose filter");
        grid.add(chooseFilter,1,4);
        chooseFilter.setOnAction(event -> {
            if(filerFlag.equals(false)){
                filerFlag = true;
                grid.add(filterText, 2,4);
                grid.add(filter,2,5);
                grid.add(backFilterBookBtn,2,6);
            }
            filterButtonAction(filter, chooseFilter.getValue());
        });

        Button addBtn = new Button("Add");
        addBtn.setOnAction(event -> {
            actionAdd();
        });
        grid.add(addBtn,1,7);

        Button deleteBtn = new Button("Delete book");
        deleteBtn.setOnAction(event -> {
            Book deleteBook = table.getSelectionModel().getSelectedItem();
            if(deleteBook!=null){
            books.remove(deleteBook);
            bookService.deleteBook(loader,deleteBook);
            setInfo(books);}
        });
        grid.add(deleteBtn,2,7);

        Button updateTable = new Button("Update Table");
        updateTable.setOnAction(event -> {
            books = FXCollections.observableArrayList( bookService.findAllBooks(loader));
            setInfo(books);
        });
        grid.add(updateTable,1,8);

    }

    public void actionAdd(){
        Stage stage = new Stage();
        AddBookView addBookView = new AddBookView(loader,stage, books);
        Pane pane = new Pane();
        pane.getChildren().add(addBookView.getPane());
        Scene scene = new Scene(pane, 400,400);
        stage.setScene(scene);
        stage.show();
    }
    public void filterButtonAction(Button button, String filterType){
        if(filterType.equals("Jenres")){
            button.setOnAction(event -> {
                ObservableList<Book> searchedBooks = bookService.findBookByJenre(loader, filterText.getText());
                setInfo(searchedBooks);
            });
        }
        else if(filterType.equals("Authors")){
            button.setOnAction(event -> {
                ObservableList<Book> searchedBooks = bookService.findBookByAuthor(loader, filterText.getText());
                setInfo(searchedBooks);
            });
        }
    }
    public GridPane getPane() {
        return grid;
    }

    public void setBooks(ObservableList<Book> books) {
        this.books = books;
    }

    public void setInfo(ObservableList<Book> books){
        //setBooks(books);
        table.setItems(books);
    }

    public BookView(ObservableList<Book> books){
        setBooks(books);
        createPane();
    }

}
