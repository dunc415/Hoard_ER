package View;

import java.util.ArrayList;

import Add.AddAlbum;
import Add.AddArtist;
import DataManager.DataManager;
import Objects.Album;
import Main.Main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;


public class ViewAlbum extends Application {

    // Pane
    private Pane fillerBottomRow;

    // Button
    private Button btnDelete;
    private Button btnDone;
    private Button btnEdit;

    // Hbox | Vbox
    private HBox hboxBottomRow;

    private DataManager dm = Main.dm;
    private TableView<Album> tableView = new TableView<>();
    private HBox hboxBTN;
    private ObservableList<Album> list = FXCollections.observableArrayList();
    private GridPane grid = new GridPane();

    public void start(Stage viewAlbumStage) {
        viewAlbumStage.setResizable(false);

        grid.setGridLinesVisible(false);

        createRowsColumnsForGridPane();

        insertIntoTable();

        /*
            TableView Section
        */

        tableView.setPlaceholder(new Label("No Artist in Collection"));
        TableColumn<Album, String> nameColumn = new TableColumn<>("Title");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> {
            Album rowData = event.getRowValue();
            TablePosition cellPosition = event.getTablePosition();
            String newValueInputted = event.getNewValue();
            dm.updateAlbumInfo(rowData, cellPosition, newValueInputted);
        });

        TableColumn<Album, String> artistNameColumn = new TableColumn<>("Artist");
        artistNameColumn.setCellValueFactory(new PropertyValueFactory<>("artistName"));

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.getColumns().addAll(nameColumn, artistNameColumn);

        addButtonToTable();

        Label placeHolder = new Label("No Items");
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
            viewArtist.start(viewAlbumStage);
        });
        MenuItem artistMenuItem_AddArtists  = new MenuItem("Add Artist");
        artistMenuItem_AddArtists.setOnAction(ActionEvent -> {
            AddArtist addArtist = new AddArtist();
            addArtist.start(viewAlbumStage);
        });
        artistMenu.getItems().addAll(artistMenuItem_ViewArtists, new SeparatorMenuItem(), artistMenuItem_AddArtists);

        Menu albumMenu = new Menu("Albums");
        MenuItem albumMenuItem_ViewAlbums = new MenuItem("View Albums");
        albumMenuItem_ViewAlbums.setOnAction(ActionEvent -> {
            ViewAlbum viewAlbums = new ViewAlbum();
            viewAlbums.start(viewAlbumStage);
        });
        MenuItem albumMenuItem_AddAlbum = new MenuItem("Add Album");
        albumMenuItem_AddAlbum.setOnAction(ActionEvent -> {
            AddAlbum addAlbum = new AddAlbum();
            addAlbum.start(viewAlbumStage);
        });
        MenuItem albumMenuItem_FavoriteAlbums = new MenuItem("Favorite Albums");
        albumMenu.getItems().addAll(albumMenuItem_ViewAlbums, new SeparatorMenuItem(), albumMenuItem_AddAlbum, new SeparatorMenuItem(), albumMenuItem_FavoriteAlbums);

        Menu wishlistMenu = new Menu("Wish List");
        
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(artistMenu, albumMenu, wishlistMenu);

        /*
            Exit section
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
           Bottom Section (Delete Button, Edit Button, Done Button)
        */

        grid.getRowConstraints().get(8).setMinHeight(45);

        fillerBottomRow = new Pane();
        HBox.setHgrow(fillerBottomRow, Priority.ALWAYS);

        btnDelete = new Button("Delete Row");
        btnDelete.getStyleClass().add("custom-button");

        btnDelete.setOnAction(ActionEvent -> {
            Album obj = tableView.getSelectionModel().getSelectedItem();
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
        */

        FilteredList<Album> flObject = new FilteredList(list, p -> true);
        tableView.setItems(flObject);

        TextField tfSearchBar = new TextField();
        tfSearchBar.getStyleClass().add("search-bar");
        tfSearchBar.setPromptText("Search");
        tfSearchBar.setPrefWidth(150);
        tfSearchBar.textProperty().addListener((observableValue, oldValue, newValue) -> {
            flObject.setPredicate(p -> p.getName().toLowerCase().contains(newValue.toLowerCase().trim())
                                    || p.getArtistName().toLowerCase().contains(newValue.toLowerCase().trim()));
        });


        /*
            Title Section
        */

        Label lblTitle = new Label("Albums");
        lblTitle.getStyleClass().add("title-font");
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetY(3.0f);
        dropShadow.setOffsetX(3.0f);
        dropShadow.setColor(Color.web("#0A0908"));
        lblTitle.setEffect(dropShadow);

        Pane filler1 = new Pane();
        HBox.setHgrow(filler1, Priority.ALWAYS);

        Button btnClearSearch = new Button("X");
        btnClearSearch.setMaxSize(25, 25);
        btnClearSearch.getStyleClass().add("clear-search-button");
        btnClearSearch.setOnAction(ActionEvent -> {
           tfSearchBar.setText("");
        });

        HBox hboxSearchBar = new HBox(tfSearchBar, btnClearSearch);
        hboxSearchBar.setAlignment(Pos.CENTER);

        HBox hboxTitle = new HBox(lblTitle, filler1, hboxSearchBar);
        hboxTitle.setStyle("-fx-background-color: #22333B");
        hboxTitle.setAlignment(Pos.CENTER_LEFT);
        hboxTitle.setPadding(new Insets(5, 80, 5, 80));
        grid.add(hboxTitle, 0, 1, 7, 2);

        /*
            Scene and Stage
        */

        Scene scene = new Scene(grid , 600, 550);
        scene.getStylesheets().add("styles/ViewAlbumStyle.css");
        viewAlbumStage.setScene(scene);
        viewAlbumStage.show();
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

    public void insertIntoTable(){
        ArrayList<Album> albums = dm.getAlbums();

        for(int i = 0; i < albums.size(); i++){
            list.add(albums.get(i));
        }        
    }


    private void addButtonToTable() {
        TableColumn<Album, Void> colBtn = new TableColumn("Action");
        Callback<TableColumn<Album, Void>, TableCell<Album, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Album, Void> call(TableColumn<Album, Void> param) {
                final TableCell<Album, Void> cell = new TableCell<Album, Void>() {

                    private Button btnShowCoverArt = new Button("Cover Art");

                    {
                        btnShowCoverArt.getStyleClass().add("action-button");

                        btnShowCoverArt.setOnAction((event) -> {
                            Album data = (Album)getTableView().getItems().get(getIndex());
                            createCoverArtStage(data.coverArt);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnShowCoverArt);
                        }

                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        tableView.getColumns().add(colBtn);
    }


    /**
     * This helped a lot with this: https://stackoverflow.com/questions/45633851/javafx-tableview-scrolling
     * Not needed now. Will need if I decide to add another button into the actions column.
     * @param newbtn
     * @param newbtn1
     * @return
     */
    // https://stackoverflow.com/questions/45633851/javafx-tableview-scrolling
    public HBox createButtonHBox(Button newbtn, Button newbtn1) {
        Text txt = new Text("|");
        hboxBTN = new HBox(newbtn, txt, newbtn1);
        hboxBTN.setSpacing(10.0D);
        hboxBTN.setAlignment(Pos.CENTER);
        return hboxBTN;
    }


    /**
     * Creating the Cover Art Screen. (The one that displays the cover art)
     * @param coverArt
     */
    public void createCoverArtStage(ImageView coverArt) {
        Stage coverArtStage = new Stage();
        HBox hboxCoverArt;
        Label lblMessage;

        if(coverArt != null) {
            coverArt.setFitWidth(300);
            coverArt.setFitHeight(300);
            hboxCoverArt = new HBox(coverArt);
        } else {
            lblMessage = new Label("No Album Cover Chosen");
            hboxCoverArt = new HBox(lblMessage);
           
        }

        hboxCoverArt.setAlignment(Pos.CENTER);
        hboxCoverArt.setMaxSize(300, 300);

        Scene coverArtScene = new Scene(hboxCoverArt, 300, 300);
        coverArtStage.setScene(coverArtScene);
        coverArtStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}