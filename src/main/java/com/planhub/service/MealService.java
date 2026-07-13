package com.planhub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class MealService {

    // 💡 급식 정보 전용 키 주입
    @Value("${neis.api.meal-key}")
    private String neisKey;

    private final String neisBaseUrl = "https://open.neis.go.kr/hub/mealServiceDietInfo";

    public String getMeal(String officeCode, String schoolCode, String date, String mealType) {
        RestTemplate restTemplate = new RestTemplate();

        URI uri = UriComponentsBuilder
                .fromHttpUrl(neisBaseUrl)
                .queryParam("KEY", neisKey)
                .queryParam("Type", "json")
                .queryParam("pIndex", 1)
                .queryParam("pSize", 10)
                .queryParam("ATPT_OFCDC_SC_CODE", officeCode)
                .queryParam("SD_SCHUL_CODE", schoolCode)
                .queryParam("MLSV_YMD", date)
                .queryParam("MMEAL_SC_CODE", mealType)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();

        try {
            return restTemplate.getForObject(uri, String.class);
        } catch (Exception e) {
            return "{\"error\": \"급식 정보 조회 실패: " + e.getMessage() + "\"}";
        }
    }
}