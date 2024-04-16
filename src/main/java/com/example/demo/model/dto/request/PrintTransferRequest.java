package com.example.demo.model.dto.request;

import com.example.demo.service.emun.TypeBill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrintTransferRequest {

    private String userLogin;
    private String billName;
    private TypeBill typeBill;
}
