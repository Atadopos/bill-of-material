/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */
package link.pihda.billofmaterial.util

object PathUtil {
    fun GetUserDataPath(): String {
        val appName = "BillOfMaterial"
        val os: String = java.lang.System.getProperty("os.name").lowercase(java.util.Locale.getDefault())
        val userHome: String = java.lang.System.getProperty("user.home")
        return if (os.contains("win")) {
            // Windows
            "$userHome\\AppData\\Local\\$appName\\"
        } else if (os.contains("nux") || os.contains("nix") || os.contains("aix") || os.contains("mac")) {
            // Unix-based (Linux, Mac, etc.)
            "$userHome/.local/share/$appName/"
        } else {
            // Other OS (fallback)
            "./"
        }
    }
}
