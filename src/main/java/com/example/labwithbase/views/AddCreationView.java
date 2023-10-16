package com.example.labwithbase.views;

import com.example.labwithbase.*;
import com.example.labwithbase.services.*;
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

public class AddCreationView {

    DataLoader loader;
    JenreService jenreService = new JenreService();
    CreationService creationService = new CreationService();
    private AuthorService authorService =new AuthorService();
    private GridPane grid;
    private ObservableList<Author> authorsName;
    private ObservableList<Jenre> jenres;
    private TableView<Jenre> jenreTable;
    private List<Integer> jenreId=new ArrayList<>();
    private ObservableList<Creation> creations;

    private Creation creation;
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

        ObservableList<String> auth = FXCollections.observableArrayList();
        for(Author el : authorsName){
            auth.add(el.getName());
        }

        ComboBox<String> authors = new ComboBox<>(auth);
        authors.setPrefWidth(80);
        grid.add(authors,2,3);

        jenreTable = new TableView<Jenre>(jenres);
        jenreTable.setPrefWidth(200);
        jenreTable.setPrefHeight(150);

        TableColumn<Jenre,String> nameColumn = new TableColumn<Jenre,String>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Jenre,String>("name"));
        jenreTable.getColumns().add(nameColumn);

        grid.add(jenreTable,1,4);

        Button choose = new Button("choose");
        choose.setOnAction(event -> {
            Jenre chosen = jenreTable.getSelectionModel().getSelectedItem();
            if(chosen!=null){
                jenreId.add(chosen.getId());
            }
        });
        grid.add(choose,2,4);

        Button add = new Button("Add");
        add.setOnAction(event -> {
            if(!Objects.equals(nameFiels.getText(), "") && (!jenreId.isEmpty())&& authors.getValue()!=null){
                int lastId = 0;
                for(Creation el: creations) lastId= el.getId();
                Author choseAuth = new Author();
                for(Author el : authorsName){
                    if(el.getName().equals(authors.getValue()))
                        choseAuth = el;
                }
                creation = new Creation(lastId+1,
                        nameFiels.getText(),choseAuth.getId());
                creationService.addCreation(loader,creation,jenreId);
                creations.add(creation);
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
        grid.add(add,1,5);

    }
    public GridPane getPane() {
        return grid;
    }
    public void setLoader(DataLoader loader){
        this.loader=loader;
    }

    public void setAuthorsName(){
        List<Author> au = authorService.findAllAuthors(loader);
        this.authorsName= FXCollections.observableArrayList(au);
    }

    public void setJenres(){
        List<Jenre> j = jenreService.findAllJenres(loader);
       this.jenres = FXCollections.observableArrayList(j);
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }

    public AddCreationView(DataLoader loader, Stage stage, ObservableList<Creation> creations){
        setLoader(loader);
        this.creations = creations;
        setJenres();
        setAuthorsName();
        setStage(stage);
        createPane();
    }

}
