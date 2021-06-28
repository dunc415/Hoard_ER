package Main;

import Add.AddArtist;
import DataManager.DatabaseDM;
import Methods.SharedMethods;
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

	private Label lblAlreadyHaveCollection, lblPickCollectionName, lblAuthor, lblTitle, lblNewCollectionName, lblCreateNewCollectionTitle;

	private ComboBox<String> cbCollections;

	private Button btnEnterDatabase, btnExit, btnCreatingDatabase;

    private HBox titlePane, hboxExit;
    private VBox vboxAlreadyHaveDatabase, vboxNewCollection;

    private TextField tfCollectionName;

    private Scene scene;

	public static DatabaseDM dm = new DatabaseDM();
	private GridPane grid = new GridPane();;
	private ObservableList<String> collectionList = FXCollections.observableArrayList();
	private Pane filler = new Pane();
	private DropShadow dropShadow = new DropShadow();
    private SharedMethods sharedMethods = new SharedMethods();

    @Override
    public void start(Stage startingStage){
        startingStage.setResizable(false);
        startingStage.initStyle(StageStyle.UNDECORATED);

        grid.setGridLinesVisible(false);

        collectionList = dm.getCollections();

		sharedMethods.createRowsColumnsForGridPane(grid, 9, 7);
    
		/* Title Section */

        lblTitle = new Label("Hoard_ER");
        lblTitle.getStyleClass().add("title-font");
      
        dropShadow.setOffsetY(3.0f);
        dropShadow.setOffsetX(3.0f);
        dropShadow.setColor(Color.web("#0A0908"));
        lblTitle.setEffect(dropShadow);

        titlePane = new HBox(lblTitle);
        titlePane.setAlignment(Pos.CENTER_LEFT);
        titlePane.setPadding(new Insets(5, 5, 5, 80));
        titlePane.setStyle("-fx-background-color: #22333B");
        grid.add(titlePane, 0, 1, 7, 1);
	
		/* Exit Section */

        btnExit = new Button();
        btnExit.getStyleClass().add("exit-button");
        btnExit.setPrefSize(25, 25);
        btnExit.setOnAction(ActionEvent -> {
            Platform.exit();
        });
		
		lblAuthor = new Label("By: Duncan Campbell");
        lblAuthor.getStyleClass().add("author-font");

        HBox.setHgrow(filler, Priority.ALWAYS);

        hboxExit = new HBox(lblAuthor, filler, btnExit);
        hboxExit.setStyle("-fx-background-color: #22333B");
        grid.add(hboxExit, 0, 0, 7, 1);

		/* Already have a Collection Section */
		
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
					AddArtist addArtist = new AddArtist();
					addArtist.start(startingStage);
				}	
			}
			
        });

		vboxAlreadyHaveDatabase = new VBox(lblPickCollectionName, cbCollections);
        vboxAlreadyHaveDatabase.setSpacing(10);
        grid.add(vboxAlreadyHaveDatabase, 1, 5);
       
        /* Creating Collection Section */

        lblNewCollectionName = new Label("Collection Name");
        lblCreateNewCollectionTitle = new Label("Create New Collection?");
        lblCreateNewCollectionTitle.getStyleClass().add("createCollection-font");
        grid.add(lblCreateNewCollectionTitle, 5, 4);

        tfCollectionName = new TextField();
        tfCollectionName.setPromptText("Name");
        tfCollectionName.setPrefWidth(130);

        btnCreatingDatabase = new Button("Create");
        btnCreatingDatabase.setPrefWidth(130);
        btnCreatingDatabase.getStyleClass().add("custom-button");
        grid.add(btnCreatingDatabase, 5, 6);

		btnCreatingDatabase.setOnAction(ActionEvent -> {
			if(dm.createDB(tfCollectionName.getText())){
				cbCollections.getItems().add(tfCollectionName.getText());
			}
		});

        vboxNewCollection = new VBox(lblNewCollectionName, tfCollectionName);
        vboxNewCollection.setSpacing(10);
        grid.add(vboxNewCollection, 5, 5);

		/* Scene and Stage Section */

        scene = new Scene(grid, 725, 400);
        scene.getStylesheets().add("styles/PickCollectionStyle.css");
        startingStage.setScene(scene);
        startingStage.show();
    }
    public static void main(String[] args) { launch(args); }
}

