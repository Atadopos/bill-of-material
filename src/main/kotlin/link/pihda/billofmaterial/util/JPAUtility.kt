/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */
package link.pihda.billofmaterial.util

import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.Persistence

object JPAUtility {
    private var entityManagerFactory: EntityManagerFactory? = null

    init {
        try {
            val properties: MutableMap<String, String> = HashMap()
            properties["jakarta.persistence.jdbc.url"] =
                "jdbc:h2:file:${PathUtil.GetUserDataPath()}data;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE"
            entityManagerFactory = Persistence.createEntityManagerFactory("myPU", properties)
        } catch (ex: Throwable) {
            // Log the exception and throw an exception so the application fails to start
            System.err.println("Initial EntityManagerFactory creation failed.$ex")
            throw ExceptionInInitializerError(ex)
        }
    }

    fun getEntityManagerFactory(): EntityManagerFactory {
        if (entityManagerFactory == null) {
            error("unable to create entityManagerFactory")
        } else {
            return entityManagerFactory as EntityManagerFactory

        }
    }

    fun close() {
        entityManagerFactory?.close()
    }
}

