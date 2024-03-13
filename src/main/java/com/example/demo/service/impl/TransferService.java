package com.example.demo.service.impl;

import com.example.demo.exception.TransferException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.PrintTransferRequest;
import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.model.dto.response.PrintTransferResponse;
import com.example.demo.model.dto.response.TransferListResponse;
import com.example.demo.model.dto.response.TransferResponse;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.TransferRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BillType;
import com.example.demo.service.RequestType;
import com.example.demo.service.ServiceTransfer;
import com.example.demo.service.converter.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransferService implements ServiceTransfer {

    private final TransferRepository transferRepository;
    private final BillRepository billRepository;
    private final UserRepository userRepository;
    private final Converter<Transfer, TransferListResponse> converter;
    private final BillService billService;

    public Transfer addTransfer(User lastUser, User toUser, String nameFromBill, String nameToBill, BigDecimal transactionSumma) {
        Bill fromBill = billRepository.findBillByUser_LoginAndAndBillName(lastUser.getLogin(), nameFromBill).orElseThrow(() -> new UserNotFoundException("not found"));
        Bill toBill = billRepository.findBillByUser_LoginAndAndBillName(toUser.getLogin(), nameToBill).orElseThrow(() -> new UserNotFoundException("not found"));
        Transfer transfer = Transfer.builder()
                .fromUser(lastUser)
                .toUser(toUser)
                .fromBill(fromBill)
                .toBill(toBill)
                .sumTransaction(transactionSumma)
                .timeDateTransaction(new Timestamp(System.currentTimeMillis()))
                .build();
        transferRepository.save(transfer);
        return transfer;
    }

    public TransferResponse transfer(TransferRequest request) {
        if ((RequestType.SUM == request.getRequestType())) {
            return sumTransaction(request);
        }
        if (RequestType.REDUCE == request.getRequestType()) {
            return reduceTransaction(request);
        }
        if (RequestType.TRANSFER == request.getRequestType()) {
            return transactionBetweenBill(request);
        } else {
            return TransferResponse.builder().message("Wrong RequestType").build();
        }
    }

    public TransferResponse reduceTransaction(TransferRequest request) {
        try {
            String billName = request.getNameFromBill();
            BigDecimal reduceDigit = request.getSumTransfer();
            String login = request.getLoginFromUser();
            User userByLogin = userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException("User not found by login = " + login));
            Bill fromBill = billRepository.findBillByUser_LoginAndAndBillName(login, billName).orElseThrow(() -> new UserNotFoundException("Not found"));
            int idFromBill = fromBill.getId();
            String nameFromBill = reduceBalance(idFromBill, reduceDigit).getFromBill().getBillName();
            Transfer transfer = addTransfer(userByLogin, userByLogin, nameFromBill, nameFromBill, reduceDigit);
            return getSuccessTransferResponse(transfer.getId());
        } catch (TransferException e) {
            return getErrorTransferResponse(e.getMessage());
        }
    }

    public TransferResponse sumTransaction(TransferRequest request) {
        try {
            String loginToUser = request.getLoginToUser();
            User user = userRepository.findByLogin(loginToUser).orElseThrow(() -> new UserNotFoundException("User not found by login = " + loginToUser));
            String nameToBillRequest = request.getNameToBill();
            Bill toBill = billRepository.findBillByUser_LoginAndAndBillName(loginToUser, nameToBillRequest).orElseThrow(() -> new UserNotFoundException("Not found"));
            int idToBill = toBill.getId();
            BigDecimal sumTransfer = request.getSumTransfer();
            String nameToBill = sumBalanceTransaction(idToBill, sumTransfer).getToBill().getBillName();
            Transfer transfer = addTransfer(user, user, nameToBill, nameToBill, sumTransfer);
            return getSuccessAddTransferResponse(transfer.getId());
        } catch (TransferException e) {
            return getErrorAddTransferResponse(e.getMessage());
        }
    }

    @Override
    public PrintTransferResponse findTransferByBillsName(PrintTransferRequest request) {
        if ((BillType.FROM_BILL == request.getBillType())) {
            try {
                String userLogin = request.getUserLogin();
                User fromUser = userRepository.findByLogin(userLogin).orElseThrow(() -> new UserNotFoundException("User not found by login = " + userLogin));
                String billName = request.getBillName();
                Bill fromBill = billRepository.findBillByUser_LoginAndAndBillName(userLogin, billName).orElseThrow(() -> new UserNotFoundException("Not found"));
                return getSuccessPrintTransferResponse(fromBill);
            } catch (UserNotFoundException e) {
                return getErrorPrintTransferResponse(e.getMessage());
            }
        }
        if ((BillType.TO_BILL == request.getBillType())) {
            try {
                String userLogin = request.getUserLogin();
                User toUser = userRepository.findByLogin(userLogin).orElseThrow(() -> new UserNotFoundException("User not found by login = " + userLogin));
                String billName = request.getBillName();
                Bill toBill = billRepository.findBillByUser_LoginAndAndBillName(userLogin, billName).orElseThrow(() -> new UserNotFoundException("Not found"));
                return getSuccessPrintTransferResponse(toBill);
            } catch (UserNotFoundException e) {
                return getErrorPrintTransferResponse(e.getMessage());
            }
        } else {
            return PrintTransferResponse.builder().message("Wrong BillTYPE").build();
        }
    }

    @Override
    public Transfer sumBalanceTransaction(int idToBill, BigDecimal sumDigit) {
        Bill toBill = billRepository.findBillById(idToBill).orElseThrow(() -> new UserNotFoundException("Bill not found"));
        BigDecimal billBalance = toBill.getBalance();
        BigDecimal sumBillBalance = billBalance.add(sumDigit);
        toBill.setBalance(sumBillBalance);
        billRepository.save(toBill);
        Transfer toTransfer = new Transfer();
        toTransfer.setToBill(toBill);
        return toTransfer;
    }

    @Override
    public Transfer reduceBalance(int idFromBill, BigDecimal reduceDigit) {
        Bill fromBill = billRepository.findBillById(idFromBill).orElseThrow(() -> new UserNotFoundException("Bill not found"));
        BigDecimal billBalance = fromBill.getBalance();
        BigDecimal reduceBillBalance = billBalance.subtract(reduceDigit);
        Transfer fromTransfer = new Transfer();
        if (reduceBillBalance.compareTo(BigDecimal.ZERO) > 0) {
            fromBill.setBalance(reduceBillBalance);
            billRepository.save(fromBill);
            fromTransfer.setFromBill(fromBill);
        } else {
            throw new TransferException("TRY AGAIN YOUR BALANCE IS MINUS");
        }
        return fromTransfer;
    }

    public TransferResponse transactionBetweenBill(TransferRequest request) throws TransferException {
        try {
            String loginFromUser = request.getLoginFromUser();
            String loginToUser = request.getLoginToUser();
            User fromUser = userRepository.findByLogin(loginFromUser).orElseThrow(() -> new UserNotFoundException("User not found by login = " + loginFromUser));
            User toUser = userRepository.findByLogin(loginToUser).orElseThrow(() -> new UserNotFoundException("User not found by login = " + loginToUser));

            String nameFromBill = request.getNameFromBill();
            String nameToBill = request.getNameToBill();
            Bill findFromBill = billRepository.findBillByUser_LoginAndAndBillName(loginFromUser, nameFromBill).orElseThrow(() -> new UserNotFoundException("Not found"));
            Bill findToBill = billRepository.findBillByUser_LoginAndAndBillName(loginToUser, nameToBill).orElseThrow(() -> new UserNotFoundException("Not found"));
            int idFindFromBill = findFromBill.getId();
            int idFindToBill = findToBill.getId();
            BigDecimal transactionSum = request.getSumTransfer();
            Bill fromBillAfterSave = reduceBalance(idFindFromBill, transactionSum).getFromBill();
            Bill toBillAfterSave = sumBalanceTransaction(idFindToBill, transactionSum).getToBill();
            Transfer transfer = Transfer.builder()
                    .fromUser(fromUser)
                    .toUser(toUser)
                    .fromBill(fromBillAfterSave)
                    .toBill(toBillAfterSave)
                    .sumTransaction(transactionSum)
                    .timeDateTransaction(new Timestamp(System.currentTimeMillis()))
                    .build();
            transferRepository.save(transfer);
            return getSuccessAddTransferResponse(transfer.getId());
        } catch (TransferException e) {
            return getErrorAddTransferResponse(e.getMessage());
        }
    }

    private TransferResponse getSuccessAddTransferResponse(int idTransaction) {
        return TransferResponse.builder()
                .message("Success")
                .idTransaction(idTransaction)
                .build();
    }

    private TransferResponse getErrorAddTransferResponse(String message) {
        return TransferResponse.builder()
                .message(message)
                .build();
    }

    private TransferResponse getSuccessTransferResponse(int idTransaction) {
        return TransferResponse.builder()
                .message("Success")
                .idTransaction(idTransaction)
                .build();
    }

    private TransferResponse getErrorTransferResponse(String message) {
        return TransferResponse.builder()
                .message(message)
                .build();
    }

    private PrintTransferResponse getSuccessPrintTransferResponse(Bill bill) {
        return PrintTransferResponse.builder()
                .message("Success")
                .transferList(getResponseTransfers(bill))
                .build();
    }

    private PrintTransferResponse getErrorPrintTransferResponse(String message) {
        return PrintTransferResponse.builder()
                .message(message)
                .build();
    }

    private List<TransferListResponse> getResponseTransfers(Bill bill) {
        return transferRepository.findAll()
                .stream()
                .filter(transfer -> bill.equals(bill))
                .map(converter::convert)
                .collect(Collectors.toList());
    }
}