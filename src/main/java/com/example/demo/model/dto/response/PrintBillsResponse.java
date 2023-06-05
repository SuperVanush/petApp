package com.example.demo.model.dto.response;

import com.example.demo.model.Bill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrintBillsResponse {

    private String name;
    private List<Bill> billList;
    private String message;

}
