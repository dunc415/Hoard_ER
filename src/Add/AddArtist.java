package Add;

import java.sql.SQLException;

import API.GoogleSearchAPI;
import DataManager.ArtistDM;
import Methods.SharedMethods;
import View.ViewAlbum;
import View.ViewArtist;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AddArtist extends Application{

    // Label
    private Label lblTitle;
    private Label lblArtistName;
    private Label lblNumberOfAlbumInDiscography;
    private Label lblNumberOfAlbumInCollection;

    // Textfield
    private TextField tfArtistName;
    private TextField tfNumberOfAlbumInDiscography;
    private TextField tfNumberOfAlbumInCollection;

    // Button
    private Button btnAddArtist;
    private Button btnExit;
    

    // Hbox
    private HBox hboxTitle;
    private HBox hboxExit;
    private HBox hboxAlbumNumber;

    private VBox vboxArtistName;
    private VBox vboxNumberOfAlbumsInDiscography;
    private VBox vboxNumberOfAlbumsInCollection;

    private Stage stage;

    private GridPane grid = new GridPane();
    private ArtistDM dm = new ArtistDM();
    private GoogleSearchAPI googleSearchAPI = new GoogleSearchAPI();
    private SharedMethods sharedMethods = new SharedMethods();

    @Override
    public void start(Stage addArtistStage) {
        stage  = addArtistStage;
        addArtistStage.setResizable(false);

        grid = new GridPane();
        grid.setGridLinesVisible(false);

        sharedMethods.createRowsColumnsForGridPane(grid, 9, 7);

        /*
            MenuBar stuff
        */

        Menu artistMenu = new Menu("Artists");
        MenuItem artistMenuItem_ViewArtists = new MenuItem("View Artists");
        artistMenuItem_ViewArtists.setOnAction(ActionEvent -> {
            ViewArtist viewArtist = new ViewArtist();
            viewArtist.start(addArtistStage);
        });
        MenuItem artistMenuItem_AddArtists  = new MenuItem("Add Artist");
        artistMenuItem_AddArtists.setOnAction(ActionEvent -> {
            AddArtist addArtist = new AddArtist();
            addArtist.start(addArtistStage);
        });
        artistMenu.getItems().addAll(artistMenuItem_ViewArtists, new SeparatorMenuItem(), artistMenuItem_AddArtists);

        Menu albumMenu = new Menu("Albums");
        MenuItem albumMenuItem_ViewAlbums = new MenuItem("View Albums");
        albumMenuItem_ViewAlbums.setOnAction(ActionEvent -> {
            ViewAlbum viewAlbum = new ViewAlbum();
            viewAlbum.start(addArtistStage);
        });
        MenuItem albumMenuItem_AddAlbum = new MenuItem("Add Album");
        albumMenuItem_AddAlbum.setOnAction(ActionEvent -> {
            AddAlbum addAlbum = new AddAlbum();
            addAlbum.start(addArtistStage);
        });
        MenuItem albumMenuItem_FavoriteAlbums = new MenuItem("Favorite Albums");
        albumMenu.getItems().addAll(albumMenuItem_ViewAlbums, new SeparatorMenuItem(), albumMenuItem_AddAlbum, new SeparatorMenuItem(), albumMenuItem_FavoriteAlbums);

        Menu wishlistMenu = new Menu("Wish List");
        
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(artistMenu, albumMenu, wishlistMenu);

        /*
            Exit section of the screen
        */
        btnExit = new Button();
        btnExit.getStyleClass().add("exit-button");
        btnExit.setPrefSize(25, 25);
        btnExit.setAlignment(Pos.CENTER_RIGHT);
        btnExit.setOnAction(ActionEvent -> {
            Platform.exit();
        });

        Pane filler = new Pane();
        HBox.setHgrow(filler, Priority.ALWAYS);

        hboxExit = new HBox(menuBar, filler, btnExit);
        hboxExit.setStyle("-fx-background-color: #22333B");
        grid.add(hboxExit, 0, 0, 7, 1);

        /*
            Title section.
            Labels, HBOXs, DropShadow
        */

        lblTitle = new Label("Add Artist");
        lblTitle.getStyleClass().add("title-font");
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetY(3.0f);
        dropShadow.setOffsetX(3.0f);
        dropShadow.setColor(Color.web("#0A0908"));
        lblTitle.setEffect(dropShadow);

        hboxTitle = new HBox(lblTitle);
        hboxTitle.setStyle("-fx-background-color: #22333B");
        hboxTitle.setAlignment(Pos.CENTER_LEFT);
        hboxTitle.setPadding(new Insets(0, 0, 0, 80));
        grid.add(hboxTitle, 0, 1, 7, 1);


         /*
            Information section regarding the album.
            Labels, VBOXs, TextFields
        */

        // NEED TO CHANGE THE FORMAT OF THIS PAGE
        lblArtistName = new Label("Artist Name*");
        tfArtistName = new TextField();
        tfArtistName.setMaxWidth(150);
        tfArtistName.setPromptText("Name");

        vboxArtistName = new VBox(lblArtistName, tfArtistName);
        vboxArtistName.setSpacing(5);
        grid.add(vboxArtistName, 1, 4);

        // lblNumberOfAlbumInDiscography = new Label("# Album's in Discography");
        // tfNumberOfAlbumInDiscography = new TextField();
        // tfNumberOfAlbumInDiscography.getStyleClass().add("number-text-field");
        // tfNumberOfAlbumInDiscography.setMaxWidth(35);
        // tfNumberOfAlbumInDiscography.setPromptText("#");

        // vboxNumberOfAlbumsInDiscography = new VBox(lblNumberOfAlbumInDiscography, tfNumberOfAlbumInDiscography);
        // vboxNumberOfAlbumsInDiscography.setSpacing(5);
        // vboxNumberOfAlbumsInDiscography.setAlignment(Pos.CENTER);

        // lblNumberOfAlbumInCollection = new Label("# Album's in Collection");
        // tfNumberOfAlbumInCollection = new TextField();
        // tfNumberOfAlbumInCollection.getStyleClass().add("number-text-field");
        // tfNumberOfAlbumInCollection.setMaxWidth(35);
        // tfNumberOfAlbumInCollection.setPromptText("#");

        // vboxNumberOfAlbumsInCollection = new VBox(lblNumberOfAlbumInCollection, tfNumberOfAlbumInCollection);
        // vboxNumberOfAlbumsInCollection.setSpacing(5);
        // vboxNumberOfAlbumsInCollection.setAlignment(Pos.CENTER);

        // hboxAlbumNumber = new HBox(vboxNumberOfAlbumsInDiscography, vboxNumberOfAlbumsInCollection);
        // hboxAlbumNumber.setSpacing(40);
        // grid.add(hboxAlbumNumber, 1, 5, 2, 1);
        
        /*
            Add Artist to db section.
            Button
        */

        btnAddArtist = new Button("Add to Collection");
        btnAddArtist.setPrefWidth(180);
        btnAddArtist.getStyleClass().add("custom-button");
        grid.add(btnAddArtist, 1, 7);
        GridPane.setHalignment(btnAddArtist, HPos.CENTER);
        btnAddArtist.setOnAction(this::addingArtist);

        /*
            Scene and Stage stuff
        */

        Scene scene = new Scene(grid, 700, 500);
        scene.getStylesheets().add("styles/AddArtistStyle.css");
        addArtistStage.setScene(scene);
        addArtistStage.centerOnScreen();
        addArtistStage.show();
        
    }

    public void addingArtist(ActionEvent event){

        try{
            if(!tfArtistName.getText().equals("")) {
                dm.setArtist(tfArtistName.getText(), googleSearchAPI.findNumberOfAlbums(tfArtistName.getText()), 0);
                sharedMethods.popupActivation("Artist Added", stage);
            } else {
                sharedMethods.popupActivation("Enter All Information that has an Asterisk (*)", stage);
            }
            
        }catch(SQLException e){
            System.out.println("Problem Adding Artist" + e.getMessage());
        } catch(NumberFormatException ex) {
            System.out.println("Problem Adding Artist" + ex.getMessage());
        }
    }


    public static void main(String[] args){
        Application.launch(args);
    }
    
}
