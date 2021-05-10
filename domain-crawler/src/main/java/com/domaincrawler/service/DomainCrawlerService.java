package com.domaincrawler.service;

import com.domaincrawler.entity.Domain;
import com.domaincrawler.entity.DomainList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class DomainCrawlerService {

  private KafkaTemplate<String, Domain> kafkaTemplate;
  private static final String KAFKA_TOPIC = "web-domains";

  public DomainCrawlerService(KafkaTemplate<String, Domain> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void crawl(String name) {

    Mono<DomainList> domainListMono = WebClient.create()
        .get()
        .uri("https://api.domainsdb.info/v1/domains/search?domain=" + name + "&zone=com")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(DomainList.class);


    domainListMono.subscribe(domainList -> {
      domainList.getDomains()
          .forEach(domain -> {
            kafkaTemplate.send(KAFKA_TOPIC, domain);
            log.info(String.format("Domain message received : [%s]" , domain.getDomain()));
          });
    });

  }
}
