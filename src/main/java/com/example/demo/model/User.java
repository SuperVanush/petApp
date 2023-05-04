package com.example.demo.model;

import lombok.*;

import java.util.List;
import java.util.Objects;

@Builder (toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int id;
    private String name;
    private String login;
    private String password;
    private List<Bill> bills;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(password, user.password) &&
                Objects.equals(login, user.login) && Objects.equals(bills, user.bills);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, login, bills);
    }
}