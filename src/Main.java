import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.controls.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.event.ActionEvent;


public class Main {
	
	DataManager dm = new DataManager();
	
	public void start(Stage mainStage){
		mainStage.setTitle("Welcome");
		
		Label lblTitle, lblNewCollection, lblExistingCollection;
		Button btnContCollection, btnNewCollection;
		TextField tfNameOfCollection;
		ChoiceBox cbCollections;
		BorderPane borderpane;
		
		// Will need to add different font and style using CSS
		
		// LABELS - DEFINING
		
		lblTitle = new Label("The Collection");
		lblTitle.setAlignment(Pos.CENTER);
		lblNewCollection = new Label("Start a new Collecction");
		lblNewCollection.setAlignment(Pos.CENTER);
		lblExistingCollection = new Label("Already have a collection?");
		lblExistingCollection.setAlignment(Pos.CENTER);
		
		// TEXTFIELDS - DEFINING
		
		tfNameOfCollection = new TextField();
		
		// CHOICE BOX - DEFINING
		// Will need to create a public variable that is a number that keeps track
		// of how many databases/collections have been created. We will use this
		// variable to gather the names of the collections from the database that stores the collection names.
		
		cbCollections = new ChoiceBox("Pick your Collection...");
		cbCollections.setAlignment(Pos.CENTER);
		
		// BUTTONS - DEFINING
		
		btnContCollection = new Button("Continue Collection");
		btnContCollection.setAlignment(Pos.CENTER);
		btnContCollection.setOnAction(ActionEvent -> {
			if(dm.connectDB(cbCollections.getValue())){
				//Set Stage to Add Artist/Album screen
			}else{
		});
		btnNewCollection = new Button("New Collection");
		btnNewCollection.setAlignment(Pos.CENTER);
		btnNewCollection.setOnAction(ActionEvent -> {
			if(dm.createDB(tfNameOfCollection.getText())){
				//Set Stage to Add Artist/Album screen
			}else{
		});
		
		// VBOXs and HBOXs
		
		VBox vboxCollectionExist = new VBox(lblExistingCollection, cbCollections, btnContCollection);
		vboxCollectionExist.setSpacing(20);
		
		VBox vboxNewCollection = new VBox(lblNewCollection, tfNameOfCollection, btnNewCollection);
		vboxNewCollection.setSpacing(20);
		
		// BORDERPANE
		
		borderpane = new BorderPane();
		borderpane.setTop(lblTitle);
		borderpane.setLeft(vboxCollectionExist);
		borderpane.setRight(vboxNewCollection);
		
		
		// SCENE/STAGE
		
		Scene scene = new Scene(btnContCollection, 500, 500);
		mainStage = new setScene(scene);
		mainStage.show();
		
	}
	
	public static void main(String[] args){
		launch(args);
	}
	
}
