package com.planhub.repository;

import com.planhub.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    /** 특정 학급의 전체 일정 */
    List<Schedule> findByClassRoomIdOrderByDateAsc(Long classRoomId);

    /** 특정 날짜 범위의 학교 전체 일정 */
    @Query("""
        SELECT s FROM Schedule s
        WHERE s.classRoom.school.id = :schoolId
          AND s.date BETWEEN :from AND :to
        ORDER BY s.date ASC
        """)
    List<Schedule> findBySchoolAndDateRange(
        @Param("schoolId") Long schoolId,
        @Param("from") LocalDate from,
        @Param("to") LocalDate to
    );

    /** 같은 학급 + 같은 날 일정 (충돌 검사용) */
    @Query("""
        SELECT s FROM Schedule s
        WHERE s.classRoom.id = :classRoomId
          AND s.date = :date
          AND (:excludeId IS NULL OR s.id <> :excludeId)
        """)
    List<Schedule> findConflicts(
        @Param("classRoomId") Long classRoomId,
        @Param("date") LocalDate date,
        @Param("excludeId") Long excludeId
    );

    /** D-Day 기준 정렬, 특정 학급 */
    @Query("""
        SELECT s FROM Schedule s
        WHERE s.classRoom.id = :classRoomId
          AND s.date >= CURRENT_DATE
        ORDER BY s.date ASC
        """)
    List<Schedule> findUpcoming(@Param("classRoomId") Long classRoomId);
}
