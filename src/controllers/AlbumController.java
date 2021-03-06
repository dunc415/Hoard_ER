package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;

import add.AddAlbumArtist;
import databasemanager.AlbumDM;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AlbumController {
    
    private InputStream inputStream;
    private String defaultAlbumCoverPath = "No Album Cover Chosen";
    private String albumCoverPath;

    private SharedController sharedController = new SharedController();
    private AddAlbumArtist addAlbum = new AddAlbumArtist();
    private AlbumDM dm = addAlbum.getClassDataManager_Album();

    /**
     * Grabbing the appropriate string equivalents to the input of the radio buttons for the audio formats.
     * @param radioButtonCD
     * @param radioButtonVinyl
     * @param radioButtonCassette
     * @return
     */
	public String audioFormats_RadioButtonSelection(boolean radioButtonCD, boolean radioButtonVinyl, boolean radioButtonCassette) {
        String formatsReturned = ""; //The formats that are selected from the inputs of the radio buttons. (String format)

        if(radioButtonCD) { formatsReturned += "CD "; }
        if(radioButtonVinyl) { formatsReturned += "Vinyl "; }
        if(radioButtonCassette) { formatsReturned += "Cassette "; }

        return formatsReturned;
    }

     /**
      * The action of choosing an album cover from the computer directory.
      * @param albumCoverPath
      * @param vboxCover
      * @param stage
      */
    public void fileChooserAction(VBox vboxCover, Stage stage) {
        FileChooser fileChooser = new FileChooser();
        File albumCoverFile;

        try {
            albumCoverFile = fileChooser.showOpenDialog(stage);
            albumCoverPath = albumCoverFile.getPath();
            if (albumCoverFile != null) {
                inputStream = new FileInputStream(albumCoverFile);
                
                Image image = new Image(inputStream);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(150);
                imageView.setFitHeight(150);

                vboxCover.getChildren().clear();
                vboxCover.getChildren().add(imageView);

            }
        } catch(NullPointerException ex) {
            System.out.println("Error : FileChooserAction " + ex.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("Error : FileChooserAction " + e.getMessage());
        }
    }

    /**
     * Adding albums to the database using the method from the Album data manager.
     * @param stage
     * @param radioButtons
     * @param tfAlbumName
     * @param tfArtistName
     * @param cboxAlbumCover
     */
    public void addingAlbums(Stage stage, RadioButton[] radioButtons, TextField tfAlbumName, TextField tfArtistName, CheckBox cboxAlbumCover) {

        String audioFormatsString = audioFormats_RadioButtonSelection(radioButtons[0].isSelected(), radioButtons[1].isSelected(), radioButtons[2].isSelected());

        try{
            if(!tfArtistName.getText().equals("") && !tfAlbumName.getText().equals("")) {
                if(cboxAlbumCover.isSelected() && albumCoverPath != null) {
                    dm.setAlbum(tfArtistName.getText(), tfAlbumName.getText(), albumCoverPath, audioFormatsString);
                    sharedController.popupActivation("Album Added to Collection", stage);
                } else if(!cboxAlbumCover.isSelected() || albumCoverPath == null) {
                    dm.setAlbum(tfArtistName.getText(), tfAlbumName.getText(), defaultAlbumCoverPath, audioFormatsString);
                    sharedController.popupActivation("Album Added to Collection (No Cover Art)", stage);
                }  
            } else {
                sharedController.popupActivation("Enter Artist Name and Album Name", stage);
            }
                     
        }catch(SQLException e){
            System.out.println("Problem Adding Album");
        } catch(NullPointerException ex) {
            System.out.println("Not Connected to a Collection | Need to be connected");
        }

    }

}
