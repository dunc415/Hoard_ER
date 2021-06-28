package Methods;

import Add.AddAlbum;
import Add.AddArtist;
import View.ViewAlbum;
import View.ViewArtist;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.Stage;

public class UIMethods {
    
    public MenuBar createMenuBar(Stage stage) {
        Menu artistMenu = new Menu("Artists");
        MenuItem artistMenuItem_ViewArtists = new MenuItem("View Artists");
        artistMenuItem_ViewArtists.setOnAction(ActionEvent -> {
            ViewArtist viewArtist = new ViewArtist();
            viewArtist.start(stage);
        });
        MenuItem artistMenuItem_AddArtists  = new MenuItem("Add Artist");
        artistMenuItem_AddArtists.setOnAction(ActionEvent -> {
            AddArtist addArtist = new AddArtist();
            addArtist.start(stage);
        });
        artistMenu.getItems().addAll(artistMenuItem_ViewArtists, new SeparatorMenuItem(), artistMenuItem_AddArtists);

        Menu albumMenu = new Menu("Albums");
        MenuItem albumMenuItem_ViewAlbums = new MenuItem("View Albums");
        albumMenuItem_ViewAlbums.setOnAction(ActionEvent -> {
            ViewAlbum viewAlbums = new ViewAlbum();
            viewAlbums.start(stage);
        });
        MenuItem albumMenuItem_AddAlbum = new MenuItem("Add Album");
        albumMenuItem_AddAlbum.setOnAction(ActionEvent -> {
            AddAlbum addAlbum = new AddAlbum();
            addAlbum.start(stage);
        });
        MenuItem albumMenuItem_FavoriteAlbums = new MenuItem("Favorite Albums");
        albumMenu.getItems().addAll(albumMenuItem_ViewAlbums, new SeparatorMenuItem(), albumMenuItem_AddAlbum, new SeparatorMenuItem(), albumMenuItem_FavoriteAlbums);

        Menu wishlistMenu = new Menu("Wish List");
        
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(artistMenu, albumMenu, wishlistMenu);

        return menuBar;
    }

}
