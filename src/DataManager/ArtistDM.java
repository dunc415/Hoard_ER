package DataManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Objects.Artist;
import Main.Main;
import javafx.scene.control.TablePosition;


public class ArtistDM {

    private DataManager dm = Main.dm;

    private Connection artistConnection = dm.connection;
    private Statement artistState = dm.state;
    
    /**
     * Removing an artist from the Database
     * @param artistToRemove
     * @return
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

    public void setArtist (String newArtistName, int newArtistAmountOfAlbums, int newArtistCurrentAmountOfAlbums) throws SQLException{
		
		artistState = artistConnection.createStatement();

		String addArtistQuery = "INSERT INTO Artists (artistName, artistNumberOfAlbums, artistNumberOfAlbumsInCollection) VALUES ('" + newArtistName + "'," + newArtistAmountOfAlbums + ", " + newArtistCurrentAmountOfAlbums + ");";
		artistState.executeUpdate(addArtistQuery);
	}


    /**
     * Getting the list of artist from the database.
     * @return
     */

    public ArrayList<Artist> getArtists(){
		
		ArrayList<Artist> artists = new ArrayList<Artist>();
		
		try{
		
			artistState = artistConnection.createStatement();
			String queryForAlbums = "select * from Artists;";
			
			ResultSet resultSet = artistState.executeQuery(queryForAlbums);
			
			while(resultSet.next()){
				Artist artist = new Artist();
				artist.id = resultSet.getInt(1);
				artist.name = resultSet.getString(2);
				artist.numberOfAlbums = resultSet.getInt(3);
				artist.numberOfAlbumsInCollection = resultSet.getInt(4);
				
				artists.add(artist);
			}
		
		return artists;
		
		}catch(SQLException e){
			System.err.println("SQL Error: getArtists()");
		}
		return artists;
	}

}
