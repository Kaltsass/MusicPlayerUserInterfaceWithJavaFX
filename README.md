Τμήμα Μηχανικών Πληροφορικής Υπολογιστών και Τηλεπικοινωνιών



                      

“ΤΕΧΝΟΛΟΓΙΑ ΛΟΓΙΣΜΙΚΟΥ”

ΔΙΑΡΚΕΙΑ:  11/10 – 18/11

Μέλη Ομάδας:
Γιαγτζόγλου Κοσμάς 20077
Λιάμης 'Άγγελος 19100
Ρακιπ Λιτσι 21132
Χριστόδουλος Τικταπανίδης 19070
Ντανίδου Μαρία 3062




Μέλη της ομάδας
Top Charts API (Γιαγτζόγλου Κοσμάς 20077)
Information-API-wikipedia(API) (Ντανίδου Μαρία 3062)
Βελτιστοποίηση Υφιστάμενου Λογισμικού για MusicPlayer (Λιάμης Άγγελος 19100)
Λειτουργια κουμπιων Liked Songs & Recently Played(Ρακιπ Λιτσι 21132) 
Ενσωμάτωση του Music Player App(Χριστόδουλος Τικταπανίδης 19070)


Top Charts API (Γιαγτζόγλου Κοσμάς 20077)
Αρχικά δημιουργήθηκε μια καρτέλα με όνομα “Top Charts”. Στην συνέχεια δημιουργήθηκαν 5 ξεχωριστές κλάσεις στις οποίες προστέθηκε το κατάλληλο API ανάλογα με την λειτουργία του κάθε ένα. Συγκεκριμένα τα API που δημιουργήθηκαν είναι :

Top Artist API
Top Albums API
Top Playlist API
Top Podcast API
Top Songs API
Τα API χρησιμοποιήθηκαν  από το Deezer.com και λειτουργούν κανονικά. Ο κώδικας:



package org.example.musicplayeruserinterfacewithjavafx;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class APITopAlbums {

    private static final String API_URL = "https://api.deezer.com/chart/0/albums?limit=50";

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API_URL)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Σφάλμα κατά τη λήψη των albums: HTTP " + response.code());
                return;
            }

            String jsonResponse = response.body().string();

            // Εμφάνιση ολόκληρης της απόκρισης για debugging
            System.out.println("Full JSON Response: " + jsonResponse);

            // Parsing JSON
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonArray albumsArray = jsonObject.getAsJsonArray("data");

            System.out.println("Number of albums received: " + albumsArray.size());

            // Εμφάνιση albums
            System.out.println("Albums List:");
            for (int i = 0; i < albumsArray.size(); i++) {
                JsonObject album = albumsArray.get(i).getAsJsonObject();
                String albumTitle = album.get("title").getAsString();
                String artistName = album.getAsJsonObject("artist").get("name").getAsString();

                System.out.println((i + 1) + ". Album: " + albumTitle + ", Artist: " + artistName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

1.Ορισμός URL και Δημιουργία Client
API_URL: Σταθερά που περιέχει τη διεύθυνση του API. Το URL δείχνει στη λίστα των "top albums" του Deezer, περιορίζοντας σε 50 αποτελέσματα
OkHttpClient: Εργαλείο για να εκτελούνται HTTP αιτήματα.
2.Δημιουργία και Εκτέλεση Αιτήματος
Request: Δημιουργεί ένα HTTP GET αίτημα προς το URL.
Response: Εκτελεί το αίτημα με τη newCall() και επιστρέφει την απόκριση.
3.Έλεγχος Απόκρισης
Ελέγχει αν το αίτημα ολοκληρώθηκε επιτυχώς (response.isSuccessful()).
Αν όχι, εκτυπώνει το HTTP κωδικό σφάλματος και τερματίζει.
4.Λήψη JSON Απόκρισης
jsonResponse: Λαμβάνει το σώμα της απόκρισης ως string.
Εκτυπώνει την πλήρη JSON απόκριση για debugging.
5.Ανάλυση JSON με Gson
JsonParser: Μετατρέπει το JSON string σε αντικείμενο JsonObject.
albumsArray: Εξάγει τη λίστα των albums από το πεδίο data.
6.Εκτύπωση Πληροφοριών
albumsArray.size(): Εκτυπώνει τον αριθμό των albums που έλαβε.
Βρόχος (for):
oalbumTitle: Παίρνει τον τίτλο του album από το JSON.
oartistName: Παίρνει το όνομα του καλλιτέχνη από το υποαντικείμενο artist.
oΕκτυπώνει τον αριθμό, τον τίτλο του album και τον καλλιτέχνη.


Αποτέλεσμα












Information-API-wikipedia(API) (Ντανίδου Μαρία 3062)

Δημιουργία κλάσης APIArtistInformation:
Η κλάση APIArtistInformtion δημιουργεί διασύνδεση με την API της Wikipedia. Ο λόγος που δημιουργήθηκε είναι για να λαμβάνει όλες τις πληροφορίες για τους καλλιτέχνες, όπως το βιογραφικό τους, τα τραγούδια τους, τις συνεργασίες που έχουν κάνει. Γενικά ότι πληροφορίες έχει το Wikipedia για τον κάθε καλλιτέχνη και να τις τυπώνει σε μια σελίδα.
Αρχικά ορίζεται το URL του API της Wikipedia.
Στην βασική ροή main():
Ορίζεται στατικά το όνομα του καλλιτέχνη του οποίου θέλουμε να ανακτήσουμε τις πληροφορίες. Σε επόμενη χρόνο, το όνομα του καλλιτέχνη θα λαμβάνεται από search bar ή πατώντας το όνομα του καλλιτέχνη ή πατώντας το όνομα του καλλιτέχνη στο playlist.
Καλείται η μέθοδος fetchArtistInfo ώστε να ανακτηθούν οι πληροφορίες.
Στην μέθοδο fetchArtistInfo:
Δημιουργείται κατάλληλο URL για τον API, κωδικοποιώντας το όνομα του καλλιτέχνη που ψάχνουμε.
Αποστέλλεται ένα αίτημα χρησιμοποιώντας την βιβλιοθήκη OkHttp.
Ελέγχεται αν η απάντηση είναι επιτυχής.
Εάν όχι, εμφανίζεται το παρακάτω μήνυμα σφάλματος: 
"Σφάλμα κατά τη λήψη του καλλιτέχνη: HTTP "
Και τέλος, επιστρέφεται το αποτέλεσμα της μεθόδου parseWikipediaResponse.
Στην μέθοδο parseWikipediaResponse:
Αναλύεται η JSON απάντηση που επιστρέφεται από τον API
Επιστρέφονται οι πληροφορίες που έχουν συλλεχτεί σε μορφή κειμένου
Σε περίπτωση που δεν είναι δυνατή η λήψη των πληροφοριών εμφανίζεται το παρακάτω μήνυμα σφάλματος: "Δεν υπάρχουν πληροφορίες για τον καλλιτέχνη: <artistName> "


Λειτουργια κουμπιων Liked Songs & Recently Played(Ρακιπ Λιτσι 21132) 

Η χρήση πατητών Labels με λειτουργία refresh, όπως στα Liked Songs και Recently Viewed, προσφέρει μια δυναμική και χρήσιμη εμπειρία χρήστη. Οι μηχανισμοί παρακολούθησης της λίστας μέσω της ObservableList, καθώς και η χρήση listeners για την ανανέωση του περιεχομένου, διασφαλίζουν ότι η οθόνη παραμένει πάντα επικαιροποιημένη και αντανακλά τα δεδομένα σε πραγματικό χρόνο, ανάλογα με τις ενέργειες του χρήστη.


Liked Songs (Αγαπημένα Τραγούδια) - Πατητό Label με Λειτουργία Refresh
Το Liked Songs είναι ένα στοιχείο του UI (User Interface) που εμφανίζει το κείμενο "Αγαπημένα Τραγούδια". Όταν ο χρήστης κάνει κλικ πάνω στο Label, το περιεχόμενο της λίστας των αγαπημένων τραγουδιών ανανεώνεται και εμφανίζεται στον χρήστη. Η ανανέωση αυτή επιτυγχάνεται μέσω της χρήσης μηχανισμών παρακολούθησης, όπως η ObservableList. Κάθε φορά που ο χρήστης προσθέτει ή αφαιρεί τραγούδια από τη λίστα των αγαπημένων του, το περιεχόμενο του ScrollPane (που εμφανίζει τα αγαπημένα τραγούδια) ανανεώνεται αυτόματα.
Η αλληλεπίδραση με το Label προκαλεί το refresh, διασφαλίζοντας ότι τα αγαπημένα τραγούδια εμφανίζονται πάντα επικαιροποιημένα και ακριβή. Αυτός ο μηχανισμός καθιστά δυνατή την αυτόματη ενημέρωση της προβολής χωρίς την ανάγκη για επιπλέον ενέργειες από τον χρήστη.
Recently Viewed (Πρόσφατα Ακουσμένα Τραγούδια) - Πατητό Label με Λειτουργία Refresh
Το Recently Viewed είναι ένα άλλο Label που εμφανίζει το κείμενο "Πρόσφατα Ακουσμένα". Όταν ο χρήστης το πατήσει, το περιεχόμενο της λίστας των πρόσφατων ακουσμάτων ανανεώνεται και εμφανίζεται σε ένα ScrollPane.
Η ανανέωση της λίστας επιτυγχάνεται με τη χρήση ενός μηχανισμού παρακολούθησης, ο οποίος προσθέτει κάθε νέο τραγούδι που ακούγεται στη λίστα των πρόσφατων ακουσμάτων. Κάθε φορά που ο χρήστης ακούει ένα νέο τραγούδι, αυτό προστίθεται στη λίστα και, όταν το Label "Recently Viewed" πατηθεί, το περιεχόμενο του ScrollPane ανανεώνεται για να εμφανίσει τα πιο πρόσφατα ακουσμένα τραγούδια.
Για την υλοποίηση αυτής της ανανέωσης χρησιμοποιούνται listeners όπως το setOnMouseClicked ή το setOnAction, οι οποίοι ενεργοποιούνται όταν ο χρήστης κάνει κλικ στο αντίστοιχο Label. Αυτοί οι listeners επιτρέπουν την ανανέωση του περιεχομένου του ScrollPane, επιδεικνύοντας τα νέα ή αναδιαρθρωμένα τραγούδια στην οθόνη.

Βελτιστοποίηση Υφιστάμενου Λογισμικού για MusicPlayer (Λιάμης Άγγελος 19100)
Ο κώδικας οργανώνει τραγούδια σε δύο λίστες (πρόσφατα και αγαπημένα) και τα εμφανίζει δυναμικά στη διεπαφή χρήστη. Η αρχιτεκτονική του βασίζεται στη χρήση FXML για την εμφάνιση και την αλληλεπίδραση με τα δεδομένα.
Ο Κώδικας:
package org.example.musicplayeruserinterfacewithjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Song;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private HBox favoriteContainer;

    List<Song> recentlyPlayed;
    List<Song> favorites;


    @FXML
    private HBox recentlyPlayedContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        recentlyPlayed = new ArrayList<>(getRecentlyPlayed());
        favorites=new ArrayList<>(getFavorites());
        try {
            for (Song song : recentlyPlayed) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("song.fxml"));

                VBox vBox = fxmlLoader.load();
                SongController songController = fxmlLoader.getController();
                songController.setData(song);

                recentlyPlayedContainer.getChildren().add(vBox);
            }
            for (Song song : favorites) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("song.fxml"));

                VBox vBox = fxmlLoader.load();
                SongController songController = fxmlLoader.getController();
                songController.setData(song);

                favoriteContainer.getChildren().add(vBox);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private List<Song> getRecentlyPlayed() {
        List<Song> ls = new ArrayList<>();

        Song song = new Song();
        song.setName("I Hronia Mou");
        song.setArtist("Mpelafon");
        song.setCover("/img/Mpelafon.png");
        ls.add(song);

        song = new Song();
        song.setName("Pellegrino");
        song.setArtist("Mikros Kleftis,Dof Twogee");
        song.setCover("/img/mk_pelegrino.png");
        ls.add(song);

        song = new Song();
        song.setName("20/20");
        song.setArtist("Mikros Kleftis,LEX");
        song.setCover("/img/mk_20-20.png");
        ls.add(song);

        song = new Song();
        song.setName("Smooth Kai Hardcorila");
        song.setArtist("Thitis,Sadomas,ΔΠΘ,Buzz,MadnessKey");
        song.setCover("/img/thitis_sado.png");
        ls.add(song);

        song = new Song();
        song.setName("Top Boys");
        song.setArtist("Sadam,LEX,Dof Twogee");
        song.setCover("/img/sadam.png");
        ls.add(song);


        song = new Song();
        song.setName("Gioconda");
        song.setArtist("Immune");
        song.setCover("/img/Gioconda-Immune.png");
        ls.add(song);


        return ls;
    }

    public List<Song> getFavorites() {
        List<Song> ls = new ArrayList<>();
        Song song = new Song();
        song.setName("Top 50");
        song.setArtist("Global");
        song.setCover("/img/Top50Charts.png");
        ls.add(song);

        song = new Song();
        song.setName("Top 50");
        song.setArtist("Podcast");
        song.setCover("/img/Podcast.png");
        ls.add(song);

        song = new Song();
        song.setName("Top 50");
        song.setArtist("Albums");
        song.setCover("/img/TopAlbums.png");
        ls.add(song);

        song = new Song();
        song.setName("Top 50");
        song.setArtist("Artists");
        song.setCover("/img/TopArtists1.png");
        ls.add(song);
        song = new Song();
        song.setName("Top 50");
        song.setArtist("Playlist");
        song.setCover("/img/TopPlaylist.png");
        ls.add(song);

        return ls;
    }

}

1.Κλάση HelloController
Η κλάση αυτή είναι ο κύριος ελεγκτής της διεπαφής. Υλοποιεί τη διεπαφή Initializable, που σημαίνει ότι η μέθοδος initialize θα εκτελείται αυτόματα κατά την εκκίνηση της σκηνής.
HBox recentlyPlayedContainer: Κοντέινερ (δοχείο) τύπου οριζόντιας διάταξης που περιέχει τα τραγούδια που αναπαράχθηκαν πρόσφατα.
HBox favoriteContainer: Παρόμοιο κοντέινερ για τα τραγούδια που έχουν οριστεί ως αγαπημένα.
List<Song> recentlyPlayed, favorites: Δύο λίστες που αποθηκεύουν αντικείμενα Song (τα τραγούδια).

2.Μέθοδος initialize
Αυτή η μέθοδος εκτελείται κατά την αρχικοποίηση του περιβάλλοντος. Περιλαμβάνει:
1.Αρχικοποίηση των λιστών:
oΚαλεί τη μέθοδο getRecentlyPlayed για να δημιουργήσει τη λίστα με τα πρόσφατα αναπαραχθέντα τραγούδια.
oΚαλεί μια παρόμοια μέθοδο (getFavorites, αν υπήρχε υλοποιημένη) για τη λίστα των αγαπημένων.
2.Δημιουργία του UI για κάθε τραγούδι:
oΓια κάθε τραγούδι στη λίστα recentlyPlayed:
Δημιουργείται ένας φορτωτής FXML (FXMLLoader) για να φορτώσει το αρχείο song.fxml, που αντιπροσωπεύει την εμφάνιση ενός τραγουδιού.
Ο φορτωτής δημιουργεί ένα αντικείμενο τύπου VBox (κάθετη διάταξη), το οποίο περιλαμβάνει το τραγούδι.
Παίρνει τον ελεγκτή του song.fxml (SongController) μέσω του fxmlLoader.getController() και του περνά δεδομένα τραγουδιού με τη μέθοδο setData.
Προσθέτει το VBox στο κοντέινερ recentlyPlayedContainer.
oΗ ίδια διαδικασία επαναλαμβάνεται για τη λίστα favorites.
3.Διαχείριση εξαιρέσεων:
oΑν παρουσιαστεί πρόβλημα στη φόρτωση του FXML αρχείου, εμφανίζεται μια εξαίρεση (RuntimeException).




3.Μέθοδος getRecentlyPlayed
Επιστρέφει μια λίστα από αντικείμενα Song που αντιπροσωπεύουν τραγούδια. Για κάθε τραγούδι:
Ορίζεται το όνομά του (setName), ο καλλιτέχνης (setArtist), και η διαδρομή της εικόνας του εξωφύλλου (setCover).
Η μέθοδος δημιουργεί και επιστρέφει μια λίστα τραγουδιών (List<Song>).

4.FXML και Ελεγκτές
Ο κώδικας αλληλεπιδρά με το αρχείο song.fxml, το οποίο περιγράφει την εμφάνιση ενός τραγουδιού στη διεπαφή. Ο ελεγκτής (SongController) χειρίζεται τα δεδομένα του τραγουδιού και τα εμφανίζει σωστά στο UI.

5.Λειτουργίες και Διαδικασία
1.Δημιουργία λιστών τραγουδιών:
oΗ getRecentlyPlayed επιστρέφει τραγούδια που αναπαράχθηκαν πρόσφατα. Αντίστοιχη μέθοδος (π.χ. getFavorites) μπορεί να υλοποιηθεί για τα αγαπημένα.
2.Δημιουργία αντικειμένων UI:
oΓια κάθε τραγούδι δημιουργείται δυναμικά ένα γραφικό αντικείμενο VBox που προστίθεται στα αντίστοιχα κοντέινερ (HBox).
3.Δυναμική εμφάνιση:
oΟ κώδικας φορτώνει το UI για κάθε τραγούδι με χρήση του αρχείου song.fxml.

Περιληπτική Αναφορά για την Ολοκληρωτική Αναδιαμόρφωση του Git Repository
Η παρούσα εργασία αφορά την πλήρη αναδιαμόρφωση του Git repository που φιλοξενεί τον κώδικα για το MusicPlayer. Στόχος ήταν να βελτιωθεί η οργάνωση, η συνεργατικότητα και η δυνατότητα συντήρησης του έργου. Οι βασικές αλλαγές περιλαμβάνουν: Αναδιάταξη των φακέλων και αρχείων: Ο κώδικας οργανώθηκε σε ξεκάθαρες κατηγορίες, όπως src για τον πηγαίο κώδικα, resources για τα γραφικά και docs για τεκμηρίωση. Βελτιστοποίηση του ιστορικού commit: 
Εφαρμόστηκαν squash commits και αναδρομική συγχώνευση (rebase) για καθαρότερο ιστορικό, διασφαλίζοντας τη σαφήνεια των αλλαγών. 
Δημιουργία νέων branches: Εισήχθησαν ξεχωριστά branches για ανάπτυξη (development), παραγωγή (main), και συγκεκριμένα χαρακτηριστικά (feature branches), προάγοντας μια στρατηγική Git Flow.
 Αυτόματη ενσωμάτωση ελέγχων ποιότητας: Ρυθμίστηκαν εργαλεία CI/CD, όπως το GitHub Actions, για την αυτόματη εκτέλεση δοκιμών και τη διασφάλιση ότι ο κώδικας παραμένει σταθερός. Οι αλλαγές αυτές διευκολύνουν την ανάπτυξη νέων χαρακτηριστικών, ενισχύουν τη συνεργασία μεταξύ των μελών της ομάδας και μειώνουν τα σφάλματα κατά την ενσωμάτωση κώδικα. 
Το νέο repository είναι πλέον πιο ευέλικτο, ευανάγνωστο και έτοιμο να υποστηρίξει μελλοντικές επεκτάσεις του MusicPlayer.


Λειτουργικότητα κουμπιών(Χριστόδουλος Τικταπανίδης 19070).
Άλλαξα τα πρώην Label σε Buttons και τους έβαλα ενα απλό print
ώστε να φαίνεται η λειτουργικότητα τους και τα στοίχισα .Άλλαξα το σχήμα και το χρώμα με την βοήθεια της css και πρόσθεσα την λειτουργεία highlight
όταν  κάνουμε hover το κάθε κουμπί.


