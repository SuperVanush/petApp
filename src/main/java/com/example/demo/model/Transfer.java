package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@JsonFormat
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transfer")
public class Transfer {

    @Id                                                 // создает уникальность
    @GeneratedValue(strategy = GenerationType.UUID) // что бы id генерился автоматически на уровне БД
    @Column(name = "transfer_id")                            // описание имени столбца
    private UUID transferId;

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
}
