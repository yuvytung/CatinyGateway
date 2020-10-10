package org.regitiny.catiny.gateway.service.kafka.producer;

import lombok.extern.log4j.Log4j2;
import org.regitiny.catiny.gateway.service.kafka.Producer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MasterProducer
{

//  @Value("${kafka.producer.services.catiny-uaa.reply-topic}")
//  private String replyTopic;
//
//  private final Producer<String,String> producer;
//
//  public MasterProducer(Producer<String, String> producer)
//  {
//    this.producer = producer;
//  }
//
//  @SuppressWarnings("rawtypes")
//  @Bean(name = "ReplyingKafkaTemplate_CatinyUAA_Master")
//  public ReplyingKafkaTemplate replyKafkaTemplateImplementation()
//  {
//    return producer.replyKafkaTemplate(replyTopic);
//  }

  //  quy tắc đặt tên reply topic : replyKafka_<service request>_<service reply>_<Entity>_<Other>
  //  quy tắc đặt tên method : replyKafka__<service reply>_<Entity>_<Other>
  @SuppressWarnings("rawtypes")
  @Bean
  public ReplyingKafkaTemplate replyKafkaCatinyUAAMaster(Producer<String,String> producer)
  {
    return producer.replyKafkaTemplate("replyKafka_CatinyMessenger_CatinyUAA_Master");
  }

}
