# Spring Kafka Streams using Spring Cloud Streams

## Endpoint
- http://localhost:8080/domain/lookup/facebook - to pull all facebook related web domain names

## Kafka Docker Steps 
- Run below command tp pull docker compose file from confluent (docker-compose.yml)
```unix
curl --silent --output docker-compose.yml \
  https://raw.githubusercontent.com/confluentinc/cp-all-in-one/6.1.1-post/cp-all-in-one-community/docker-compose.yml
```
- Run below command 
```unix
docker-compose up -d
```
- Verify docker is up 
```unix
docker-compose ps
```

- Topic creation 
```unix

docker-compose exec broker kafka-topics \
  --create \
  --bootstrap-server localhost:9092 \
  --replication-factor 1 \
  --partitions 1 \
  --topic web-domains
  
docker-compose exec broker kafka-topics \
  --create \
  --bootstrap-server localhost:9092 \
  --replication-factor 1 \
  --partitions 1 \
  --topic active.web-domains
  
docker-compose exec broker kafka-topics \
  --create \
  --bootstrap-server localhost:9092 \
  --replication-factor 1 \
  --partitions 1 \
  --topic inactive.web-domains    

```

## Microservices
- 'domain-crawler' - uses Spring Kafka
- 'domain-processor' - uses Spring Cloud Stream with Kafka Streams binder
- 'domain-service' - uses Spring Cloud Stream with Kafka Streams binder

## Architecture
![architecture](architecture.png)

## Related Links
- Kafka setup: https://docs.confluent.io/platform/cu...​
- Public Domain API: https://domainsdb.info/
