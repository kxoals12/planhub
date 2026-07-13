package com.planhub.controller;

import com.planhub.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schools")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // 💡 프론트엔드 오리진 허용 (CORS 해결)
public class SchoolController {

    private final SchoolService schoolService;

    @GetMapping("/search")
    public ResponseEntity<String> searchSchool(@RequestParam String keyword) {
        String result = schoolService.searchSchoolFromNeis(keyword);
        return ResponseEntity.ok(result);
    }
}