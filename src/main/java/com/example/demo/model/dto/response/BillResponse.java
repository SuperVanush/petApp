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
public class BillResponse {

    private String userName;
    private String message;
    private List<Bill> billList;

}
