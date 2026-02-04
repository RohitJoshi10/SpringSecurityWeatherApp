package com.Caching.Weather_Service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Weather {

    @Id
    @GeneratedValue
    private Long id;

    private String city;

    private String forecast;


}
