/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

package link.pihda.billofmaterial.entity;

import jakarta.persistence.*;
import link.pihda.billofmaterial.enums.CurrencyCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Setter
@Getter
@Accessors(chain = true)
public class Transaction {
    @Id
    private UUID id;
    private String transactionName = "";
    private Double taxes = 0d;
    private Double shippingRate = 0d;
    private Double totalPrice = 0d;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_id")
    private List<Item> items = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private CurrencyCode currencyCode = CurrencyCode.IDR;

    public Transaction initializeUUID() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        return this;
    }
}
