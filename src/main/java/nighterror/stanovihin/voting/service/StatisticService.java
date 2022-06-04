package nighterror.stanovihin.voting.service;

import javafx.beans.binding.MapExpression;
import nighterror.stanovihin.voting.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticService {

    private final Map<Long, Vote> statisticStorage = new HashMap<>();

    public void addStatisticRecord(Vote vote) {
        Long currentMillis = System.currentTimeMillis();
        statisticStorage.put(currentMillis, vote);
    }

    public String getStatistic(Long requestFrom, Long requestTo, Long intervals, String artists) {
        Map<Long, ArrayList<Vote>> votesInInterval = new HashMap<>();
        double intervalRange = (requestTo - requestFrom) / (double) intervals;
        System.out.println("Interval range: " + intervalRange);
        //находим записи подходящие ко времени from и to
        for (Map.Entry<Long, Vote> entry : statisticStorage.entrySet()) {
            Long entryTime = entry.getKey();
            if (entryTime >= requestFrom && entryTime <= requestTo) {
                Long intervalIndex = (long) ((entryTime - requestFrom) / intervalRange);
                System.out.println("Interval index: " + intervalIndex);
                addInInterval(votesInInterval, intervalIndex, entry.getValue());
            }
        }
        System.out.println(votesInInterval);
        return null;
    }

    private void addInInterval(Map<Long, ArrayList<Vote>> votesInInterval, long index, Vote vote) {
        ArrayList<Vote> intervalVotes;
        if (votesInInterval.containsKey(index)) {
            intervalVotes = votesInInterval.get(index);
        } else {
            intervalVotes = new ArrayList<>();
        }
        intervalVotes.add(vote);
        votesInInterval.put(index, intervalVotes);
    }


}
