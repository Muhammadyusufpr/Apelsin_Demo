package com.apelsin.service.integration;

import com.apelsin.dto.response.CardResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UzcardCardService {
    @Value("${uzcard.url}")
    private String uzCardUrl;
    @Value("${uzcard.username}")
    private String username;
    @Value("${uzcard.password}")
    private String password;




    public CardResponseDTO getCardByNumber(String cardNumber) {
        RestTemplate restTemplate = new RestTemplate();
        String url = uzCardUrl + "/v1/card/getByCardNumber/" + cardNumber;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(username, password);

        HttpEntity httpEntity = new HttpEntity(headers);

        ResponseEntity<CardResponseDTO> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, CardResponseDTO.class);
        System.out.println(response.getBody());
        return response.getBody();
    }

}
