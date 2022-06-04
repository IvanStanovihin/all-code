package nighterror.stanovihin.voting.model;

import java.util.Arrays;
import java.util.Map;

public class StatisticResponse {

   IntervalStatistic[] data;

    public StatisticResponse(IntervalStatistic[] data) {
        this.data = data;
    }

    public IntervalStatistic[] getData() {
        return data;
    }

    public void setData(IntervalStatistic[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StatisticResponse{" +
                "data=" + Arrays.toString(data) +
                '}';
    }
}
