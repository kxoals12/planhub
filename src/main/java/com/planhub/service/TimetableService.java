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
public class TimetableService {

    // 💡 시간표 정보 전용 키(timetable-key)를 주입받도록 명칭 변경
    @Value("${neis.api.timetable-key}")
    private String neisKey;

    // 고등학교 시간표 (중/초등학교는 misTimetable / elsTimetable로 별도 확장 가능)
    private final String neisBaseUrl = "https://open.neis.go.kr/hub/hisTimetable";

    /**
     * @param officeCode 교육청코드 (ATPT_OFCDC_SC_CODE)
     * @param schoolCode 학교코드 (SD_SCHUL_CODE)
     * @param grade      학년 (1,2,3)
     * @param classNm    반 (1,2,3...)
     * @param fromDate   조회 시작일 (YYYYMMDD)
     * @param toDate     조회 종료일 (YYYYMMDD)
     */
    public String getTimetable(String officeCode, String schoolCode, String grade,
                               String classNm, String fromDate, String toDate) {
        RestTemplate restTemplate = new RestTemplate();

        URI uri = UriComponentsBuilder
                .fromHttpUrl(neisBaseUrl)
                .queryParam("KEY", neisKey) // 주입받은 전용 키 사용
                .queryParam("Type", "json")
                .queryParam("pIndex", 1)
                .queryParam("pSize", 100)
                .queryParam("ATPT_OFCDC_SC_CODE", officeCode)
                .queryParam("SD_SCHUL_CODE", schoolCode)
                .queryParam("GRADE", grade)
                .queryParam("CLASS_NM", classNm)
                .queryParam("TI_FROM_YMD", fromDate)
                .queryParam("TI_TO_YMD", toDate)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();

        try {
            return restTemplate.getForObject(uri, String.class);
        } catch (Exception e) {
            return "{\"error\": \"시간표 정보 조회 실패: \"}";
        }
    }
}