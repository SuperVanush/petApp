package com.example.demo.servlets;

import com.example.demo.request.BillRequest;
import com.example.demo.response.BillResponse;

public class BillController implements Controller<BillResponse, BillRequest> {

    @Override
    public BillRequest execute(BillResponse request) {
        return null;
    }

    @Override
    public Class<BillResponse> getRequestClass() {
        return null;
    }
}
