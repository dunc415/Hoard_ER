package databasemanager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.scene.control.TablePosition;
import login.Main;
import objects.Artist;


public class ArtistDM {

    private DatabaseDM dm = Main.dm;

    private Connection artistConnection = dm.connection;
    private Statement artistState = dm.state;
    
    /**
     * Removing an artist from the Database
     * @param artistToRemove
     * @return removed | false = did not remove - true = did remove
     */

	public boolean removeArtist(Artist artistToRemove) {
		boolean removed = false;
		try {
			artistState = artistConnection.createStatement();

			String queryToGetAlbumsUnderArtistName = "SELECT * FROM Albums INNER JOIN Artists WHERE albumArtistID = artistId AND artistId = " + artistToRemove.getId() + ";";

			ResultSet resultSet = artistState.executeQuery(queryToGetAlbumsUnderArtistName);

			while(resultSet.next()) {
				String queryToRemoveAlbum = "DELETE FROM Albums WHERE albumName = '" + resultSet.getString(2) + "' AND albumArtistName = '" + resultSet.getString(3) + "';";
				System.out.println(queryToRemoveAlbum);
				artistState.executeUpdate(queryToRemoveAlbum);
			}

			if(artistToRemove != null) {
				String queryToRemoveArtist = "DELETE FROM Artists WHERE artistName = '" + artistToRemove.getName() + "';";
				System.out.println(queryToRemoveArtist);
				artistState.executeUpdate(queryToRemoveArtist);
				removed = true;
        	}
		} catch(SQLException ex) {
			System.out.println("Error : removeArtist " + ex.getMessage());
		}
		return removed;
	}

    /**
     * Updating the information of an artist.
     * @param oldArtistData
     * @param tablePosition
     * @param newValue
     */

    public void updateArtistInfo(Artist oldArtistData, TablePosition tablePosition, String newValue) {

		try {
			artistState = artistConnection.createStatement();

			 if(tablePosition.getColumn() == 0) {
				String queryUpdateArtistName = "UPDATE Artists SET artistName = '" + newValue + "' WHERE artistName = '" + oldArtistData.getName() + "' AND artistNumberOfAlbums = " + oldArtistData.getNumberOfAlbums() + ";";
				System.out.println(queryUpdateArtistName);

				artistState.executeUpdate(queryUpdateArtistName);

			} else if(tablePosition.getColumn() == 1) {
				int number = Integer.parseInt(newValue);
				String queryUpdateArtistDiscographyNumber = "UPDATE Artists SET artistNumberOfAlbums = " + number + " WHERE artistName = '" + oldArtistData.getName() + "' AND artistNumberOfAlbums = " + oldArtistData.getNumberOfAlbums() + ";";
				System.out.println(queryUpdateArtistDiscographyNumber);

				artistState.executeUpdate(queryUpdateArtistDiscographyNumber);
			}
		} catch(SQLException e) {
			System.out.println("Error : updateArtistInfo");
		}
       
    }

    /**
     * Process for adding an artist to the database.
     * @param newArtistName
     * @param newArtistAmountOfAlbums
     * @param newArtistCurrentAmountOfAlbums
     * @throws SQLException
     */

    public void setArtist (String newArtistName, int newArtistAmountOfAlbums, int newArtistCurrentAmountOfAlbums) {
		
		try {
			artistState = artistConnection.createStatement();

			String queryCheckArtist = "SELECT COUNT(*) FROM Artists WHERE artistName = '" + newArtistName + "';";
			int artistCount = artistState.executeUpdate(queryCheckArtist);

			if(artistCount == 0) {
				String addArtistQuery = "INSERT INTO Artists (artistName, artistNumberOfAlbums, artistNumberOfAlbumsInCollection) VALUES ('" + newArtistName + "'," + newArtistAmountOfAlbums + ", " + newArtistCurrentAmountOfAlbums + ");";
				artistState.executeUpdate(addArtistQuery);
			}

		} catch (SQLException e) {
			System.out.println("Error | ArtistDM - setArtist " + e.getMessage());
		}
	}


    /**
     * Getting the list of artist from the database.
     * @return artists | List of artists in the collection
     */

    public ArrayList<Artist> getArtists(){
		
		ArrayList<Artist> artists = new ArrayList<Artist>();
		
		try{
		
			artistState = artistConnection.createStatement();
			String queryForAlbums = "select * from Artists;";
			
			ResultSet resultSet = artistState.executeQuery(queryForAlbums);
			
			while(resultSet.next()){
				Artist artist = new Artist();
				artist.setId(resultSet.getInt(1));
				artist.setName(resultSet.getString(2));
				artist.setNumberOfAlbums(resultSet.getInt(3));
				artist.setNumberOfAlbumsInCollection(resultSet.getInt(4));
				
				artists.add(artist);
			}
		
		return artists;
		
		}catch(SQLException e){
			System.err.println("SQL Error: getArtists()");
		}
		return artists;
	}

}
