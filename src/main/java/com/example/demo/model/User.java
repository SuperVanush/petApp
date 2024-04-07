package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Builder
@Entity
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id                                                 // создает уникальность
    @GeneratedValue(strategy = GenerationType.IDENTITY) // что бы id генерился автоматически на уровне БД
    @Column(name = "user_id")                            // описание имени столбца
    private int userId;

    @Column(name = "login")
    private String login;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Bill> listBills;

    @Override                                           // метод сравнения объектов
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && Objects.equals(userName, user.userName) && Objects.equals(password, user.password)
                && Objects.equals(login, user.login) && Objects.equals(listBills, user.listBills);
    }

    @Override                                            // метод сравнения объектов
    public int hashCode() {
        return Objects.hash(userId, userName, login, password);
    }
}
