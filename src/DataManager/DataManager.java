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

import Objects.Album;
import Objects.Artist;
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
}