/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

package link.pihda.billofmaterial;

import atlantafx.base.theme.CupertinoDark;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import link.pihda.billofmaterial.views.ProcurementListViewController;

public class BomApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {

        Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
        FXMLLoader fxmlLoader = ProcurementListViewController.getView();
        try {
            Scene scene = new Scene(fxmlLoader.load());
            ProcurementListViewController procurementListViewController = fxmlLoader.getController();
            procurementListViewController.init();
            stage.setTitle("Bill of Materials");
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (ex.getCause() != null) {
                ex.getCause().printStackTrace();
            }
        }

    }
}
