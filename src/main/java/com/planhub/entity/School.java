package com.planhub.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "schools")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 300)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private SubscriptionPlan plan = SubscriptionPlan.FREE;

    private LocalDate subscriptionExpiry;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private List<ClassRoom> classRooms;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private List<Teacher> teachers;

    public enum SubscriptionPlan { FREE, BASIC, STANDARD, PREMIUM }
}
