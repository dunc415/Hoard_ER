import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;


public class Main extends Application{
	
	public static DataManager dm = new DataManager();
	
	public void start(Stage mainStage){
		mainStage.setTitle("Welcome");
		
		Label lblTitle, lblNewCollection, lblExistingCollection;
		Button btnContCollection, btnNewCollection;
		TextField tfNameOfCollection;
		ChoiceBox<String> cbCollections;
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
		 
		cbCollections = new ChoiceBox();
		cbCollections.getItems().add("CollectionInformation");
		cbCollections.getItems().add("Test");
		
		// BUTTONS - DEFINING
		
		btnContCollection = new Button("Continue Collection");
		btnContCollection.setAlignment(Pos.CENTER);
		btnContCollection.setOnAction(ActionEvent -> {
			if(dm.connectDB(cbCollections.getValue())){
				//Set Stage to Add Artist/Album screen
				AddArtist addArtist = new AddArtist();
				addArtist.start(mainStage);
			}else{
			
			}
		});
		btnNewCollection = new Button("New Collection");
		btnNewCollection.setAlignment(Pos.CENTER);
		btnNewCollection.setOnAction(ActionEvent -> {
			if(dm.createDB(tfNameOfCollection.getText())){
				//Set Stage to Add Artist/Album screen
				cbCollections.getItems().add(tfNameOfCollection.getText());

			}else{

			}
		});
		
		// VBOXs and HBOXs
		
		VBox vboxCollectionExist = new VBox(lblExistingCollection, cbCollections, btnContCollection);
		vboxCollectionExist.setSpacing(20);
		vboxCollectionExist.setAlignment(Pos.CENTER);
		
		VBox vboxNewCollection = new VBox(lblNewCollection, tfNameOfCollection, btnNewCollection);
		vboxNewCollection.setSpacing(20);
		vboxNewCollection.setAlignment(Pos.CENTER);
		
		// BORDERPANE
		
		borderpane = new BorderPane();
		
		borderpane.setTop(lblTitle);
		BorderPane.setAlignment(lblTitle, Pos.CENTER);
		BorderPane.setMargin(lblTitle, new Insets(15));

		borderpane.setLeft(vboxCollectionExist);
		BorderPane.setAlignment(vboxCollectionExist, Pos.CENTER);
		BorderPane.setMargin(vboxCollectionExist, new Insets(15));

		borderpane.setRight(vboxNewCollection);
		BorderPane.setAlignment(vboxNewCollection, Pos.CENTER);
		BorderPane.setMargin(vboxNewCollection, new Insets(15));
		
		
		// SCENE/STAGE
		
		Scene scene = new Scene(borderpane, 600, 400);
		mainStage.setScene(scene);
		mainStage.show();
		
	}
	
	public static void main(String[] args){
		launch(args);
	}
}
