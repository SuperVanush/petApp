package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Builder
@Entity
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@Table(name = "bills")
public class Bill {

    @Id                                                 // создает уникальность
    @GeneratedValue(strategy = GenerationType.IDENTITY) // что бы id генерился автоматически на уровне БД
    @Column(name = "bill_id")                            // описание имени столбца
    private int billId;

    @Column(name = "bill_name")
    private String billName;

    @Column(name = "balance")
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    // отношение к другой сущности, LAZY, потому, что не всегда мне нужно что бы при загрузке счета сразу грузился пользак. потом по запросу загружу
    @JoinColumn(name = "user_id")               // столбец из др таблицы
    private User user;


    @Override                                           // метод сравнения объектов
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return billId == bill.billId && Objects.equals(billName, bill.billName) && Objects.equals(balance, bill.balance)
                && Objects.equals(user, bill.user);
    }

    @Override                                            // метод сравнения объектов
    public int hashCode() {
        return Objects.hash(billId, billName, balance, user);
    }
}
