package com.planhub.controller;

import com.planhub.dto.Dtos;
import com.planhub.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 관리자 REST API
 * Base URL: /api/admin
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")  // 개발용 — 운영 시 구체적인 도메인으로 제한할 것
public class AdminController {

    private final ScheduleService scheduleService;

    // ── GET /api/schedules?classRoomId={id}
    @GetMapping
    public ResponseEntity<List<Dtos.Response>> list(
        @RequestParam Long classRoomId
    ) {
        return ResponseEntity.ok(scheduleService.getByClassRoom(classRoomId));
    }

    // ── POST /api/schedules
    @PostMapping
    public ResponseEntity<Dtos.Response> create(
        @RequestBody Dtos.Request request
    ) {
        return ResponseEntity.ok(scheduleService.create(request));
    }

    // ── PUT /api/schedules/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Dtos.Response> update(
        @PathVariable Long id,
        @RequestBody Dtos.Request request
    ) {
        return ResponseEntity.ok(scheduleService.update(id, request));
    }

    // ── DELETE /api/schedules/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        scheduleService.delete(id);
        return ResponseEntity.ok(Map.of("message", "삭제 완료"));
    }

    // ── GET /api/schedules/conflict-check?classRoomId={id}&date={yyyy-MM-dd}&excludeId={id}
    @GetMapping("/conflict-check")
    public ResponseEntity<Dtos.ConflictInfo> conflictCheck(
        @RequestParam Long classRoomId,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam(required = false) Long excludeId
    ) {
        Dtos.ConflictInfo info = scheduleService.checkConflict(classRoomId, date, excludeId);
        return ResponseEntity.ok(info);   // null이면 충돌 없음
    }
}
