package com.example.demo.service.impl;

import com.example.demo.exception.BalanceException;
import com.example.demo.exception.BillException;
import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.model.dto.response.PrintTransferDto;
import com.example.demo.model.dto.response.PrintTransferResponse;
import com.example.demo.model.dto.response.TransferResponse;
import com.example.demo.model.types.TypeAction;
import com.example.demo.model.types.TypeBill;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.TransferRepository;
import com.example.demo.service.ServiceTransfer;
import com.example.demo.service.converter.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransferService implements ServiceTransfer {

    private final TransferRepository transferRepository;
    private final BillRepository billRepository;
    private final Converter<Transfer, PrintTransferDto> converter;

    @Override

    public TransferResponse transferDistribution(TransferRequest request) {
        Bill fromBill = billRepository.findById(request.getFromBillId())
                .orElseThrow(() -> new BillException("Нет такого счета"));
        Bill toBill = billRepository.findById(request.getToBillId())
                .orElseThrow(() -> new BillException("Нет такого счета"));

        BigDecimal sumTransfer = request.getSumTransfer();

        if (request.getTypeAction() == TypeAction.DEPOSIT_TO_BILL) {
            return depositOnBill(toBill, sumTransfer);
        }
        if (request.getTypeAction() == TypeAction.WITHDRAW_FROM_BILL) {
            return withdrawFromBill(fromBill, sumTransfer);
        }
        if (request.getTypeAction() == TypeAction.BETWEEN_BILLS) {
            return transferBetweenUsers(fromBill, toBill, sumTransfer);
        }
        if (request.getTypeAction() == TypeAction.DELETE_BILL) {
            return deleteTransfer(fromBill.getId());
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
        return transferRepository.save(transfer);
    }

    @Override
    public TransferResponse depositOnBill(Bill toBill, BigDecimal sumTransfer) {
        Bill billNewBalance = sumToBillTransfer(toBill, sumTransfer);
        Transfer transfer = addTransfer(toBill.getUser(), toBill, billNewBalance.getUser(), billNewBalance, sumTransfer);
        return getSuccessTransferToBill(transfer);
    }

    @Override
    public TransferResponse withdrawFromBill(Bill fromBill, BigDecimal sumTransfer) {
        Bill billNewBalance = reduceFromBillTransfer(fromBill, sumTransfer);
        Transfer transfer = addTransfer(fromBill.getUser(), fromBill, billNewBalance.getUser(), billNewBalance, sumTransfer);
        return getSuccessTransferFromBill(transfer);
    }

    @Override
    public TransferResponse transferBetweenUsers(Bill fromBill, Bill toBill, BigDecimal sumTransfer) {
        Bill billDepositNewBalance = sumToBillTransfer(toBill, sumTransfer);
        Bill billReduceNewBalance = reduceFromBillTransfer(fromBill, sumTransfer);
        Transfer transfer = addTransfer(billReduceNewBalance.getUser(), billReduceNewBalance, billDepositNewBalance.getUser(), billDepositNewBalance, sumTransfer);
        return getSuccessTransferBetweenBills(transfer);
    }

    @Override
    public Bill sumToBillTransfer(Bill toBill, BigDecimal sumTransfer) {
        BigDecimal newBalance = toBill.getBalance().add(sumTransfer);
        toBill.setBalance(newBalance);
        return toBill;
    }

    @Override
    public Bill reduceFromBillTransfer(Bill fromBill, BigDecimal sumTransfer) {
        BigDecimal newBalance = fromBill.getBalance().subtract(sumTransfer);
        if (newBalance.compareTo(BigDecimal.ZERO) > 0) {
            fromBill.setBalance(newBalance);
        } else {
            throw new BalanceException("Баланс меньше ноля, попробуйте снова");
        }
        return fromBill;
    }

    public TransferResponse deleteTransfer(UUID idDeleteTransfer) {
        transferRepository.deleteById(idDeleteTransfer);
        return getSuccessDeleteTransfer();
    }

    @Override
    public PrintTransferResponse printTransfersByUser(UUID billId, TypeBill typeBill) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new BillException("Нет такого счета"));
        if (typeBill == TypeBill.FROM_BILL) {
            return getSuccessPrintTransferFromBill(bill);
        }
        if (typeBill == TypeBill.TO_BILL) {
            return getSuccessPrintTransferToBill(bill);
        } else {
            return getErrorPrintTransfer();
        }
    }

    public TransferResponse getSuccessTransferToBill(Transfer transfer) {
        return TransferResponse.builder()
                .message("Success")
                .toUserName(transfer.getToUser().getUserName())
                .toBillName(transfer.getToBill().getBillName())
                .ToUserBalance(transfer.getToBill().getBalance())
                .build();
    }

    public TransferResponse getSuccessTransferFromBill(Transfer transfer) {
        return TransferResponse.builder()
                .message("Success")
                .fromUserName(transfer.getFromUser().getUserName())
                .fromBillName(transfer.getFromBill().getBillName())
                .FromUserBalance(transfer.getFromBill().getBalance())
                .build();
    }

    public TransferResponse getSuccessTransferBetweenBills(Transfer transfer) {
        return TransferResponse.builder()
                .message("Success")
                .fromUserName(transfer.getFromUser().getUserName())
                .fromBillName(transfer.getFromBill().getBillName())
                .toUserName(transfer.getToUser().getUserName())
                .toBillName(transfer.getToBill().getBillName())
                .FromUserBalance(transfer.getFromBill().getBalance())
                .ToUserBalance(transfer.getToBill().getBalance())
                .build();
    }

    public TransferResponse getSuccessDeleteTransfer() {
        return TransferResponse.builder()
                .message(String.valueOf(HttpStatus.OK))
                .build();
    }

    public PrintTransferResponse getSuccessPrintTransferFromBill(Bill bill) {
        return PrintTransferResponse.builder()
                .userName(bill.getUser().getUserName())
                .billName(bill.getBillName())
                .printTransferDtoList(getListPrintTransferFromBill(bill))
                .build();
    }

    public List<PrintTransferDto> getListPrintTransferFromBill(Bill bill) {
        return transferRepository.findTransfersByFromBill_id(bill.getId())
                .stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    public PrintTransferResponse getSuccessPrintTransferToBill(Bill bill) {
        return PrintTransferResponse.builder()
                .userName(bill.getUser().getUserName())
                .billName(bill.getBillName())
                .printTransferDtoList(getListPrintTransferToBill(bill))
                .build();
    }

    public List<PrintTransferDto> getListPrintTransferToBill(Bill bill) {
        return transferRepository.findTransfersByFromBill_id(bill.getId())
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
