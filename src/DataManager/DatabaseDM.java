package DataManager;

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
	
	public DatabaseDM(){
		connection = null;
	}
	
	
	/**
	 * Connecting to a collection/database
	 * @param NAME_OF_DB
	 * @return
	 */
	public boolean connectDB(String NAME_OF_DB){
		boolean connected = false;
		try{
			// String path = "INSERT_THE_PATH_FOR_STORING_DATABASES";
			String path = "jdbc:sqlite:" + System.getProperty("user.dir") + "/databases/" + NAME_OF_DB + ".db";
			connection = DriverManager.getConnection(path);
			System.out.println("Connected to the database: " + NAME_OF_DB + ".db");
			
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
	public boolean createDB(String NAME_OF_COLLECTOR_DB){
		
		try{
			Class.forName("org.sqlite.JDBC");
			String collectorPath = "jdbc:sqlite:" + System.getProperty("user.dir") + "/databases/CollectionInformation.db";
			
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

			String path = "jdbc:sqlite:" + System.getProperty("user.dir") + "/databases/" + NAME_OF_COLLECTOR_DB + ".db";
			connection = DriverManager.getConnection(path);
			state = connection.createStatement();

			String tableArtistQuery = "CREATE TABLE Artists (artistId INTEGER PRIMARY KEY,artistName VARCHAR NOT NULL,artistNumberOfAlbums INTEGER,artistNumberOfAlbumsInCollection INTEGER);";
			String tableAlbumQuery = "CREATE TABLE Albums (albumId INTEGER PRIMARY KEY,albumName VARCHAR NOT NULL,albumArtistName VARCHAR NOT NULL,albumCoverPath VARCHAR,albumArtistId INTEGER, albumFormat VARCHAR, FOREIGN KEY (albumArtistId) REFERENCES Artists(artistId));";
			state.executeUpdate(tableArtistQuery);
			state.executeUpdate(tableAlbumQuery);

		}catch(SQLException e){
			System.out.println(e.getMessage());
			return false;
		}
		return true;
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

		try {

			Class.forName("org.sqlite.JDBC");
			String collectorPath = "jdbc:sqlite:" + System.getProperty("user.dir") + "/databases/CollectionInformation.db";
			
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

