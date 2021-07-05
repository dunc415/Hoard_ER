package controllers;

import java.sql.SQLException;

import add.AddAlbumArtist;
import api.GoogleSearchAPI;
import databasemanager.ArtistDM;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ArtistController {
    
    private GoogleSearchAPI googleSearchAPI = new GoogleSearchAPI();
    private SharedController sharedController = new SharedController();
    private AddAlbumArtist addAlbumArtist = new AddAlbumArtist();
    private ArtistDM dm = addAlbumArtist.getClassDataManager_Artist();

    public void addingArtist(TextField tfArtistName, Stage stage){

        try{
            if(!tfArtistName.getText().equals("")) {
                dm.setArtist(tfArtistName.getText(), googleSearchAPI.findNumberOfAlbums(tfArtistName.getText()), 0);
                sharedController.popupActivation("Artist Added", stage);
            } else {
                sharedController.popupActivation("Enter All Information that has an Asterisk (*)", stage);
            }
            
        } catch(NumberFormatException ex) {
            System.out.println("Error | ArtistController - addingArtist" + ex.getMessage());
        }
    }

}
