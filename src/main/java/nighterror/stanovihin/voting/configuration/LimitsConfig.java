package nighterror.stanovihin.voting.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Properties;

@Component
@PropertySource("classpath:limits.properties")
public class LimitsConfig {

    @Value("${refill.token}")
    int refillTokens;

    @Value("${refill.time}")
    int refillTime;

    @Value("${bandwidth.capacity}")
    int bandwidthCapacity;

    public int getRefillTokens() {
        return refillTokens;
    }

    public void setRefillTokens(int refillTokens) {
        this.refillTokens = refillTokens;
    }

    public int getRefillTime() {
        return refillTime;
    }

    public void setRefillTime(int refillTime) {
        this.refillTime = refillTime;
    }

    public int getBandwidthCapacity() {
        return bandwidthCapacity;
    }

    public void setBandwidthCapacity(int bandwidthCapacity) {
        this.bandwidthCapacity = bandwidthCapacity;
    }
}
