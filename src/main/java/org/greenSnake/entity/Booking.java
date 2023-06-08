package org.greenSnake.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = "booking")
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String sort;
    @Column(name = "bush_count")
    private int bushCount;
    private int phone;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    private boolean status;


    @Override
    public String toString() {
        return "\nЗаказ номер:"+
                id +"\n" +
                "Сорт клубники: " +
                sort + " В кол-ве:"
                + bushCount + "Кг.";
    }
}
