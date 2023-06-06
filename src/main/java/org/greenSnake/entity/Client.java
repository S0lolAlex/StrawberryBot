package org.greenSnake.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "client")
@Data
public class Client {
    @Id
    private Long id;
    @Column(name = "chat_id")
    private Long chatId;
    private String name;
    private String phone;
    @OneToMany(mappedBy = "client",cascade = CascadeType.REMOVE)
    private List<Booking> booking = new ArrayList<>();
}
