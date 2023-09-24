package org.hca.middleware.EventHandler.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hca.common.domain.KafkaMessage;
import org.hca.common.domain.KafkaMessageMetadata;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final Logger LOGGER = LogManager.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "testTopic", groupId = "test")
    public void consumeMessage(KafkaMessage message){
        KafkaMessageMetadata data = message.getMetadata();
        LOGGER.info("the elastic cluster is " + data.getElasticIndex() );
        LOGGER.info("json is " + message.getBody());
    }
}
