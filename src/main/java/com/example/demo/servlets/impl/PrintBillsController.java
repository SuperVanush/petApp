package com.example.demo.servlets.impl;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.BillRequest;
import com.example.demo.model.dto.response.BillDtoResponse;
import com.example.demo.model.dto.response.BillResponse;
import com.example.demo.service.converter.Converter;
import com.example.demo.service.impl.BillService;
import com.example.demo.service.impl.UserService;
import com.example.demo.servlets.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("/print-bill")
@RequiredArgsConstructor
public class PrintBillsController implements Controller<BillRequest, BillResponse> {

    private static final String SUCCESS_MESSAGE = "Success";
    private static final String ERROR_MESSAGE = "Error";

    private final BillService billService;
    private final UserService userService;
    private final Converter<Bill, BillDtoResponse> converter;

    @Override
    public BillResponse execute(BillRequest request) {
        try {
            User userByLogin = userService.findUserByLogin(request.getLogin());

            return getSuccessResponse(userByLogin);
        } catch (UserNotFoundException e) {
            return getErrorResponse(e.getMessage());
        }
    }

    private BillResponse getSuccessResponse(User user) {
        return BillResponse.builder()
                .message(SUCCESS_MESSAGE)
                .login(user.getLogin())
                .billList(getResponseBills(user))
                .build();
    }

    private List<BillDtoResponse> getResponseBills(User user) {
        return billService.findBillsByUser(user).stream().
                map(converter::convert).collect(Collectors.toList());
    }

    private BillResponse getErrorResponse(String message) {
        return BillResponse.builder()
                .message(ERROR_MESSAGE)
                .build();
    }

    @Override
    public Class<BillRequest> getRequestClass() {
        return BillRequest.class;
    }
}
