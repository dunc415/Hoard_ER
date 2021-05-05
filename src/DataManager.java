
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataManager {
	
	Connection connection;
	public int numOfDB = 0;
	
	public DataManager(){
		connection = null;
	}
	
	
	// Might want to change this to have be a method.
	public boolean connectDB(String NAME_OF_DB){
		
		try{
			// String path = "INSERT_THE_PATH_FOR_STORING_DATABASES";
			String path = "jdbc:sqlite:C:/Users/Duncan/Documents/Projects/Collection/" + NAME_OF_DB + ".db";
			connection = DriverManager.getConnection(path);
			System.out.println("Connected to the database: " + NAME_OF_DB + ".db");
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
			System.out.println("Cannot Connect to Database");
			
		}finally{
			try{
				if(connection != null){
					connection.close();
				}
			}catch(SQLException ex){
				System.out.println(ex.getMessage());
			}
		}
		return true;
	}
	
	public boolean createDB(String NAME_OF_COLLECTOR_DB){
		
		try{
			Class.forName("org.sqlite.JDBC");
			String collectorPath = "jdbc:sqlite:C:/Users/Duncan/Documents/Projects/Collection/CollectionInformation.db";
			
			connection = DriverManager.getConnection(collectorPath);
			
			Statement state = connection.createStatement();
			
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
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	public int getNumberOfCollections(){
		return numOfDB;
	}
	
	public ObservableList<Album> getAlbums(){
		
		ObservableList<Album> list = FXCollections.observableArrayList();
		
		try{
		
			Statement state = connection.createStatement();
			String queryForAlbums = "select * from Albums;";
			
			ResultSet resultSet = state.executeQuery(queryForAlbums);
			
			while(resultSet.next()){
				Album album = new Album();
				album.id = resultSet.getInt(1);
				album.name = resultSet.getString(2);
				album.coverPath = resultSet.getString(3);
				album.artistId = resultSet.getInt(4);
				
				list.add(album);
			}
		
		return list;
		
		}catch(SQLException e){
			System.err.println("SQL Error: getAlbums()");
		}
		return list;
	}
}