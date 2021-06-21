package API;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class GoogleSearchAPI {
    
    public int findNumberOfAlbums(String artist) {
        String google = "https://en.wikipedia.org/wiki/";
        String search = artist.replace(" ", "_");
        String charset = "UTF-8";

        Document links;
        int numberOfAlbums = 0;
        int maxPropertiesForAlbums = 9;

        ArrayList<String> searchEndings = new ArrayList<String>();
        searchEndings.add("_albums_discography");
        searchEndings.add("_discography");

        ArrayList<String> searchLabelsAlbums = populateList();

        // Use array full of options and cycle through the array using each of the options.
        // Going through the two different google searches (_discography and _album_discography)
        for(int i = 0; i < searchEndings.size(); i++) {
            try {
                links = Jsoup.connect(google + URLEncoder.encode((search + "" + searchEndings.get(i)), charset)).get();

                // Going through the maximum amount of labels that is seen on wikipedia
                for(int j = 0; j < maxPropertiesForAlbums; j++) {
                    Elements element = links.getElementsByClass("infobox-label");
                    Elements elementNumber = links.getElementsByClass("infobox-data");

                    if(j != element.size()){
                        // Going through the needed labels and comparing them to the ones seen on wikipedia
                        for(int k = 0; k < searchLabelsAlbums.size(); k++) {
                            if((element.get(j).text()).equals(searchLabelsAlbums.get(k))) {
                                System.out.println("Wiki "+element.get(j).text());
                                System.out.println("Array "+searchLabelsAlbums.get(k));
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
