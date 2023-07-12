/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */
package link.pihda.billofmaterial.views

import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.util.Callback
import link.pihda.billofmaterial.entity.Item
import link.pihda.billofmaterial.entity.Procurement
import link.pihda.billofmaterial.entity.Transaction
import link.pihda.billofmaterial.enums.CurrencyCode
import link.pihda.billofmaterial.service.ProcurementService
import link.pihda.billofmaterial.service.TransactionService
import link.pihda.billofmaterial.ui.RegexLimitingTextField
import link.pihda.billofmaterial.util.ExcelGenerator
import link.pihda.billofmaterial.util.GuiUtil.changeScene
import link.pihda.billofmaterial.util.InputValidator
import link.pihda.billofmaterial.util.TableUtil.createRowWithClickHandler
import link.pihda.billofmaterial.util.TableUtil.setDoubleCellFactory
import java.text.NumberFormat
import java.util.concurrent.atomic.AtomicInteger

class ItemListViewController {
    // Services
    private val transactionService = TransactionService()
    private val procurementService = ProcurementService()

    // State
    private val lastSelectedIndex = AtomicInteger(-1)

    // UI Components
    lateinit var itemListTable: TableView<Item>
    lateinit var itemColumn: TableColumn<Item, String>
    lateinit var quantityColumn: TableColumn<Item, Double>
    lateinit var unitColumn: TableColumn<Item, String>
    lateinit var priceColumn: TableColumn<Item, Double>
    lateinit var totalPriceColumn: TableColumn<Item, Double>
    lateinit var descriptionArea: TextArea
    lateinit var buyLinkArea: TextArea
    lateinit var descriptionLabel: Label
    lateinit var buyLinkLabel: Label
    lateinit var subtotalLabel: Label
    lateinit var taxesLabel: Label
    lateinit var shippingFeesLabel: Label
    lateinit var totalLabel: Label
    lateinit var currencyComboBox: ComboBox<CurrencyCode>
    lateinit var removeItemButton: Button
    lateinit var taxesInput: RegexLimitingTextField
    lateinit var shippingFeesInput: RegexLimitingTextField
    lateinit var backButton: Button
    lateinit var transactionNameField: TextField
    lateinit var editItemButton: Button
    private lateinit var transaction: Transaction
    private lateinit var procurement: Procurement

    // Method to get the FXML view
    companion object {
        fun getView() = FXMLLoader(ItemListViewController::class.java.getResource("ItemListView.fxml"))
    }

    private fun updateTransactionDetails() {
        transaction.let {
            val format = NumberFormat.getNumberInstance().apply {
                minimumFractionDigits = 2
                maximumFractionDigits = 2
                maximumIntegerDigits = Int.MAX_VALUE
            }

            // Load items and set table data
            val items = FXCollections.observableArrayList(it.items)
            itemListTable.items = items

            // Calculating the subtotal by summing up total prices of all items.
            val subtotal = items.map(Item::totalPrice).sum()

            // Calculating the total cost of the transaction
            it.totalPrice = subtotal + it.taxes + it.shippingRate
            transactionService.update(it)
            procurementService.update(procurement)

            // Display the details
            subtotalLabel.text = format.format(subtotal)
            taxesLabel.text = format.format(it.taxes)
            shippingFeesLabel.text = format.format(it.shippingRate)
            totalLabel.text = format.format(it.totalPrice)
        }
    }

    // Method to initialize the BOM Table
    private fun initializeTable() {
        taxesInput.inputValidator = InputValidator("^[0-9]*$", "Only numbers are allowed")
        shippingFeesInput.inputValidator = InputValidator("^[0-9]*$", "Only numbers are allowed")
        itemListTable.columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN

        // Set cell value factories
        itemColumn.cellValueFactory = PropertyValueFactory("item")
        quantityColumn.cellValueFactory = PropertyValueFactory("quantity")
        unitColumn.cellValueFactory = PropertyValueFactory("unit")
        priceColumn.cellValueFactory = PropertyValueFactory("price")
        totalPriceColumn.cellValueFactory = PropertyValueFactory("totalPrice")

        setDoubleCellFactory(priceColumn)
        setDoubleCellFactory(totalPriceColumn)

        // Set table selection listener
        itemListTable.selectionModel.selectedItemProperty().addListener { _, _, newSelection ->
            handleTableSelection(newSelection)
        }

        // Set custom row factory to handle row clicks

        // Set custom row factory to handle row clicks
        itemListTable.rowFactory = Callback {
            createRowWithClickHandler(
                itemListTable,
                lastSelectedIndex, ::editItem
            )
        }
        // Bind button disable property to table selection
        removeItemButton.disableProperty().bind(itemListTable.selectionModel.selectedItemProperty().isNull)
        editItemButton.disableProperty().bind(itemListTable.selectionModel.selectedItemProperty().isNull)
    }

    // Method to initialize currency ComboBox
    private fun initializeCurrencyComboBox() {
        currencyComboBox.items = FXCollections.observableArrayList(*CurrencyCode.entries.toTypedArray())
    }

    // Method to initialize the information area
    private fun initializeInfoArea() {
        collapseInfoArea(true)
    }

    // Method to handle table selection
    private fun handleTableSelection(newSelection: Item?) {
        if (newSelection != null) {
            descriptionArea.text = newSelection.description
            buyLinkArea.text = newSelection.buyLink
            collapseInfoArea(false)
        } else {
            collapseInfoArea(true)
        }
    }

    // Method to toggle the visibility of the information area
    private fun collapseInfoArea(collapse: Boolean) {
        val visible = !collapse
        descriptionLabel.isVisible = visible
        descriptionLabel.isManaged = visible
        buyLinkLabel.isVisible = visible
        buyLinkLabel.isManaged = visible
        descriptionArea.isVisible = visible
        descriptionArea.isManaged = visible
        buyLinkArea.isVisible = visible
        buyLinkArea.isManaged = visible
    }

    // Method to add an item
    fun handleAddItem(actionEvent: ActionEvent) {
        val itemFormController = changeScene(ItemFormController.getView(), actionEvent) as ItemFormController
        itemFormController.init(procurement, transaction)
    }

    // Method to remove an item
    fun handleRemoveItem(actionEvent: ActionEvent) {
        val selectedItem = itemListTable.selectionModel.selectedItem
        if (selectedItem != null) {
            transaction.items.remove(selectedItem)
            itemListTable.items.remove(selectedItem)
            transactionService.update(transaction)
            procurementService.update(procurement)
        } else {
            println("No item selected to remove.")
        }
    }

    fun updateTaxes(actionEvent: ActionEvent) {
        transactionService.update(transaction.apply { taxes = taxesInput.text.toDouble() })
        updateTransactionDetails()
    }

    fun updateShipping(actionEvent: ActionEvent) {
        transactionService.update(transaction.apply { shippingRate = shippingFeesInput.text.toDouble() })
        updateTransactionDetails()
    }

    fun handleBack(actionEvent: ActionEvent) {
        val transactionListViewController =
            changeScene(TransactionListViewController.getView(), actionEvent) as TransactionListViewController
        transactionListViewController.init(procurement)
    }

    fun updateCurrency(actionEvent: ActionEvent) {
        transactionService.update(transaction.apply { currencyCode = currencyComboBox.value })
    }

    fun handleChangeTransactionName(actionEvent: ActionEvent) {
        val newTransactionName = transactionNameField.text
        if (!newTransactionName.isNullOrBlank()) {
            transaction.transactionName = newTransactionName
            transactionService.update(transaction)
        } else {
            transactionNameField.text = transaction.transactionName
        }
    }

    private fun editItem(item: Item, scene: Scene) {
        val itemFormController = changeScene(ItemFormController.getView(), scene) as ItemFormController
        itemFormController.init(procurement, transaction)
        itemFormController.setItem(item)
    }

    fun handleEditItem(actionEvent: ActionEvent) {
        val selectedItem = itemListTable.selectionModel.selectedItem
        if (selectedItem != null) {
            val itemFormController = changeScene(ItemFormController.getView(), actionEvent) as ItemFormController
            itemFormController.init(procurement, transaction)
            itemFormController.setItem(selectedItem)
        } else {
            println("No item selected to update.")
        }
    }

    fun handleExportExcel(actionEvent: ActionEvent) {
        ExcelGenerator.generateExcelFromTransaction(transaction)
    }

    fun init(procurement: Procurement, transaction: Transaction) {
        this.procurement = procurement
        this.transaction = transaction

        currencyComboBox.selectionModel.select(transaction.currencyCode)
        transactionNameField.text = transaction.transactionName

        initializeTable()
        initializeCurrencyComboBox()
        initializeInfoArea()
        updateTransactionDetails()
    }
}