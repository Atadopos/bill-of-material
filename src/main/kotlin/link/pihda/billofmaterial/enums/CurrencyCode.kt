/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */
package link.pihda.billofmaterial.enums

enum class CurrencyCode(private val displayName: String) {
    IDR("Indonesian Rupiah"),
    USD("United States Dollar"),
    EUR("Euro"),
    GBP("British Pound Sterling"),
    JPY("Japanese Yen"),
    AUD("Australian Dollar"),
    CAD("Canadian Dollar"),
    CNY("Chinese Yuan"),
    INR("Indian Rupee"),
    BRL("Brazilian Real"),
    ZAR("South African Rand"),
    NZD("New Zealand Dollar"),
    MXN("Mexican Peso"),
    SGD("Singapore Dollar"),
    HKD("Hong Kong Dollar"),
    KRW("South Korean Won"),
    SEK("Swedish Krona"),
    CHF("Swiss Franc"),
    RUB("Russian Ruble"),
    DKK("Danish Krone"),
    PLN("Polish Zloty"),
    NOK("Norwegian Krone"),
    TRY("Turkish Lira");

    override fun toString(): String {
        return displayName
    }
}
