
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataManager {
	
	Connection connection;
	public int numOfDB = 0;
	
	public DataManager(){
		connection = null;
	}
	
	
	// Might want to change this to have be a method.
	public void connectDB(String NAME_OF_DB){
		
		try{
			// String path = "INSERT_THE_PATH_FOR_STORING_DATABASES";
			String path = "jdbc:sqlite:C:/sqlite/db/" + NAME_OF_DB + ".db";
			connection = DriverManager.getConnection(path);
			System.out.println("Connected to the database: " + NAME_OF_DB + ".db");
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
			
		}finally{
			try{
				if(connection != null){
					connection.close();
				}
			}catch(SQLExcpetion ex){
				System.out.println(ex.getMessage());
			}
		}
	}
	
	public void createDB(String NAME_OF_DB){
		
		try{
			String collectorPath = "jdbc:sqlite:C:/sqlite/db/" + NAME_OF_COLLECTOR_DB + ".db";
			
			connection = DriverManager.getConnection(collectorPath);
			
			Statement state = connection.createStatement();
			
			// SQL Query for adding Collection Name into the database.
			String sqlQuery = "insert into CollectionInfo values( , '" + NAME_OF_DB + "');";
			
			state.executeUpdate(sqlQuery);
			
		}catch(SQLException ex){
			System.out.println(e.getMeassage());
		}
		
		String path = "jdbc:sqlite:C:/sqlite/db/" + NAME_OF_DB + ".db";
		
		try(connection = DriverManager.getConnection(path)){
			if(connection != null){
				DatabaseMetaData meta = connection.getMetaData();
				System.out.println("The driver name is " + meta.getDriverName());
				System.out.println("A new database has been created");
				numOfDB++;
			}
		}catch(SQLException e){
			System.out.println(e.getMeassage());
		}
	}

	public int getNumberOfCollections(){
		return numOfDB;
	}
	
	public ObservableList<Album> getAlbums(){
		
		private ObservableList<Album> list = FXCollections.observableArrayList();
		
		try{
		
			Statement state = connection.createStatement();
			String queryForAlbums = "select * from Albums;"
			
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
	}
}