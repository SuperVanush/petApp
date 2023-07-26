package com.example.demo.model.dto.response;

import com.example.demo.model.Transfer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PrintTransferResponse {

    private String message;
    private List<Transfer> transferList;
}
