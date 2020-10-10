package org.regitiny.catiny.gateway.service.kafka.consumer;


import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MasterConsumer
{
//  đây là mẫu tùy biến
//  final Consumer<String, String> consumer;
//
//  public MasterConsumer(Consumer<String, String> consumer)
//  {
//    this.consumer = consumer;
//  }
//
//  @Bean(name = "requestKafkaListener_CatinyUAA_Master")
//  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> requestKafkaListenerContainerFactory()
//  {
//    return consumer.requestListenerContainerFactory();
//  }
//
//  @KafkaListener(topics = "topic", containerFactory = "requestKafkaListener_CatinyUAA_Master")
//  @SendTo
//  public String receive(String request)
//  {
//    log.debug(request);
//    return request;
//  }


  //  quy tắc đặt tên reply topic : requestKafka_<service request>_<service reply>_<Entity>_<Other>
  //  quy tắc đặt tên method : requestKafka__<service reply>_<Entity>_<Other>

  @KafkaListener(topics = "requestKafka_CatinyMessenger_CatinyUAA_Master", containerFactory = "requestListenerContainerFactory")
  @SendTo
  public String receive(String request)
  {
    log.debug(request);
    return request;
  }
}
