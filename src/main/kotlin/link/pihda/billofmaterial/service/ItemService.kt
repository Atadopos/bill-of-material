/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */
package link.pihda.billofmaterial.service

import link.pihda.billofmaterial.dao.ItemDao
import link.pihda.billofmaterial.entity.Item

class ItemService {
    private val itemDao = ItemDao()

    fun save(item: Item) {
        item.totalPrice = item.price * item.quantity
        itemDao.save(item)
    }

    fun getAll(): List<Item> {
        return itemDao.getAll()
    }

    fun delete(item: Item) {
        itemDao.delete(item)
    }

    fun update(item: Item) {
        itemDao.update(item)
    }

    // Other service methods
}
