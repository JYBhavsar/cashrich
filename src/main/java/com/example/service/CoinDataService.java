package com.example.service;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.model.CoinData;
import com.example.repository.CoinDataRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CoinDataService {


    private static final String API_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest";
    private static final String API_KEY = "27ab17d1-215f-49e5-9ca4-afd48810c149";

    @Autowired
    private CoinDataRepository coinDataRepository;

    public String getCoinData(Long userId, String symbols) throws JsonMappingException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        // Set the headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CMC_PRO_API_KEY", API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the entity with headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Create the URL with query parameters
        String url = String.format("%s?symbol=%s", API_URL, symbols);

        System.out.println("Url------::" + url);

        // Make the request and handle response
        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception, e.g., return a default value or throw a custom exception
            return null;
        }
        System.out.println("responseEntity.getStatusCode()------>>"+responseEntity.getStatusCode());
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            
        	String response = responseEntity.getBody();
            
        	JSONObject jsonObj = new JSONObject(response.toString());
            System.out.println("API-response-------::" + jsonObj.toString());
            
            ObjectMapper mapper = new ObjectMapper();
            JsonNode actualObj = mapper.readTree(jsonObj.toString());
            JsonNode data = actualObj.get("data");
            Iterator<JsonNode> iterator = data.elements();

            while (iterator.hasNext()) {
                JsonNode symbolData = iterator.next();
                String symbol = symbolData.get("symbol").asText();
                String name = symbolData.get("name").asText();
                long circulatingSupply = symbolData.get("circulating_supply").asLong();
                double price = symbolData.get("quote").get("USD").get("price").asDouble();

                System.out.println("Symbol: " + symbol);
                System.out.println("Name: " + name);
                System.out.println("Circulating Supply: " + circulatingSupply);
                System.out.println("Price: " + price);
                System.out.println();
           }
            
//            JsonNode data = actualObj.get("data");
//            Iterator<Map.Entry<String, JsonNode>> fields = data.fields();
//            while (fields.hasNext()) {
//                Map.Entry<String, JsonNode> entry = fields.next();
//                String symbol = entry.getKey();
//                String name = entry.getValue().get("name").asText();
//                double price = entry.getValue().get("quote").get("USD").get("price").asDouble();
//
//                System.out.println("Symbol: " + symbol + ", Name: " + name + ", USD Price: " + price);
//            }
            
            CoinData coinData = new CoinData();
            coinData.setUserId(userId);
            coinData.setSymbol(symbols);
            coinData.setData(response);
            coinData.setTimestamp(new Date());
            coinDataRepository.save(coinData);
            
            return jsonObj.toString();
        } else {
            // Handle non-200 responses
            System.err.println("Failed to fetch data. Status code: " + responseEntity.getStatusCode());
            return null;
        }
    }
}
