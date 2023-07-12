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
import javafx.scene.control.*
import link.pihda.billofmaterial.entity.Item
import link.pihda.billofmaterial.entity.Procurement
import link.pihda.billofmaterial.entity.Transaction
import link.pihda.billofmaterial.enums.UnitOfMeasurement
import link.pihda.billofmaterial.service.ItemService
import link.pihda.billofmaterial.service.ProcurementService
import link.pihda.billofmaterial.service.TransactionService
import link.pihda.billofmaterial.ui.RegexLimitingTextField
import link.pihda.billofmaterial.util.InputValidator
import link.pihda.billofmaterial.util.GuiUtil.ChangeScene

class ItemFormController {
    private val itemService = ItemService()
    private val transactionService = TransactionService()
    private val quantityValidator = InputValidator("^[0-9]*\\.?[0-9]*$", "Only numbers and decimal point are allowed")
    private val priceValidator = InputValidator("^[0-9]*\\.?[0-9]*$", "Only numbers and decimal point are allowed")
    private val urlValidator = InputValidator("^(https?)://[\\w.-]+(\\.[\\w.-]+)+([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?$", "Input is not a valid URL")
    private val procurementService = ProcurementService()
    lateinit var priceField: RegexLimitingTextField
    lateinit var buyLinkField: RegexLimitingTextField
    lateinit var unitField: ComboBox<UnitOfMeasurement>
    lateinit var itemField: TextField
    lateinit var quantityField: RegexLimitingTextField
    lateinit var descriptionField: TextField
    lateinit var saveButton: Button
    private var transaction: Transaction? = null
    private var item: Item? = null
    private var procurement: Procurement? = null

    companion object {
        fun getView() = FXMLLoader(ItemFormController::class.java.getResource("ItemForm.fxml"))
    }

    fun setItem(item: Item) {
        priceField.text = item.price.toString()
        buyLinkField.text = item.buyLink
        unitField.value = item.unit
        itemField.text = item.item
        quantityField.text = item.quantity.toString()
        descriptionField.text = item.description
        this.item = item
    }

    fun handleSave(event: ActionEvent) {
        if (itemField.text.isEmpty() || quantityField.text.isEmpty() ||
            unitField.value == null || priceField.text.isEmpty() || buyLinkField.text.isEmpty()) {
            val alert = Alert(Alert.AlertType.WARNING)
            alert.title = "Empty Fields"
            alert.headerText = null
            alert.contentText = "Please fill out all fields before submitting!"
            alert.showAndWait()
            return
        }

        if (item == null) {
            item = Item()
            transaction?.items?.add(item!!)
        }

        item?.apply {
            item = itemField.text
            quantity = quantityField.text.toDouble()
            unit = unitField.value
            description = descriptionField.text
            price = priceField.text.toDouble()
            buyLink = buyLinkField.text
            totalPrice = quantity * price
        }

        itemService.update(item!!)
        transactionService.update(transaction!!)
        procurementService.update(procurement!!)

        val itemListViewController = ChangeScene<ItemListViewController>(ItemListViewController.getView(), event)
        itemListViewController.init(procurement!!, transaction!!)
    }

    fun handleCancel(event: ActionEvent) {
        val itemListViewController = ChangeScene<ItemListViewController>(ItemListViewController.getView(), event)
        itemListViewController.init(procurement!!, transaction!!)
    }

    fun init(procurement: Procurement, transaction: Transaction) {
        this.procurement = procurement
        this.transaction = transaction

        quantityField.inputValidator = quantityValidator
        priceField.inputValidator = priceValidator
        buyLinkField.inputValidator = urlValidator
        unitField.items.addAll(UnitOfMeasurement.entries.toTypedArray())

        saveButton.disableProperty().bind(
            Bindings.createBooleanBinding({
                itemField.text.isEmpty() || !quantityValidator.isValid(quantityField.text) ||
                        unitField.value == null || !priceValidator.isValid(priceField.text) ||
                        !urlValidator.isValid(buyLinkField.text)
            },
                itemField.textProperty(),
                quantityField.textProperty(),
                unitField.valueProperty(),
                priceField.textProperty(),
                buyLinkField.textProperty()
            )
        )
    }
}
