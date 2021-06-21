package Add;

import java.sql.SQLException;

import API.GoogleSearchAPI;
import DataManager.DataManager;
import Main.Main;
import View.ViewAlbum;
import View.ViewArtist;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

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
    private DataManager dm = Main.dm;
    private GoogleSearchAPI googleSearchAPI = new GoogleSearchAPI();

    @Override
    public void start(Stage addArtistStage) {
        stage  = addArtistStage;
        addArtistStage.setResizable(false);

        grid = new GridPane();
        grid.setGridLinesVisible(false);

        createRowsColumnsForGridPane();

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
                popupActivation("Artist Added");
            } else {
                popupActivation("Enter All Information that has an Asterisk (*)");
            }
            
        }catch(SQLException e){
            System.out.println("Problem Adding Artist" + e.getMessage());
        } catch(NumberFormatException ex) {
            System.out.println("Problem Adding Artist" + ex.getMessage());
        }
    }

    /**
     * This is the popup that you see if information is not inputted correctly and
     * also gives a confirmation message
     * @param message
     */
    public void popupActivation(String message) {
        Timeline timeline = new Timeline();

        double popupWidth = 250;
        double popupHeight = 50;

        Rectangle2D rectangle2D = findPopupPosition(Screen.getPrimary().getVisualBounds(), popupWidth, popupHeight);


        Stage messageStage = new Stage();
        messageStage.setAlwaysOnTop(true);
        messageStage.setX(rectangle2D.getWidth());
        messageStage.setY(rectangle2D.getHeight());
        messageStage.initStyle(StageStyle.UNDECORATED);

        Label lblMessage = new Label(message);
        lblMessage.setWrapText(true);
        lblMessage.setTextAlignment(TextAlignment.CENTER);

        HBox popup = new HBox(lblMessage);
        popup.setAlignment(Pos.CENTER);
        popup.setSpacing(10);
        popup.setVisible(false);

        Scene messageScene = new Scene(popup, 250, 50);
        messageScene.getStylesheets().add("styles/AddAlbumStyle.css");
        messageStage.setScene(messageScene);
        messageStage.show();

        KeyValue transparent = new KeyValue(messageStage.opacityProperty(), 0.0);
        KeyValue opaque = new KeyValue(messageStage.opacityProperty(), 1.0);

        popup.setVisible(true);
        KeyFrame startFadeIn = new KeyFrame(Duration.ZERO, transparent);
        KeyFrame endFadeIn = new KeyFrame(Duration.millis(500), opaque);
        KeyFrame startFadeOut = new KeyFrame(Duration.millis(5000), opaque);
        KeyFrame endFadeOut = new KeyFrame(Duration.millis(5500), transparent);

        timeline.getKeyFrames().addAll(startFadeIn, endFadeIn, startFadeOut, endFadeOut);

        timeline.setCycleCount(1);
        timeline.play();

        timeline.setOnFinished(ActionEvent -> {
            messageStage.close();
        });
    }

    /**
     * Find the correct position for the error/added message.
     * @param rectangle2D
     * @param popupWidth
     * @param popupHeight
     * @return newRectangle2D - Best way to return two doubles
     */

    public Rectangle2D findPopupPosition(Rectangle2D rectangle2D, double popupWidth, double popupHeight) {
        double mainStageWidth = stage.getWidth();
        double mainStageHeight = stage.getHeight();
        double mainStageStartingX = stage.getX();
        double mainStageStartingY = stage.getY();

        double mainStageEndingX = mainStageStartingX + mainStageWidth;
        double mainStageEndingY = mainStageStartingY + mainStageHeight;

        double positionOfPopupX = mainStageEndingX - popupWidth;
        double positionOfPopupY = mainStageEndingY - popupHeight;

        Rectangle2D newRectangle2D = new Rectangle2D(positionOfPopupX, positionOfPopupY, positionOfPopupX, positionOfPopupY);

        return newRectangle2D;
    }

    /**
     * Creating the rows and columns for the GridPane
     */
	public void createRowsColumnsForGridPane() {
		for(int i = 0; i < 9; i++) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.ALWAYS);
            grid.getRowConstraints().add(row);
        }
        for(int i = 0; i < 7; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.ALWAYS);
            grid.getColumnConstraints().add(col);
        }
	}

    public static void main(String[] args){
        Application.launch(args);
    }
    
}
