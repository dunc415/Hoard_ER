package Main;

import Add.AddArtist;
import DataManager.DatabaseDM;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	// Labels
	private Label lblAlreadyHaveCollection;
	private Label lblPickCollectionName;
	private Label lblAuthor;
	private Label lblTitle;
	private Label lblNewCollectionName;
	private Label lblCreateNewCollection;

	// Combo Box
	private ComboBox<String> cbCollections;

	// Buttons
	private Button btnEnterDatabase;
	private Button btnExit;

	// Important Stuff used in the screen
	public static DatabaseDM dm = new DatabaseDM();
	private GridPane grid = new GridPane();;
	private ObservableList<String> collectionList = FXCollections.observableArrayList();
	private Pane filler = new Pane();
	private DropShadow dropShadow = new DropShadow();

    @Override
    public void start(Stage startingStage){
        startingStage.setResizable(false);
        startingStage.initStyle(StageStyle.UNDECORATED);

        grid.setGridLinesVisible(false);

        collectionList = dm.getCollections();

		createRowsColumnsForGridPane();
		
    
		/*
			Title label and dropshadow effect setup.
			It follows with the HBOX setup for the title.
		*/
        lblTitle = new Label("Hoard_ER");
        lblTitle.getStyleClass().add("title-font");
      
        dropShadow.setOffsetY(3.0f);
        dropShadow.setOffsetX(3.0f);
        dropShadow.setColor(Color.web("#0A0908"));
        lblTitle.setEffect(dropShadow);

        HBox titlePane = new HBox(lblTitle);
        titlePane.setAlignment(Pos.CENTER_LEFT);
        titlePane.setPadding(new Insets(5, 5, 5, 80));
        titlePane.setStyle("-fx-background-color: #22333B");
        grid.add(titlePane, 0, 1, 7, 1);

	
		/*
			Creation of the button and labels for exiting the application.
		*/
        btnExit = new Button();
        btnExit.getStyleClass().add("exit-button");
        btnExit.setPrefSize(25, 25);
        btnExit.setOnAction(ActionEvent -> {
            Platform.exit();
        });
		
		lblAuthor = new Label("By: Duncan Campbell");
        lblAuthor.getStyleClass().add("author-font");

        HBox.setHgrow(filler, Priority.ALWAYS);

        HBox hboxExit = new HBox(lblAuthor, filler, btnExit);
        hboxExit.setStyle("-fx-background-color: #22333B");
        grid.add(hboxExit, 0, 0, 7, 1);

		/*
			Creation of the different buttons and labels for when the user want to pick
			a collection that was already created.
		*/
		lblAlreadyHaveCollection = new Label("Already have a Collection?");
		lblAlreadyHaveCollection.getStyleClass().add("createCollection-font");
		grid.add(lblAlreadyHaveCollection, 1, 4);

        lblPickCollectionName = new Label("Pick Collection Name");

		cbCollections = new ComboBox<>();
        cbCollections.setPrefWidth(150);
     	cbCollections.setPromptText("Collection Names");
		cbCollections.setItems(collectionList);

        btnEnterDatabase = new Button("Enter");
        btnEnterDatabase.setPrefWidth(130);
        btnEnterDatabase.getStyleClass().add("custom-button");
        grid.add(btnEnterDatabase, 1, 6);

 		btnEnterDatabase.setOnAction(ActionEvent -> {
			if(!cbCollections.getSelectionModel().isEmpty()){
				if(dm.connectDB(cbCollections.getValue())){
					//Set Stage to Add Artist/Album screen
					AddArtist addArtist = new AddArtist();
					addArtist.start(startingStage);
				}	
			}
			
        });

		VBox vboxAlreadyHaveDatabase = new VBox(lblPickCollectionName, cbCollections);
        vboxAlreadyHaveDatabase.setSpacing(10);
        grid.add(vboxAlreadyHaveDatabase, 1, 5);
       
		/*
			Creation of buttons and labels for when the user 
			wants to create a new collection.
		*/
        lblNewCollectionName = new Label("Collection Name");
        lblCreateNewCollection = new Label("Create New Collection?");
        lblCreateNewCollection.getStyleClass().add("createCollection-font");
        grid.add(lblCreateNewCollection, 5, 4);

        TextField tfCollectionName = new TextField();
        tfCollectionName.setPromptText("Name");
        tfCollectionName.setPrefWidth(130);

        Button btnCreatingDatabase = new Button("Create");
        btnCreatingDatabase.setPrefWidth(130);
        btnCreatingDatabase.getStyleClass().add("custom-button");
        grid.add(btnCreatingDatabase, 5, 6);

		btnCreatingDatabase.setOnAction(ActionEvent -> {
			if(dm.createDB(tfCollectionName.getText())){
				//Set Stage to Add Artist/Album screen
				cbCollections.getItems().add(tfCollectionName.getText());
			}
		});

        VBox vboxNewCollection = new VBox(lblNewCollectionName, tfCollectionName);
        vboxNewCollection.setSpacing(10);
        grid.add(vboxNewCollection, 5, 5);

		/*
			Scene and Stage stuff
		*/
        Scene scene = new Scene(grid, 725, 400);
        scene.getStylesheets().add("styles/PickCollectionStyle.css");
        startingStage.setScene(scene);
        startingStage.show();
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


    public static void main(String[] args) {
        launch(args);
    }
}

