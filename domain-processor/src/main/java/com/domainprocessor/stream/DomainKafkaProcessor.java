package com.domainprocessor.stream;

import com.entity.Domain;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
@Slf4j
public class DomainKafkaProcessor {

    @Bean
    public Function<KStream<String, Domain>, KStream<String, Domain>> domainProcessor() {

        /*
         * Kafka Stream gives us flexible way to process messages from kafka topic
         * - Removes boiler plate code , everything moved to yaml file
         * - Only have to write business logic in terms of processing message
         * */

        return kstream -> kstream.filter((key, domain) -> {
            if (domain.isDead()) {
                log.info(String.format("Inactive Domain: [%s]", domain.getDomain()));
            } else {
                log.info(String.format("Active Domain: [%s]", domain.getDomain()));
            }
            return !domain.isDead();
        });

    }
}
