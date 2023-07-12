/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */
package link.pihda.billofmaterial.entity

import jakarta.persistence.*
import link.pihda.billofmaterial.enums.UnitOfMeasurement
import java.util.*

@Entity
@Table(name = "items")
open class Item {
    @Id
    var id: UUID = UUID.randomUUID()
    var item: String = ""
    var quantity: Double = 0.0

    @Enumerated(EnumType.STRING)
    var unit: UnitOfMeasurement = UnitOfMeasurement.UNIT
    var price: Double = 0.0
    var totalPrice: Double = 0.0

    @Column(columnDefinition = "TEXT")
    var description: String = ""
    var buyLink: String = ""
}