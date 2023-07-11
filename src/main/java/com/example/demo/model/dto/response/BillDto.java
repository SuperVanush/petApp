package com.example.demo.model.dto.response;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BillDto {

  private int id;
  private String billName;
  private BigDecimal balance;
  private int userId;
}
