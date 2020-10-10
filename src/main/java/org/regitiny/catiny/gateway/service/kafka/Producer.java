package org.regitiny.catiny.gateway.service.kafka;

import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;

import java.util.Map;

@Getter
@Setter
@Service
public class Producer<Request, Reply>
{

  private Map<String, Object> producerProperties ;

  private Map<String, Object> consumerProperties ;

  public Producer(KafkaDefaultConfig defaultConfig)
  {
    this.producerProperties= defaultConfig.producerProperties();
    this.consumerProperties= defaultConfig.consumerProperties();
    consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG,"reply:"+consumerProperties.get(ConsumerConfig.GROUP_ID_CONFIG));
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public ReplyingKafkaTemplate replyKafkaTemplate(String replyTopic)
  {
    return  replyKafkaTemplateCustom(replyTopic,(Class<? super Reply>) String.class);
//    return new ReplyingKafkaTemplate(requestProducerFactory(), replyListenerContainer(replyTopic , (Class<? super Reply>) String.class));
  }

  public ReplyingKafkaTemplate<String, Request, Reply> replyKafkaTemplateCustom(String replyTopic,Class<? super Reply> targetType)
  {
    return new ReplyingKafkaTemplate<>(requestProducerFactory(), replyListenerContainer(replyTopic, targetType));
  }

  public KafkaTemplate<String, String> kafkaTemplate()
  {
    return new KafkaTemplate(requestProducerFactory());
  }

  private ProducerFactory<String, Request> requestProducerFactory()
  {
    Map<String, Object> props = producerProperties;

    return new DefaultKafkaProducerFactory<>(props);
  }

  private ConsumerFactory<String, Reply> replyConsumerFactory(Class<? super Reply> targetType)
  {
    Map<String, Object> props = consumerProperties;

    return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(targetType));
  }

  private KafkaMessageListenerContainer<String, Reply> replyListenerContainer(String replyTopic, Class<? super Reply> targetType)
  {
    ContainerProperties containerProperties = new ContainerProperties(replyTopic);
    return new KafkaMessageListenerContainer<>(replyConsumerFactory(targetType), containerProperties);
  }
}
