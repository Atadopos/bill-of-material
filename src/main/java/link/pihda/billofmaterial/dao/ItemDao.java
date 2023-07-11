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
import link.pihda.billofmaterial.entity.Item;
import link.pihda.billofmaterial.util.JPAUtility;

import java.util.List;

public class ItemDao {
    public void save(Item item) {
        try (EntityManager entityManager = JPAUtility.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(item);
            entityManager.getTransaction().commit();
        }

    }

    public List<Item> getAll() {
        try (EntityManager entityManager = JPAUtility.getEntityManagerFactory().createEntityManager()) {
            return entityManager.createQuery("SELECT i FROM Item i", Item.class).getResultList();
        }
    }

    public void delete(Item item) {
        try (EntityManager entityManager = JPAUtility.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(item) ? item : entityManager.merge(item));
            entityManager.getTransaction().commit();
        }
    }

    public void update(Item item) {
        try (EntityManager entityManager = JPAUtility.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.merge(item);
            entityManager.getTransaction().commit();
        }
    }
}