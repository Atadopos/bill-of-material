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
import jakarta.persistence.Persistence;
import link.pihda.billofmaterial.entity.Procurement;
import link.pihda.billofmaterial.util.JPAUtility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static link.pihda.billofmaterial.util.PathUtil.GetUserDataPath;

public class ProcurementDao {
    public void save(Procurement procurement) {
        try (EntityManager entityManager = JPAUtility.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(procurement);
            entityManager.getTransaction().commit();
        }
    }

    public List<Procurement> getAll() {
        Map<String, String> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.url", "jdbc:h2:file:" + GetUserDataPath() + "data;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        var entityManagerFactory = Persistence.createEntityManagerFactory("myPU", properties);
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery("SELECT i FROM Procurement i", Procurement.class).getResultList();
        }

    }


    public void delete(Procurement procurement) {
        try (EntityManager entityManager = JPAUtility.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(procurement) ? procurement : entityManager.merge(procurement));
            entityManager.getTransaction().commit();
        }
    }

    public void update(Procurement procurement) {
        try (EntityManager entityManager = JPAUtility.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.merge(procurement);
            entityManager.getTransaction().commit();
        }
    }
}
