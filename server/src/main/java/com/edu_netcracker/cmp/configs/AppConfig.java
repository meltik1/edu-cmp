package com.edu_netcracker.cmp.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.research.ws.wadl.Resource;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.File;


@Configuration
public class AppConfig {


//    @Value("${server.ssl.trust-store}")
//    private String keyStore;
//    @Value("${server.ssl.trust-store-password}")
//    private String keyStorePassword;

    @Bean
    public ObjectMapper defaultMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

//    @Profile("dad")
//    @Bean
//    RestTemplate restTemplate() throws Exception {
//        SSLContext sslContext = new SSLContextBuilder()
//                .loadTrustMaterial(new File(keyStore), keyStorePassword.toCharArray())
//                .build();
//        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
//        CloseableHttpClient httpClient = HttpClients.custom()
//                .setSSLSocketFactory(socketFactory)
//                .build();
//        HttpComponentsClientHttpRequestFactory factory =
//                new HttpComponentsClientHttpRequestFactory(httpClient);
//        return new RestTemplate(factory);
//    }

}
