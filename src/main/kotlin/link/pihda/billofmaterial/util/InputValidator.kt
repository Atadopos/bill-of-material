/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */
package link.pihda.billofmaterial.util

class InputValidator(regex: String, errorMessage: String) {
    val errorMessage: String
    private var regexLimiter = ".*"

    init {
        regexLimiter = regex
        this.errorMessage = errorMessage
    }

    fun isValid(input: String): Boolean {
        return input.matches(regexLimiter.toRegex())
    }
}