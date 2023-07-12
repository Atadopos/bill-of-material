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
import java.util.*

@Entity
@Table(name = "procurements")
open class Procurement {
    @Id
    var id: UUID = UUID.randomUUID()
    var name: String = ""
    var totalPrice: Double = 0.0

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "procurement_id")
    var transactions: MutableList<Transaction> = ArrayList()
}
