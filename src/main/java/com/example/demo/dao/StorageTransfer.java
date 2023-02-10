package com.example.demo.dao;

import com.example.demo.model.Transfer;

import java.util.List;

public interface StorageTransfer {

    Transfer add(Transfer transfer);

    List<Transfer> getListOfElements();

    Transfer findTransferFromId(int idTransfer);

    void updateTransfer(Transfer transfer);
}
