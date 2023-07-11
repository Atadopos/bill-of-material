/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

package link.pihda.billofmaterial.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import link.pihda.billofmaterial.entity.Procurement;
import link.pihda.billofmaterial.service.ProcurementService;

import java.util.concurrent.atomic.AtomicInteger;

import static link.pihda.billofmaterial.util.GuiUtil.ChangeScene;
import static link.pihda.billofmaterial.util.TableUtil.createRowWithClickHandler;
import static link.pihda.billofmaterial.util.TableUtil.setDoubleCellFactory;

public class ProcurementListViewController {
    private final ProcurementService procurementService = new ProcurementService();
    private final AtomicInteger lastSelectedIndex = new AtomicInteger(-1);
    public TableView<Procurement> procurementTableView;
    public TableColumn<Procurement, String> procurementNameColumn;
    public TableColumn<Procurement, Double> totalPriceColumn;
    public Button viewButton;
    public Button removeButton;
    public TextField procurementField;

    public static FXMLLoader getView() {
        return new FXMLLoader(ProcurementListViewController.class.getResource("ProcurementListView.fxml"));
    }

    public void init() {
        procurementTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        viewButton.disableProperty().bind(procurementTableView.getSelectionModel().selectedItemProperty().isNull());
        removeButton.disableProperty().bind(procurementTableView.getSelectionModel().selectedItemProperty().isNull());
        procurementTableView.setRowFactory(tv -> createRowWithClickHandler(procurementTableView, lastSelectedIndex));
        loadProcurements();
    }

    public void viewProcurement(ActionEvent actionEvent) {
        Procurement selectedProcurement = procurementTableView.getSelectionModel().getSelectedItem();
        if (selectedProcurement != null) {
            TransactionListViewController transactionListViewController = ChangeScene(TransactionListViewController.getView(), actionEvent);
            transactionListViewController.init(selectedProcurement);
        }
    }

    public void handleRemoveProcurement(ActionEvent actionEvent) {
        Procurement selectedProcurement = procurementTableView.getSelectionModel().getSelectedItem();
        if (selectedProcurement != null) {
            procurementService.delete(selectedProcurement);
            procurementTableView.getItems().remove(selectedProcurement);
        } else {
            System.out.println("No procurement selected to remove.");
        }
    }

    public void handleNewProcurement(ActionEvent actionEvent) {
        ProcurementFormController procurementFormController = ChangeScene(ProcurementFormController.getView(), actionEvent);
        procurementFormController.init();
    }

    private void loadProcurements() {
        ObservableList<Procurement> procurements = FXCollections.observableArrayList(procurementService.getAll());
        procurementNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        setDoubleCellFactory(totalPriceColumn);
        procurementTableView.setItems(procurements);
    }
}
