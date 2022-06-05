package nighterror.stanovihin.voting.service;

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

    public String getStatistic(Long requestFrom, Long requestTo, Long countIntervals, Set<String>allowedArtists) {
        ArrayList<IntervalStatistic>intervalStatistics = new ArrayList<>();
        long requestArea = requestTo - requestFrom;
        long intervalRange = (requestTo - requestFrom) / countIntervals;
        System.out.println("Request area: " + requestArea);
        System.out.println("Interval range: " + intervalRange);
        int intervalIndex = 1;
        long leftBorder = requestFrom;
        long rightBorder = requestFrom + intervalRange;
        System.out.println("Artists: " + allowedArtists);
        //находим записи подходящие ко времени from и to
        for (int i = 0; i < countIntervals; i++) {
            Long votes = 0L;
            if (allowedArtists != null && !allowedArtists.isEmpty()){
                votes = getVotesInIntervalForArtists(leftBorder, rightBorder, allowedArtists);
            }else{
                votes = getVotesInInterval(leftBorder, rightBorder);
            }
            intervalStatistics.add(new IntervalStatistic(leftBorder, rightBorder, votes));

            leftBorder = rightBorder;
            rightBorder = leftBorder + intervalRange;
        }
        System.out.println(intervalStatistics);
        return null;
    }

    //перебираем все записи в хранилище, считаем те которые входят в интервал
    private Long getVotesInInterval(long leftBorder, long rightBorder){
        long votes = 0;
        for (Map.Entry<Long, Vote> voteEntry : statisticStorage.entrySet()) {
            Long voteAddTime = voteEntry.getKey();
            //если запись входит в интервал, добавляем её в статистику
            if (voteAddTime >= leftBorder && voteAddTime <= rightBorder) {
                votes++;
            }
        }
        return votes;
    }

    //перебираем все записи в хранилище, считаем те которые входят в интервал с проверкой артиста
    private Long getVotesInIntervalForArtists(long leftBorder, long rightBorder, Set<String>allowedArtists){
        System.out.println("Allowed artists: " + allowedArtists);
        long votes = 0;
        for (Map.Entry<Long, Vote> voteEntry : statisticStorage.entrySet()) {
            Long voteAddTime = voteEntry.getKey();
            String artist = voteEntry.getValue().getArtist();
            //если запись входит в интервал, добавляем её в статистику
            if (voteAddTime >= leftBorder && voteAddTime <= rightBorder && allowedArtists.contains(artist)) {
                votes++;
            }
        }
        return votes;
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
