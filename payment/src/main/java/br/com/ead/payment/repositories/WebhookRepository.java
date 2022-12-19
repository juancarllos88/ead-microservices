package br.com.ead.payment.repositories;

import br.com.ead.payment.models.WebHook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebhookRepository extends JpaRepository<WebHook, Long>{

    List<WebHook> findByCompanyNameAndType(String companyName, String type);

    List<WebHook> findByCompanyName(String companyName);
}