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
import link.pihda.billofmaterial.entity.Transaction
import link.pihda.billofmaterial.util.JPAUtility

class TransactionDao {
    fun save(transaction: Transaction) {
        JPAUtility.getEntityManagerFactory().createEntityManager().use { entityManager: EntityManager ->
            entityManager.transaction.begin()
            entityManager.persist(transaction)
            entityManager.transaction.commit()
        }
    }

    fun getAll(): List<Transaction> {
        return JPAUtility.getEntityManagerFactory().createEntityManager().use { entityManager: EntityManager ->
            entityManager.createQuery("SELECT i FROM Transaction i", Transaction::class.java).resultList
        }
    }

    fun delete(transaction: Transaction) {
        JPAUtility.getEntityManagerFactory().createEntityManager().use { entityManager: EntityManager ->
            entityManager.transaction.begin()
            val managedTransaction =
                if (entityManager.contains(transaction)) transaction else entityManager.merge(transaction)
            entityManager.remove(managedTransaction)
            entityManager.transaction.commit()
        }
    }

    fun update(transaction: Transaction) {
        JPAUtility.getEntityManagerFactory().createEntityManager().use { entityManager: EntityManager ->
            entityManager.transaction.begin()
            entityManager.merge(transaction)
            entityManager.transaction.commit()
        }
    }
}

