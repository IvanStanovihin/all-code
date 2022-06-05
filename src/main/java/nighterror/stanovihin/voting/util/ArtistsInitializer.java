package nighterror.stanovihin.voting.util;

import com.google.gson.Gson;
import nighterror.stanovihin.voting.exception.ArtistNotFoundException;
import nighterror.stanovihin.voting.model.ArtistsConfig;

import java.util.*;

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

    public static Set<String>validateArtists(String artists) throws ArtistNotFoundException {
        if (artists.isEmpty()){
            return Collections.emptySet();
        }
        Map<String, Long>correctArtists = initArtists();
        Set<String>allowedArtists = new HashSet<>();
        String[]artistsForFilter = artists.trim().split(",");
        for (String artist : artistsForFilter){
            String trimmedArtists = artist.trim();
            if (correctArtists.containsKey(trimmedArtists)){
                allowedArtists.add(trimmedArtists);
            }else{
                throw new ArtistNotFoundException();
            }
        }
        return allowedArtists;
    }

}
