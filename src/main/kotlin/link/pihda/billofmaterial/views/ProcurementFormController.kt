/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */
package link.pihda.billofmaterial.views

import javafx.beans.binding.Bindings
import javafx.event.ActionEvent
import javafx.fxml.FXMLLoader
import javafx.scene.control.Button
import javafx.scene.control.TextField
import link.pihda.billofmaterial.entity.Procurement
import link.pihda.billofmaterial.service.ProcurementService
import link.pihda.billofmaterial.util.GuiUtil.changeScene

class ProcurementFormController {
    private val procurementService = ProcurementService()
    lateinit var saveButton: Button
    lateinit var procurementNameField: TextField

    companion object {
        fun getView(): FXMLLoader {
            return FXMLLoader(ProcurementFormController::class.java.getResource("ProcurementForm.fxml"))
        }
    }

    fun init() {
        saveButton.disableProperty().bind(
            Bindings.createBooleanBinding({
                procurementNameField.text.isEmpty()
            }, procurementNameField.textProperty())
        )
    }

    fun handleSave(actionEvent: ActionEvent) {
        val procurement = Procurement()
        procurement.name = procurementNameField.text
        procurementService.save(procurement)
        closeWindow(actionEvent)
    }

    fun handleCancel(actionEvent: ActionEvent) {
        closeWindow(actionEvent)
    }

    private fun closeWindow(actionEvent: ActionEvent) {
        val procurementListViewController =
            changeScene<ProcurementListViewController>(ProcurementListViewController.getView(), actionEvent)
        procurementListViewController.init()
    }
}
