package com.nugu.greenery.NuguServer.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;


//entity
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="humidity_tb")
public class Humidity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "humidity_id", nullable = false)
    private Long id;

    @Column(name = "humidity_value", nullable = false)
    @NonNull
    private int value;
    @NonNull
    @Column(name = "humitidy_date", nullable = false)
    private LocalDateTime datetime;
}
