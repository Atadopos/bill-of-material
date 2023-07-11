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
import link.pihda.billofmaterial.enums.UnitOfMeasurement;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Entity
@Table(name = "items")
@Setter
@Getter
@Accessors(chain = true)
public class Item {
    @Id
    private UUID id;
    private String item;
    private Double quantity;
    @Enumerated(EnumType.STRING)
    private UnitOfMeasurement unit;
    private Double price;
    private Double totalPrice;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String buyLink;

    public Item initializeUUID() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        return this;
    }
}