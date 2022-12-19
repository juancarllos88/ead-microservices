package br.com.ead.payment.service.impl;

import br.com.ead.payment.annotations.Metric;
import br.com.ead.payment.metrics.PaymenMetricTypes;
import br.com.ead.payment.metrics.PaymentsMetricsCounter;
import br.com.ead.payment.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentsMetricsCounter metrics;

    public PaymentServiceImpl(PaymentsMetricsCounter metrics) {
        this.metrics = metrics;
    }

    @Metric(value = PaymenMetricTypes.APPROVED)
    @Override
    public String doSomething(String param){
        log.info("Begin");
        if(param.equals("juan")){
            metrics.incrementPayemnt(PaymenMetricTypes.REJECTED);
            throw new RuntimeException("deu merda");
        }
        metrics.incrementPayemnt(PaymenMetricTypes.APPROVED);
        log.info("end");
        return param;
    }
}
