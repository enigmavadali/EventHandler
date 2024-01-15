package org.hca.middleware.EventHandler.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    @Bean("elasticRestTemplate")
    public RestTemplate getElasticRestTemplate(){
        return new RestTemplate();
    }
}
