package tech.gabrieljbo.lab.hubspotta.crosscutting.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Configuration
public class RestClientConfiguration {

    @Value( "${http.request.timeout}" )
    private int httpRequestTimeout;

    @Bean
    public RestTemplate restTemplate() {
        var clientHttpRequestFactory  = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(httpRequestTimeout);
        clientHttpRequestFactory.setConnectionRequestTimeout(httpRequestTimeout);

        return new RestTemplate(clientHttpRequestFactory);
    }

}
