package br.com.ead.payment.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class PaymentsMetricsCounter {

    private Map<String, Counter> paymentsMetricsCounter = null;

    private final MeterRegistry meterRegistry;
    public PaymentsMetricsCounter(MeterRegistry meterRegistry){
        this.meterRegistry = meterRegistry;
        createCounters();
    }

    public void createCounters() {
        paymentsMetricsCounter = new HashMap<String, Counter>();
        for (PaymenMetricTypes type : PaymenMetricTypes.values()) {
            Counter petCounter = Counter.builder("payments_status")
                    .tag("type", type.name())
                    .description("Count payments by status")
                    .register(meterRegistry);
            paymentsMetricsCounter.put(type.name(), petCounter);
        }
    }

    public void incrementPayemnt(PaymenMetricTypes type){
        Counter counter = paymentsMetricsCounter.get(type.name());
        counter.increment();
    }

}
