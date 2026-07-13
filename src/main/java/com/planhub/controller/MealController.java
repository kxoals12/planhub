package com.planhub.controller;

import com.planhub.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meal")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    @GetMapping
    public String getMeal(
            @RequestParam String officeCode,
            @RequestParam String schoolCode,
            @RequestParam String date,       // YYYYMMDD
            @RequestParam String mealType    // 2=중식, 3=석식
    ) {
        return mealService.getMeal(officeCode, schoolCode, date, mealType);
    }
}