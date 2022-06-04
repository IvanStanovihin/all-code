package nighterror.stanovihin.voting.service;

import com.google.gson.Gson;
import javafx.beans.binding.MapExpression;
import nighterror.stanovihin.voting.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatisticService {

    private final Map<Long, Vote> statisticStorage = new HashMap<>();

    public void addStatisticRecord(Vote vote) {
        Long currentMillis = System.currentTimeMillis();
        statisticStorage.put(currentMillis, vote);
    }

    public String getStatistic(Long requestFrom, Long requestTo, Long intervals, String artists) {
        Map<Long, ArrayList<Vote>> votesInInterval = new HashMap<>();
        long requestArea = requestTo - requestFrom;
        double intervalRange = (requestTo - requestFrom) / (double) intervals;
        System.out.println("Request area: " + requestArea);
        System.out.println("Interval range: " + intervalRange);
        //находим записи подходящие ко времени from и to
        for (Map.Entry<Long, Vote> entry : statisticStorage.entrySet()) {
            Long entryTime = entry.getKey();
            if (entryTime >= requestFrom && entryTime <= requestTo) {
                long intervalIndex = (long) ((entryTime - requestFrom) / intervalRange);
                System.out.println("Interval index: " + intervalIndex);
                addInInterval(votesInInterval, intervalIndex, entry.getValue());
            }
        }
        System.out.println(votesInInterval);
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(countIntervalVotes(votesInInterval));
        System.out.println("Statistic json response: " + jsonResponse);
        return jsonResponse;
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

    private Map<Long, GetVotesResponse>countIntervalVotes(Map<Long, ArrayList<Vote>> votesInInterval){
        //статистика для интервалов
        Map<Long, GetVotesResponse>countVotesInInterval = new HashMap<>();
        //перебираем каждый интервал
        for (Map.Entry<Long, ArrayList<Vote>>interval : votesInInterval.entrySet()){
            GetVotesResponse statForInterval = countForArtist(interval.getValue());
            countVotesInInterval.put(interval.getKey(), statForInterval);
        }
        return countVotesInInterval;
    }

    //Считаем для каждого артиста в интервале голоса
    private GetVotesResponse countForArtist(ArrayList<Vote>interval){
        Map<String, Long>artistVotes = new HashMap<>();
        for (Vote vote : interval){
            String artist = vote.getArtist();
            if (artistVotes.containsKey(artist)){
                Long countVotes = artistVotes.get(artist);
                artistVotes.put(artist, countVotes+1);
            }else{
                artistVotes.put(artist, 1L);
            }
        }
        ArrayList<VotesCount>votesCounts = new ArrayList<>();
        for (Map.Entry<String, Long>entry : artistVotes.entrySet()){
            votesCounts.add(new VotesCount(entry.getKey(), entry.getValue()));
        }
        return new GetVotesResponse(votesCounts.toArray(new VotesCount[0]));
    }


}
