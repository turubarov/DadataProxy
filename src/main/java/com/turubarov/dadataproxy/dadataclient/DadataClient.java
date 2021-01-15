package com.turubarov.dadataproxy.dadataclient;

import com.turubarov.dadataproxy.dadataclient.domain.DadataResponse;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;

public class DadataClient {

    private static final String API_KEY = "5961ba10e3f34e52cf21672961a09598aafc4420";
    private static final String SUGGEST_URL = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/address";

    public static DadataResponse suggestAdress(String query) {
        WebClient webClient = WebClient.create();
        HashMap<String, String> headersMap = new HashMap<>();
        headersMap.put("Authorization", "Token " + API_KEY);
        headersMap.put("Accept", "application/json");

        DadataResponse dadataResponse = webClient.post().uri(SUGGEST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(httpHeaders -> httpHeaders.setAll(headersMap))
                .body(BodyInserters.fromObject("{\"query\":\""+query+"\"}"))
                .exchange().block()
                .bodyToMono(DadataResponse.class)
                .block();

        return dadataResponse;
    }
}
