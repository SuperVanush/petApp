package com.example.demo.model.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillResponse {

  private String message;
  private String login;
  private List<BillDto> billList;
}
