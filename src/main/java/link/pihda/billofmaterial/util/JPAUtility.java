/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

package link.pihda.billofmaterial.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static link.pihda.billofmaterial.util.PathUtil.GetUserDataPath;

public class JPAUtility {
    private static final EntityManagerFactory entityManagerFactory;

    static {
        try {
            Map<String, String> properties = new HashMap<>();
            properties.put("jakarta.persistence.jdbc.url", "jdbc:h2:file:" + GetUserDataPath() + "data;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
            entityManagerFactory = Persistence.createEntityManagerFactory("myPU", properties);
        } catch (Throwable ex) {
            // Log the exception and throw an exception so the application fails to start
            System.err.println("Initial EntityManagerFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static void close() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
