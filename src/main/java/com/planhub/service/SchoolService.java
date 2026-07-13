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
public class SchoolService {

    // 💡 학교 정보 전용 키 주입
    @Value("${neis.api.type.school-key:${neis.api.school-key}}")
    private String neisKey;

    private final String neisBaseUrl = "https://open.neis.go.kr/hub/schoolInfo";

    public String searchSchoolFromNeis(String keyword) {
        RestTemplate restTemplate = new RestTemplate();

        URI uri = UriComponentsBuilder
                .fromHttpUrl(neisBaseUrl)
                .queryParam("KEY", neisKey)
                .queryParam("Type", "json")
                .queryParam("pIndex", 1)
                .queryParam("pSize", 10)
                .queryParam("SCHUL_NM", keyword)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();

        try {
            return restTemplate.getForObject(uri, String.class);
        } catch (Exception e) {
            return "{\"error\": \"학교 검색 실패: " + e.getMessage() + "\"}";
        }
    }
}