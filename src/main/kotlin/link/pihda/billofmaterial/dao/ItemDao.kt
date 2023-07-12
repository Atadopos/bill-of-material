/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */
package link.pihda.billofmaterial.dao

import jakarta.persistence.EntityManager
import link.pihda.billofmaterial.entity.Item
import link.pihda.billofmaterial.util.JPAUtility

class ItemDao {
    fun save(item: Item) {
        JPAUtility.getEntityManagerFactory().createEntityManager().use { entityManager: EntityManager ->
            entityManager.transaction.begin()
            entityManager.persist(item)
            entityManager.transaction.commit()
        }
    }

    fun getAll(): List<Item> {
        return JPAUtility.getEntityManagerFactory().createEntityManager().use { entityManager: EntityManager ->
            entityManager.createQuery("SELECT i FROM Item i", Item::class.java).resultList
        }
    }

    fun delete(item: Item) {
        JPAUtility.getEntityManagerFactory().createEntityManager().use { entityManager: EntityManager ->
            entityManager.transaction.begin()
            val managedItem = if (entityManager.contains(item)) item else entityManager.merge(item)
            entityManager.remove(managedItem)
            entityManager.transaction.commit()
        }
    }

    fun update(item: Item) {
        JPAUtility.getEntityManagerFactory().createEntityManager().use { entityManager: EntityManager ->
            entityManager.transaction.begin()
            entityManager.merge(item)
            entityManager.transaction.commit()
        }
    }
}
