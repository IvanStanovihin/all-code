package nighterror.stanovihin.voting.model;

public class VotesCount {

    private String name;
    private Long votes;

    public VotesCount(String name, Long votes) {
        this.name = name;
        this.votes = votes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }
}
