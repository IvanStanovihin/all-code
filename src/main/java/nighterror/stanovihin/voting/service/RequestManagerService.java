package nighterror.stanovihin.voting.service;

import io.github.bucket4j.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class RequestManagerService {

    private final Map<String, Bucket> requestsCounter = new HashMap<>();

    public ConsumptionProbe isAllow(String phone) {
        if (!requestsCounter.containsKey(phone)) {
            createBucket(phone);
        }
        Bucket phoneBucket = requestsCounter.get(phone);
        return phoneBucket.tryConsumeAndReturnRemaining(1);
    }

    private void createBucket(String phone) {
        Refill refill = Refill.intervally(10, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(10, refill);
        Bucket bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
        requestsCounter.put(phone, bucket);
    }
}
