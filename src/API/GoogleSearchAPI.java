package API;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Class : Where all the Google Search information comes from and is acquired.
 */
public class GoogleSearchAPI {
    

    /**
     * Functionality : Grabs the number of albums an artist has in their discography.
     * @param artist
     * @return numberOfAlbums | The number of albums that the artist has in their discography.
     */
    public int findNumberOfAlbums(String artist) {
        String wikipediaPage = "https://en.wikipedia.org/wiki/";
        String artistName_WithUnderscore = artist.replace(" ", "_");
        String charset = "UTF-8";

        Document webpageHTML;
        int numberOfAlbums = 0;
        int maxPropertiesForAlbums = 9;

        ArrayList<String> searchOptions_FindDiscogrpahy = new ArrayList<String>();
        searchOptions_FindDiscogrpahy.add("_albums_discography");
        searchOptions_FindDiscogrpahy.add("_discography");

        ArrayList<String> searchLabels_AlbumTypes = populateList();

        // Use array full of options and cycle through the array using each of the options.
        // Going through the two different google searches (_discography and _album_discography)
        for(int i = 0; i < searchOptions_FindDiscogrpahy.size(); i++) {
            try {
                webpageHTML = Jsoup.connect(wikipediaPage + URLEncoder.encode((artistName_WithUnderscore + "" + searchOptions_FindDiscogrpahy.get(i)), charset)).get();

                // Going through the maximum amount of labels that is seen on wikipedia
                for(int j = 0; j < maxPropertiesForAlbums; j++) {
                    Elements element = webpageHTML.getElementsByClass("infobox-label");
                    Elements elementNumber = webpageHTML.getElementsByClass("infobox-data");

                    if(j != element.size()){
                        // Going through the needed labels and comparing them to the ones seen on wikipedia
                        for(int k = 0; k < searchLabels_AlbumTypes.size(); k++) {
                            if((element.get(j).text()).equals(searchLabels_AlbumTypes.get(k))) {
                                System.out.println("Wiki " + element.get(j).text());
                                System.out.println("Array " + searchLabels_AlbumTypes.get(k));
                                numberOfAlbums += Integer.parseInt(elementNumber.get(j).text());
                            }
                        }
                    } else {
                        break;
                    }
                }
            } catch (IOException e) {
                continue;
            }
            return numberOfAlbums;
        }
        return -1;
    }
    

    /**
     * Populate a list of different album labels that are seen on wikipedia.
     * @return searchLabelsAlbums | List of labels
     */
    public static ArrayList<String> populateList() {
        ArrayList<String> searchLabelsAlbums = new ArrayList<String>();
        searchLabelsAlbums.add("Mixtapes");
        searchLabelsAlbums.add("Studio albums");
        searchLabelsAlbums.add("Compilation albums");
        searchLabelsAlbums.add("Collaborative albums");
        searchLabelsAlbums.add("Collaboration albums");
        searchLabelsAlbums.add("Soundtrack albums");
        return searchLabelsAlbums;
    }

}
