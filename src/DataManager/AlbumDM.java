package DataManager;

import Objects.Album;
import javafx.scene.control.TablePosition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Main.Main;

public class AlbumDM {
    
    private DatabaseDM dm = Main.dm;

    private Connection albumConnection = dm.connection;
    private Statement albumState = dm.state;

    /**
     * Process for changing the album cover.
     * @param newPath
     * @param album
     * @return
     */
	public boolean changeAlbumCover(String newPath, Album album) {
		boolean changed = false;

		try {
			albumState = albumConnection.createStatement();

			String queryUpdateAlbumCoverPath = "UPDATE Albums SET albumCoverPath = '" + newPath + "' WHERE albumId = " + album.getId() + ";";

			albumState.executeUpdate(queryUpdateAlbumCoverPath);

			changed = true;

		} catch(SQLException e) {
			System.out.println("Error | ChangeAlbumCover " + e.getMessage());
		}

		return changed;
	}


    /**
     * Process for removing an album from the database.
     * @param albumToRemove
     * @return
     */
    public boolean removeAlbum(Album albumToRemove) {
		boolean removed = false;
		try {
			albumState = albumConnection.createStatement();
			if(albumToRemove != null) {
				String queryToRemoveAlbum = "DELETE FROM Albums WHERE albumName = '" + albumToRemove.getName() + "' AND albumArtistName = '" + albumToRemove.getArtistName() + "';";
				System.out.println(queryToRemoveAlbum);
				albumState.executeUpdate(queryToRemoveAlbum);
				removed = true;
        	}
		} catch(SQLException ex) {
			System.out.println("Error : removeAlbum");
		}
		return removed;
	}


    /**
     * Process for updating the informatio of an album.
     * @param oldAlbumData
     * @param tablePos
     * @param newValue
     */
    public void updateAlbumInfo(Album oldAlbumData, TablePosition tablePos, String newValue) {

		try {
			albumState = albumConnection.createStatement();

			if(tablePos.getColumn() == 0) {
				String queryUpdateAlbum = "UPDATE Albums SET albumName = '" + newValue + "' WHERE albumName = '" + oldAlbumData.getName() + "' AND albumArtistName = '" + oldAlbumData.getArtistName() + "';";
				System.out.println(queryUpdateAlbum);

				albumState.executeUpdate(queryUpdateAlbum);
			}

		} catch (SQLException e) {
			System.out.println("Error : updateAlbumInfo = " + e.getMessage());
		}

    }


    /**
     * Process for adding an album to the database.
     * @param newArtistName
     * @param newAlbumName
     * @param albumCoverPath
     * @throws SQLException
     */
    public void setAlbum (String newArtistName, String newAlbumName, String albumCoverPath) throws SQLException{
		
		int artistID = 0;
		albumState = albumConnection.createStatement();

		String artistIdQuery = "SELECT artistId FROM Artists WHERE artistName = '" + newArtistName + "';";

		ResultSet rSet = albumState.executeQuery(artistIdQuery);
		artistID = rSet.getInt(1);;

		String addAlbumQuery = "INSERT INTO Albums (albumName, albumArtistName, albumCoverPath, albumArtistId) VALUES ('" + newAlbumName + "', '" + newArtistName + "', '" + albumCoverPath  + "', " + artistID + ");";
		albumState.executeUpdate(addAlbumQuery);

		String updateArtistAlbum = "UPDATE Artists SET artistNumberOfAlbumsInCollection = artistNumberOfAlbumsInCollection + 1 WHERE artistName = '" + newArtistName + "';"; 
		System.out.println(updateArtistAlbum);
		albumState.executeUpdate(updateArtistAlbum);
	}
    

    /**
     * Process for getting a list of the current albums in the database.
     * @return
     */
    public ArrayList<Album> getAlbums(){
		
		ArrayList<Album> albums = new ArrayList<Album>();
		ImageView imageView;
		
		try{
		
			Statement state = albumConnection.createStatement();
			String queryForAlbums = "select * from Albums;";
			
			ResultSet resultSet = state.executeQuery(queryForAlbums);
			
			while(resultSet.next()){
				Album album = new Album();
				album.id = resultSet.getInt(1);
				album.name = resultSet.getString(2);
				album.artistName = resultSet.getString(3);

				String coverArtPath = resultSet.getString(4);
				
				if(coverArtPath.equals("No Album Cover Chosen")) {
					imageView = null;
					album.coverArt = imageView;
				} else if (!coverArtPath.equals("No Album Cover Chosen")) {
					imageView = new ImageView(new Image(new FileInputStream(new File(coverArtPath))));
					album.coverArt = imageView;
				}

				albums.add(album);
			}
		
		return albums;
		
		}catch(SQLException e){
			System.err.println("SQL Error: getAlbums()");
		}catch(FileNotFoundException e){
			System.err.println("SQL Error: getAlbums()");
		}
		
		return albums;
	}


}
