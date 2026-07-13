package com.planhub.controller;

import com.planhub.service.TimetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/timetable")
@RequiredArgsConstructor
public class TimetableController {

    private final TimetableService timetableService;


    /**
     * GET /api/timetable?officeCode=..&schoolCode=..&grade=1&classNm=3&fromDate=20260706&toDate=20260710
     */
    @GetMapping
    public String getTimetable(
            @RequestParam String officeCode,
            @RequestParam String schoolCode,
            @RequestParam String grade,
            @RequestParam String classNm,
            @RequestParam String fromDate,   // YYYYMMDD
            @RequestParam String toDate      // YYYYMMDD
    ) {
        return timetableService.getTimetable(officeCode, schoolCode, grade, classNm, fromDate, toDate);
    }
}