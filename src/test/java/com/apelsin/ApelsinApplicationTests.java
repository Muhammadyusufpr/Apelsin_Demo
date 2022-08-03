package com.apelsin;

import com.apelsin.dto.response.UserTestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class ApelsinApplicationTests {

    @Test
    void post() {
        RestTemplate restTemplate = new RestTemplate();
        UserTestDTO dto = new UserTestDTO();
        dto.setName("Alish");
        dto.setStatus("active");
        dto.setGender("male");
        dto.setEmail("test@mail.ru");


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer 0b153649ba4ca92044c46379d093f1868e94e42299ad77372f614cfd537e1511");

        HttpEntity<UserTestDTO> request = new HttpEntity<UserTestDTO>(dto, headers);

        String response = restTemplate.postForObject("https://gorest.co.in/public/v2/users", request, String.class);
        System.out.println(response);

    }

}
