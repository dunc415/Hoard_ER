package Main;

import Add.AddArtist;
import DataManager.DataManager;


// public class Main extends Application{
	
	// public static DataManager dm = new DataManager();
	
// 	public void start(Stage mainStage){
// 		mainStage.setTitle("Welcome");
		
// 		Label lblTitle, lblNewCollection, lblExistingCollection;
// 		Button btnContCollection, btnNewCollection;
// 		TextField tfNameOfCollection;
// 		ChoiceBox<String> cbCollections;
// 		BorderPane borderpane;
		
// 		// Will need to add different font and style using CSS
		
// 		// LABELS - DEFINING
		
// 		lblTitle = new Label("The Collection");
// 		lblTitle.setAlignment(Pos.CENTER);
// 		lblNewCollection = new Label("Start a new Collecction");
// 		lblNewCollection.setAlignment(Pos.CENTER);
// 		lblExistingCollection = new Label("Already have a collection?");
// 		lblExistingCollection.setAlignment(Pos.CENTER);
		
// 		// TEXTFIELDS - DEFINING
		
// 		tfNameOfCollection = new TextField();
		
// 		// CHOICE BOX - DEFINING
// 		// Will need to create a public variable that is a number that keeps track
// 		// of how many databases/collections have been created. We will use this
// 		// variable to gather the names of the collections from the database that stores the collection names.
		 
// 		cbCollections = new ChoiceBox();
// 		cbCollections.getItems().add("CollectionInformation");
// 		cbCollections.getItems().add("Test");
		
// 		// BUTTONS - DEFINING
		
		// btnContCollection = new Button("Continue Collection");
		// btnContCollection.setAlignment(Pos.CENTER);
		// btnContCollection.setOnAction(ActionEvent -> {
		// 	if(dm.connectDB(cbCollections.getValue())){
		// 		//Set Stage to Add Artist/Album screen
		// 		AddArtist addArtist = new AddArtist();
		// 		addArtist.start(mainStage);
		// 	}else{
			
		// 	}
		// });
		// btnNewCollection = new Button("New Collection");
		// btnNewCollection.setAlignment(Pos.CENTER);
		// btnNewCollection.setOnAction(ActionEvent -> {
			// if(dm.createDB(tfNameOfCollection.getText())){
			// 	//Set Stage to Add Artist/Album screen
			// 	cbCollections.getItems().add(tfNameOfCollection.getText());

			// }else{

			// }
		// });
		
// 		// VBOXs and HBOXs
		
// 		VBox vboxCollectionExist = new VBox(lblExistingCollection, cbCollections, btnContCollection);
// 		vboxCollectionExist.setSpacing(20);
// 		vboxCollectionExist.setAlignment(Pos.CENTER);
		
// 		VBox vboxNewCollection = new VBox(lblNewCollection, tfNameOfCollection, btnNewCollection);
// 		vboxNewCollection.setSpacing(20);
// 		vboxNewCollection.setAlignment(Pos.CENTER);
		
// 		// BORDERPANE
		
// 		borderpane = new BorderPane();
		
// 		borderpane.setTop(lblTitle);
// 		BorderPane.setAlignment(lblTitle, Pos.CENTER);
// 		BorderPane.setMargin(lblTitle, new Insets(15));

// 		borderpane.setLeft(vboxCollectionExist);
// 		BorderPane.setAlignment(vboxCollectionExist, Pos.CENTER);
// 		BorderPane.setMargin(vboxCollectionExist, new Insets(15));

// 		borderpane.setRight(vboxNewCollection);
// 		BorderPane.setAlignment(vboxNewCollection, Pos.CENTER);
// 		BorderPane.setMargin(vboxNewCollection, new Insets(15));
		
		
// 		// SCENE/STAGE
		
// 		Scene scene = new Scene(borderpane, 600, 400);
// 		mainStage.setScene(scene);
// 		mainStage.show();
		
// 	}
	
// 	public static void main(String[] args){
// 		launch(args);
// 	}
// }

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

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
	public static DataManager dm = new DataManager();
	private GridPane grid = new GridPane();;
	private ObservableList<String> collectionList = FXCollections.observableArrayList();
	private Pane filler = new Pane();
	private DropShadow dropShadow = new DropShadow();

    @Override
    public void start(Stage startingStage){
        startingStage.setResizable(false);
        startingStage.initStyle(StageStyle.UNDECORATED);

        grid.setGridLinesVisible(false);

		collectionList.add("Test");

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
		// Can be moved to another screen
        Timeline timeline = new Timeline();

        Label lblMessage = new Label("Album was added to your collection!");
        HBox popup = new HBox(lblMessage);
        popup.getStyleClass().add("hbox-popup");
        popup.setAlignment(Pos.CENTER);
        popup.setVisible(false);
        grid.add(popup, 0, 8, 2, 1);
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
		*/
       
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

