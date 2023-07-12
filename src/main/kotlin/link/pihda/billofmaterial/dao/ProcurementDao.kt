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
import link.pihda.billofmaterial.entity.Procurement
import link.pihda.billofmaterial.util.JPAUtility

class ProcurementDao {
    fun save(procurement: Procurement) {
        JPAUtility.getEntityManagerFactory().createEntityManager().use { entityManager: EntityManager ->
            entityManager.transaction.begin()
            entityManager.persist(procurement)
            entityManager.transaction.commit()
        }
    }

    fun getAll(): List<Procurement> {
        return JPAUtility.getEntityManagerFactory().createEntityManager().use { entityManager: EntityManager ->
            entityManager.createQuery("SELECT i FROM Procurement i", Procurement::class.java).resultList
        }
    }

    fun delete(procurement: Procurement) {
        JPAUtility.getEntityManagerFactory().createEntityManager().use { entityManager: EntityManager ->
            entityManager.transaction.begin()
            val managedProcurement =
                if (entityManager.contains(procurement)) procurement else entityManager.merge(procurement)
            entityManager.remove(managedProcurement)
            entityManager.transaction.commit()
        }
    }

    fun update(procurement: Procurement) {
        JPAUtility.getEntityManagerFactory().createEntityManager().use { entityManager: EntityManager ->
            entityManager.transaction.begin()
            entityManager.merge(procurement)
            entityManager.transaction.commit()
        }
    }
}


