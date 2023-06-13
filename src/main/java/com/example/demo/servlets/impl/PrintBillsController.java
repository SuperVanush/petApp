package com.example.demo.servlets.impl;


@Service("/print-bill")
@RequiredArgsConstructor
public class PrintBillsController implements Controller<PrintBillsRequest, PrintBillsResponse> {

    private final static String SUCCESS_MESSAGE = "Success";
    private final static String ERROR_MESSAGE = "Error";

    private final BillService billService;
    private final UserService userService;

    @Override
    public PrintBillsResponse execute(PrintBillsRequest request) {
        String login = request.getLogin();
        try {
            User findUser = userService.findUserByLogin(login);
            List<Bill> findUserBills = billService.findBillsByUser(findUser);
            String findUserName = findUser.getUsername();
            return getResponse(findUserName, SUCCESS_MESSAGE, findUserBills);
        } catch (MyExceptionBill | MyExceptionUser e) {
            return getResponse(ERROR_MESSAGE, e.getMessage(), Collections.emptyList());
        }
    }

    private PrintBillsResponse getResponse(String name, String message, List<Bill> billList) {
        return PrintBillsResponse.builder()
                .name(name)
                .message(message)
                .billList(billList)
                .build();
    }

    @Override
    public Class<PrintBillsRequest> getRequestClass() {
        return PrintBillsRequest.class;
    }
}
