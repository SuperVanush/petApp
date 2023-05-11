package com.example.demo.servlets.impl;

import com.example.demo.model.request.BillRequest;
import com.example.demo.servlets.response.BillResponse;
import com.example.demo.servlets.Controller;

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
