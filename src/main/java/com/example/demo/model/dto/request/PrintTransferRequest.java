package com.example.demo.model.dto.request;

import com.example.demo.service.TypeBill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PrintTransferRequest {

    private String userLogin;
    private String billName;
    private TypeBill typeBill;

}
