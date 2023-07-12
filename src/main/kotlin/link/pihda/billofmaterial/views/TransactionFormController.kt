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
import link.pihda.billofmaterial.entity.Transaction
import link.pihda.billofmaterial.service.ProcurementService
import link.pihda.billofmaterial.util.GuiUtil.changeScene

class TransactionFormController {
    private val procurementService = ProcurementService()
    lateinit var transactionNameField: TextField
    lateinit var saveButton: Button
    private lateinit var procurement: Procurement

    companion object {
        fun getView(): FXMLLoader {
            return FXMLLoader(TransactionFormController::class.java.getResource("TransactionForm.fxml"))
        }
    }

    fun handleSave(actionEvent: ActionEvent) {
        val transaction = Transaction()
        transaction.transactionName = transactionNameField.text
        procurement.transactions.add(transaction)
        procurementService.update(procurement)
        closeWindow(actionEvent)
    }

    fun handleCancel(actionEvent: ActionEvent) {
        closeWindow(actionEvent)
    }

    private fun closeWindow(actionEvent: ActionEvent) {
        val transactionListViewController = changeScene<TransactionListViewController>(TransactionListViewController.getView(), actionEvent)
        transactionListViewController.init(procurement)
    }

    fun init(procurement: Procurement) {
        this.procurement = procurement
        saveButton.disableProperty().bind(
            Bindings.createBooleanBinding(
                { transactionNameField.text.isEmpty() },
                transactionNameField.textProperty()
            )
        )
    }
}

