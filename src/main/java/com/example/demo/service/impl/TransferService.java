package com.example.demo.service.impl;

import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.model.dto.response.PrintTransferDto;
import com.example.demo.model.dto.response.PrintTransferResponse;
import com.example.demo.model.dto.response.TransferResponse;
import com.example.demo.model.types.TypeAction;
import com.example.demo.model.types.TypeBill;
import com.example.demo.repository.TransferRepository;
import com.example.demo.service.ServiceTransfer;
import com.example.demo.service.converter.Converter;
import lombok.RequiredArgsConstructor;
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
    private final BillService billService;
    private final Converter<Transfer, TransferResponse> actionConverter;
    private final Converter<TransferRequest, TransferResponse> errorActionConverter;
    private final Converter<TypeBill, PrintTransferResponse> typeErrorConverter;
    private final Converter<Bill, PrintTransferResponse> printTransferConverter;
    private final Converter<Transfer, PrintTransferDto> printConverter;

    @Override
    public TransferResponse transferDistribution(TransferRequest request) {
        Bill fromBill = billService.findBillById(request.getFromBillId());

        Bill toBill = billService.findBillById(request.getToBillId());

        BigDecimal sumTransfer = request.getSumTransfer();

        if (request.getTypeAction() == TypeAction.DEPOSIT_TO_BILL) {
            return actionConverter.convert(depositOnBill(toBill, sumTransfer));
        }
        if (request.getTypeAction() == TypeAction.WITHDRAW_FROM_BILL) {
            return actionConverter.convert(withdrawFromBill(fromBill, sumTransfer));
        }
        if (request.getTypeAction() == TypeAction.BETWEEN_BILLS) {
            return actionConverter.convert(transferBetweenUsers(fromBill, toBill, sumTransfer));
        }
        if (request.getTypeAction() == TypeAction.DELETE_BILL) {
            deleteTransfer(fromBill.getId());
        }
        return errorActionConverter.convert(request);
    }

    public Transfer addTransfer(Bill fromBill, Bill toBill, BigDecimal sumTransfer) {
        Transfer transfer = Transfer.builder()
                .fromUser(fromBill.getUser())
                .fromBill(fromBill)
                .toUser(toBill.getUser())
                .toBill(toBill)
                .sumTransfer(sumTransfer)
                .localDateTime(LocalDateTime.now())
                .build();
        return transferRepository.save(transfer);
    }

    public Transfer depositOnBill(Bill toBill, BigDecimal sumTransfer) {
        Bill billNewBalance = billService.sumToBillTransfer(toBill, sumTransfer);
        return addTransfer(toBill, billNewBalance, sumTransfer);
    }

    public Transfer withdrawFromBill(Bill fromBill, BigDecimal sumTransfer) {
        Bill billNewBalance = billService.reduceFromBillTransfer(fromBill, sumTransfer);
        return addTransfer(fromBill, billNewBalance, sumTransfer);
    }

    public Transfer transferBetweenUsers(Bill fromBill, Bill toBill, BigDecimal sumTransfer) {
        Bill billDepositNewBalance = billService.sumToBillTransfer(toBill, sumTransfer);
        Bill billReduceNewBalance = billService.reduceFromBillTransfer(fromBill, sumTransfer);
        return addTransfer(billReduceNewBalance, billDepositNewBalance, sumTransfer);
    }

    public void deleteTransfer(UUID idDeleteTransfer) {
        transferRepository.deleteById(idDeleteTransfer);
    }

    @Override
    public PrintTransferResponse printTransfersByUser(UUID billId, TypeBill typeBill) {
        Bill bill = billService.findBillById(billId);
        if (typeBill == TypeBill.FROM_BILL) {
            return getSuccessPrintTransferFromBill(bill);
        }
        if (typeBill == TypeBill.TO_BILL) {
            return getSuccessPrintTransferToBill(bill);
        } else {
            return typeErrorConverter.convert(typeBill);
        }
    }

    public PrintTransferResponse getSuccessPrintTransferFromBill(Bill bill) {
        List<PrintTransferDto> transferList = transferRepository
                .findTransfersByFromBill_id(bill.getId())
                .stream()
                .map(printConverter::convert)
                .collect(Collectors.toList());
        return PrintTransferResponse.builder()
                .userName(bill.getUser().getUserName())
                .billName(bill.getBillName())
                .printTransferDtoList(transferList)
                .build();
    }

    public PrintTransferResponse getSuccessPrintTransferToBill(Bill bill) {
        return printTransferConverter.convert(bill);
    }
}
