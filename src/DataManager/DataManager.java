package DataManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.spi.DirStateFactory.Result;

import Objects.Album;
import Objects.Artist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TablePosition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DataManager {
	
	Connection connection;
	public int numOfDB = 0;
	Statement state;
	
	public DataManager(){
		connection = null;
	}
	
	
	// Might want to change this to have be a method.
	public boolean connectDB(String NAME_OF_DB){
		boolean connected = false;
		try{
			// String path = "INSERT_THE_PATH_FOR_STORING_DATABASES";
			String path = "jdbc:sqlite:C:/Users/Duncan/Documents/Projects/Collection/" + NAME_OF_DB + ".db";
			connection = DriverManager.getConnection(path);
			System.out.println("Connected to the database: " + NAME_OF_DB + ".db");
			
			connected = true;

		}catch(SQLException e){
			System.out.println(e.getMessage());
			System.out.println("Cannot Connect to Database");
			
		}
		return connected;
	}
	
	public boolean createDB(String NAME_OF_COLLECTOR_DB){
		
		try{
			Class.forName("org.sqlite.JDBC");
			String collectorPath = "jdbc:sqlite:C:/Users/Duncan/Documents/Projects/Collection/CollectionInformation.db";
			
			connection = DriverManager.getConnection(collectorPath);
			
			state = connection.createStatement();
			
			// SQL Query for adding Collection Name into the database.
			String sqlQuery = "insert into CollectionInfo values('" + NAME_OF_COLLECTOR_DB + "');";
			System.out.println(sqlQuery);
			state.executeUpdate(sqlQuery);

		}catch(SQLException ex){
			System.out.println(ex.getMessage());
			return false;
		}catch(ClassNotFoundException e){
			System.out.println(e.getMessage());
		}
		
		try{
			if(connection != null){
				DatabaseMetaData meta = connection.getMetaData();
				System.out.println("The driver name is " + meta.getDriverName());
				System.out.println("A new database has been created");
				numOfDB++;
			}

			String path = "jdbc:sqlite:C:/Users/Duncan/Documents/Projects/Collection/" + NAME_OF_COLLECTOR_DB + ".db";
			connection = DriverManager.getConnection(path);
			state = connection.createStatement();

			String tableArtistQuery = "CREATE TABLE Artists (artistId INTEGER PRIMARY KEY,artistName VARCHAR NOT NULL,artistNumberOfAlbums INTEGER,artistNumberOfAlbumsInCollection INTEGER);";
			String tableAlbumQuery = "CREATE TABLE Albums (albumId INTEGER PRIMARY KEY,albumName VARCHAR NOT NULL,albumArtistName VARCHAR NOT NULL,albumCoverPath VARCHAR,albumArtistId INTEGER, FOREIGN KEY (albumArtistId) REFERENCES Artists(artistId));";
			state.executeUpdate(tableArtistQuery);
			state.executeUpdate(tableAlbumQuery);

		}catch(SQLException e){
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	public int getNumberOfCollections(){
		return numOfDB;
	}

	public ObservableList<String> getCollections() {
		ObservableList<String> collectionList = FXCollections.observableArrayList();

		try {

			Class.forName("org.sqlite.JDBC");
			String collectorPath = "jdbc:sqlite:C:/Users/Duncan/Documents/Projects/Collection/CollectionInformation.db";
			
			connection = DriverManager.getConnection(collectorPath);

			state = connection.createStatement();
			String queryGetCollections = "select * from CollectionInfo;";

			ResultSet resultSet = state.executeQuery(queryGetCollections);

			while(resultSet.next()) {
				collectionList.add(resultSet.getString(1));
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return collectionList;

	}
	
	public ArrayList<Album> getAlbums(){
		
		ArrayList<Album> albums = new ArrayList<Album>();
		ImageView imageView;
		
		try{
		
			Statement state = connection.createStatement();
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

	public ArrayList<Artist> getArtists(){
		
		ArrayList<Artist> artists = new ArrayList<Artist>();
		
		try{
		
			Statement state = connection.createStatement();
			String queryForAlbums = "select * from Artists;";
			
			ResultSet resultSet = state.executeQuery(queryForAlbums);
			
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

	public void setArtist (String newArtistName, int newArtistAmountOfAlbums, int newArtistCurrentAmountOfAlbums) throws SQLException{
		
		state = connection.createStatement();

		String addArtistQuery = "INSERT INTO Artists (artistName, artistNumberOfAlbums, artistNumberOfAlbumsInCollection) VALUES ('" + newArtistName + "'," + newArtistAmountOfAlbums + ", " + newArtistCurrentAmountOfAlbums + ");";
		state.executeUpdate(addArtistQuery);
	}

	public void setAlbum (String newArtistName, String newAlbumName, String albumCoverPath) throws SQLException{
		
		int artistID = 0;
		state = connection.createStatement();

		String artistIdQuery = "SELECT artistId FROM Artists WHERE artistName = '" + newArtistName + "';";

		ResultSet rSet = state.executeQuery(artistIdQuery);
		artistID = rSet.getInt(1);;

		String addAlbumQuery = "INSERT INTO Albums (albumName, albumArtistName, albumCoverPath, albumArtistId) VALUES ('" + newAlbumName + "', '" + newArtistName + "', '" + albumCoverPath  + "', " + artistID + ");";
		state.executeUpdate(addAlbumQuery);

		String updateArtistAlbum = "UPDATE Artists SET artistNumberOfAlbumsInCollection = artistNumberOfAlbumsInCollection + 1 WHERE artistName = '" + newArtistName + "';"; 
		System.out.println(updateArtistAlbum);
		state.executeUpdate(updateArtistAlbum);
	}

	public void updateAlbumInfo(Album oldAlbumData, TablePosition tablePos, String newValue) {

		try {
			state = connection.createStatement();

			if(tablePos.getColumn() == 0) {
				String queryUpdateAlbum = "UPDATE Albums SET albumName = '" + newValue + "' WHERE albumName = '" + oldAlbumData.getName() + "' AND albumArtistName = '" + oldAlbumData.getArtistName() + "';";
				System.out.println(queryUpdateAlbum);

				state.executeUpdate(queryUpdateAlbum);
			}

		} catch (SQLException e) {
			System.out.println("Error : updateAlbumInfo = " + e.getMessage());
		}

    }

    public void updateArtistInfo(Artist oldArtistData, TablePosition tablePosition, String newValue) {

		try {
			state = connection.createStatement();

			 if(tablePosition.getColumn() == 0) {
				String queryUpdateArtistName = "UPDATE Artists SET artistName = '" + newValue + "' WHERE artistName = '" + oldArtistData.getName() + "' AND artistNumberOfAlbums = " + oldArtistData.getNumberOfAlbums() + ";";
				System.out.println(queryUpdateArtistName);

				state.executeUpdate(queryUpdateArtistName);

			} else if(tablePosition.getColumn() == 1) {
				int number = Integer.parseInt(newValue);
				String queryUpdateArtistDiscographyNumber = "UPDATE Artists SET artistNumberOfAlbums = " + number + " WHERE artistName = '" + oldArtistData.getName() + "' AND artistNumberOfAlbums = " + oldArtistData.getNumberOfAlbums() + ";";
				System.out.println(queryUpdateArtistDiscographyNumber);

				state.executeUpdate(queryUpdateArtistDiscographyNumber);
			}
		} catch(SQLException e) {
			System.out.println("Error : updateArtistInfo");
		}
       
    }

	public boolean removeAlbum(Album albumToRemove) {
		boolean removed = false;
		try {
			state = connection.createStatement();
			if(albumToRemove != null) {
				String queryToRemoveAlbum = "DELETE FROM Albums WHERE albumName = '" + albumToRemove.getName() + "' AND albumArtistName = '" + albumToRemove.getArtistName() + "';";
				System.out.println(queryToRemoveAlbum);
				state.executeUpdate(queryToRemoveAlbum);
				removed = true;
        	}
		} catch(SQLException ex) {
			System.out.println("Error : removeAlbum");
		}
		return removed;
	}

	public boolean removeArtist(Artist artistToRemove) {
		boolean removed = false;
		try {
			state = connection.createStatement();

			String queryToGetAlbumsUnderArtistName = "SELECT * FROM Albums INNER JOIN Artists WHERE albumArtistID = artistId AND artistId = " + artistToRemove.getId() + ";";

			ResultSet resultSet = state.executeQuery(queryToGetAlbumsUnderArtistName);

			while(resultSet.next()) {
				String queryToRemoveAlbum = "DELETE FROM Albums WHERE albumName = '" + resultSet.getString(2) + "' AND albumArtistName = '" + resultSet.getString(3) + "';";
				System.out.println(queryToRemoveAlbum);
				state.executeUpdate(queryToRemoveAlbum);
			}

			if(artistToRemove != null) {
				String queryToRemoveArtist = "DELETE FROM Artists WHERE artistName = '" + artistToRemove.getName() + "';";
				System.out.println(queryToRemoveArtist);
				state.executeUpdate(queryToRemoveArtist);
				removed = true;
        	}
		} catch(SQLException ex) {
			System.out.println("Error : removeArtist " + ex.getMessage());
		}
		return removed;
	}

	public boolean changeAlbumCover(String newPath, Album album) {
		boolean changed = false;

		try {
			state = connection.createStatement();

			String queryUpdateAlbumCoverPath = "UPDATE Albums SET albumCoverPath = '" + newPath + "' WHERE albumId = " + album.getId() + ";";

			state.executeUpdate(queryUpdateAlbumCoverPath);

			changed = true;

		} catch(SQLException e) {
			System.out.println("Error | ChangeAlbumCover " + e.getMessage());
		}

		return changed;
	}
}

