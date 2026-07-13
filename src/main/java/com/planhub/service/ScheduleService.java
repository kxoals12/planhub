package com.planhub.service;

import com.planhub.dto.Dtos;
import com.planhub.repository.ScheduleRepository;
import com.planhub.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public List<Dtos.Response> getByClassRoom(Long classRoomId) {
        return scheduleRepository.findByClassRoomIdOrderByDateAsc(classRoomId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Dtos.Response create(Dtos.Request request) {
        Schedule schedule = toEntity(request);
        Schedule saved = scheduleRepository.save(schedule);
        return toResponse(saved);
    }

    public Dtos.Response update(Long id, Dtos.Request request) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow();
        // update fields
        schedule.setSubject(request.getSubject());
        schedule.setTitle(request.getTitle());
        schedule.setDate(request.getDate());
        schedule.setType(request.getType());
        schedule.setTeacherName(request.getTeacherName());
        schedule.setDescription(request.getDescription());
        // 학급, 교사 변경은 생략 (필요시 구현)
        Schedule saved = scheduleRepository.save(schedule);
        return toResponse(saved);
    }

    public void delete(Long id) {
        scheduleRepository.deleteById(id);
    }

    public Dtos.ConflictInfo checkConflict(Long classRoomId, LocalDate date, Long excludeId) {
        var conflicts = scheduleRepository.findConflicts(classRoomId, date, excludeId);
        if (conflicts.isEmpty()) return null;
        String classRoomLabel = conflicts.get(0).getClassRoom().getLabel();
        List<String> subjects = conflicts.stream().map(Schedule::getSubject).collect(Collectors.toList());
        return Dtos.ConflictInfo.builder()
                .classRoomLabel(classRoomLabel)
                .date(date)
                .conflictingSubjects(subjects)
                .build();
    }

    private Dtos.Response toResponse(Schedule schedule) {
        if (schedule == null) return null;
        return Dtos.Response.builder()
                .id(schedule.getId())
                .subject(schedule.getSubject())
                .title(schedule.getTitle())
                .date(schedule.getDate())
                .type(schedule.getType())
                .teacherName(schedule.getTeacherName())
                .description(schedule.getDescription())
                .conflict(schedule.isConflict())
                .classRoomLabel(schedule.getClassRoom() != null ? schedule.getClassRoom().getLabel() : null)
                .classRoomId(schedule.getClassRoom() != null ? schedule.getClassRoom().getId() : null)
                .schoolName(schedule.getClassRoom() != null && schedule.getClassRoom().getSchool() != null ? schedule.getClassRoom().getSchool().getName() : null)
                .build();
    }

    private Schedule toEntity(Dtos.Request request) {
        // 실제 환경에서는 ClassRoom, Teacher를 DB에서 조회해야 함. 여기서는 id만 세팅
        Schedule.ScheduleBuilder builder = Schedule.builder()
                .subject(request.getSubject())
                .title(request.getTitle())
                .date(request.getDate())
                .type(request.getType())
                .teacherName(request.getTeacherName())
                .description(request.getDescription());
        // 학급, 교사 연관관계는 별도 서비스에서 주입 필요
        return builder.build();
    }
}
