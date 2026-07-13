package com.planhub.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 수행평가 일정 엔티티
 */
@Entity
@Table(name = "schedules")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 과목명 (국어, 수학, 영어 ...) */
    @Column(nullable = false, length = 50)
    private String subject;

    /** 수행평가 제목 */
    @Column(nullable = false, length = 200)
    private String title;

    /** 평가 날짜 */
    @Column(nullable = false)
    private LocalDate date;

    /** 평가 유형 (서술형, 지필, 발표, 보고서, 실기, 포트폴리오) */
    @Column(nullable = false, length = 30)
    private String type;

    /** 담당 교사 이름 */
    @Column(nullable = false, length = 100)
    private String teacherName;

    /** 안내 메모 */
    @Column(length = 1000)
    private String description;

    /** 충돌 여부 (같은 학급 + 같은 날 다른 과목 존재 시 true) */
    @Column(nullable = false)
    @Builder.Default
    private boolean conflict = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_room_id", nullable = false)
    private ClassRoom classRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
