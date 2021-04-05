public class Album {
	public int id;
	public String name;
	public String coverPath;
	public int artistId;
	
	
	public int getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public String getAlbumCoverPath(){
		return coverPath;
	}
	
	public int getArtistId(){
		return artistId;
	}
	
	public void setId(int newId){
		id = newId;
	}
	
	public void setName(String newName){
		name = newName;
	}
	
	public void setAlbumCoverPath(String newCoverPath){
		coverPath = newCoverPath;
	}
	
	public void setArtistId(int newArtistId){
		artistId = newArtistId;
	}
	
	
	
}