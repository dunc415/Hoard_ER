package Objects;

import javafx.scene.image.ImageView;

public class Album {
	public int id;
	public String name;
	public String artistName;
	public ImageView coverArt;
	
	public int getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}

	public String getArtistName(){
		return artistName;
	}
	
	public ImageView getAlbumCoverPath(){
		return coverArt;
	}
	
	public void setId(int newId){
		id = newId;
	}
	
	public void setName(String newName){
		name = newName;
	}

	public void setArtistName(String newName){
		artistName = newName;
	}
	
	public void setAlbumCoverPath(ImageView newCoverPath){
		coverArt = newCoverPath;
	}
	
	public String toString() {
		return name + " | " + artistName;
	}
	
	
}