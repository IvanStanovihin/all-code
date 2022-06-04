package nighterror.stanovihin.voting.model;

import java.util.Map;

public class StatisticResponse {

    private Long start;
    private Long end;
    //data
    private Map<Long, GetVotesResponse> intervalSplittedStatistic;
    private Long votes;

    public StatisticResponse(Long start, Long end, Map<Long, GetVotesResponse> intervalSplittedStatistic, Long votes) {
        this.start = start;
        this.end = end;
        this.intervalSplittedStatistic = intervalSplittedStatistic;
        this.votes = votes;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public Map<Long, GetVotesResponse> getIntervalSplittedStatistic() {
        return intervalSplittedStatistic;
    }

    public void setIntervalSplittedStatistic(Map<Long, GetVotesResponse> intervalSplittedStatistic) {
        this.intervalSplittedStatistic = intervalSplittedStatistic;
    }

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }
}
