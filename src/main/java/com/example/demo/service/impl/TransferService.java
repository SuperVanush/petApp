package com.example.demo.service.impl;

import com.example.demo.exception.BillNotFoundException;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransferService implements ServiceTransfer {

    private final TransferRepository transferRepository;
    private final BillRepository billRepository;
    private final UserRepository userRepository;
    private final Converter<Transfer, TransferListResponse> converter;

    public Transfer addTransfer(User lastUser, User toUser, String nameFromBill, String nameToBill, BigDecimal transactionSumma) {

        Bill fromBill = billRepository.findBillByUser_LoginAndAndBillName(lastUser.getLogin(), nameFromBill)
                .orElseThrow(() -> new UserNotFoundException("not found"));
        Bill toBill = billRepository.findBillByUser_LoginAndAndBillName(toUser.getLogin(), nameToBill)
                .orElseThrow(() -> new UserNotFoundException("not found"));
        Transfer transfer = Transfer.builder()
                .fromUser(lastUser)
                .toUser(toUser)
                .fromBill(fromBill)
                .toBill(toBill)
                .sumTransaction(transactionSumma)
                .timeDateTransaction(LocalDateTime.now())
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
        String billName = request.getNameFromBill();
        BigDecimal reduceDigit = request.getSumTransfer();
        String login = request.getLoginFromUser();
        User userByLogin = Optional.ofNullable(login)
                .flatMap(userRepository::findByLogin)
                .orElseThrow(() -> new UserNotFoundException("ЭТО ИСКЛЮЧЕНИЕ"));
        if (billRepository.findBillByUser_LoginAndAndBillName(login, billName).isEmpty()) {
            throw new BillNotFoundException("User don't have this bill");
        }
        Bill fromBill = billRepository.findBillByUser_LoginAndAndBillName(login, billName).get();
        int idFromBill = fromBill.getId();
        String nameFromBill = reduceBalance(idFromBill, reduceDigit).getFromBill().getBillName();
        Transfer transfer = addTransfer(userByLogin, userByLogin, nameFromBill, nameFromBill, reduceDigit);
        return getSuccessTransferResponse(transfer.getId());
    }

    public TransferResponse sumTransaction(TransferRequest request) {
        String loginToUser = request.getLoginToUser();
        User user = Optional.ofNullable(loginToUser)
                .flatMap(userRepository::findByLogin)
                .orElseThrow(() -> new UserNotFoundException("ЭТО ИСКЛЮЧЕНИЕ"));
        String nameToBillRequest = request.getNameToBill();
        if (billRepository.findBillByUser_LoginAndAndBillName(loginToUser, nameToBillRequest).isEmpty()) {
            throw new BillNotFoundException("User don't have this bill");
        }
        Bill toBill = billRepository.findBillByUser_LoginAndAndBillName(loginToUser, nameToBillRequest).get();
        int idToBill = toBill.getId();
        BigDecimal sumTransfer = request.getSumTransfer();
        String nameToBill = sumBalanceTransaction(idToBill, sumTransfer).getToBill().getBillName();
        Transfer transfer = addTransfer(user, user, nameToBill, nameToBill, sumTransfer);
        return getSuccessAddTransferResponse(transfer.getId());
    }

    @Override
    public PrintTransferResponse findTransferByBillsName(PrintTransferRequest request) {
        Optional.ofNullable(request.getUserLogin())
                .flatMap(userRepository::findByLogin)
                .orElseThrow(() -> new UserNotFoundException("ЭТО ИСКЛЮЧЕНИЕ"));
        billRepository.findBillByUser_LoginAndAndBillName(request.getUserLogin(), request.getBillName()).orElseThrow(() -> new UserNotFoundException("Not found Bill"));
        if ((BillType.FROM_BILL == request.getBillType())) {
            try {
                String userLogin = request.getUserLogin();
                String billName = request.getBillName();
                return getSuccessPrintTransferResponseFromBill(userLogin, billName);
            } catch (UserNotFoundException e) {
                return getErrorPrintTransferResponse(e.getMessage());
            }
        }
        if ((BillType.TO_BILL == request.getBillType())) {
            try {
                String userLogin = request.getUserLogin();
                String billName = request.getBillName();
                return getSuccessPrintTransferResponseToBill(userLogin, billName);
            } catch (UserNotFoundException e) {
                return getErrorPrintTransferResponse(e.getMessage());
            }
        } else {
            return PrintTransferResponse.builder().message("Wrong BillTYPE").build();
        }
    }

    @Override
    public Transfer sumBalanceTransaction(int idToBill, BigDecimal sumDigit) {
        Bill toBill = Optional.of(idToBill)
                .flatMap(billRepository::findBillById)
                .orElseThrow(() -> new BillNotFoundException("Bill not found"));
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
        Bill fromBill = Optional.of(idFromBill)
                .flatMap(billRepository::findBillById)
                .orElseThrow(() -> new BillNotFoundException("Bill not found"));
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

    public TransferResponse transactionBetweenBill(TransferRequest request) {
        try {
            String loginFromUser = request.getLoginFromUser();
            String loginToUser = request.getLoginToUser();

            User fromUser = Optional.ofNullable(loginFromUser)
                    .flatMap(userRepository::findByLogin)
                    .orElseThrow(() -> new UserNotFoundException("User not found by login = " + loginFromUser));

            User toUser = Optional.ofNullable(loginToUser)
                    .flatMap(userRepository::findByLogin)
                    .orElseThrow(() -> new UserNotFoundException("User not found by login = " + loginToUser));

            String nameFromBill = request.getNameFromBill();
            String nameToBill = request.getNameToBill();
            Bill findFromBill = billRepository.findBillByUser_LoginAndAndBillName(loginFromUser, nameFromBill).orElseThrow(() -> new UserNotFoundException("Not found"));
            Bill findToBill = billRepository.findBillByUser_LoginAndAndBillName(loginToUser, nameToBill).orElseThrow(() -> new UserNotFoundException("Not found"));
            int idFindFromBill = findFromBill.getId();
            int idFindToBill = findToBill.getId();
            BigDecimal transactionSum = request.getSumTransfer();
            Bill fromBillAfterSave = reduceBalance(idFindFromBill, transactionSum).getFromBill();
            Bill toBillAfterSave = sumBalanceTransaction(idFindToBill, transactionSum).getToBill();
            Transfer transfer = Transfer.builder().fromUser(fromUser).toUser(toUser).fromBill(fromBillAfterSave).toBill(toBillAfterSave).sumTransaction(transactionSum).timeDateTransaction(LocalDateTime.now()).build();
            transferRepository.save(transfer);
            return getSuccessAddTransferResponse(transfer.getId());
        } catch (TransferException e) {
            return getErrorAddTransferResponse(e.getMessage());
        }
    }

    private TransferResponse getSuccessAddTransferResponse(int idTransaction) {
        return TransferResponse.builder().message("Success").idTransaction(idTransaction).build();
    }

    private TransferResponse getErrorAddTransferResponse(String message) {
        return TransferResponse.builder().message(message).build();
    }

    private TransferResponse getSuccessTransferResponse(int idTransaction) {
        return TransferResponse.builder().message("Success").idTransaction(idTransaction).build();
    }

    private PrintTransferResponse getSuccessPrintTransferResponseFromBill(String userLogin, String billName) {
        return PrintTransferResponse.builder().message("Success").transferList(getResponseTransfersFromBill(userLogin, billName)).build();
    }

    private PrintTransferResponse getSuccessPrintTransferResponseToBill(String userLogin, String billName) {
        return PrintTransferResponse.builder().message("Success").transferList(getResponseTransfersToBill(userLogin, billName)).build();
    }

    private PrintTransferResponse getErrorPrintTransferResponse(String message) {
        return PrintTransferResponse.builder().message(message).build();
    }

    private List<TransferListResponse> getResponseTransfersFromBill(String userLogin, String billName) {
        return transferRepository.findTransfersByFromUser_LoginAndFromBill_BillName(userLogin, billName).stream().map(converter::convert).collect(Collectors.toList());
    }

    private List<TransferListResponse> getResponseTransfersToBill(String userLogin, String billName) {
        return transferRepository.findTransfersByToUser_LoginAndToBill_BillName(userLogin, billName).stream().map(converter::convert).collect(Collectors.toList());
    }
}