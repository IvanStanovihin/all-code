package nighterror.stanovihin.voting.controller;

import io.github.bucket4j.ConsumptionProbe;
import nighterror.stanovihin.voting.exception.ArtistNotFoundException;
import nighterror.stanovihin.voting.model.Vote;
import nighterror.stanovihin.voting.repository.VoteRepository;
import nighterror.stanovihin.voting.service.RequestManagerService;
import nighterror.stanovihin.voting.service.VoteService;
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
    private VoteService voteService;

    @Autowired
    private RequestManagerService requestManagerService;


    @PostMapping
    public ResponseEntity<?> vote(@RequestBody Vote vote){
        System.out.println(String.format("Received request from phone: %s, vote for artist: %s", vote.getPhone(),
                vote.getArtist()));
        if (vote.getArtist() == null || vote.getPhone() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        ConsumptionProbe probe = requestManagerService.isAllow(vote.getPhone());
        if (!probe.isConsumed()){
            System.out.printf("Request DENIED for phone: %s%n", vote.getPhone() );
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).
                    header("x-ratelimit-limit", "100")
                    .header("x-ratelimit-remaining", Long.toString(probe.getRemainingTokens()))
                    .header("x-ratelimit-reset", Long.toString(probe.getNanosToWaitForReset()))
                    .build();
        }else{
            System.out.printf("Request allowed for phone: %s%n", vote.getPhone());
        }
        try {
            voteService.addVote(vote);
        }catch(ArtistNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).
                header("x-ratelimit-limit", "100")
                .header("x-ratelimit-remaining", Long.toString(probe.getRemainingTokens()))
                .header("x-ratelimit-reset", Long.toString(probe.getNanosToWaitForReset()))
                .build();
    }

    @GetMapping
    public ResponseEntity<?> getVotes(){
        String votes = voteService.getVotes();
        return ResponseEntity.ok(votes);
    }

}
