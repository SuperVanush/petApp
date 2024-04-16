package com.example.demo.service.impl;

import com.example.demo.exception.BalanceException;
import com.example.demo.exception.BillException;
import com.example.demo.exception.UserException;
import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.PrintTransferRequest;
import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.model.dto.response.PrintTransferDto;
import com.example.demo.model.dto.response.PrintTransferResponse;
import com.example.demo.model.dto.response.TransferResponse;
import com.example.demo.repository.TransferRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ServiceTransfer;
import com.example.demo.service.emun.TypeAction;
import com.example.demo.service.emun.TypeBill;
import com.example.demo.service.converter.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransferService implements ServiceTransfer {

    private final UserRepository userRepository;
    private final TransferRepository transferRepository;
    private final Converter<Transfer, PrintTransferDto> converter;

    @Override
    public TransferResponse transferDistribution(TransferRequest request) {
        User fromUser = Optional.ofNullable(request)
                .map(TransferRequest::getFromUserLogin)
                .flatMap(userRepository::findByLogin)
                .orElseThrow(() -> new UserException("Нет такого пользователя"));
        Bill fromBill = fromUser.getListBills().stream()
                .filter(bill -> bill.getBillName()
                        .equals(request.getFromBillName()))
                .findFirst()
                .orElseThrow(() -> new BillException("Нет такого счета"));

        User toUser = Optional.ofNullable(request)
                .map(TransferRequest::getToUserLogin)
                .flatMap(userRepository::findByLogin)
                .orElseThrow(() -> new UserException("Нет такого пользователя"));
        Bill toBill = toUser.getListBills().stream()
                .filter(bill -> bill.getBillName()
                        .equals(request.getToBillName()))
                .findFirst()
                .orElseThrow(() -> new BillException("Нет такого счета"));

        BigDecimal sumTransfer = request.getSumTransfer();

        if (request.getTypeAction() == TypeAction.DEPOSIT_TO_BILL) {
            TransferResponse transferResponse = depositOnBill(toUser, toBill, sumTransfer);
            BigDecimal newBalance = transferResponse.getToUserBalance();
            return getSuccessTransferSimpleBill(toUser, toBill, newBalance);
        }
        if (request.getTypeAction() == TypeAction.WITHDRAW_FROM_BILL) {
            TransferResponse transferResponse = withdrawFromBill(fromUser, fromBill, sumTransfer);
            BigDecimal newBalance = transferResponse.getFromUserBalance();
            return getSuccessTransferSimpleBill(fromUser, fromBill, newBalance);
        }
        if (request.getTypeAction() == TypeAction.BETWEEN_BILLS) {
            TransferResponse transferResponse = transferBetweenUsers(fromUser, fromBill, toUser, toBill, sumTransfer);
            BigDecimal newBalanceFromBill = transferResponse.getFromUserBalance();
            BigDecimal newBalanceToBill = transferResponse.getToUserBalance();
            return getSuccessTransferBetweenBills(fromUser, fromBill, toUser, toBill, newBalanceFromBill, newBalanceToBill);
        }
        if (request.getTypeAction() == TypeAction.DELETE_BILL) {
            deleteTransfer(fromBill.getBillId());
            return getSuccessDeleteTransfer();
        } else {
            return getErrorTransfer();
        }
    }

    @Override
    public Transfer addTransfer(User fromUser, Bill fromBill, User toUser, Bill toBill, BigDecimal sumTransfer) {
        Transfer transfer = Transfer.builder()
                .fromUser(fromUser)
                .fromBill(fromBill)
                .toUser(toUser)
                .toBill(toBill)
                .sumTransfer(sumTransfer)
                .localDateTime(LocalDateTime.now())
                .build();
        transferRepository.save(transfer);
        return transfer;
    }

    @Override
    public TransferResponse depositOnBill(User toUser, Bill toBill, BigDecimal sumTransfer) {
        Bill billNewBalance = sumToBillTransfer(toBill, sumTransfer);
        BigDecimal newBalance = billNewBalance.getBalance();
        addTransfer(toUser, toBill, toUser, billNewBalance, sumTransfer);
        return getSuccessTransferSimpleBill(toUser, billNewBalance, newBalance);
    }

    @Override
    public TransferResponse withdrawFromBill(User fromUser, Bill fromBill, BigDecimal sumTransfer) {
        Bill billNewBalance = reduceFromBillTransfer(fromBill, sumTransfer);
        BigDecimal newBalance = billNewBalance.getBalance();
        addTransfer(fromUser, fromBill, fromUser, billNewBalance, newBalance);
        return getSuccessTransferSimpleBill(fromUser, billNewBalance, newBalance);
    }

    @Override
    public TransferResponse transferBetweenUsers(User fromUser, Bill fromBill, User toUser, Bill toBill, BigDecimal sumTransfer) {
        Bill billDepositNewBalance = sumToBillTransfer(toBill, sumTransfer);
        BigDecimal depositNewBalance = billDepositNewBalance.getBalance();
        Bill billReduceNewBalance = reduceFromBillTransfer(fromBill, sumTransfer);
        BigDecimal reduceNewBalance = billReduceNewBalance.getBalance();
        addTransfer(fromUser, fromBill, toUser, toBill, sumTransfer);
        return getSuccessTransferBetweenBills(fromUser, billReduceNewBalance, toUser, billDepositNewBalance, reduceNewBalance, depositNewBalance);
    }

    @Override
    public Bill sumToBillTransfer(Bill toBill, BigDecimal sumTransfer) {
        Bill bill = toBill;
        BigDecimal newBalance = toBill.getBalance().add(sumTransfer);
        bill.setBalance(newBalance);
        return bill;
    }

    @Override
    public Bill reduceFromBillTransfer(Bill fromBill, BigDecimal sumTransfer) {
        Bill bill = fromBill;
        BigDecimal newBalance = fromBill.getBalance().subtract(sumTransfer);
        if (newBalance.compareTo(BigDecimal.ZERO) > 0) {
            bill.setBalance(newBalance);
        } else {
            throw new BalanceException("Баланс меньше ноля, попробуйте снова");
        }
        return bill;
    }

    public TransferResponse deleteTransfer(UUID idDeleteTransfer) {
        String id = idDeleteTransfer.toString();
        transferRepository.findBy // не доделала
        return getSuccessDeleteTransfer();
    }

    @Override
    public PrintTransferResponse printTransfersByUser(PrintTransferRequest request) {
        User user = Optional.ofNullable(request)
                .map(PrintTransferRequest::getUserLogin)
                .flatMap(userRepository::findByLogin)
                .orElseThrow(() -> new UserException("Нет такого пользователя"));
        Bill bill = user.getListBills().stream()
                .filter(bill1 -> bill1.getBillName()
                        .equals(request.getBillName()))
                .findFirst()
                .orElseThrow(() -> new BillException("Нет такого счета"));
        if (request.getTypeBill() == TypeBill.FROM_BILL) {
            return getSuccessPrintTransferBill(user, bill);
        }
        if (request.getTypeBill() == TypeBill.TO_BILL) {
            return getSuccessPrintTransferBill(user, bill);
        } else {
            return getErrorPrintTransfer();
        }
    }

    public TransferResponse getSuccessTransferSimpleBill(User user, Bill bill, BigDecimal newBalance) {
        return TransferResponse.builder()
                .message("Success")
                .toUserName(user.getUserName())
                .toBillName(bill.getBillName())
                .ToUserBalance(newBalance)
                .build();
    }

    public TransferResponse getSuccessTransferBetweenBills(User fromUser, Bill fromBill, User toUser, Bill toBill, BigDecimal newBalanceFromBill, BigDecimal newBalanceToBill) {
        return TransferResponse.builder()
                .message("Success")
                .fromUserName(fromUser.getUserName())
                .fromBillName(fromBill.getBillName())
                .toUserName(toUser.getUserName())
                .toBillName(toBill.getBillName())
                .FromUserBalance(newBalanceFromBill)
                .ToUserBalance(newBalanceToBill)
                .build();
    }

    public TransferResponse getSuccessDeleteTransfer() {
        return TransferResponse.builder()
                .message("Success")
                .build();
    }

    public PrintTransferResponse getSuccessPrintTransferBill(User user, Bill bill) {
        return PrintTransferResponse.builder()
                .userName(user.getLogin())
                .billName(bill.getBillName())
                .printTransferDtoList(getListPrintTransfer(bill))
                .build();
    }

    public List<PrintTransferDto> getListPrintTransfer(Bill bill) {
        return transferRepository.findTransfersByFromBill_BillId(bill.getBillId().compareTo(bill.getBillId()))
                .stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    public TransferResponse getErrorTransfer() {
        return TransferResponse.builder()
                .message("Неверный тип действия")
                .build();
    }

    public PrintTransferResponse getErrorPrintTransfer() {
        return PrintTransferResponse.builder()
                .message("Неверный тип счета")
                .build();
    }
}
