package com.example.demo.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BillAddResponse {

    private String userMessage;
    private String userLogin;
    private String billMessage;
    private String billName;
}
