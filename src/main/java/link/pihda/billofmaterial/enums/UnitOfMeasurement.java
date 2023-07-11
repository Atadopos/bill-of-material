/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

package link.pihda.billofmaterial.enums;

public enum UnitOfMeasurement {
    PIECE("Piece"),
    UNIT("Unit"),
    KILOGRAM("Kilogram"),
    GRAM("Gram"),
    METER("Meter"),
    CENTIMETER("Centimeter"),
    MILLIMETER("Millimeter"),
    LITER("Liter"),
    MILLILITER("Milliliter"),
    SQUARE_METER("Square Meter"),
    CUBIC_METER("Cubic Meter"),
    FOOT("Foot"),
    INCH("Inch"),
    YARD("Yard"),
    GALLON("Gallon"),
    POUND("Pound"),
    OUNCE("Ounce"),
    BOX("Box"),
    ROLL("Roll"),
    PACK("Pack"),
    BAG("Bag"),
    BOTTLE("Bottle"),
    CAN("Can"),
    CARTON("Carton"),
    CASE("Case"),
    CONTAINER("Container"),
    REAM("Ream"),
    SHEET("Sheet"),
    TUBE("Tube");

    private final String displayName;

    UnitOfMeasurement(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

