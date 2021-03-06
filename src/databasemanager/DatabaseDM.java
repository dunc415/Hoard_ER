package databasemanager;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseDM {
	
	public Connection connection;
	public int numOfDB = 0;
	public Statement state;
	private String directory = System.getProperty("user.dir");
	
	public DatabaseDM(){
		connection = null;
	}
	
	
	/**
	 * Connecting to a collection/database
	 * @param nameOfDatabase
	 * @return connected | false = not connected - true = connected
	 */
	public boolean connectDB(String nameOfDatabase){
		boolean connected = false;
		directory.replace("\\", "/");
		try{
			String path = "jdbc:sqlite:" + directory + "/databases/" + nameOfDatabase + ".db";
			connection = DriverManager.getConnection(path);
			System.out.println("Connected to the database: " + nameOfDatabase + ".db");
			
			connected = true;

		}catch(SQLException e){
			System.out.println(e.getMessage());
			System.out.println("Cannot Connect to Database");
			
		}
		return connected;
	}
	
	/**
	 * Creating a Collection/Database
	 * @param NAME_OF_COLLECTOR_DB
	 * @return
	 */
	public boolean createDB(String nameOfCollection){
		directory.replace("\\", "/");
		boolean created = false;

		try{
			Class.forName("org.sqlite.JDBC");
			String collectorPath = "jdbc:sqlite:" + directory + "/databases/CollectionInformation.db";
			
			connection = DriverManager.getConnection(collectorPath);
			
			state = connection.createStatement();
			
			// SQL Query for adding Collection Name into the database.
			String sqlQuery = "insert into CollectionInfo values('" + nameOfCollection + "');";
			System.out.println(sqlQuery);
			state.executeUpdate(sqlQuery);

			if(connection != null){ // Creating the new database
				DatabaseMetaData meta = connection.getMetaData();
				System.out.println("The driver name is " + meta.getDriverName());
				System.out.println("A new database has been created");
				numOfDB++;
			}

			// Connecting to that new database
			String path = "jdbc:sqlite:" + directory + "/databases/" + nameOfCollection + ".db";
			connection = DriverManager.getConnection(path);
			state = connection.createStatement();

			// Creating the tables for the database
			String tableArtistQuery = "CREATE TABLE Artists (artistId INTEGER PRIMARY KEY,artistName VARCHAR NOT NULL,artistNumberOfAlbums INTEGER,artistNumberOfAlbumsInCollection INTEGER);";
			String tableAlbumQuery = "CREATE TABLE Albums (albumId INTEGER PRIMARY KEY,albumName VARCHAR NOT NULL,albumArtistName VARCHAR NOT NULL,albumCoverPath VARCHAR,albumArtistId INTEGER, albumFormat VARCHAR, FOREIGN KEY (albumArtistId) REFERENCES Artists(artistId));";
			state.executeUpdate(tableArtistQuery);
			state.executeUpdate(tableAlbumQuery);

			created = true;

		}catch(SQLException e){
			System.out.println(e.getMessage());
		} catch(ClassNotFoundException ex){
			System.out.println(ex.getMessage());
		}
		return created;
	}

	/**
	 * Grabbing the number of collections that have been created.
	 * @return numOfDB
	 */
	public int getNumberOfCollections(){
		return numOfDB;
	}

	/**
	 * Grabbing the names of the collections that have been created.
	 * @return collectionList
	 */
	public ObservableList<String> getCollections() {
		ObservableList<String> collectionList = FXCollections.observableArrayList();
		directory.replace("\\", "/");

		try {

			Class.forName("org.sqlite.JDBC");
			String collectorPath = "jdbc:sqlite:" + directory + "/databases/CollectionInformation.db";
			
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
}

