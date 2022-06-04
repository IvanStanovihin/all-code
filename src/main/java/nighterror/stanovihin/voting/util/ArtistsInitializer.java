package nighterror.stanovihin.voting.util;

import com.google.gson.Gson;
import nighterror.stanovihin.voting.model.ArtistsConfig;

import java.util.HashMap;
import java.util.Map;

public class ArtistsInitializer {

    public static Map<String, Long>initArtists(){
        Gson gson = new Gson();
        Map<String, Long>artists = new HashMap<>();
        String artistsFile = FilesReader.readFile("src/main/resources/artists.json");
        System.out.println("Read artist init file: " + artistsFile);
        ArtistsConfig artistsConfig = gson.fromJson(artistsFile, ArtistsConfig.class);
        for (String artist : artistsConfig.getArtists()){
            artists.put(artist, 0L);
        }
        return artists;
    }

}
