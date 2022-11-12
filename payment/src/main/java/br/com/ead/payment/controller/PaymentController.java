package br.com.ead.payment.controller;

import br.com.ead.payment.dto.PaymentDto;
import br.com.ead.payment.metrics.PaymenMetricTypes;
import br.com.ead.payment.metrics.PaymentsMetricsCounter;
import br.com.ead.payment.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService service;

    private final PaymentsMetricsCounter metrics;


    public PaymentController(PaymentService service, PaymentsMetricsCounter metrics) {
        this.service = service;
        this.metrics = metrics;
    }

    @PostMapping
    public ResponseEntity<String> doSomething(@RequestBody PaymentDto payment){
        String response = service.doSomething(payment.getValor());
        metrics.incrementPayemnt(PaymenMetricTypes.APPROVED);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
