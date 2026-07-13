package com.planhub.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "class_rooms")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ClassRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int grade;

    @Column(nullable = false)
    private int classNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @OneToMany(mappedBy = "classRoom", cascade = CascadeType.ALL)
    private List<Schedule> schedules;

    public String getLabel() {
        return grade + "-" + classNumber;
    }
}
