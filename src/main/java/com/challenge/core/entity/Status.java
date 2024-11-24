package com.challenge.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "status")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Status {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String description;

}
