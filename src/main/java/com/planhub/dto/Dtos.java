package com.planhub.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

public class Dtos {

    // ── 수행평가 등록/수정 요청 DTO ──────────────────────────────
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Request {
        private String subject;
        private String title;
        private LocalDate date;
        private String type;
        private String teacherName;
        private String description;
        private Long classRoomId;
        private Long teacherId;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Response {
        private Long id;
        private String subject;
        private String title;
        private LocalDate date;
        private String type;
        private String teacherName;
        private String description;
        private boolean conflict;
        private String classRoomLabel;   // "2-1"
        private Long classRoomId;
        private String schoolName;
    }

    /** 충돌 알림 정보 */
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ConflictInfo {
        private String classRoomLabel;
        private LocalDate date;
        private List<String> conflictingSubjects;
    }

    // ── 학급 DTO (내부 static 클래스로 변경) ──────────────────────
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ClassRoomDto {
        private Long id;
        private int grade;
        private int classNumber;
        private String label;
        private String schoolName;
    }

    // ── 학교 DTO (내부 static 클래스로 변경) ──────────────────────
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class SchoolDto {
        private Long id;
        private String name;
        private String address;
        private String plan;
        private boolean active;
        private int teacherCount;
        private int studentCount;
    }

    // ── 공통 응답 래퍼 (내부 static 클래스로 변경) ─────────────────
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ApiResponse<T> {
        private boolean success;
        private String message;
        private T data;

        public static <T> ApiResponse<T> ok(T data) {
            return ApiResponse.<T>builder().success(true).message("OK").data(data).build();
        }
        public static <T> ApiResponse<T> error(String message) {
            return ApiResponse.<T>builder().success(false).message(message).build();
        }
    }
}