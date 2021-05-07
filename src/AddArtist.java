import java.sql.SQLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddArtist extends Application{

    DataManager dm = Main.dm;

    Label lblTitle;
    Label lblNameArtist;
    Label lblArtistNumberOfAlbums;

    TextField tfNameArtist;
    TextField tfArtistNumberOfAlbums;

    Button btnAddArtist;

    @Override
    public void start(Stage addArtistStage) {
        addArtistStage.setTitle("Add Artist and Album");

        lblTitle = new Label("Add Artist and Album");
        lblTitle.setAlignment(Pos.CENTER);
        lblNameArtist = new Label("Name of Artist");
        lblNameArtist.setAlignment(Pos.CENTER_LEFT);
        lblArtistNumberOfAlbums = new Label("Amount of Albums in Discography");
        lblNameArtist.setAlignment(Pos.CENTER_LEFT);
        // Label lblNameAlbum = new Label("Name of Artist");
        // lblNameAlbum.setAlignment(Pos.CENTER);

        tfNameArtist = new TextField();
        tfNameArtist.setPrefWidth(200);
        tfArtistNumberOfAlbums = new TextField();
        tfArtistNumberOfAlbums.setPrefWidth(200);
        // TextField tfNameAlbum = new TextField();
        // tfNameAlbum.setAlignment(Pos.CENTER);

        btnAddArtist = new Button("Add");
        btnAddArtist.setAlignment(Pos.CENTER);
        btnAddArtist.setOnAction(this::processOfAddingArtist);

        // VBOX 
        
        HBox vboxArtistName = new HBox(lblNameArtist, tfNameArtist);
        vboxArtistName.setAlignment(Pos.CENTER);
        vboxArtistName.setSpacing(10);

        HBox vboxArtistNumberOfAlbums = new HBox(lblArtistNumberOfAlbums, tfArtistNumberOfAlbums);
        vboxArtistNumberOfAlbums.setAlignment(Pos.CENTER);
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

    public void processOfAddingArtist(ActionEvent event){
        try{
            int numOfAlbumsInCollection = 0;
            int integerAlbumsOfArtist = Integer.parseInt(tfArtistNumberOfAlbums.getText());
            dm.setArtist(tfNameArtist.getText(), integerAlbumsOfArtist, numOfAlbumsInCollection);
            
        }catch(SQLException e){
            System.out.println("Problem Adding Artist");
        }
    }


    public static void main(String[] args){
        Application.launch(args);
    }
    
}
