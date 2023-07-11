/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

package link.pihda.billofmaterial.service;

import link.pihda.billofmaterial.dao.TransactionDao;
import link.pihda.billofmaterial.entity.Item;
import link.pihda.billofmaterial.entity.Transaction;

import java.util.List;

public class TransactionService {
    private final TransactionDao transactionDao = new TransactionDao();

    public void save(Transaction transaction) {
        transactionDao.save(transaction);
    }

    public List<Transaction> getAll() {
        return transactionDao.getAll();
    }

    public void delete(Transaction transaction) {
        transactionDao.delete(transaction);
    }

    public void update(Transaction transaction) {
        transaction.setTotalPrice(transaction.getShippingRate() + transaction.getTaxes() +
                transaction.getItems().stream().mapToDouble(Item::getTotalPrice).sum());
        transactionDao.update(transaction);
    }
}
