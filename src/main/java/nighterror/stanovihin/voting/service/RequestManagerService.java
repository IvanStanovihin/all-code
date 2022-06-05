package nighterror.stanovihin.voting.service;

import io.github.bucket4j.*;
import nighterror.stanovihin.voting.configuration.LimitsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class RequestManagerService {


    @Autowired
    private LimitsConfig limitsConfig;

    private final Map<String, Bucket> requestsCounter = new HashMap<>();

    public ConsumptionProbe isAllow(String phone) {
        if (!requestsCounter.containsKey(phone)) {
            createBucket(phone);
        }
        Bucket phoneBucket = requestsCounter.get(phone);
        return phoneBucket.tryConsumeAndReturnRemaining(1);
    }

    private void createBucket(String phone) {
        int refillTokens = limitsConfig.getRefillTokens();
        int refillTime = limitsConfig.getRefillTime();
        int bandwidth = limitsConfig.getBandwidthCapacity();
        System.out.printf("Refill tokens: %s \nRefill time: %s \nBandwidth capacity: %s%n",
                refillTokens,
                refillTime,
                bandwidth);
        Refill refill = Refill.intervally(refillTokens, Duration.ofMinutes(refillTime));
        Bandwidth limit = Bandwidth.classic(bandwidth, refill);
        Bucket bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
        requestsCounter.put(phone, bucket);
    }
}
