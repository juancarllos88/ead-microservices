package br.com.ead.payment.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class FilmesCompradosMetricsGauge implements MeterBinder {


    @Override
    public void bindTo(MeterRegistry meterRegistry) {
        Gauge.builder("filmes_comprados", this, value -> obterQuantidadeFilmesComprados())
                .description("Quantidade de filmes comprados")
                .tags(Tags.of(Tag.of("data", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))))
                .baseUnit("marianno") //uma palavra adicionada a chave da sua métrica pra você encontrar mais facilmente na lista de informações expostas
                .register(meterRegistry);
    }

    public Integer obterQuantidadeFilmesComprados() {
        return new Random().nextInt(8);
    }
}
