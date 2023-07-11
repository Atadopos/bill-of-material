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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import link.pihda.billofmaterial.entity.Procurement;
import link.pihda.billofmaterial.entity.Transaction;
import link.pihda.billofmaterial.enums.CurrencyCode;
import link.pihda.billofmaterial.service.ProcurementService;
import link.pihda.billofmaterial.service.TransactionService;

import java.text.NumberFormat;
import java.util.concurrent.atomic.AtomicInteger;

import static link.pihda.billofmaterial.util.GuiUtil.ChangeScene;
import static link.pihda.billofmaterial.util.TableUtil.createRowWithClickHandler;
import static link.pihda.billofmaterial.util.TableUtil.setDoubleCellFactory;

public class TransactionListViewController {
    private final TransactionService transactionService = new TransactionService();
    private final AtomicInteger lastSelectedIndex = new AtomicInteger(-1);
    private final ProcurementService procurementService = new ProcurementService();
    public TableView<Transaction> transactionTableView;
    public TableColumn<Transaction, String> transactionNameColumn;
    public TableColumn<Transaction, Double> totalPriceColumn;
    public Button removeButton;
    public Button viewButton;
    public TableColumn<Transaction, CurrencyCode> currencyColumn;
    public TextField procurementField;
    public Button backButton;
    public Label totalLabel;
    private Procurement procurement;

    public static FXMLLoader getView() {
        return new FXMLLoader(TransactionListViewController.class.getResource("TransactionListView.fxml"));
    }

    public void handleViewTransaction(ActionEvent event) {
        Transaction selectedTransaction = transactionTableView.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            ItemListViewController itemListViewController = ChangeScene(ItemListViewController.getView(), event);
            itemListViewController.init(procurement, selectedTransaction);
        }
    }

    public void handleNewTransaction(ActionEvent actionEvent) {
        TransactionFormController transactionFormController = ChangeScene(TransactionFormController.getView(), actionEvent);
        transactionFormController.init(procurement);
    }

    private void loadTransactions() {
        ObservableList<Transaction> transactions = FXCollections.observableArrayList(procurement.getTransactions());
        // Set up the columns in the table
        transactionNameColumn.setCellValueFactory(new PropertyValueFactory<>("transactionName"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currencyCode"));
        setDoubleCellFactory(totalPriceColumn);
        // Add the data to the table
        transactionTableView.setItems(transactions);
    }

    public void handleRemoveTransaction(ActionEvent actionEvent) {
        Transaction selectedTransaction = transactionTableView.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            transactionService.delete(selectedTransaction);
            transactionTableView.getItems().remove(selectedTransaction);
        } else {
            System.out.println("No item selected to remove.");
        }
    }

    public void changeProcurementName(ActionEvent actionEvent) {
        String newProcurementName = procurementField.getText();
        if (newProcurementName != null && !newProcurementName.trim().isEmpty()) {
            procurement.setName(newProcurementName);
            procurementService.update(procurement);
        } else {
            procurementField.setText(procurement.getName());
        }
    }

    public void handleBack(ActionEvent actionEvent) {
        ProcurementListViewController procurementListViewController = ChangeScene(ProcurementListViewController.getView(), actionEvent);
        procurementListViewController.init();
    }

    public void init(Procurement procurement) {
        transactionTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        removeButton.disableProperty().bind(transactionTableView.getSelectionModel().selectedItemProperty().isNull());
        viewButton.disableProperty().bind(transactionTableView.getSelectionModel().selectedItemProperty().isNull());
        // Set custom row factory to handle row clicks
        transactionTableView.setRowFactory(tv -> createRowWithClickHandler(transactionTableView, lastSelectedIndex));

        this.procurement = procurement;
        procurementField.setText(procurement.getName());
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        totalLabel.setText(format.format(procurement.getTotalPrice()));
        loadTransactions();
    }
}

