/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */
package link.pihda.billofmaterial

import atlantafx.base.theme.CupertinoDark
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import link.pihda.billofmaterial.views.ProcurementListViewController

class BomApplication : Application() {
    override fun start(stage: Stage) {
        setUserAgentStylesheet(CupertinoDark().userAgentStylesheet)
        val fxmlLoader: FXMLLoader = ProcurementListViewController.getView()
        try {
            val scene = Scene(fxmlLoader.load())
            val procurementListViewController: ProcurementListViewController =
                fxmlLoader.getController()
            procurementListViewController.init()
            stage.title = "Bill of Materials"
            stage.scene = scene
            stage.show()
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
            if (ex.cause != null) {
                ex.cause!!.printStackTrace()
            }
        }
    }
}

fun main() {
    Application.launch(BomApplication::class.java)
}
