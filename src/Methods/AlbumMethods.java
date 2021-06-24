package Methods;


public class AlbumMethods {
    
    
	public String audioFormats_RadioButtonSelection(boolean radioButtonCD, boolean radioButtonVinyl, boolean radioButtonCassette) {
        String formatsReturned = "";

        if(radioButtonCD) { formatsReturned += "CD "; }
        if(radioButtonVinyl) { formatsReturned += "Vinyl "; }
        if(radioButtonCassette) { formatsReturned += "Cassette "; }

        return formatsReturned;
    }



}
