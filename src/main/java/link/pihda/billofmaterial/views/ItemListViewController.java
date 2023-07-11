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
import link.pihda.billofmaterial.entity.Item;
import link.pihda.billofmaterial.entity.Procurement;
import link.pihda.billofmaterial.entity.Transaction;
import link.pihda.billofmaterial.enums.CurrencyCode;
import link.pihda.billofmaterial.service.ProcurementService;
import link.pihda.billofmaterial.service.TransactionService;
import link.pihda.billofmaterial.ui.RegexLimitingTextField;
import link.pihda.billofmaterial.util.ExcelGenerator;
import link.pihda.billofmaterial.util.InputValidator;

import java.text.NumberFormat;
import java.util.concurrent.atomic.AtomicInteger;

import static link.pihda.billofmaterial.util.GuiUtil.ChangeScene;
import static link.pihda.billofmaterial.util.TableUtil.createRowWithClickHandler;
import static link.pihda.billofmaterial.util.TableUtil.setDoubleCellFactory;

public final class ItemListViewController {

    // Services
    private final TransactionService transactionService = new TransactionService();
    private final ProcurementService procurementService = new ProcurementService();
    // State
    private final AtomicInteger lastSelectedIndex = new AtomicInteger(-1);
    // UI Components
    public TableView<Item> itemListTable;
    public TableColumn<Item, String> itemColumn;
    public TableColumn<Item, Double> quantityColumn;
    public TableColumn<Item, String> unitColumn;
    public TableColumn<Item, Double> priceColumn;
    public TableColumn<Item, Double> totalPriceColumn;
    public TextArea descriptionArea;
    public TextArea buyLinkArea;
    public Label descriptionLabel;
    public Label buyLinkLabel;
    public Label subtotalLabel;
    public Label taxesLabel;
    public Label shippingFeesLabel;
    public Label totalLabel;
    public ComboBox<CurrencyCode> currencyComboBox;
    public Button removeItemButton;
    public RegexLimitingTextField taxesInput;
    public RegexLimitingTextField shippingFeesInput;
    public Button backButton;
    public TextField transactionNameField;
    public Button editItemButton;
    private Transaction transaction;

    private Procurement procurement;

    // Method to get the FXML view
    public static FXMLLoader getView() {
        return new FXMLLoader(ItemListViewController.class.getResource("ItemListView.fxml"));
    }

    private void updateTransactionDetails() {
        if (transaction != null) {
            NumberFormat format = NumberFormat.getNumberInstance();
            format.setMinimumFractionDigits(2);
            format.setMaximumFractionDigits(2);

            // Load items and set table data
            ObservableList<Item> items = FXCollections.observableArrayList(transaction.getItems());
            itemListTable.setItems(items);

            // Calculating the subtotal by summing up total prices of all items.
            double subtotal = items.stream().mapToDouble(Item::getTotalPrice).sum();

            // Calculating the total cost of the transaction
            transaction.setTotalPrice(subtotal + transaction.getTaxes() + transaction.getShippingRate());
            transactionService.update(transaction);
            procurementService.update(procurement);

            // Display the details
            subtotalLabel.setText(format.format(subtotal));
            taxesLabel.setText(format.format(transaction.getTaxes()));
            shippingFeesLabel.setText(format.format(transaction.getShippingRate()));
            totalLabel.setText(format.format(transaction.getTotalPrice()));
        }
    }

    // Round to two decimal points


    // Method to initialize the BOM Table
    private void initializeTable() {
        taxesInput.setInputValidator(new InputValidator("^[0-9]*$", "Only numbers are allowed"));
        shippingFeesInput.setInputValidator(new InputValidator("^[0-9]*$", "Only numbers are allowed"));
        itemListTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        // Set cell value factories
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        setDoubleCellFactory(priceColumn);
        setDoubleCellFactory(totalPriceColumn);

        // Set table selection listener
        itemListTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> handleTableSelection(newSelection)
        );

        // Set custom row factory to handle row clicks
        itemListTable.setRowFactory(tv -> createRowWithClickHandler(itemListTable, lastSelectedIndex));

        // Bind button disable property to table selection
        removeItemButton.disableProperty().bind(itemListTable.getSelectionModel().selectedItemProperty().isNull());
        editItemButton.disableProperty().bind(itemListTable.getSelectionModel().selectedItemProperty().isNull());

    }

    // Method to initialize currency ComboBox
    private void initializeCurrencyComboBox() {
        currencyComboBox.setItems(FXCollections.observableArrayList(CurrencyCode.values()));
    }

    // Method to initialize the information area
    private void initializeInfoArea() {
        collapseInfoArea(true);
    }


    // Method to handle table selection
    private void handleTableSelection(Item newSelection) {
        if (newSelection != null) {
            descriptionArea.setText(newSelection.getDescription());
            buyLinkArea.setText(newSelection.getBuyLink());
            collapseInfoArea(false);
        } else {
            collapseInfoArea(true);
        }
    }

    // Method to toggle the visibility of the information area
    private void collapseInfoArea(Boolean collapse) {
        boolean visible = !collapse;
        descriptionLabel.setVisible(visible);
        descriptionLabel.setManaged(visible);
        buyLinkLabel.setVisible(visible);
        buyLinkLabel.setManaged(visible);
        descriptionArea.setVisible(visible);
        descriptionArea.setManaged(visible);
        buyLinkArea.setVisible(visible);
        buyLinkArea.setManaged(visible);
    }

    // Method to add an item
    public void handleAddItem(ActionEvent actionEvent) {
        ItemFormController itemFormController = ChangeScene(ItemFormController.getView(), actionEvent);
        itemFormController.init(procurement, transaction);
    }

    // Method to remove an item
    public void handleRemoveItem(ActionEvent actionEvent) {
        Item selectedItem = itemListTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            transaction.getItems().remove(selectedItem);
            transactionService.update(transaction);
            itemListTable.getItems().remove(selectedItem);
        } else {
            System.out.println("No item selected to remove.");
        }
    }

    public void updateTaxes(ActionEvent actionEvent) {
        transactionService.update(transaction.setTaxes(Double.parseDouble(taxesInput.getText())));
        updateTransactionDetails();
    }

    public void updateShipping(ActionEvent actionEvent) {
        transactionService.update(transaction.setShippingRate(Double.parseDouble(shippingFeesInput.getText())));
        updateTransactionDetails();
    }

    public void handleBack(ActionEvent actionEvent) {
        TransactionListViewController transactionListViewController = ChangeScene(TransactionListViewController.getView(), actionEvent);
        transactionListViewController.init(procurement);
    }

    public void updateCurrency(ActionEvent actionEvent) {
        transactionService.update(transaction.setCurrencyCode(currencyComboBox.getValue()));
    }

    public void handleChangeTransactionName(ActionEvent actionEvent) {
        String newTransactionName = transactionNameField.getText();
        if (newTransactionName != null && !newTransactionName.trim().isEmpty()) {
            transaction.setTransactionName(newTransactionName);
            transactionService.update(transaction);
        } else {
            transactionNameField.setText(transaction.getTransactionName());
        }
    }

    public void handleEditItem(ActionEvent actionEvent) {
        Item selectedItem = itemListTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            ItemFormController itemFormController = ChangeScene(ItemFormController.getView(), actionEvent);
            itemFormController.init(procurement, transaction);
            itemFormController.setItem(selectedItem);
        } else {
            System.out.println("No item selected to update.");
        }
    }

    public void handleExportExcel(ActionEvent actionEvent) {
        ExcelGenerator.generateExcelFromItems(transaction.getItems());
    }

    public void init(Procurement procurement, Transaction transaction) {
        this.procurement = procurement;
        this.transaction = transaction;

        currencyComboBox.getSelectionModel().select(transaction.getCurrencyCode());
        transactionNameField.setText(transaction.getTransactionName());

        initializeTable();
        initializeCurrencyComboBox();
        initializeInfoArea();
        updateTransactionDetails();
    }
}
