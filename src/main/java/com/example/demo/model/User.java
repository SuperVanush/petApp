package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "userId")
@Table(name = "users")
public class User {

    @Id                                                 // создает уникальность
    @GeneratedValue(strategy = GenerationType.UUID) // что бы id генерился автоматически на уровне БД
    @Column(name = "user_id")                            // описание имени столбца
    private UUID id;

    @Column(name = "login")
    private String login;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Bill> listBills;
}