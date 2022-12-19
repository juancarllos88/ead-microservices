package br.com.ead.payment.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Data
@Entity
@Table(name = "TB_WEBHOOK")
public class WebHook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="url")
    private String url;

    @Column(name="company_name")
    private String companyName;

    @Column(name="type")
    private String type;

}
