/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

package link.pihda.billofmaterial.ui;

import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import link.pihda.billofmaterial.util.InputValidator;

public class RegexLimitingTextField extends TextField {
    private final Tooltip tooltip;
    private InputValidator validator;

    public RegexLimitingTextField() {
        tooltip = new Tooltip();
        tooltip.setStyle("-fx-background-color: red; -fx-text-fill: white;");

        focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateInput();
            }
        });

        textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isFocused()) {
                validateInput();
            }
        });
    }

    public void setInputValidator(InputValidator validator) {
        this.validator = validator;
        tooltip.setText(validator.getErrorMessage());
    }

    private void validateInput() {
        if (!validator.isValid(getText())) {
            setStyle("-fx-border-color: red;");
            setTooltip(tooltip);
        } else {
            setStyle(null);
            setTooltip(null);
        }
    }
}
