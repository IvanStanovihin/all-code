package nighterror.stanovihin.voting.controller;

import nighterror.stanovihin.voting.model.StatisticRequest;
import nighterror.stanovihin.voting.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        statisticService.getStatistic(from, to, intervals, artists);
        return ResponseEntity.ok().build();
    }
}
