package nighterror.stanovihin.voting.model;

public class GetVotesResponse {

    private VotesCount data[];

    public GetVotesResponse(VotesCount[] data) {
        this.data = data;
    }

    public VotesCount[] getData() {
        return data;
    }

    public void setData(VotesCount[] data) {
        this.data = data;
    }
}
