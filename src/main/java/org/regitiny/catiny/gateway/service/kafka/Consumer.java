package org.regitiny.catiny.gateway.service.kafka;

import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;

import java.util.Map;

@Getter
@Setter
@Service
public class Consumer<Request, Reply>
{
  private Map<String, Object> producerProperties ;

  private Map<String, Object> consumerProperties ;

  public Consumer(KafkaDefaultConfig defaultConfig)
  {
    this.producerProperties= defaultConfig.producerProperties();
    this.consumerProperties= defaultConfig.consumerProperties();
    consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG,"request:"+consumerProperties.get(ConsumerConfig.GROUP_ID_CONFIG));

  }

  @Bean
  @SuppressWarnings("unchecked")
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Request>> requestListenerContainerFactory()
  {
    return requestListenerContainerFactoryCustom((Class<? super Request>) String.class);
  }

  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Request>> requestListenerContainerFactoryCustom(Class<? super Request> targetType)
  {
    ConcurrentKafkaListenerContainerFactory<String, Request> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(requestConsumerFactory(targetType));
    factory.setReplyTemplate(replyTemplate());
    return factory;
  }

  public KafkaTemplate<String, Reply> replyTemplate()
  {
    return new KafkaTemplate<>(replyProducerFactory());
  }

  private ProducerFactory<String, Reply> replyProducerFactory()
  {
    Map<String, Object> props = producerProperties;

    return new DefaultKafkaProducerFactory<>(props);
  }

  private ConsumerFactory<String, Request> requestConsumerFactory(Class<? super Request> targetType)
  {
    Map<String, Object> props = consumerProperties;

    return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(targetType));
  }
}

