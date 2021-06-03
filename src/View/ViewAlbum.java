package View;

import java.util.ArrayList;

import Add.AddAlbum;
import Add.AddArtist;
import Objects.Album;
import DataManager.DataManager;
import Main.Main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewAlbum extends Application{

    DataManager dm = Main.dm;
    
    TableView<Album> tableView;
    Label lblViewAlbum;
    ObservableList<Album> data = FXCollections.observableArrayList();

    public void start(Stage viewAlbumStage){
        viewAlbumStage.setTitle("View Album");

        // Labels

        lblViewAlbum = new Label("View Album");

        // MENUBAR

        SeparatorMenuItem separatorArtist = new SeparatorMenuItem();
        SeparatorMenuItem separatorAlbum = new SeparatorMenuItem();


        Menu artistMenu = new Menu("Artists");
        MenuItem artistMenuItem_ViewArtists = new MenuItem("View Artists");
        artistMenuItem_ViewArtists.setOnAction(ActionEvent -> {
            ViewArtist viewArtist = new ViewArtist();
            viewArtist.start(viewAlbumStage);
        });
        MenuItem artistMenuItem_AddArtists  = new MenuItem("Add Artist");
        artistMenuItem_AddArtists.setOnAction(ActionEvent -> {
            AddArtist addArtist = new AddArtist();
            addArtist.start(viewAlbumStage);
        });
        
        artistMenu.getItems().add(artistMenuItem_ViewArtists);
        artistMenu.getItems().add(separatorArtist);
        artistMenu.getItems().add(artistMenuItem_AddArtists);

        Menu albumMenu = new Menu("Albums");
        MenuItem albumMenuItem_ViewAlbums = new MenuItem("View Albums");
        MenuItem albumsMenuItem_AddAlbum = new MenuItem("Add Album");
        albumsMenuItem_AddAlbum.setOnAction(ActionEvent -> {
            AddAlbum addAlbum = new AddAlbum();
            addAlbum.start(viewAlbumStage);
        });

        albumMenu.getItems().add(albumMenuItem_ViewAlbums);
        albumMenu.getItems().add(separatorAlbum);
        albumMenu.getItems().add(albumsMenuItem_AddAlbum);

        Menu wishlistMenu = new Menu("Wish List");
        
        Menu favoriteAlbumMenu = new Menu("Favorite Album");

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(artistMenu);
        menuBar.getMenus().add(albumMenu);
        menuBar.getMenus().add(wishlistMenu);

        // TableView

        tableView = new TableView();
        tableView.setPlaceholder(new Label("No Artist in Collection"));
        TableColumn<Album, String> nameColumn = new TableColumn<>("Title");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Album, String> artistNameColumn = new TableColumn<>("Artist");
        artistNameColumn.setCellValueFactory(new PropertyValueFactory<>("artistName"));
        TableColumn<Album, ImageView> coverArtColumn = new TableColumn<>("Cover Art");
        coverArtColumn.setCellValueFactory(new PropertyValueFactory<>("coverArt"));

        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(artistNameColumn);
        tableView.getColumns().add(coverArtColumn);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        insertIntoTable();

        // Vbox

        VBox vboxCenter = new VBox(lblViewAlbum, tableView);
        vboxCenter.setAlignment(Pos.CENTER);

        // BorderPane

        BorderPane borderpane = new BorderPane();

        borderpane.setTop(menuBar);

        borderpane.setCenter(vboxCenter);


        Scene scene = new Scene(borderpane, 700, 500);
        viewAlbumStage.setScene(scene);
        viewAlbumStage.show();
    }

    public void insertIntoTable(){
        ArrayList<Album> albums = dm.getAlbums();

        for(int i = 0; i < albums.size(); i++){
            data.add(albums.get(i));
        }

        tableView.setItems(data);
        
    }

    public static void main(String[] args){
        Application.launch(args);
    }
}
