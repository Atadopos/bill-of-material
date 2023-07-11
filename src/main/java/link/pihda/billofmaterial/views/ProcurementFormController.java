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
import link.pihda.billofmaterial.service.ProcurementService;

import static link.pihda.billofmaterial.util.GuiUtil.ChangeScene;

public class ProcurementFormController {
    private final ProcurementService procurementService = new ProcurementService();
    public Button saveButton;
    public TextField procurementNameField;

    public static FXMLLoader getView() {
        return new FXMLLoader(ProcurementFormController.class.getResource("ProcurementForm.fxml"));

    }


    public void init() {
        saveButton.disableProperty().bind(
                Bindings.createBooleanBinding(() ->
                                procurementNameField.getText().isEmpty(),
                        procurementNameField.textProperty()
                )
        );
    }

    public void handleSave(ActionEvent actionEvent) {
        var procurement = new Procurement().initializeUUID().setName(procurementNameField.getText());
        procurementService.save(procurement);
        closeWindow(actionEvent);
    }

    public void handleCancel(ActionEvent actionEvent) {
        closeWindow(actionEvent);
    }

    public void closeWindow(ActionEvent actionEvent) {
        ProcurementListViewController procurementListViewController = ChangeScene(ProcurementListViewController.getView(), actionEvent);
        procurementListViewController.init();
    }
}
