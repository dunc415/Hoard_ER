package Add;

import DataManager.AlbumDM;
import Methods.AlbumMethods;
import Methods.SharedMethods;
import View.ViewAlbum;
import View.ViewArtist;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class allows the user to add an album to their collection.
 */
public class AddAlbum extends Application {

    private Text txtCoverText;
    private Label lblTitle, lblArtistName, lblAlbumName, lblAlbumCover, lblAudioFormat;

    private VBox vboxCover, vboxArtistName, vboxAlbumName, vboxAlbumCover_TitleInputs, vboxAudioFormat_InfoInputs, vboxRadioButton;
    private HBox hboxExit, hboxTitle, hboxAlbumCoverInputs;

    private RadioButton radioButtonCD, radioButtonVinyl, radioButtonCassette;
    private TextField tfArtistName, tfAlbumName;
    private CheckBox cboxAlbumCover;
    private Button btnExit, btnOpenFileChooser, btnAddAlbum;

    private Stage stage;
    private Scene scene;

    private SharedMethods sharedMethods = new SharedMethods();
    private GridPane grid = new GridPane();
    private AlbumDM dm = new AlbumDM();
    private AlbumMethods albumMethods = new AlbumMethods();

    public void start(Stage addAlbumStage) {
        stage  = addAlbumStage;
        addAlbumStage.setResizable(false);

        grid = new GridPane();
        grid.setGridLinesVisible(false);

        sharedMethods.createRowsColumnsForGridPane(grid, 11, 7);

        /* MenuBar Section */

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

        /* Exit Section */

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

        /* Title section. */

        lblTitle = new Label("Add Album");
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

        /* Information section regarding the album */

        lblArtistName = new Label("Artist Name*");
        tfArtistName = new TextField();
        tfArtistName.setPromptText("Name");

        vboxArtistName = new VBox(lblArtistName, tfArtistName);
        grid.add(vboxArtistName, 1, 4);

        lblAlbumName = new Label("Album Name*");
        tfAlbumName = new TextField();
        tfAlbumName.setPromptText("Name");

        vboxAlbumName = new VBox(lblAlbumName, tfAlbumName);
        grid.add(vboxAlbumName, 1, 5);

        /* Album Cover Section */

        lblAlbumCover = new Label("Choose an Album Cover?");

        cboxAlbumCover = new CheckBox();
        btnOpenFileChooser = new Button("File Explorer");
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

        hboxAlbumCoverInputs = new HBox(cboxAlbumCover, btnOpenFileChooser);
        hboxAlbumCoverInputs.setAlignment(Pos.CENTER);
        hboxAlbumCoverInputs.setSpacing(15);

        vboxAlbumCover_TitleInputs = new VBox(lblAlbumCover, hboxAlbumCoverInputs);
        vboxAlbumCover_TitleInputs.setSpacing(10);
        grid.add(vboxAlbumCover_TitleInputs, 1, 6);

        txtCoverText = new Text("No Album Cover Chosen");

        vboxCover = new VBox();

        btnOpenFileChooser.setOnAction(ActionEvent -> {
            albumMethods.fileChooserAction(vboxCover, stage);
        });

        vboxCover.getChildren().add(txtCoverText);
        vboxCover.setSpacing(10);
        vboxCover.setPrefSize(150, 150);
        vboxCover.setAlignment(Pos.CENTER);
        grid.add(vboxCover, 2, 4, 4, 4);

        /* Format Section */

        String[] radioButtonAudioFormatLabels = {"CD", "Vinyl", "Cassette"};

        vboxAudioFormat_InfoInputs = new VBox();

        lblAudioFormat = new Label("Audio Format*");

        radioButtonCD = new RadioButton();
        radioButtonVinyl = new RadioButton();
        radioButtonCassette  = new RadioButton();

        RadioButton[] radioButtonArray = {radioButtonCD, radioButtonVinyl, radioButtonCassette};

        vboxRadioButton = new VBox();
        vboxRadioButton.setSpacing(5);

        for(int i = 0; i < radioButtonAudioFormatLabels.length; i++) {
            radioButtonArray[i].setText(radioButtonAudioFormatLabels[i]);
            vboxRadioButton.getChildren().add(radioButtonArray[i]);
        }

        vboxAudioFormat_InfoInputs.getChildren().addAll(lblAudioFormat, vboxRadioButton);
        vboxAudioFormat_InfoInputs.setSpacing(5);
        grid.add(vboxAudioFormat_InfoInputs, 1, 7);

        /* Add Album Section */

        btnAddAlbum = new Button("Add to Collection");
        btnAddAlbum.setPrefWidth(180);
        btnAddAlbum.getStyleClass().add("custom-button");
        grid.add(btnAddAlbum, 1, 9);
        GridPane.setHalignment(btnAddAlbum, HPos.CENTER);
        btnAddAlbum.setOnAction(ActionEvent -> {
            albumMethods.addingAlbums(stage, radioButtonArray, tfAlbumName, tfArtistName, cboxAlbumCover);
        });

        /* Scene and Stage Section */

        scene = new Scene(grid, 650, 500);
        scene.getStylesheets().add("styles/AddAlbumStyle.css");
        addAlbumStage.setScene(scene);
        addAlbumStage.centerOnScreen();
        addAlbumStage.show();
    }

    public AlbumDM getClassDataManager() { return dm; }

    public static void main (String[] args) {
        launch(args);
    }
}
