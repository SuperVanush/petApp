package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "bills")
public class Bill {

    @Id                                                 // создает уникальность
    @GeneratedValue(strategy = GenerationType.UUID) // что бы id генерился автоматически на уровне БД
    @Column(name = "bill_id")  // описание имени столбца
    @EqualsAndHashCode.Include
    private UUID billId;

    @Column(name = "bill_name")
    private String billName;

    @Column(name = "balance")
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    // отношение к другой сущности, LAZY, потому, что не всегда мне нужно что бы при загрузке счета сразу грузился пользак. потом по запросу загружу
    @JoinColumn(name = "user_id")               // столбец из др таблицы
    private User user;
}
