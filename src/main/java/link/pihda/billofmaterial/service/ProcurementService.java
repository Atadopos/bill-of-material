/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

package link.pihda.billofmaterial.service;

import link.pihda.billofmaterial.dao.ProcurementDao;
import link.pihda.billofmaterial.entity.Procurement;
import link.pihda.billofmaterial.entity.Transaction;

import java.util.List;

public class ProcurementService {
    private final ProcurementDao procurementDao = new ProcurementDao();

    public void save(Procurement procurement) {
        procurementDao.save(procurement);
    }

    public List<Procurement> getAll() {
        return procurementDao.getAll();
    }

    public void delete(Procurement procurement) {
        procurementDao.delete(procurement);
    }

    public void update(Procurement procurement) {
        procurement.setTotalPrice(procurement.getTransactions().stream().mapToDouble(Transaction::getTotalPrice).sum());
        procurementDao.update(procurement);
    }
}
