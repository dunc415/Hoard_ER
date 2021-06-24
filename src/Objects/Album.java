package Objects;

import javafx.scene.image.ImageView;

public class Album {
	private int id;
	private String name;
	private String artistName;
	private ImageView coverArt;
	private String audioFormat;
	
	/**
	 * Getter and Setter methods
	 */

	public int getId() { return id; }
	
	public String getName() { return name; }

	public String getArtistName() { return artistName; }
	
	public ImageView getAlbumCoverImage() { return coverArt; }

	public String getAudioFormat() { return audioFormat; }
	
	public void setId(int newId) { id = newId; }
	
	public void setName(String newName) { name = newName; }

	public void setArtistName(String newArtistName) { artistName = newArtistName; }
	
	public void setAlbumCoverImage(ImageView newCoverImage) { coverArt = newCoverImage; }

	public void setAudioFormat(String newAudioFormat) { audioFormat = newAudioFormat; };

	/**
	 * toString methods
	 */
	
	public String toString() {
		return name + " | " + artistName;
	}

	public String toStringAudioFormat() {
		String[] formats = audioFormat.split(" ");
		String formattedOutput = "";

        for(int i = 0; i < formats.length; i++) {
            if(i == formats.length - 1){
                formattedOutput += formats[i];
            } else {
                formattedOutput += formats[i] + " | ";
            }
        }
		return formattedOutput;
	}
	
}