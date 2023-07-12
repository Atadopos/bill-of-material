/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */
package link.pihda.billofmaterial.service

import link.pihda.billofmaterial.dao.ProcurementDao
import link.pihda.billofmaterial.entity.Procurement

class ProcurementService {
    private val procurementDao = ProcurementDao()

    fun save(procurement: Procurement) {
        procurementDao.save(procurement)
    }

    fun getAll(): List<Procurement> {
        return procurementDao.getAll()
    }

    fun delete(procurement: Procurement) {
        procurementDao.delete(procurement)
    }

    fun update(procurement: Procurement) {
        procurement.totalPrice = procurement.transactions.sumOf { it.totalPrice }
        procurementDao.update(procurement)
    }
}
