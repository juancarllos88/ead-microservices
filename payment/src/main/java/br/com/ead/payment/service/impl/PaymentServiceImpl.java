package br.com.ead.payment.service.impl;

import br.com.ead.payment.annotations.Metric;
import br.com.ead.payment.metrics.PaymenMetricTypes;
import br.com.ead.payment.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Metric(value = PaymenMetricTypes.APPROVED)
    @Override
    public String doSomething(String param) {
        if(param.equals("juan")){
            throw new RuntimeException("deu merda");
        }
        return param;
    }
}
