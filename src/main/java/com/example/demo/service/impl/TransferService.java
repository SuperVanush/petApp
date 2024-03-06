package com.example.demo.service.impl;

import com.example.demo.exception.TransferException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.PrintTransferRequest;
import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.model.dto.response.PrintTransferResponse;
import com.example.demo.model.dto.response.TransferResponse;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.TransferRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BillType;
import com.example.demo.service.RequestType;
import com.example.demo.service.ServiceTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService implements ServiceTransfer {

    private final TransferRepository transferRepository;
    private final BillRepository billRepository;
    private final UserRepository userRepository;
    private final BillService billService;

    public Transfer addTransfer(User lastUser, User toUser, String nameFromBill, String nameToBill, BigDecimal transactionSumma) {
        Bill fromBill = lastUser.getBills().stream().filter(bill -> bill.getBillName().equals(nameFromBill)).findFirst().get();
        Bill toBill = toUser.getBills().stream().filter(bill -> bill.getBillName().equals(nameToBill)).findFirst().get();
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
            Bill fromBill = userByLogin.getBills().stream().filter(bill -> bill.getBillName().equals(billName)).findFirst().get();
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
            Bill toBill = user.getBills().stream().filter(bill -> bill.getBillName().equals(nameToBillRequest)).findFirst().get();
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
                Bill fromBill = fromUser.getBills().stream().filter(bill -> bill.getBillName().equals(billName)).findFirst().get();
                List<Transfer> transferList = transferRepository.findTransfersByToBill(fromBill);
                return getSuccessPrintTransferResponse(transferList);
            } catch (UserNotFoundException e) {
                return getErrorPrintTransferResponse(e.getMessage());
            }
        }
        if ((BillType.TO_BILL == request.getBillType())) {
            try {
                String userLogin = request.getUserLogin();
                User toUser = userRepository.findByLogin(userLogin).orElseThrow(() -> new UserNotFoundException("User not found by login = " + userLogin));
                String billName = request.getBillName();
                Bill toBill = toUser.getBills().stream().filter(bill -> bill.getBillName().equals(billName)).findFirst().get();
                List<Transfer> transferList = transferRepository.findTransfersByToBill(toBill);
                return getSuccessPrintTransferResponse(transferList);
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
            Bill findFromBill = fromUser.getBills().stream().filter(bill -> bill.getBillName().equals(nameFromBill)).findFirst().get();
            Bill findToBill = toUser.getBills().stream().filter(bill -> bill.getBillName().equals(nameToBill)).findFirst().get();
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

    private PrintTransferResponse getSuccessPrintTransferResponse(List<Transfer> transferList) {
        return PrintTransferResponse.builder()
                .message("Success")
                .transferList(transferList)
                .build();
    }

    private PrintTransferResponse getErrorPrintTransferResponse(String message) {
        return PrintTransferResponse.builder()
                .message(message)
                .build();
    }
}