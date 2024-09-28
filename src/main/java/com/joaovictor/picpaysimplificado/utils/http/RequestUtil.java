package com.joaovictor.picpaysimplificado.utils.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Log4j2
public class RequestUtil {
    public static Optional<HttpServletRequest> getHttpRequestByContext() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
              .filter(ServletRequestAttributes.class::isInstance)
              .map(ServletRequestAttributes.class::cast)
              .map(ServletRequestAttributes::getRequest);
    }

    public static ResponseEntity<String> doPost(String url, Map<String, Object> queryString, Object body) throws JsonProcessingException {
        return doRequest(url, queryString, null, body, HttpMethod.POST, null);
    }

    public static ResponseEntity<String> doRequest(String url, Map<String, Object> queryString, Map<String, String> headers, Object body, HttpMethod method, Integer timeout) throws JsonProcessingException {
        RestTemplate restTemplate = getRestTemplate(timeout);

        if (queryString == null) queryString = new HashMap<>();
        if (headers == null) headers = new HashMap<>();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.forEach(httpHeaders::set);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        queryString.forEach(builder::queryParam);

        log.info("REQUEST URL:: [{}] - {}", method, url);
        log.info("REQUEST QUERY STRING:: {}", queryString);
        log.info("REQUEST HEADERS:: {}", headers);

        HttpEntity<?> entity;
        if (body == null) {
            entity = new HttpEntity<>(httpHeaders);
        } else {
            entity = new HttpEntity<>(JsonUtil.toJson(body), httpHeaders);
        }

        final var exchange = restTemplate.exchange(
              builder.toUriString(),
              method,
              entity,
              String.class
        );

        log.info("RESPONSE STATUS CODE:: {}", exchange.getStatusCode().value());
        log.info("RESPONSE BODY:: {}", exchange.getBody());
        return exchange;
    }

    @NotNull
    private static RestTemplate getRestTemplate(Integer timeout) {
        RestTemplate restTemplate;
        if (timeout == null) {
            restTemplate = new RestTemplate();
        } else {
            HttpComponentsClientHttpRequestFactory clientRequestFactory = new HttpComponentsClientHttpRequestFactory();
            clientRequestFactory.setConnectTimeout(timeout);
            clientRequestFactory.setConnectionRequestTimeout(timeout);
            restTemplate = new RestTemplate(clientRequestFactory);
        }
        return restTemplate;
    }
}
