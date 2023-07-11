/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

package link.pihda.billofmaterial.util;


public class InputValidator {
    private final String errorMessage;
    private String regexLimiter = ".*";

    public InputValidator(String regex, String errorMessage) {
        this.regexLimiter = regex;
        this.errorMessage = errorMessage;
    }

    public boolean isValid(String input) {
        return input.matches(regexLimiter);
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}