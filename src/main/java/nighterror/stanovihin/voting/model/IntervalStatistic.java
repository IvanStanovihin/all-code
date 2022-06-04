package nighterror.stanovihin.voting.model;

public class IntervalStatistic {

    private Long start;
    private Long end;
    private Long votes;

    public IntervalStatistic(Long start, Long end, Long votes) {
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

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "IntervalStatistic{" +
                "start=" + start +
                ", end=" + end +
                ", votes=" + votes +
                '}';
    }
}
