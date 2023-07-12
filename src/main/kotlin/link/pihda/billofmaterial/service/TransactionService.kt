/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */
package link.pihda.billofmaterial.service

import link.pihda.billofmaterial.dao.TransactionDao
import link.pihda.billofmaterial.entity.Transaction

class TransactionService {
    private val transactionDao = TransactionDao()

    fun save(transaction: Transaction) {
        transactionDao.save(transaction)
    }

    fun getAll(): List<Transaction> {
        return transactionDao.getAll()
    }

    fun delete(transaction: Transaction) {
        transactionDao.delete(transaction)
    }

    fun update(transaction: Transaction) {
        transaction.totalPrice = transaction.shippingRate + transaction.taxes + transaction.items.sumOf { it.totalPrice }
        transactionDao.update(transaction)
    }
}

