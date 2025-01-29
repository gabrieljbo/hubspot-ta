package tech.gabrieljbo.lab.hubspotta.crosscutting.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Configuration
public class RestClientConfiguration {

    @Value( "${http.request.timeout}" )
    private int httpRequestTimeout;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        var clientHttpRequestFactory  = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(httpRequestTimeout);
        clientHttpRequestFactory.setConnectionRequestTimeout(httpRequestTimeout);

        var restTemplate = new RestTemplateBuilder()
                .requestFactory(() -> clientHttpRequestFactory)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return restTemplate;
    }

}
