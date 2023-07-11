/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

package link.pihda.billofmaterial.util;

public class PathUtil {
    public static String GetUserDataPath() {
        String appName = "BillOfMaterial";

        String os = System.getProperty("os.name").toLowerCase();
        String userHome = System.getProperty("user.home");

        if (os.contains("win")) {
            // Windows
            return userHome + "\\AppData\\Local\\" + appName + "\\";
        } else if (os.contains("nux") || os.contains("nix") || os.contains("aix") || os.contains("mac")) {
            // Unix-based (Linux, Mac, etc.)
            return userHome + "/.local/share/" + appName + "/";
        } else {
            // Other OS (fallback)
            return "./";
        }
    }
}
