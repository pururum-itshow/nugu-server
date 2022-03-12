package com.nugu.greenery.NuguServer.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;


//entity
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Humidity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private long id;

    @NonNull
    @Column(name = "datetime_value", nullable = false)
    private Timestamp datetime;

    @Column(name = "humidity_value", nullable = false)
    private String humidity;
}
