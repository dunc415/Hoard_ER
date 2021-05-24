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

public class AddAlbum extends Application{
    DataManager dm = Main.dm;

    Label lblTitle;
    Label lblArtistName;
    Label lblAlbumName;
    Label lblAlbumCover;


    TextField tfArtistName;
    TextField tfAlbumName;
    TextField tfAlbumCover;

    Button btnAddAlbum;

    @Override
    public void start(Stage addAlbumStage) {
        addAlbumStage.setTitle("Add Album");

        lblTitle = new Label("Add Album");
        lblTitle.setAlignment(Pos.CENTER);
        lblArtistName = new Label("Name of Artist");
        lblArtistName.setAlignment(Pos.CENTER_LEFT);
        lblAlbumName = new Label("Name of Album");
        lblAlbumName.setAlignment(Pos.CENTER_LEFT);
        lblAlbumCover = new Label("Album Cover");
        lblAlbumCover.setAlignment(Pos.CENTER);

        tfArtistName = new TextField();
        tfArtistName.setPrefWidth(200);
        tfAlbumName = new TextField();
        tfAlbumName.setPrefWidth(200);

        // This is going to be a file explorer
        tfAlbumCover = new TextField();
        tfAlbumCover.setPrefWidth(200);

        btnAddAlbum = new Button("Add");
        btnAddAlbum.setAlignment(Pos.CENTER);
        btnAddAlbum.setOnAction(this::processOfAddingAlbum);

        // MENUBAR

        SeparatorMenuItem separatorArtist = new SeparatorMenuItem();
        SeparatorMenuItem separatorAlbum = new SeparatorMenuItem();

        Menu artistMenu = new Menu("Artists");
        MenuItem artistMenuItem_ViewArtists = new MenuItem("View Artists");
        artistMenuItem_ViewArtists.setOnAction(ActionEvent -> {
            ViewArtist viewArtist = new ViewArtist();
            viewArtist.start(addAlbumStage);
        });
        MenuItem artistMenuItem_AddArtists  = new MenuItem("Add Artist");
        artistMenuItem_AddArtists.setOnAction(ActionEvent -> {
            ViewArtist addArtist = new ViewArtist();
            addArtist.start(addAlbumStage);
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


        // VBOX 
        
        HBox hboxArtistName = new HBox(lblArtistName, tfArtistName);
        hboxArtistName.setAlignment(Pos.CENTER);
        hboxArtistName.setSpacing(10);

        HBox hboxAlbumName = new HBox(lblAlbumName, tfAlbumName);
        hboxAlbumName.setAlignment(Pos.CENTER);
        hboxAlbumName.setSpacing(10);

        HBox hboxAlbumCover = new HBox(lblAlbumCover, tfAlbumCover);
        hboxAlbumCover.setAlignment(Pos.CENTER);
        hboxAlbumCover.setSpacing(10);

        // HBox hboxAlbumName = new HBox(lblNameAlbum, tfNameAlbum);
        // hboxAlbumName.setAlignment(Pos.CENTER);
        // hboxAlbumName.setSpacing(10);

        VBox vboxCenterItems = new VBox(lblTitle,hboxArtistName, hboxAlbumName, hboxAlbumCover);
        vboxCenterItems.setAlignment(Pos.CENTER);
        vboxCenterItems.setSpacing(20);
        
        // BORDERPANE 

        BorderPane borderpane = new BorderPane();

        borderpane.setCenter(vboxCenterItems);
        BorderPane.setAlignment(vboxCenterItems, Pos.CENTER);

        borderpane.setTop(menuBar);

        borderpane.setBottom(btnAddAlbum);
        BorderPane.setAlignment(btnAddAlbum, Pos.CENTER);
        BorderPane.setMargin(btnAddAlbum, new Insets(10));

        Scene scene = new Scene(borderpane, 700, 500);
        addAlbumStage.setScene(scene);
        addAlbumStage.show();
        
    }

    public void processOfAddingAlbum(ActionEvent event){
        // try{
        //     int numOfAlbumsInCollection = 0;
        //     int integerAlbumsOfArtist = Integer.parseInt(tfArtistNumberOfAlbums.getText());
        //     dm.setArtist(tfNameArtist.getText(), integerAlbumsOfArtist, numOfAlbumsInCollection);
            
        // }catch(SQLException e){
        //     System.out.println("Problem Adding Artist");
        // }
    }


    public static void main(String[] args){
        Application.launch(args);
    }
    
}
