package nighterror.stanovihin.voting.model;

import java.util.Map;

public class StatisticResponse {

    private Long start;
    private Long end;
    private Integer votes;

    public StatisticResponse(Long start, Long end, Integer votes) {
        this.start = start;
        this.end = end;
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

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }
}
