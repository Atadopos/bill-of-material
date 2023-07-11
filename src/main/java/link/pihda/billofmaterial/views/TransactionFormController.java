/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

package link.pihda.billofmaterial.views;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import link.pihda.billofmaterial.entity.Procurement;
import link.pihda.billofmaterial.entity.Transaction;
import link.pihda.billofmaterial.service.ProcurementService;

import static link.pihda.billofmaterial.util.GuiUtil.ChangeScene;

public class TransactionFormController {
    private final ProcurementService procurementService = new ProcurementService();
    public TextField transactionNameField;
    public Button saveButton;

    private Procurement procurement;

    public static FXMLLoader getView() {
        return new FXMLLoader(TransactionFormController.class.getResource("TransactionForm.fxml"));
    }

    public void handleSave(ActionEvent actionEvent) {
        var transaction = new Transaction()
                .initializeUUID()
                .setTaxes(0d)
                .setShippingRate(0d)
                .setTransactionName(transactionNameField.getText());
        procurement.getTransactions().add(transaction);
        procurementService.update(procurement);
        closeWindow(actionEvent);
    }

    public void handleCancel(ActionEvent actionEvent) {
        closeWindow(actionEvent);
    }

    public void closeWindow(ActionEvent actionEvent) {
        TransactionListViewController transactionListViewController = ChangeScene(TransactionListViewController.getView(), actionEvent);
        transactionListViewController.init(procurement);
    }

    public void init(Procurement procurement) {
        this.procurement = procurement;
        saveButton.disableProperty().bind(
                Bindings.createBooleanBinding(() ->
                                transactionNameField.getText().isEmpty(),
                        transactionNameField.textProperty()
                )
        );
    }
}
