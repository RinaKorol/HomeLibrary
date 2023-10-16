package com.example.labwithbase.views;

import com.example.labwithbase.Book;
import com.example.labwithbase.Creation;
import com.example.labwithbase.DataLoader;
import com.example.labwithbase.services.BookService;
import com.example.labwithbase.services.CreationService;
import com.example.labwithbase.services.ShelfService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddBookView {
    DataLoader loader;
    ShelfService shelfService = new ShelfService();
    CreationService creationService = new CreationService();
    BookService bookService = new BookService();
    private GridPane grid;
    private ObservableList<Integer> shelfIdList;
    private ObservableList<Book> books;
    private Book book;
    private ObservableList<Creation> creations;
    private List<Integer> creationId= new ArrayList<>();
    private TableView<Creation> creationTableView;
    private Stage stage;

    public void createPane(){

        grid = new GridPane();
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));


        Label nameLabel = new Label("Name");
        TextField nameFiels = new TextField();
        grid.add(nameLabel,1,2);
        grid.add(nameFiels,2,2);

        Label yearLabel = new Label("Year");
        TextField yearFiels = new TextField();
        grid.add(yearLabel,1,3);
        grid.add(yearFiels,2,3);

        Label placeLabel = new Label("Place");
        TextField placeFiels = new TextField();
        grid.add(placeLabel,1,4);
        grid.add(placeFiels,2,4);

        Label shelfLabel = new Label("Shelf ID");
        grid.add(shelfLabel,1,5);

        ComboBox<Integer> shelves = new ComboBox<>(shelfIdList);
        shelves.setPrefWidth(60);
        grid.add(shelves,2,5);

        creationTableView = new TableView<Creation>(creations);
        creationTableView.setPrefWidth(200);
        creationTableView.setPrefHeight(150);

        TableColumn<Creation,String> nameColumn = new TableColumn<Creation,String>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Creation,String>("name"));
        creationTableView.getColumns().add(nameColumn);

        grid.add(creationTableView,1,6);

        Button chooseCreationBtn = new Button("Choose");
        chooseCreationBtn.setOnAction(event -> {
            Creation chosen = creationTableView.getSelectionModel().getSelectedItem();
            if(chosen!=null)
                creationId.add(chosen.getId());
        });
        grid.add(chooseCreationBtn,2,6);

        Button addBook = new Button("Add Book");
        addBook.setOnAction(event -> {
            if((!Objects.equals(nameFiels.getText(), ""))&&
                    (!Objects.equals(yearFiels.getText(), ""))&&(!Objects.equals(placeFiels.getText(), ""))
                    &&shelves.getValue()!=null&&(!creationId.isEmpty()) && isNumber(yearFiels.getText())
            ){
                int lastId = 0;
                for(Book el: books) lastId= el.getId();
                 book = new Book(lastId+1,
                    nameFiels.getText(),
                    yearFiels.getText(),
                    placeFiels.getText(),
                    shelves.getValue()
                );
                 books.add(book);
                 bookService.addBook(loader,book,creationId);
                 stage.close();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Some fields are empty");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all fields");

                alert.showAndWait();
            }
        });
        grid.add(addBook,1,7);

    }
    public Boolean isNumber( String value){
        try{
            Integer.parseInt(value);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
    public GridPane getPane() {
        return grid;
    }

    public void setShelfIdList() {
        this.shelfIdList = shelfService.findAllShelvesId(loader);
    }
    public void setCreations(){
        List<Creation> cre = creationService.findAllCreations(loader);
        creations = FXCollections.observableArrayList(cre);
    }

    public void setLoader(DataLoader loader){
        this.loader=loader;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
    public AddBookView(DataLoader loader, Stage stage, ObservableList<Book> books){
        setLoader(loader);
        setCreations();
        setStage(stage);
        this.books = books;
        setShelfIdList();
        createPane();
    }

}
