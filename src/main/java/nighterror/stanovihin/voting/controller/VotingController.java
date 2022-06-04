package nighterror.stanovihin.voting.controller;

import nighterror.stanovihin.voting.exception.ArtistNotFoundException;
import nighterror.stanovihin.voting.model.Vote;
import nighterror.stanovihin.voting.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("votes")
public class VotingController {

    @Autowired
    private VoteRepository voteRepository;


    @PostMapping
    public ResponseEntity<?> vote(@RequestBody Vote vote){
        String artist = vote.getArtist();
        String phone = vote.getPhone();
        if (artist == null || phone == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        System.out.println(String.format("Received request from phone: %s, vote for artist: %s", phone, artist));
        try {
            voteRepository.addVote(artist);
        }catch(ArtistNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<?> getVotes(){
        String votes = voteRepository.getVotes();
        return ResponseEntity.ok(votes);
    }

}
