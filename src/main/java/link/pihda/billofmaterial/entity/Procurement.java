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
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "procurements")
@Setter
@Getter
@Accessors(chain = true)
public class Procurement {
    @Id
    private UUID id;
    private String Name;
    private Double totalPrice = 0d;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "procurement_id")
    private List<Transaction> transactions = new ArrayList<>();

    public Procurement initializeUUID() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        return this;
    }
}
