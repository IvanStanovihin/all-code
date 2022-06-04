package nighterror.stanovihin.voting.model;

import java.util.Arrays;

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

    @Override
    public String toString() {
        return "GetVotesResponse{" +
                "data=" + Arrays.toString(data) +
                '}';
    }


}
