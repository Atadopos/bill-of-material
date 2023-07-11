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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import link.pihda.billofmaterial.entity.Item;
import link.pihda.billofmaterial.entity.Procurement;
import link.pihda.billofmaterial.entity.Transaction;
import link.pihda.billofmaterial.enums.UnitOfMeasurement;
import link.pihda.billofmaterial.service.ItemService;
import link.pihda.billofmaterial.service.ProcurementService;
import link.pihda.billofmaterial.service.TransactionService;
import link.pihda.billofmaterial.ui.RegexLimitingTextField;
import link.pihda.billofmaterial.util.InputValidator;

import static link.pihda.billofmaterial.util.GuiUtil.ChangeScene;

public class ItemFormController {
    private final ItemService itemService = new ItemService();
    private final TransactionService transactionService = new TransactionService();
    private final InputValidator quantityValidator = new InputValidator("^[0-9]*\\.?[0-9]*$", "Only numbers and decimal point are allowed");
    private final InputValidator priceValidator = new InputValidator("^[0-9]*\\.?[0-9]*$", "Only numbers and decimal point are allowed");
    private final InputValidator urlValidator = new InputValidator("^(https?)://[\\w.-]+(\\.[\\w.-]+)+([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?$", "Input is not a valid URL");
    private final ProcurementService procurementService = new ProcurementService();
    public RegexLimitingTextField priceField;
    public RegexLimitingTextField buyLinkField;
    public ComboBox<UnitOfMeasurement> unitField;
    public TextField itemField;
    public RegexLimitingTextField quantityField;
    public TextField descriptionField;
    public Button saveButton;
    private Transaction transaction;
    private Item item;
    private Procurement procurement;

    public static FXMLLoader getView() {
        return new FXMLLoader(ItemFormController.class.getResource("ItemForm.fxml"));
    }

    public void setItem(Item item) {
        priceField.setText(String.valueOf(item.getPrice()));
        buyLinkField.setText((item.getBuyLink()));
        unitField.setValue(item.getUnit());
        itemField.setText(item.getItem());
        quantityField.setText(String.valueOf(item.getQuantity()));
        descriptionField.setText(item.getDescription());
        this.item = item;
    }

    public void handleSave(ActionEvent event) {
        // Checking if any fields are empty
        if (itemField.getText().isEmpty() || quantityField.getText().isEmpty() ||
                unitField.getValue() == null ||
                priceField.getText().isEmpty() || buyLinkField.getText().isEmpty()) {
            // Here you can show an alert to the user, or handle this situation in your own way
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Fields");
            alert.setHeaderText(null);
            alert.setContentText("Please fill out all fields before submitting!");
            alert.showAndWait();
            return;
        }

        if (item == null) {
            item = new Item().initializeUUID();
            transaction.getItems().add(item);
        }

        item.setItem(itemField.getText())
                .setQuantity(Double.valueOf(quantityField.getText()))
                .setUnit(unitField.getValue())
                .setDescription(descriptionField.getText())
                .setPrice(Double.valueOf(priceField.getText()))
                .setBuyLink(buyLinkField.getText());
        item.setTotalPrice(item.getQuantity() * item.getPrice());

        // Add the new item to your data
        itemService.update(item);
        transactionService.update(transaction);
        procurementService.update(procurement);

        ItemListViewController itemListViewController = ChangeScene(ItemListViewController.getView(), event);
        itemListViewController.init(procurement, transaction);
    }

    public void handleCancel(ActionEvent event) {
        // Switch back to the main view without adding a new item
        ItemListViewController itemListViewController = ChangeScene(ItemListViewController.getView(), event);
        itemListViewController.init(procurement, transaction);
    }

    public void init(Procurement procurement, Transaction transaction) {
        this.procurement = procurement;
        this.transaction = transaction;

        quantityField.setInputValidator(quantityValidator);
        priceField.setInputValidator(priceValidator);
        buyLinkField.setInputValidator(urlValidator);
        unitField.getItems().addAll(UnitOfMeasurement.values());

        saveButton.disableProperty().bind(
                Bindings.createBooleanBinding(() ->
                                itemField.getText().isEmpty() ||
                                        !quantityValidator.isValid(quantityField.getText()) ||
                                        unitField.getValue() == null ||
                                        !priceValidator.isValid(priceField.getText()) ||
                                        !urlValidator.isValid(buyLinkField.getText()),
                        itemField.textProperty(),
                        quantityField.textProperty(),
                        unitField.valueProperty(),
                        priceField.textProperty(),
                        buyLinkField.textProperty()
                )
        );
    }
}
