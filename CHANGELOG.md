## _| 2021/06/29 |_ [1.1.13](https://github.com/dunc415/Collection/issues/40)
 - Name of "AddAlbum.java" file is now "AddAlbumArtist.java"
 - Now checks if the artist is already in the "Artists" table
    - True: Continues on adding the album
    - False: Adds the artist and then adds the album
 - Created a ArtistController.java file

## _| 2021/06/28 |_ [1.1.12](https://github.com/dunc415/Collection/issues/43)
 - Change the path directory that databases are created to. Using the path where the project was downloaded to, it now always will go to the project.

## _| 2021/06/28 |_ [1.1.11](https://github.com/dunc415/Collection/issues/15)
 - Documentation Improvements

## _| 2021/06/24 |_ [1.1.10](https://github.com/dunc415/Collection/issues/41)
 - Added formats to the "AddAlbum" screen.
 - Can now pick between a CD, Vinyl, and Cassette.

## _| 2021/06/24 |_ [1.1.9](https://github.com/dunc415/Collection/issues/14)
 - Split the DataManager Class into the specific categories. (Artist methods together, Album methods together, etc.)
 - Created a file for methods that are used by multiple classes.

## _| 2021/06/21 |_ [1.1.8](https://github.com/dunc415/Collection/issues/28)
 - The user can Change or Add an album cover after they have added that specific album. Access this on the "Cover Art" screen from the "View Album" screen.
 - Added the CSS implementation for this as well.

## _| 2021/06/21 |_ [1.1.7](https://github.com/dunc415/Collection/issues/32)
 - Removed the Textfields for inputting the following:
     - The amount of albums in the Artist discography | Reason : New API to replace the feature.
     - The amount of albums in the collection from that Artist | Reason : No need as it could lead to the number being false information.
 - When adding an Artist, the amount of albums in their discography are now searched on google via the GoogleSearchAPI (Jsoup.1.13.1) that is used.

## _| 2021/06/21 |_ [1.1.6](https://github.com/dunc415/Collection/issues/18)
 - The user can now "Delete" an artist and their albums from their collection.
 
## _| 2021/06/19 |_ [1.1.5](https://github.com/dunc415/Collection/issues/17)
 - The user can now "Delete" a whole album from their collection.

## _| 2021/06/15 |_ [1.1.4](https://github.com/dunc415/Collection/issues/20)
 - The user can now "Edit" the names of the Artist and Album in the collection.

## _| 2021/06/14 |_ [1.1.3](https://github.com/dunc415/Collection/issues/26)
 - Collection Names now show in the ComboBox on the starting page.

## _| 2021/06/14 |_ [1.1.2](https://github.com/dunc415/Collection/issues/23)
 - Buttons on ViewAlbum screen in tableview do not disappear when scrolling.

## _| 2021/06/14 |_ [1.1.1](https://github.com/dunc415/Collection/issues/22)
 - Popup on AddArtist and AddAlbum page will be positioned correctly.
 
## _| 2021/06/11 |_ [1.1.0](https://github.com/dunc415/Collection/issues/10)
 - CSS Implementation
 - Some Features have also been added
    - SearchBar in ViewArtist and ViewALbum
    - Proper MenuBar
    - Popup in AddAlbum and AddArtist screen
  
## _| 2021/06/03 |_ [1.0.1](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSP5pVKEbt76K2A5zmrRmNzEwEXHkruNkDNaA&usqp=CAU)
 - Can open Album Cover on the ViewAlbum screen
  
## _| 2021/06/02 |_ 1.0.0
 - Created CHANGELOG
 - Formated file structure
 - MAIN FUNCTIONALITY IS WORKING
