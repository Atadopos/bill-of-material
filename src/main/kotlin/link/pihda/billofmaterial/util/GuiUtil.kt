/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */
package link.pihda.billofmaterial.util

import javafx.event.ActionEvent
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import java.io.IOException

object GuiUtil {
    fun <T> changeScene(fxmlLoader: FXMLLoader, actionEvent: ActionEvent): T {
        var controller: T? = null
        try {
            // Load the transaction form FXML file
            val transactionForm: Parent = fxmlLoader.load()

            // Set the form content as the current scene
            val scene = (actionEvent.source as Node).scene
            val stage = scene.window as Stage
            stage.scene = Scene(transactionForm, scene.width, scene.height)

            // Get the controller
            controller = fxmlLoader.getController()

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return controller!!
    }
}

