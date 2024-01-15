package org.hca.middleware.EventHandler.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hca.common.domain.KafkaMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ElasticSearchService {

    private static final Logger LOGGER = LogManager.getLogger(ElasticSearchService.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier("elasticRestTemplate")
    private RestTemplate restTemplate;

    @Value("${elasticsearch.host}")
    private String elasticHost;

    public void createDocumentInElastic(KafkaMessage kafkaMessage){
        String messagePayload = "";
        try{
            messagePayload = this.objectMapper.writeValueAsString(kafkaMessage);
        } catch(Exception e){
            LOGGER.error("error",e);
        }
        HttpEntity<String> elasticRequestPayload = new HttpEntity<String>(messagePayload,addElasticHeaders());
        String URL = this.elasticHost + kafkaMessage.getMetadata().getElasticIndex() + "/_doc/" + kafkaMessage.getMetadata().getUUID();
        try{
            ResponseEntity<String> response = this.restTemplate.exchange(URL, HttpMethod.PUT,elasticRequestPayload, String.class);
        } catch (Exception e){
            LOGGER.error("Error while creating doc in elastic ", e);
        }
        LOGGER.info("Document created in elastic for UUID " + kafkaMessage.getMetadata().getUUID());
    }

    private HttpHeaders addElasticHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept","application/json");
        return headers;
    }

}
