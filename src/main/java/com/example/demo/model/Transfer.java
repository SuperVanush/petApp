package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@Entity
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@JsonFormat
@Table(name = "transfer")
public class Transfer {

    @Id                                                 // создает уникальность
    @GeneratedValue(strategy = GenerationType.IDENTITY) // что бы id генерился автоматически на уровне БД
    @Column(name = "transfer_id")                            // описание имени столбца
    private int transferId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id")
    private User toUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_bill_id")
    private Bill fromBill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_bill_id")
    private Bill toBill;

    @Column(name = "sum_transfer")
    private BigDecimal sumTransfer;

    @Column(name = "date_time_transfer")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'   'HH:mm:ss")
    private LocalDateTime localDateTime;

    @Override                                           // метод сравнения объектов
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return transferId == transfer.transferId && Objects.equals(fromUser, transfer.fromUser) && Objects.equals(toUser, transfer.toUser)
                && Objects.equals(fromBill, transfer.fromBill) && Objects.equals(toBill, transfer.toBill)
                && Objects.equals(sumTransfer, transfer.sumTransfer) && Objects.equals(localDateTime, transfer.localDateTime);
    }

    @Override                                            // метод сравнения объектов
    public int hashCode() {
        return Objects.hash(fromUser, toUser, fromBill, toUser, sumTransfer, localDateTime);
    }
}
