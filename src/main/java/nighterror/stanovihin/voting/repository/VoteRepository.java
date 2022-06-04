package nighterror.stanovihin.voting.repository;

import com.google.gson.Gson;
import nighterror.stanovihin.voting.exception.ArtistNotFoundException;
import nighterror.stanovihin.voting.model.ArtistsConfig;
import nighterror.stanovihin.voting.model.GetVotesResponse;
import nighterror.stanovihin.voting.model.VotesCount;
import nighterror.stanovihin.voting.util.FilesReader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class VoteRepository {

    private final Map<String, Long> votesStorage = new HashMap<>();

    @PostConstruct
    private void initArtists(){
        Gson gson = new Gson();
        String artistsFile = FilesReader.readFile("src/main/resources/artists.json");
        System.out.println("Read artist init file: " + artistsFile);
        ArtistsConfig artistsConfig = gson.fromJson(artistsFile, ArtistsConfig.class);
        for (String artist : artistsConfig.getArtists()){
            votesStorage.put(artist, 0L);
        }
        printStorage();
    }

    public void addVote(String artist) throws ArtistNotFoundException{
        if (votesStorage.containsKey(artist)) {
            votesStorage.merge(artist, 1L, Long::sum);
        }else{
            throw new ArtistNotFoundException();
        }
    }

    public GetVotesResponse getVotes(){
        ArrayList<VotesCount>votesList = new ArrayList<>();
        for (Map.Entry<String, Long> entry : votesStorage.entrySet()){
            VotesCount votesCount = new VotesCount(entry.getKey(), entry.getValue());
            votesList.add(votesCount);
        }
        return new GetVotesResponse(votesList.toArray(new VotesCount[0]));
    }

    private void printStorage(){
        for (Map.Entry<String, Long>entry : votesStorage.entrySet()){
            System.out.printf("Artist: %s Votes: %s%n", entry.getKey(), entry.getValue());
        }
    }


}
