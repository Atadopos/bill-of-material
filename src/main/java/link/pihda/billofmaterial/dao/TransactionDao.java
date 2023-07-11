/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

package link.pihda.billofmaterial.dao;

import jakarta.persistence.EntityManager;
import link.pihda.billofmaterial.entity.Transaction;
import link.pihda.billofmaterial.util.JPAUtility;

import java.util.List;

public class TransactionDao {
    public void save(link.pihda.billofmaterial.entity.Transaction transaction) {
        try (EntityManager entityManager = JPAUtility.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(transaction);
            entityManager.getTransaction().commit();
        }
    }

    public List<link.pihda.billofmaterial.entity.Transaction> getAll() {
        try (EntityManager entityManager = JPAUtility.getEntityManagerFactory().createEntityManager()) {
            return entityManager.createQuery("SELECT i FROM Transaction i", Transaction.class).getResultList();
        }
    }


    public void delete(link.pihda.billofmaterial.entity.Transaction transaction) {
        try (EntityManager entityManager = JPAUtility.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(transaction) ? transaction : entityManager.merge(transaction));
            entityManager.getTransaction().commit();
        }
    }

    public void update(link.pihda.billofmaterial.entity.Transaction transaction) {
        try (EntityManager entityManager = JPAUtility.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.merge(transaction);
            entityManager.getTransaction().commit();
        }
    }
}
