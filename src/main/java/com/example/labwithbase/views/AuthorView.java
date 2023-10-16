package com.example.labwithbase.views;

import com.example.labwithbase.Author;
import com.example.labwithbase.DataLoader;
import com.example.labwithbase.services.AuthorService;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Objects;

public class AuthorView {

    private AuthorService service;
    private DataLoader loader;
    private ObservableList<Author> authors;
    private GridPane grid;
    private TableView<Author> table;

    public void createPane(){
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

        table = new TableView<Author>(authors);
        table.setPrefWidth(250);
        table.setPrefHeight(200);

        TableColumn<Author, String> idColumn = new TableColumn<Author,String>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<Author,String>("id"));
        table.getColumns().add(idColumn);

        TableColumn<Author,String> nameColumn = new TableColumn<Author,String>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Author,String>("name"));
        table.getColumns().add(nameColumn);

        Button deleteBtn = new Button("delete");
        deleteBtn.setOnAction(event -> {
            int ind = table.getSelectionModel().getSelectedIndex();
            Author authorOnDelete = table.getItems().get(ind);
            if(authorOnDelete != null) {
                if(service.creationsByAuthor(loader,authorOnDelete).isEmpty()){
                service.deleteAuthor(loader,authorOnDelete);
                authors.remove(authorOnDelete);
                setInfo(authors);}
                else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Has creations");
                    alert.setHeaderText(null);
                    alert.setContentText("You can not delete this author because it has creations. Please delete all creations with this author before");

                    alert.showAndWait();
                }
            }
        });
        grid.add(deleteBtn,2,1);

        grid.add(table,1,1);

        TextField nameFild = new TextField();
        Button addAuthorBtn = new Button("Add Author");
        addAuthorBtn.setOnAction(event ->
                {
                    if(!Objects.equals(nameFild.getText(), "")) {
                        int lastId = 0;
                        for(Author el : authors) lastId = el.getId();
                        Author addAuthor = new Author(lastId+1, nameFild.getText());
                        service.addAuthor(loader, addAuthor);
                        authors.add(addAuthor);
                        setInfo(authors);
                    }
                }
        );
        grid.add(nameFild,1,3);
        grid.add(addAuthorBtn,2,3);

    }

    public GridPane getPane() {
        return grid;
    }

    public void setAuthors(ObservableList<Author> authors) {
        this.authors = authors;
    }

    public void setInfo(ObservableList<Author> authors){
        setAuthors(authors);
        //table.getItems().add(author);
    }

    public void setService(AuthorService service){
        this.service=service;
    }
    public void setLoader(DataLoader loader){
        this.loader=loader;
    }
    public AuthorView(ObservableList<Author> authors, AuthorService service, DataLoader loader){
        setAuthors(authors);
        setService(service);
        setLoader(loader);
        createPane();
    }


}
