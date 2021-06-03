package Objects;

public class Artist {
    
    public int id;
	public String name;
	public int numberOfAlbums;
    public int numberOfAlbumsInCollection;
	
	
	public int getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public int getNumberOfAlbums(){
		return numberOfAlbums;
	}

    public int getNumberOfAlbumsInCollection(){
		return numberOfAlbumsInCollection;
	}
	
	public void setId(int newId){
		id = newId;
	}
	
	public void setName(String newName){
		name = newName;
	}
	
	public void setNumberOfAlbums(int newNumberOfAlbums){
		numberOfAlbums = newNumberOfAlbums;
	}
	
    public void setNumberOfAlbumsInCollection(int newNumberOfAlbumsInCollection){
		numberOfAlbums = newNumberOfAlbumsInCollection;
	}

}

