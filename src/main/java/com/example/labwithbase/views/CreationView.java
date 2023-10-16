package com.example.labwithbase.views;

import com.example.labwithbase.Book;
import com.example.labwithbase.Creation;
import com.example.labwithbase.DataLoader;
import com.example.labwithbase.Jenre;
import com.example.labwithbase.services.CreationService;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class CreationView {
    private DataLoader loader;
    private CreationService service;
    private CreationService creationService = new CreationService();
    private ObservableList<Creation> creations ;
    private GridPane grid;
    private TableView<Creation> table;

    public void createPane(){
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

        TextField search = new TextField();
        grid.add(search,1,1);
        Button searchCreationBtn = new Button("Search");
        searchCreationBtn.setOnAction(event -> {
            String serchName = search.getText();
            ObservableList<Creation> searchedCreations = service.findCreationByName(loader,serchName);
            setInfo(searchedCreations);
        });
        grid.add(searchCreationBtn,2,1);
        Button backSearchBookBtn = new Button("Back");
        backSearchBookBtn.setOnAction(event -> {
            setInfo(creations);
        });
        grid.add(backSearchBookBtn,1,2);


        table = new TableView<Creation>(creations);
        table.setPrefWidth(250);
        table.setPrefHeight(200);

        TableColumn<Creation, String> idColumn = new TableColumn<Creation,String>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<Creation,String>("id"));
        table.getColumns().add(idColumn);

        TableColumn<Creation,String> nameColumn = new TableColumn<Creation,String>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Creation,String>("name"));
        table.getColumns().add(nameColumn);

        grid.add(table,1,3);

        Button addCreation = new Button("Add creation");
        addCreation.setOnAction(event -> {
            addAction();
        });
        grid.add(addCreation,1,4);

        Button delete = new Button("delete");
        delete.setOnAction(event -> {
            Creation onDelCre = table.getSelectionModel().getSelectedItem();
            if(onDelCre!= null){
                if(
                        creationService.booksByCreation(loader,onDelCre).isEmpty()) {
                    creationService.deleteCreation(loader,onDelCre);
                    creations.remove(onDelCre);
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Has books");
                    alert.setHeaderText(null);
                    alert.setContentText("You can not delete this creation because it has books. Please delete all books with this creation before");
                    alert.showAndWait();
                }
            }
        });
        grid.add(delete,2,4);
    }

    public void addAction(){
        Stage stage = new Stage();
        AddCreationView addCreationView = new AddCreationView(loader,stage, creations);
        Pane pane = new Pane();
        pane.getChildren().add(addCreationView.getPane());
        Scene scene = new Scene(pane, 400,400);
        stage.setScene(scene);
        stage.show();
    }

    public GridPane getPane() {
        return grid;
    }

    public void setCreations(ObservableList<Creation> creations){
        this.creations=creations;
    }

    public void setInfo(ObservableList<Creation> creations){
        table.setItems(creations);
    }

    public void setLoader(DataLoader loader){
        this.loader=loader;
    }
    public void setService(CreationService service){
        this.service = service;
    }
    public CreationView(ObservableList<Creation> creations, CreationService service, DataLoader loader){
        setCreations(creations);
        setService(service);
        setLoader(loader);
        createPane();
    }


}
