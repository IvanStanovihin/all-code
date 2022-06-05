package nighterror.stanovihin.voting.util;

import com.google.gson.Gson;
import nighterror.stanovihin.voting.exception.ArtistNotFoundException;
import nighterror.stanovihin.voting.model.ArtistsConfig;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class ArtistsInitializer {

    private String fileName;

    public ArtistsInitializer(String fileName) {
        this.fileName = fileName;
    }

    public Map<String, Long> initArtists() {
        Gson gson = new Gson();
        Map<String, Long> artists = new HashMap<>();
//        URL artistUrl = this.getClass().getClassLoader().getResource("artists.json");
//        File artistsFile = new File(artistUrl.getFile());
        InputStream is = getClass().getResourceAsStream("/artists.json");
        String fileContent = FilesReader.readFileContent(is);
        System.out.println("Read artist init file: " + fileContent);
        ArtistsConfig artistsConfig = gson.fromJson(fileContent, ArtistsConfig.class);
        for (String artist : artistsConfig.getArtists()) {
            artists.put(artist, 0L);
        }
        return artists;
    }

    public Set<String> validateArtists(String artists) throws ArtistNotFoundException {
        if (artists.isEmpty()) {
            return Collections.emptySet();
        }
        Map<String, Long> correctArtists = initArtists();
        Set<String> allowedArtists = new HashSet<>();
        String[] artistsForFilter = artists.trim().split(",");
        for (String artist : artistsForFilter) {
            String trimmedArtists = artist.trim();
            if (correctArtists.containsKey(trimmedArtists)) {
                allowedArtists.add(trimmedArtists);
            } else {
                throw new ArtistNotFoundException();
            }
        }
        return allowedArtists;
    }

}
