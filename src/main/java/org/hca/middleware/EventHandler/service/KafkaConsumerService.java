package org.hca.middleware.EventHandler.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hca.common.domain.KafkaMessage;
import org.hca.common.domain.KafkaMessageMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final Logger LOGGER = LogManager.getLogger(KafkaConsumerService.class);

    @Autowired
    private ElasticSearchService elasticSearchService;

//    @KafkaListener(topics = "testTopic", groupId = "test")
//    public void consumeMessage(KafkaMessage message){
//        KafkaMessageMetadata data = message.getMetadata();
//        if(data.getFacilityId().equals("1") || data.getFacilityId().equals("5000") || data.getFacilityId().equals("10000") ||data.getFacilityId().equals("15000"))
//            LOGGER.info("Consumed message " + data.getFacilityId());
////        LOGGER.info("the elastic cluster is " + data.getElasticIndex() );
////        LOGGER.info("json is " + message.getBody());
//    }

    @KafkaListener(topics = "testTopic", groupId = "test")
    public void consumeMessage(KafkaMessage message){
        LOGGER.info("Consumed message for UUID " + message.getMetadata().getUUID());
        this.elasticSearchService.createDocumentInElastic(message);
    }
}
