package link.pihda.billofmaterial.entity/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */
import jakarta.persistence.*
import link.pihda.billofmaterial.enums.CurrencyCode
import java.util.*

@Entity
@Table(name = "transactions")
open class Transaction {
    @Id
    var id: UUID = UUID.randomUUID()
    var transactionName: String = ""
    var taxes: Double = 0.0
    var shippingRate: Double = 0.0
    var totalPrice: Double = 0.0

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_id")
    var items: MutableList<Item> = ArrayList()

    @Enumerated(EnumType.STRING)
    var currencyCode: CurrencyCode = CurrencyCode.IDR
}
