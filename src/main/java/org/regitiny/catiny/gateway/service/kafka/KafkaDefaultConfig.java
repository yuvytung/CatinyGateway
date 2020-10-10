package org.regitiny.catiny.gateway.service.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaDefaultConfig
{
  @Value("${kafka.bootstrap-servers}")
  private String bootstrapServers;

  @SuppressWarnings("rawtypes")
  @Value("${kafka.producer.key.serializer}")
  private Class producerKeySerializer;

  @SuppressWarnings("rawtypes")
  @Value("${kafka.producer.value.serializer}")
  private Class producerValueSerializer;

  @SuppressWarnings("rawtypes")
  @Value("${kafka.consumer.key.deserializer}")
  private Class consumerKeyDeserializer;

  @SuppressWarnings("rawtypes")
  @Value("${kafka.consumer.value.deserializer}")
  private Class consumerValueDeserializer;

  @Value("${kafka.consumer.group.id}")
  private String consumerGroupId;

  @Value("${kafka.consumer.auto.offset.reset}")
  private String consumerAutoOffsetReset;

  public Map<String, Object> producerProperties()
  {
    Map<String, Object> props = new HashMap<>();

    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producerKeySerializer);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producerValueSerializer);

    return props;
  }

  public Map<String, Object> consumerProperties()
  {
    Map<String, Object> props = new HashMap<>();

    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumerKeyDeserializer);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumerValueDeserializer);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumerAutoOffsetReset);

    return props;
  }

}
