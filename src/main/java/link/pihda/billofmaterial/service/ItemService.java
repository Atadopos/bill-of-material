/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

package link.pihda.billofmaterial.service;

import link.pihda.billofmaterial.dao.ItemDao;
import link.pihda.billofmaterial.entity.Item;

import java.util.List;

public class ItemService {

    private final ItemDao itemDao = new ItemDao();

    public void save(Item item) {
        item.setTotalPrice(item.getPrice() * item.getQuantity());
        itemDao.save(item);
    }

    public List<Item> getAll() {
        return itemDao.getAll();
    }

    public void delete(Item item) {
        itemDao.delete(item);
    }

    public void update(Item item) {
        itemDao.update(item);
    }

    // Other service methods
}
