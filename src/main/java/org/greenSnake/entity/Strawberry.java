package org.greenSnake.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "strawberry")
@Data
public class Strawberry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private float price;
    @Column(name = "bush_count")
    private int bushCount;
    private boolean status;
}
