/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

package link.pihda.billofmaterial.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiUtil {
    public static <T> T ChangeScene(FXMLLoader fxmlLoader, ActionEvent actionEvent) {
        T controller = null;
        try {
            // Load the transaction form FXML file
            Parent transactionForm = fxmlLoader.load();

            // Set the form content as the current scene
            var scene = ((Node) actionEvent.getSource()).getScene();
            var stage = (Stage) scene.getWindow();
            stage.setScene(new Scene(transactionForm, scene.getWidth(), scene.getHeight()));

            // Get the controller
            controller = fxmlLoader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return controller;
    }
}
