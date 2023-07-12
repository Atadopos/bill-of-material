/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */
package link.pihda.billofmaterial.ui

import javafx.beans.value.ChangeListener
import javafx.scene.control.TextField
import javafx.scene.control.Tooltip
import link.pihda.billofmaterial.util.InputValidator

class RegexLimitingTextField : TextField() {
    var inputValidator: InputValidator
        get() {
            TODO()
        }
        set(value) {
            this.validator = value
            tooltip.text = value.errorMessage
        }
    private var tooltip: Tooltip = Tooltip()
    private var validator: InputValidator? = null

    init {
        tooltip.style = "-fx-background-color: red; -fx-text-fill: white;"

        focusedProperty().addListener(ChangeListener { _, _, newValue ->
            if (!newValue) {
                validateInput()
            }
        })

        textProperty().addListener(ChangeListener { _, _, _ ->
            if (!isFocused) {
                validateInput()
            }
        })
    }
    private fun validateInput() {
        if (!validator!!.isValid(text)) {
            style = "-fx-border-color: red;"
            setTooltip(tooltip)
        } else {
            style = null
            setTooltip(null)
        }
    }
}
