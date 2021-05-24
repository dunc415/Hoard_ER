import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewArtist extends Application{

    DataManager dm = Main.dm;
    
    TableView<Artist> tableView;
    Label lblViewArtist;
    ObservableList<Artist> data = FXCollections.observableArrayList();

    public void start(Stage viewArtistStage){
        viewArtistStage.setTitle("View Artist");

        // Labels

        lblViewArtist = new Label("View Artist");

        // MENUBAR

        SeparatorMenuItem separatorArtist = new SeparatorMenuItem();
        SeparatorMenuItem separatorAlbum = new SeparatorMenuItem();


        Menu artistMenu = new Menu("Artists");
        MenuItem artistMenuItem_ViewArtists = new MenuItem("View Artists");
        MenuItem artistMenuItem_AddArtists  = new MenuItem("Add Artist");
        artistMenuItem_AddArtists.setOnAction(ActionEvent -> {
            AddArtist addArtist = new AddArtist();
            addArtist.start(viewArtistStage);
        });
        artistMenu.getItems().add(artistMenuItem_ViewArtists);
        artistMenu.getItems().add(separatorArtist);
        artistMenu.getItems().add(artistMenuItem_AddArtists);

        Menu albumMenu = new Menu("Albums");
        MenuItem albumMenuItem_ViewAlbums = new MenuItem("View Albums");
        MenuItem albumsMenuItem_AddAlbum = new MenuItem("Add Album");
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
        TableColumn<Artist, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Artist, String> numberOfAlbumsColumn = new TableColumn<>("Total Number of Albums");
        numberOfAlbumsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfAlbums"));
        TableColumn<Artist, String> numberOfAlbumsInCollectionColumn = new TableColumn<>("Number of Albums in Collection");
        numberOfAlbumsInCollectionColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfAlbumsInCollection"));

        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(numberOfAlbumsColumn);
        tableView.getColumns().add(numberOfAlbumsInCollectionColumn);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        insertIntoTable();

        // Vbox

        VBox vboxCenter = new VBox(lblViewArtist, tableView);
        vboxCenter.setAlignment(Pos.CENTER);

        // BorderPane

        BorderPane borderpane = new BorderPane();

        borderpane.setTop(menuBar);

        borderpane.setCenter(vboxCenter);


        Scene scene = new Scene(borderpane, 700, 500);
        viewArtistStage.setScene(scene);
        viewArtistStage.show();
    }

    public void insertIntoTable(){
        ArrayList<Artist> artists = dm.getArtists();

        for(int i = 0; i < artists.size(); i++){
            data.add(artists.get(i));
        }

        tableView.setItems(data);
        
    }

    public static void main(String[] args){
        Application.launch(args);
    }
}
