package nighterror.stanovihin.voting.service;

import com.google.gson.Gson;
import nighterror.stanovihin.voting.exception.ArtistNotFoundException;
import nighterror.stanovihin.voting.model.GetVotesResponse;
import nighterror.stanovihin.voting.model.Vote;
import nighterror.stanovihin.voting.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    public void addVote(Vote vote) throws ArtistNotFoundException {
        voteRepository.addVote(vote.getArtist());
    }


    public String getVotes(){
        GetVotesResponse response = voteRepository.getVotes();
        Gson gson = new Gson();
        return gson.toJson(response);
    }
}
