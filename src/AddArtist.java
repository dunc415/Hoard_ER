import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddArtist extends Application{

    @Override
    public void start(Stage addArtistStage) {
        addArtistStage.setTitle("Add Artist and Album");

        Label lblTitle = new Label("Add Artist and Album");
        lblTitle.setAlignment(Pos.CENTER);
        Label lblNameArtist = new Label("Name of Artist");
        lblNameArtist.setAlignment(Pos.CENTER_LEFT);
        Label lblArtistNumberOfAlbums = new Label("Amount of Albums in Discography");
        lblNameArtist.setAlignment(Pos.CENTER_LEFT);
        // Label lblNameAlbum = new Label("Name of Artist");
        // lblNameAlbum.setAlignment(Pos.CENTER);

        TextField tfNameArtist = new TextField();
        TextField tfArtistNumberOfAlbums = new TextField();
        // TextField tfNameAlbum = new TextField();
        // tfNameAlbum.setAlignment(Pos.CENTER);

        Button btnAddArtist = new Button("Add");
        btnAddArtist.setAlignment(Pos.CENTER);

        // VBOX 
        
        VBox vboxArtistName = new VBox(lblNameArtist, tfNameArtist);
        vboxArtistName.setAlignment(Pos.CENTER_LEFT);
        vboxArtistName.setSpacing(10);

        VBox vboxArtistNumberOfAlbums = new VBox(lblArtistNumberOfAlbums, tfArtistNumberOfAlbums);
        vboxArtistNumberOfAlbums.setAlignment(Pos.CENTER_LEFT);
        vboxArtistNumberOfAlbums.setSpacing(10);

        // HBox hboxAlbumName = new HBox(lblNameAlbum, tfNameAlbum);
        // hboxAlbumName.setAlignment(Pos.CENTER);
        // hboxAlbumName.setSpacing(10);

        VBox vboxCenterItems = new VBox(lblTitle,vboxArtistName, vboxArtistNumberOfAlbums);
        vboxCenterItems.setAlignment(Pos.CENTER);
        vboxCenterItems.setSpacing(20);
        
        // BORDERPANE 

        BorderPane borderpane = new BorderPane();
        borderpane.setCenter(vboxCenterItems);
        BorderPane.setAlignment(vboxCenterItems, Pos.CENTER);
        borderpane.setBottom(btnAddArtist);
        BorderPane.setAlignment(btnAddArtist, Pos.CENTER);
        BorderPane.setMargin(btnAddArtist, new Insets(10));

        Scene scene = new Scene(borderpane, 700, 500);
        addArtistStage.setScene(scene);
        addArtistStage.show();
        
    }

    public static void main(String[] args){
        Application.launch(args);
    }
    
}
