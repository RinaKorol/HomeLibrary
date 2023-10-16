package com.example.labwithbase.views;

import com.example.labwithbase.DataLoader;
import com.example.labwithbase.Jenre;
import com.example.labwithbase.Shelf;
import com.example.labwithbase.services.JenreService;
import com.example.labwithbase.services.ShelfService;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.util.Objects;

public class JenreShelfView {
    private DataLoader loader = new DataLoader();
    private ShelfService shelfService;
    private JenreService jenreService;
    private ObservableList<Jenre> jenres;
    private ObservableList<Shelf> shelves;
    private GridPane grid;
    private TableView<Jenre> tableJenre;
    private TableView<Shelf> tableShelf;

    public void createPane(){
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

        tableJenre = new TableView<Jenre>(jenres);
        tableJenre.setPrefWidth(200);
        tableJenre.setPrefHeight(200);

        TableColumn<Jenre, String> idColumn = new TableColumn<Jenre,String>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<Jenre,String>("id"));
        tableJenre.getColumns().add(idColumn);

        TableColumn<Jenre,String> nameColumn = new TableColumn<Jenre,String>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Jenre,String>("name"));
        tableJenre.getColumns().add(nameColumn);

        grid.add(tableJenre,1,2);

        Label addJenrefLab = new Label("Input jenre name");
        TextField jenreNameField = new TextField();
        grid.add(addJenrefLab, 1,3);
        grid.add(jenreNameField,1,4);

        Button addJenre = new Button("Add Jenre");
        addJenre.setOnAction(event -> {
            if(!Objects.equals(jenreNameField.getText(), "")) {
                int lastId=0;
                for( Jenre el : jenres) lastId = el.getId();
                Jenre addJen = new Jenre(lastId+1,jenreNameField.getText());
                jenreService.addJenre(loader,addJen);
                jenres.add(addJen);
            }
        });
        grid.add(addJenre,1,5);

        Button deleteJenre = new Button("Delete jenre");
        deleteJenre.setOnAction(event -> {
            Jenre onDelJenre = tableJenre.getSelectionModel().getSelectedItem();
            if(onDelJenre!= null){
                if(jenreService.creationsByJenre(loader,onDelJenre)==null ||
                        jenreService.creationsByJenre(loader,onDelJenre).isEmpty()) {
                    jenreService.deleteJenre(loader, onDelJenre);
                    jenres.remove(onDelJenre);
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Has creations");
                    alert.setHeaderText(null);
                    alert.setContentText("You can not delete this jenre because it has books. Please delete all creations with this jenre before");

                    alert.showAndWait();
                }
            }
        });
        grid.add(deleteJenre,1,6);

        tableShelf = new TableView<Shelf>(shelves);
        tableShelf.setPrefWidth(200);
        tableShelf.setPrefHeight(200);

        TableColumn<Shelf, String> idColumnSh = new TableColumn<Shelf,String>("ID");
        idColumnSh.setCellValueFactory(new PropertyValueFactory<Shelf,String>("id"));
        tableShelf.getColumns().add(idColumnSh);

        TableColumn<Shelf,String> kindColumn = new TableColumn<Shelf,String>("Kind");
        kindColumn.setCellValueFactory(new PropertyValueFactory<Shelf,String>("kind"));
        tableShelf.getColumns().add(kindColumn);

        grid.add(tableShelf,2,2);

        Label addShelfLab = new Label("Input shelf kind");
        TextField shelfNameField = new TextField();
        grid.add(addShelfLab, 2,3);
        grid.add(shelfNameField,2,4);

        Button addShelf = new Button("Add Shelf");
        addShelf.setOnAction(event -> {
            if(!Objects.equals(shelfNameField.getText(), "")) {
                int lastId=0;
                for( Shelf el : shelves) lastId = el.getId();
                Shelf addSh = new Shelf(lastId+1,shelfNameField.getText());
                shelfService.addShelf(loader,addSh);
                shelves.add(addSh);
            }
        });
        grid.add(addShelf,2,5);

        Button deleteShelf = new Button("Delete shelf");
        deleteShelf.setOnAction(event -> {
            Shelf onDelShelf = tableShelf.getSelectionModel().getSelectedItem();
            if(onDelShelf!=null) {
               if( shelfService.booksByShelf(loader,onDelShelf.getId())== null ||
                       shelfService.booksByShelf(loader,onDelShelf.getId()).isEmpty()) {
                   shelfService.deleteShelf(loader, onDelShelf);
                   shelves.remove(onDelShelf);
               }
               else {
                   Alert alert = new Alert(Alert.AlertType.WARNING);
                   alert.setTitle("Has Books");
                   alert.setHeaderText(null);
                   alert.setContentText("You can not delete this shelf because it has books. Please delete all books from shelf before");

                   alert.showAndWait();
               }
            }
        });
        grid.add(deleteShelf,2,6);
    }

    public GridPane getPane() {
        return grid;
    }

    public void setJenres(ObservableList<Jenre> jenres) {
        this.jenres = jenres;
    }
    public void setShelves(ObservableList<Shelf> shelves){
        this.shelves=shelves;
    }

    public void setInfo(ObservableList<Jenre> jenres){

        //table.getItems().add(author);
    }

    public JenreShelfView(ObservableList<Jenre> jenres, ObservableList<Shelf> shelves, ShelfService shSer, JenreService jenSer){
        setJenres(jenres);
        this.shelfService= shSer;
        this.jenreService = jenSer;
        setShelves(shelves);
        createPane();
    }
}
