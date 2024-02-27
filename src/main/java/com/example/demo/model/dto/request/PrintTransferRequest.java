package com.example.demo.model.dto.request;

import com.example.demo.service.BillType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrintTransferRequest {
    private String userLogin;
    private String billName;
    private BillType billType;
}
