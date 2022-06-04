package nighterror.stanovihin.voting.service;

import com.google.gson.Gson;
import javafx.beans.binding.MapExpression;
import nighterror.stanovihin.voting.model.*;
import nighterror.stanovihin.voting.util.ArtistsInitializer;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class StatisticService {

    private final Map<Long, Vote> statisticStorage = new HashMap<>();

    public void addStatisticRecord(Vote vote) {
        Long currentMillis = System.currentTimeMillis();
        statisticStorage.put(currentMillis, vote);
    }

    public String getStatistic(Long requestFrom, Long requestTo, Long countIntervals, String artists) {
        Map<Integer, Map<String, Long>> intervalArtistStat = new HashMap<>();
        long requestArea = requestTo - requestFrom;
        double intervalRange = (requestTo - requestFrom) / (double) countIntervals;
        System.out.println("Request area: " + requestArea);
        System.out.println("Interval range: " + intervalRange);
        int intervalIndex = 1;
        double leftBorder = requestFrom;
        double rightBorder = requestFrom + intervalRange;
        //находим записи подходящие ко времени from и to
        for (int i = 0; i < countIntervals; i++) {
            //Статистика для рассматриваемого интервала
            Map<String, Long> artistsVoteCount = ArtistsInitializer.initArtists();
            //перебираем все записи в хранилище, ищем те которые входят в интервал
            for (Map.Entry<Long, Vote> voteEntry : statisticStorage.entrySet()) {
                Long voteAddTime = voteEntry.getKey();
                Vote vote = voteEntry.getValue();
                String artist = vote.getArtist();
                //если запись входит в интервал, добавляем её в статистику
                if (voteAddTime >= leftBorder && voteAddTime <= rightBorder) {
                    Long artistCountVotes = artistsVoteCount.get(artist);
                    artistsVoteCount.put(artist, artistCountVotes + 1);
                }
            }
            intervalArtistStat.put(i, artistsVoteCount);

            leftBorder = rightBorder;
            rightBorder = leftBorder + intervalRange;
        }
        System.out.println(intervalArtistStat);
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

    private Map<Long, GetVotesResponse> countIntervalVotes(Map<Long, ArrayList<Vote>> votesInInterval) {
        //статистика для интервалов
        Map<Long, GetVotesResponse> countVotesInInterval = new HashMap<>();
        //перебираем каждый интервал
        for (Map.Entry<Long, ArrayList<Vote>> interval : votesInInterval.entrySet()) {
            GetVotesResponse statForInterval = countForArtist(interval.getValue());
            countVotesInInterval.put(interval.getKey(), statForInterval);
        }
        return countVotesInInterval;
    }

    //Считаем для каждого артиста в интервале голоса
    private GetVotesResponse countForArtist(ArrayList<Vote> interval) {
        Map<String, Long> artistVotes = new HashMap<>();
        for (Vote vote : interval) {
            String artist = vote.getArtist();
            if (artistVotes.containsKey(artist)) {
                Long countVotes = artistVotes.get(artist);
                artistVotes.put(artist, countVotes + 1);
            } else {
                artistVotes.put(artist, 1L);
            }
        }
        ArrayList<VotesCount> votesCounts = new ArrayList<>();
        for (Map.Entry<String, Long> entry : artistVotes.entrySet()) {
            votesCounts.add(new VotesCount(entry.getKey(), entry.getValue()));
        }
        return new GetVotesResponse(votesCounts.toArray(new VotesCount[0]));
    }


}
