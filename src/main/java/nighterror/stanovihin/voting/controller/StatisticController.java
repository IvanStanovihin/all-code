package nighterror.stanovihin.voting.controller;

import nighterror.stanovihin.voting.exception.ArtistNotFoundException;
import nighterror.stanovihin.voting.model.StatisticRequest;
import nighterror.stanovihin.voting.service.StatisticService;
import nighterror.stanovihin.voting.util.ArtistsInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("votes/stats")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;


    @GetMapping
    public ResponseEntity<?> getStatistic(@RequestParam Long from,
                                          @RequestParam Long to,
                                          @RequestParam Long intervals,

                                          @RequestParam String artists){
        System.out.println("Received artists: " + artists);
        Set<String> allowedArtists = null;
        try {
            ArtistsInitializer artistsInitializer = new ArtistsInitializer("artists.json");
             allowedArtists = artistsInitializer.validateArtists(artists);
        }catch(ArtistNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        String statistic = statisticService.getStatistic(from, to, intervals, allowedArtists);
        return ResponseEntity.ok(statistic);
    }
}
