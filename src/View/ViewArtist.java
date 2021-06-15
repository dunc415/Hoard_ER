package View;

import java.util.ArrayList;

import Add.AddAlbum;
import Add.AddArtist;
import DataManager.DataManager;
import Objects.Artist;
import Main.Main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

public class ViewArtist extends Application {

    // Label
    private Label placeHolder;
    private Label lblTitle;

    // Button
    private Button btnExit;
    private Button btnDelete;
    private Button btnDone;
    private Button btnEdit;

    // Textfield
    private TextField tfSearchBar;

    // Hbox
    private HBox hboxExit;
    private HBox hboxTitle;
    private HBox hboxBottomRow;

    // TableView
    private TableView<Artist> tableView = new TableView<>();
    private ObservableList<Artist> list = FXCollections.observableArrayList();

    // IMPORTANT THINGS
    private GridPane grid = new GridPane();
    private DataManager dm = Main.dm;

    // Pane
    private Pane fillerBottomRow;

    public void start(Stage viewArtistStage) {
        viewArtistStage.setResizable(false);

        grid.setGridLinesVisible(false);

        createRowsColumnsForGridPane();

        /*
            TableView Section
        */

        tableView = new TableView();
        tableView.setPlaceholder(new Label("No Artist in Collection"));

        TableColumn<Artist, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> {
            Artist rowData = event.getRowValue();
            TablePosition cellPosition = event.getTablePosition();
            String newValueInputted = event.getNewValue();
            dm.updateArtistInfo(rowData, cellPosition, newValueInputted);
        });

        TableColumn<Artist, Integer> numberOfAlbumsInDiscographyColumn = new TableColumn<>("Discography (Albums)");
        numberOfAlbumsInDiscographyColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfAlbums"));
        numberOfAlbumsInDiscographyColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        numberOfAlbumsInDiscographyColumn.setOnEditCommit(event -> {
            Artist rowData = event.getRowValue();
            TablePosition cellPosition = event.getTablePosition();
            int newValueInt = event.getNewValue();
            String newValueString = newValueInt + "";
            dm.updateArtistInfo(rowData, cellPosition, newValueString);
        });

        TableColumn<Artist, String> numberOfAlbumsInCollectionColumn = new TableColumn<>("Collection (Albums)");
        numberOfAlbumsInCollectionColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfAlbumsInCollection"));

        tableView.getColumns().addAll(nameColumn, numberOfAlbumsInDiscographyColumn, numberOfAlbumsInCollectionColumn);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        insertIntoTable();


        placeHolder = new Label("No Items");
        placeHolder.setStyle("-fx-text-fill: #EAE0D5;");
        tableView.setPlaceholder(placeHolder);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        grid.add(tableView, 0, 3, 7 ,5);

        /*
            MenuBar stuff
        */

        Menu artistMenu = new Menu("Artists");
        MenuItem artistMenuItem_ViewArtists = new MenuItem("View Artists");
        artistMenuItem_ViewArtists.setOnAction(ActionEvent -> {
            ViewArtist viewArtist = new ViewArtist();
            viewArtist.start(viewArtistStage);
        });
        MenuItem artistMenuItem_AddArtists  = new MenuItem("Add Artist");
        artistMenuItem_AddArtists.setOnAction(ActionEvent -> {
            AddArtist addArtist = new AddArtist();
            addArtist.start(viewArtistStage);
        });
        artistMenu.getItems().addAll(artistMenuItem_ViewArtists, new SeparatorMenuItem(), artistMenuItem_AddArtists);

        Menu albumMenu = new Menu("Albums");
        MenuItem albumMenuItem_ViewAlbums = new MenuItem("View Albums");
        albumMenuItem_ViewAlbums.setOnAction(ActionEvent -> {
            ViewAlbum viewAlbums = new ViewAlbum();
            viewAlbums.start(viewArtistStage);
        });
        MenuItem albumMenuItem_AddAlbum = new MenuItem("Add Album");
        albumMenuItem_AddAlbum.setOnAction(ActionEvent -> {
            AddAlbum addAlbum = new AddAlbum();
            addAlbum.start(viewArtistStage);
        });
        MenuItem albumMenuItem_FavoriteAlbums = new MenuItem("Favorite Albums");
        albumMenu.getItems().addAll(albumMenuItem_ViewAlbums, new SeparatorMenuItem(), albumMenuItem_AddAlbum, new SeparatorMenuItem(), albumMenuItem_FavoriteAlbums);

        Menu wishlistMenu = new Menu("Wish List");
        
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(artistMenu, albumMenu, wishlistMenu);

        /*
            The exit section (Top part of the page)
            Button, SetOnAction
        */ 

        btnExit = new Button();
        btnExit.getStyleClass().add("exit-button");
        btnExit.setPrefSize(25, 25);
        btnExit.setAlignment(Pos.CENTER_RIGHT);
        btnExit.setOnAction(ActionEvent -> {
            Platform.exit();
        });

        Pane fillerExit = new Pane();
        HBox.setHgrow(fillerExit, Priority.ALWAYS);

        hboxExit = new HBox(menuBar, fillerExit, btnExit);
        hboxExit.setStyle("-fx-background-color: #22333B");
        grid.add(hboxExit, 0, 0, 7, 1);


        /*
           Bottom Section (Delete Button, Edit Button, Done Button)
        */

        grid.getRowConstraints().get(8).setMinHeight(45);

        fillerBottomRow = new Pane();
        HBox.setHgrow(fillerBottomRow, Priority.ALWAYS);

        btnDelete = new Button("Delete Row");
        btnDelete.getStyleClass().add("custom-button");

        btnDelete.setOnAction(ActionEvent -> {
            Artist obj = tableView.getSelectionModel().getSelectedItem();
            list.remove(obj);
        });

        btnDone = new Button("Done");
        btnDone.setVisible(false);
        btnDone.getStyleClass().add("custom-button");
        btnDone.setOnAction(ActionEvent -> {
            tableView.setEditable(false);
            btnDone.setVisible(false);
        });

        btnEdit = new Button("Edit Album Name");
        btnEdit.getStyleClass().add("custom-button");
        btnEdit.setOnAction(ActionEvent -> {
            tableView.setEditable(true);
            btnDone.setVisible(true);
        });

        hboxBottomRow = new HBox(btnEdit, btnDone, fillerBottomRow, btnDelete);
        hboxBottomRow.setAlignment(Pos.CENTER);
        hboxBottomRow.setSpacing(15);
        hboxBottomRow.setPadding(new Insets(0, 62, 0, 30));
        grid.add(hboxBottomRow, 0, 8, 7 ,1);
        

         /*
            Search Bar Section
            FilteredList, TextField, Listener
        */

        FilteredList<Artist> flObject = new FilteredList(list, p -> true);
        tableView.setItems(flObject);

        tfSearchBar = new TextField();
        tfSearchBar.getStyleClass().add("search-bar");
        tfSearchBar.setPromptText("Search");
        tfSearchBar.setPrefWidth(130);
        tfSearchBar.textProperty().addListener((observableValue, s, t1) -> {
            flObject.setPredicate(p -> p.getName().toLowerCase().contains(t1.toLowerCase().trim()));
        });

        /*
            Title Section
            Label, DropShadow
        */

        lblTitle = new Label("View Artist");
        lblTitle.getStyleClass().add("title-font");
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetY(3.0f);
        dropShadow.setOffsetX(3.0f);
        dropShadow.setColor(Color.web("#0A0908"));
        lblTitle.setEffect(dropShadow);

        Pane fillerTitle = new Pane();
        HBox.setHgrow(fillerTitle, Priority.ALWAYS);

        hboxTitle = new HBox(lblTitle, fillerTitle, tfSearchBar);
        hboxTitle.setStyle("-fx-background-color: #22333B");
        hboxTitle.setAlignment(Pos.CENTER_LEFT);
        hboxTitle.setPadding(new Insets(5, 80, 5, 80));
        grid.add(hboxTitle, 0, 1, 7, 2);



        Scene scene = new Scene(grid , 600, 550);
        scene.getStylesheets().add("styles/ViewArtistStyle.css");
        viewArtistStage.setScene(scene);
        viewArtistStage.show();
    }

    /**
     * Inserting the Artists into the tableview
     */
    public void insertIntoTable(){
        ArrayList<Artist> artists = dm.getArtists();

        for(int i = 0; i < artists.size(); i++){
            list.add(artists.get(i));
        }
        
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

    public static void main(String[] args) {
        launch(args);
    }

}
