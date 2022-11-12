package br.com.ead.payment.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentDto implements Serializable {

    private String valor;

}
