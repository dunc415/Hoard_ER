package Add;

import java.sql.SQLException;

import Main.Main;
import DataManager.DataManager;
import View.ViewArtist;


// public class AddAlbum extends Application{
//     DataManager dm = Main.dm;

//     Label lblTitle;
//     Label lblArtistName;
//     Label lblAlbumName;
//     Label lblAlbumCover;

//     Text txtAlbumCover;

//     TextField tfArtistName;
//     TextField tfAlbumName;
//     TextField tfAlbumCover;

//     Button btnAddAlbum;
//     Button btnAlbumCover;

//     FileChooser fileChooser = new FileChooser();
//     File cover;
//     ImageView imageView = new ImageView();;
    
//     Stage addAlbumStage;
    
//     @Override
//     public void start(Stage primaryStage) {
//         addAlbumStage = primaryStage;
//         addAlbumStage.setTitle("Add Album");
        

//         lblTitle = new Label("Add Album");
//         lblTitle.setAlignment(Pos.CENTER);
//         lblArtistName = new Label("Name of Artist");
//         lblArtistName.setAlignment(Pos.CENTER_LEFT);
//         lblAlbumName = new Label("Name of Album");
//         lblAlbumName.setAlignment(Pos.CENTER_LEFT);
//         lblAlbumCover = new Label("Album Cover");
//         lblAlbumCover.setAlignment(Pos.CENTER);

//         txtAlbumCover = new Text();
//         txtAlbumCover.setTextAlignment(TextAlignment.CENTER);

//         tfArtistName = new TextField();
//         tfArtistName.setPrefWidth(200);
//         tfAlbumName = new TextField();
//         tfAlbumName.setPrefWidth(200);

//         btnAlbumCover = new Button("Choose Album Cover");
//         btnAlbumCover.setAlignment(Pos.CENTER);
//         btnAlbumCover.setOnAction(this::processOfChoosingACover);

//         btnAddAlbum = new Button("Add");
//         btnAddAlbum.setAlignment(Pos.CENTER);
//         btnAddAlbum.setOnAction(this::processOfAddingAlbum);

//         // MENUBAR

        // SeparatorMenuItem separatorArtist = new SeparatorMenuItem();
        // SeparatorMenuItem separatorAlbum = new SeparatorMenuItem();

        // Menu artistMenu = new Menu("Artists");
        // MenuItem artistMenuItem_ViewArtists = new MenuItem("View Artists");
        // artistMenuItem_ViewArtists.setOnAction(ActionEvent -> {
        //     ViewArtist viewArtist = new ViewArtist();
        //     viewArtist.start(addAlbumStage);
        // });
        // MenuItem artistMenuItem_AddArtists  = new MenuItem("Add Artist");
        // artistMenuItem_AddArtists.setOnAction(ActionEvent -> {
        //     ViewArtist addArtist = new ViewArtist();
        //     addArtist.start(addAlbumStage);
        // });
        // artistMenu.getItems().add(artistMenuItem_ViewArtists);
        // artistMenu.getItems().add(separatorArtist);
        // artistMenu.getItems().add(artistMenuItem_AddArtists);

        // Menu albumMenu = new Menu("Albums");
        // MenuItem albumMenuItem_ViewAlbums = new MenuItem("View Albums");
        // MenuItem albumsMenuItem_AddAlbum = new MenuItem("Add Album");
        // albumMenu.getItems().add(albumMenuItem_ViewAlbums);
        // albumMenu.getItems().add(separatorAlbum);
        // albumMenu.getItems().add(albumsMenuItem_AddAlbum);

        // Menu wishlistMenu = new Menu("Wish List");
        
        // Menu favoriteAlbumMenu = new Menu("Favorite Album");

        // MenuBar menuBar = new MenuBar();
        // menuBar.getMenus().add(artistMenu);
        // menuBar.getMenus().add(albumMenu);
        // menuBar.getMenus().add(wishlistMenu);


//         // VBOX 
        
//         HBox hboxArtistName = new HBox(lblArtistName, tfArtistName);
//         hboxArtistName.setAlignment(Pos.CENTER);
//         hboxArtistName.setSpacing(10);

//         HBox hboxAlbumName = new HBox(lblAlbumName, tfAlbumName);
//         hboxAlbumName.setAlignment(Pos.CENTER);
//         hboxAlbumName.setSpacing(10);

//         HBox hboxAlbumCover = new HBox(lblAlbumCover, btnAlbumCover);
//         hboxAlbumCover.setAlignment(Pos.CENTER);
//         hboxAlbumCover.setSpacing(10);

//         // HBox hboxAlbumName = new HBox(lblNameAlbum, tfNameAlbum);
//         // hboxAlbumName.setAlignment(Pos.CENTER);
//         // hboxAlbumName.setSpacing(10);

//         VBox vboxImageInfo = new VBox(txtAlbumCover, imageView);
//         vboxImageInfo.setAlignment(Pos.CENTER);
//         vboxImageInfo.setSpacing(20);

//         VBox vboxCenterItems = new VBox(lblTitle,hboxArtistName, hboxAlbumName, hboxAlbumCover, vboxImageInfo);
//         vboxCenterItems.setAlignment(Pos.CENTER);
//         vboxCenterItems.setSpacing(20);
        
//         // BORDERPANE 

//         BorderPane borderpane = new BorderPane();

//         borderpane.setCenter(vboxCenterItems);
//         BorderPane.setAlignment(vboxCenterItems, Pos.CENTER);

//         borderpane.setTop(menuBar);

//         borderpane.setBottom(btnAddAlbum);
//         BorderPane.setAlignment(btnAddAlbum, Pos.CENTER);
//         BorderPane.setMargin(btnAddAlbum, new Insets(10));

//         Scene scene = new Scene(borderpane, 700, 500);
//         addAlbumStage.setScene(scene);
//         addAlbumStage.show();
        
//     }

//     public void processOfAddingAlbum(ActionEvent event){
//         try{

//             dm.setAlbum(tfArtistName.getText(), tfAlbumName.getText(), txtAlbumCover.getText());
            
//         }catch(SQLException e){
//             System.out.println("Problem Adding Album");
//         }
//     }

//     public void processOfChoosingACover(ActionEvent event){
//         try{
//             cover = fileChooser.showOpenDialog(addAlbumStage);
//             txtAlbumCover.setText(cover.getPath());

//             InputStream is = new FileInputStream(cover);
//             Image image = new Image(is);
//             imageView.setImage(image);
//             imageView.setFitHeight(50);
//             imageView.setFitWidth(50);

            
//         } catch(NullPointerException e) {
//             System.out.println(e.getMessage());
//         } catch (FileNotFoundException e) {
//             System.out.println(e.getMessage());
//         }
        

//     }


//     public static void main(String[] args){
//         Application.launch(args);
//     }
    
// }

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
        addAlbumStage.initStyle(StageStyle.UNDECORATED);
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
        MenuItem albumMenuItem_AddAlbum = new MenuItem("Add Album");
        MenuItem albumMenuItem_FavoriteAlbums = new MenuItem("Favorite Albums");
        albumMenu.getItems().addAll(albumMenuItem_ViewAlbums, new SeparatorMenuItem(), albumMenuItem_AddAlbum, new SeparatorMenuItem(), albumMenuItem_FavoriteAlbums);

        Menu wishlistMenu = new Menu("Wish List");
        
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(artistMenu);
        menuBar.getMenus().add(albumMenu);
        menuBar.getMenus().add(wishlistMenu);


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

        Label lblTitle = new Label("Add Artist");
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

        Label lblArtistName = new Label("Artist Name");
        tfArtistName = new TextField();
        tfArtistName.setPromptText("Name");

        VBox vboxArtistName = new VBox(lblArtistName, tfArtistName);
        grid.add(vboxArtistName, 1, 4);

        Label lblAlbumName = new Label("Album Name");
        tfAlbumName = new TextField();
        tfAlbumName.setPromptText("Name");

        VBox vboxAlbumName = new VBox(lblAlbumName, tfAlbumName);
        grid.add(vboxAlbumName, 1, 5);

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

        VBox vboxAlbumCover = new VBox(lblAlbumCover, hboxAlbumCover);
        vboxAlbumCover.setSpacing(10);
        grid.add(vboxAlbumCover, 1, 6);

        txtCoverText = new Text("No Album Cover Chosen");

        vboxCover = new VBox();

        btnOpenFileChooser.setOnAction(this::fileChooserAction);

        vboxCover.getChildren().add(txtCoverText);
        vboxCover.setSpacing(10);
        vboxCover.setPrefSize(150, 150);
        vboxCover.setAlignment(Pos.CENTER);
        grid.add(vboxCover, 3, 4, 3, 3);

        Button btnAddAlbum = new Button("Add to Collection");
        btnAddAlbum.setPrefWidth(180);
        btnAddAlbum.getStyleClass().add("custom-button");
        grid.add(btnAddAlbum, 1, 7);
        GridPane.setHalignment(btnAddAlbum, HPos.CENTER);
        btnAddAlbum.setOnAction(this::addingAlbums);

        Scene scene = new Scene(grid, 650, 450);
        scene.getStylesheets().add("styles/AddAlbumStyle.css");
        addAlbumStage.setScene(scene);
        addAlbumStage.show();
    }

    public void addingAlbums(ActionEvent event) {

       

        try{
            if(cboxAlbumCover.isSelected() && albumCoverPath != null) {
                dm.setAlbum(tfArtistName.getText(), tfAlbumName.getText(), albumCoverPath);
            } else if(!cboxAlbumCover.isSelected() || albumCoverPath == null) {
                dm.setAlbum(tfArtistName.getText(), tfAlbumName.getText(), defaultAlbumCoverPath);
            }            
        }catch(SQLException e){
            System.out.println("Problem Adding Album");
        }

    }

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

    // Creating the rows and columns for the GridPane
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
