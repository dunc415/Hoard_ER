package Add;

import java.sql.SQLException;

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
import javafx.stage.Stage;
import javafx.util.Duration;

public class AddArtist extends Application{

    // Label
    private Label lblTitle;
    private Label lblArtistName;
    private Label lblNameArtist;
    private Label lblArtistNumberOfAlbums;

    // Textfield
    private TextField tfNameArtist;
    private TextField tfArtistNumberOfAlbums;

    // Button
    private Button btnAddArtist;
    private Button btnExit;

    // Hbox
    private HBox hboxTitle;
    private HBox hboxExit;

    private Stage stage;

    private GridPane grid = new GridPane();
    private DataManager dm = Main.dm;

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
            ViewArtist addArtist = new ViewArtist();
            addArtist.start(addArtistStage);
        });
        artistMenu.getItems().addAll(artistMenuItem_ViewArtists, new SeparatorMenuItem(), artistMenuItem_AddArtists);

        Menu albumMenu = new Menu("Albums");
        MenuItem albumMenuItem_ViewAlbums = new MenuItem("View Albums");
        MenuItem albumMenuItem_AddAlbum = new MenuItem("Add Album");
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
        hboxTitle.setPadding(new Insets(5, 5, 5, 80));
        grid.add(hboxTitle, 0, 1, 7, 1);


         /*
            Information section regarding the album.
            Labels, VBOXs, TextFields
        */

        lblArtistName = new Label("Artist Name");
        TextField tfArtistName = new TextField();
        tfArtistName.setPromptText("Name");

        VBox vboxArtistName = new VBox(lblArtistName, tfArtistName);
        grid.add(vboxArtistName, 1, 4);

        Label lblNumberOfAlbumInDiscography = new Label("Number of albums in discography");
        TextField tfNumberOfAlbumInDiscography = new TextField();
        tfNumberOfAlbumInDiscography.setPromptText("Number");

        VBox vboxNumberOfAlbumsInDiscography = new VBox(lblNumberOfAlbumInDiscography, tfNumberOfAlbumInDiscography);
        grid.add(vboxNumberOfAlbumsInDiscography, 1, 5);

        Label lblNumberOfAlbumInCollection = new Label("Number of albums in discography");
        TextField tfNumberOfAlbumInCollection = new TextField();
        tfNumberOfAlbumInCollection.setPromptText("Number");

        VBox vboxNumberOfAlbumsInCollection = new VBox(lblNumberOfAlbumInCollection, tfNumberOfAlbumInCollection);
        grid.add(vboxNumberOfAlbumsInCollection, 1, 6);
        
        /*
            Add Artist to db section.
            Button
        */

        Button btnAddArtist = new Button("Add to Collection");
        btnAddArtist.setPrefWidth(180);
        btnAddArtist.getStyleClass().add("custom-button");
        grid.add(btnAddArtist, 1, 7);
        GridPane.setHalignment(btnAddArtist, HPos.CENTER);
        btnAddArtist.setOnAction(this::addingArtist);

        /*
            Scene and Stage stuff
        */

        Scene scene = new Scene(grid, 700, 500);
        addArtistStage.setScene(scene);
        addArtistStage.show();
        
    }

    public void addingArtist(ActionEvent event){
        try{
            int numOfAlbumsInCollection = 0;
            int integerAlbumsOfArtist = Integer.parseInt(tfArtistNumberOfAlbums.getText());
            dm.setArtist(tfNameArtist.getText(), integerAlbumsOfArtist, numOfAlbumsInCollection);
            
        }catch(SQLException e){
            System.out.println("Problem Adding Artist");
        }
    }

    /**
     * This is the popup that you see if information is not inputted correctly and
     * also gives a confirmation message
     * @param message
     */
    public void popupActivation(String message) {
        Timeline timeline = new Timeline();

        Label lblMessage = new Label(message);
        lblMessage.setStyle("-fx-font-size: 12px;");
        HBox popup = new HBox(lblMessage);
        popup.setAlignment(Pos.CENTER);
        popup.getStyleClass().add("hbox-popup");
        popup.setAlignment(Pos.CENTER);
        popup.setVisible(false);
        grid.add(popup, 5, 8, 2, 1);
        KeyValue transparent = new KeyValue(popup.opacityProperty(), 0.0);
        KeyValue opaque = new KeyValue(popup.opacityProperty(), 1.0);

        popup.setVisible(true);
        KeyFrame startFadeIn = new KeyFrame(Duration.ZERO, transparent);
        KeyFrame endFadeIn = new KeyFrame(Duration.millis(500), opaque);
        KeyFrame startFadeOut = new KeyFrame(Duration.millis(5000), opaque);
        KeyFrame endFadeOut = new KeyFrame(Duration.millis(5500), transparent);

        timeline.getKeyFrames().addAll(startFadeIn, endFadeIn, startFadeOut, endFadeOut);

        timeline.setCycleCount(1);
        timeline.play();
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
