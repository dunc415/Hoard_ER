import java.sql.SQLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddArtist extends Application{

    DataManager dm = Main.dm;

    Label lblTitle;
    Label lblNameArtist;
    Label lblArtistNumberOfAlbums;

    TextField tfNameArtist;
    TextField tfArtistNumberOfAlbums;

    Button btnAddArtist;

    @Override
    public void start(Stage addArtistStage) {
        addArtistStage.setTitle("Add Artist");

        lblTitle = new Label("Add Artist");
        lblTitle.setAlignment(Pos.CENTER);
        lblNameArtist = new Label("Name of Artist");
        lblNameArtist.setAlignment(Pos.CENTER_LEFT);
        lblArtistNumberOfAlbums = new Label("Amount of Albums in Discography");
        lblNameArtist.setAlignment(Pos.CENTER_LEFT);
        // Label lblNameAlbum = new Label("Name of Artist");
        // lblNameAlbum.setAlignment(Pos.CENTER);

        tfNameArtist = new TextField();
        tfNameArtist.setPrefWidth(200);
        tfArtistNumberOfAlbums = new TextField();
        tfArtistNumberOfAlbums.setPrefWidth(200);
        // TextField tfNameAlbum = new TextField();
        // tfNameAlbum.setAlignment(Pos.CENTER);

        btnAddArtist = new Button("Add");
        btnAddArtist.setAlignment(Pos.CENTER);
        btnAddArtist.setOnAction(this::processOfAddingArtist);

        // MENUBAR

        SeparatorMenuItem separatorArtist = new SeparatorMenuItem();
        SeparatorMenuItem separatorAlbum = new SeparatorMenuItem();

        Menu artistMenu = new Menu("Artists");
        MenuItem artistMenuItem_ViewArtists = new MenuItem("View Artists");
        artistMenuItem_ViewArtists.setOnAction(ActionEvent -> {
            ViewArtist viewArtist = new ViewArtist();
            viewArtist.start(addArtistStage);
        });
        MenuItem artistMenuItem_AddArtists  = new MenuItem("Add Artist");
        artistMenu.getItems().add(artistMenuItem_ViewArtists);
        artistMenu.getItems().add(separatorArtist);
        artistMenu.getItems().add(artistMenuItem_AddArtists);

        Menu albumMenu = new Menu("Albums");
        MenuItem albumMenuItem_ViewAlbums = new MenuItem("View Albums");
        albumMenuItem_ViewAlbums.setOnAction(ActionEvent -> {
            ViewAlbum viewAlbum = new ViewAlbum();
            viewAlbum.start(addArtistStage);
        });
        MenuItem albumsMenuItem_AddAlbum = new MenuItem("Add Album");
        albumsMenuItem_AddAlbum.setOnAction(ActionEvent -> {
            AddAlbum addAlbum = new AddAlbum();
            addAlbum.start(addArtistStage);
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


        // VBOX 
        
        HBox vboxArtistName = new HBox(lblNameArtist, tfNameArtist);
        vboxArtistName.setAlignment(Pos.CENTER);
        vboxArtistName.setSpacing(10);

        HBox vboxArtistNumberOfAlbums = new HBox(lblArtistNumberOfAlbums, tfArtistNumberOfAlbums);
        vboxArtistNumberOfAlbums.setAlignment(Pos.CENTER);
        vboxArtistNumberOfAlbums.setSpacing(10);

        // HBox hboxAlbumName = new HBox(lblNameAlbum, tfNameAlbum);
        // hboxAlbumName.setAlignment(Pos.CENTER);
        // hboxAlbumName.setSpacing(10);

        VBox vboxCenterItems = new VBox(lblTitle,vboxArtistName, vboxArtistNumberOfAlbums);
        vboxCenterItems.setAlignment(Pos.CENTER);
        vboxCenterItems.setSpacing(20);
        
        // BORDERPANE 

        BorderPane borderpane = new BorderPane();

        borderpane.setCenter(vboxCenterItems);
        BorderPane.setAlignment(vboxCenterItems, Pos.CENTER);

        borderpane.setTop(menuBar);

        borderpane.setBottom(btnAddArtist);
        BorderPane.setAlignment(btnAddArtist, Pos.CENTER);
        BorderPane.setMargin(btnAddArtist, new Insets(10));

        Scene scene = new Scene(borderpane, 700, 500);
        addArtistStage.setScene(scene);
        addArtistStage.show();
        
    }

    public void processOfAddingArtist(ActionEvent event){
        try{
            int numOfAlbumsInCollection = 0;
            int integerAlbumsOfArtist = Integer.parseInt(tfArtistNumberOfAlbums.getText());
            dm.setArtist(tfNameArtist.getText(), integerAlbumsOfArtist, numOfAlbumsInCollection);
            
        }catch(SQLException e){
            System.out.println("Problem Adding Artist");
        }
    }


    public static void main(String[] args){
        Application.launch(args);
    }
    
}
