package Add;

import java.sql.SQLException;

import Main.Main;
import DataManager.DataManager;
import View.ViewAlbum;
import View.ViewArtist;
import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddAlbum extends Application {

    private InputStream is = null;
    private File albumCoverFile;
    private String defaultAlbumCoverPath = "No Album Cover Chosen";
    private String albumCoverPath;
    private CheckBox cboxAlbumCover;
    private TextField tfArtistName;
    private TextField tfAlbumName;
    private FileChooser fileChooser;
    private Text txtCoverText;
    private VBox vboxCover;
    private Stage stage;

    private GridPane grid = new GridPane();
    private DataManager dm = Main.dm;

    public void start(Stage addAlbumStage) {
        stage  = addAlbumStage;
        addAlbumStage.setResizable(false);

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
            viewArtist.start(addAlbumStage);
        });
        MenuItem artistMenuItem_AddArtists  = new MenuItem("Add Artist");
        artistMenuItem_AddArtists.setOnAction(ActionEvent -> {
            ViewArtist addArtist = new ViewArtist();
            addArtist.start(addAlbumStage);
        });
        artistMenu.getItems().addAll(artistMenuItem_ViewArtists, new SeparatorMenuItem(), artistMenuItem_AddArtists);

        Menu albumMenu = new Menu("Albums");
        MenuItem albumMenuItem_ViewAlbums = new MenuItem("View Albums");
        albumMenuItem_ViewAlbums.setOnAction(ActionEvent -> {
            ViewAlbum viewAlbum = new ViewAlbum();
            viewAlbum.start(addAlbumStage);
        });
        MenuItem albumMenuItem_AddAlbum = new MenuItem("Add Album");
        albumMenuItem_AddAlbum.setOnAction(ActionEvent -> {
            AddAlbum addAlbum = new AddAlbum();
            addAlbum.start(addAlbumStage);
        });
        MenuItem albumMenuItem_FavoriteAlbums = new MenuItem("Favorite Albums");
        albumMenu.getItems().addAll(albumMenuItem_ViewAlbums, new SeparatorMenuItem(), albumMenuItem_AddAlbum, new SeparatorMenuItem(), albumMenuItem_FavoriteAlbums);

        Menu wishlistMenu = new Menu("Wish List");
        
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(artistMenu, albumMenu, wishlistMenu);


        /*
            Exit section of the screen
        */
        Button btnExit = new Button();
        btnExit.getStyleClass().add("exit-button");
        btnExit.setPrefSize(25, 25);
        btnExit.setAlignment(Pos.CENTER_RIGHT);
        btnExit.setOnAction(ActionEvent -> {
            Platform.exit();
        });

        Pane filler = new Pane();
        HBox.setHgrow(filler, Priority.ALWAYS);

        HBox hboxExit = new HBox(menuBar, filler, btnExit);
        hboxExit.setStyle("-fx-background-color: #22333B");
        grid.add(hboxExit, 0, 0, 7, 1);

        /*
            Title section.
            Labels, HBOXs, DropShadow
        */

        Label lblTitle = new Label("Add Album");
        lblTitle.getStyleClass().add("title-font");
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetY(3.0f);
        dropShadow.setOffsetX(3.0f);
        dropShadow.setColor(Color.web("#0A0908"));
        lblTitle.setEffect(dropShadow);

        HBox hboxTitle = new HBox(lblTitle);
        hboxTitle.setStyle("-fx-background-color: #22333B");
        hboxTitle.setAlignment(Pos.CENTER_LEFT);
        hboxTitle.setPadding(new Insets(5, 5, 5, 80));
        grid.add(hboxTitle, 0, 1, 7, 1);

        /*
            Information section regarding the album.
            Labels, VBOXs, TextFields
        */

        Label lblArtistName = new Label("Artist Name*");
        tfArtistName = new TextField();
        tfArtistName.setPromptText("Name");

        VBox vboxArtistName = new VBox(lblArtistName, tfArtistName);
        grid.add(vboxArtistName, 1, 4);

        Label lblAlbumName = new Label("Album Name*");
        tfAlbumName = new TextField();
        tfAlbumName.setPromptText("Name");

        VBox vboxAlbumName = new VBox(lblAlbumName, tfAlbumName);
        grid.add(vboxAlbumName, 1, 5);

        /*
            Album Cover Section.
            CheckBox, Button, HBOXs, VBOXs, Listener
        */

        Label lblAlbumCover = new Label("Choose an Album Cover?");

        cboxAlbumCover = new CheckBox();
        fileChooser = new FileChooser();
        Button btnOpenFileChooser = new Button("File Explorer");
        btnOpenFileChooser.getStyleClass().add("file-explorer-button");
        btnOpenFileChooser.setDisable(true);
        btnOpenFileChooser.setPrefWidth(100);
        cboxAlbumCover.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1) {
                    btnOpenFileChooser.setDisable(false);
                } else {
                    btnOpenFileChooser.setDisable(true);
                }
            }
        });

        HBox hboxAlbumCover = new HBox(cboxAlbumCover, btnOpenFileChooser);
        hboxAlbumCover.setAlignment(Pos.CENTER);
        hboxAlbumCover.setSpacing(15);

        VBox vboxAlbumCoverInfo = new VBox(lblAlbumCover, hboxAlbumCover);
        vboxAlbumCoverInfo.setSpacing(10);
        grid.add(vboxAlbumCoverInfo, 1, 6);

        txtCoverText = new Text("No Album Cover Chosen");

        vboxCover = new VBox();

        btnOpenFileChooser.setOnAction(this::fileChooserAction);

        vboxCover.getChildren().add(txtCoverText);
        vboxCover.setSpacing(10);
        vboxCover.setPrefSize(150, 150);
        vboxCover.setAlignment(Pos.CENTER);
        grid.add(vboxCover, 3, 4, 3, 3);

        /*
            Add Album to db section.
            Button
        */

        Button btnAddAlbum = new Button("Add to Collection");
        btnAddAlbum.setPrefWidth(180);
        btnAddAlbum.getStyleClass().add("custom-button");
        grid.add(btnAddAlbum, 1, 7);
        GridPane.setHalignment(btnAddAlbum, HPos.CENTER);
        btnAddAlbum.setOnAction(this::addingAlbums);

        /*
            Scene and Stage stuff
        */
        
        Scene scene = new Scene(grid, 650, 450);
        scene.getStylesheets().add("styles/AddAlbumStyle.css");
        addAlbumStage.setScene(scene);
        addAlbumStage.centerOnScreen();
        addAlbumStage.show();
    }

    /**
     * This involves the process of adding an album to the collection.
     * @param event
     */
    public void addingAlbums(ActionEvent event) {

        try{
            if(!tfArtistName.getText().equals("") && !tfAlbumName.getText().equals("")) {
                if(cboxAlbumCover.isSelected() && albumCoverPath != null) {
                    dm.setAlbum(tfArtistName.getText(), tfAlbumName.getText(), albumCoverPath);
                    popupActivation("Album Added to Collection");
                } else if(!cboxAlbumCover.isSelected() || albumCoverPath == null) {
                    dm.setAlbum(tfArtistName.getText(), tfAlbumName.getText(), defaultAlbumCoverPath);
                    popupActivation("Album Added to Collection (No Cover Art)");
                }  
            } else {
                popupActivation("Enter Artist Name and Album Name");
            }
                     
        }catch(SQLException e){
            System.out.println("Problem Adding Album");
        } catch(NullPointerException ex) {
            System.out.println("Not Connected to a Collection | Need to be connected");
        }

    }

    /**
     * This is the action of picking an album cover.
     * @param event
     */
    public void fileChooserAction(ActionEvent event) {
        try {
            albumCoverFile = fileChooser.showOpenDialog(stage);
            albumCoverPath = albumCoverFile.getPath();
            if (albumCoverFile != null) {
                try {
                    is = new FileInputStream(albumCoverFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Image image = new Image(is);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(150);
                imageView.setFitHeight(150);

                vboxCover.getChildren().clear();
                vboxCover.getChildren().add(imageView);

                // Putting an item before another item in a vbox
//                int indexVboxCover = vboxCover.getChildren().indexOf(txtCoverText);
//                vboxCover.getChildren().add(indexVboxCover, imageView);
            }
        } catch(NullPointerException ex) {
            // No need to log anything here.
            // Exception is fine to have.
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

    public static void main (String[] args) {
        launch(args);
    }
}
