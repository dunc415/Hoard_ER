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
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ViewAlbum extends Application{

    DataManager dm = Main.dm;
    
    TableView<Album> tableView;
    Label lblViewAlbum;
    ObservableList<Album> data = FXCollections.observableArrayList();

    public static Album columnAlbumData; 

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

        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(artistNameColumn);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        addButtonToTable();

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

    private void addButtonToTable() {
        TableColumn<Album, Void> colBtn = new TableColumn("Cover Art");

        Callback<TableColumn<Album, Void>, TableCell<Album, Void>> cellFactory = new Callback<TableColumn<Album, Void>, TableCell<Album, Void>>() {
            @Override
            public TableCell<Album, Void> call(final TableColumn<Album, Void> param) {
                final TableCell<Album, Void> cell = new TableCell<Album, Void>() {

                    private final Button btn = new Button("View Cover");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            columnAlbumData = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " +columnAlbumData);
                            openAlbumCover();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        tableView.getColumns().add(colBtn);

    }

    public void openAlbumCover() {

        Stage albumCoverStage = new Stage();
        albumCoverStage.setTitle("Album Cover");

        BorderPane bpane = new BorderPane();

        ImageView cover = new ImageView();
        cover = columnAlbumData.getAlbumCoverPath();
        cover.setFitHeight(300);
        cover.setFitWidth(300);

        Text txtAlbumInfo = new Text(columnAlbumData.toString());

        VBox vbox_CoverInfo = new VBox();
        vbox_CoverInfo.getChildren().add(columnAlbumData.getAlbumCoverPath());
        vbox_CoverInfo.getChildren().add(txtAlbumInfo);
        
        bpane.setCenter(vbox_CoverInfo);
        BorderPane.setAlignment(vbox_CoverInfo, Pos.CENTER);

        Scene scene = new Scene(bpane, 300, 300);
        albumCoverStage.setScene(scene);
        albumCoverStage.show();

    }
    public static void main(String[] args){
        Application.launch(args);
    }
}
