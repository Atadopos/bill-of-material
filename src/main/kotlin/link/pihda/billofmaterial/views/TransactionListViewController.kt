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
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.fxml.FXMLLoader
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.util.Callback
import link.pihda.billofmaterial.entity.Procurement
import link.pihda.billofmaterial.entity.Transaction
import link.pihda.billofmaterial.enums.CurrencyCode
import link.pihda.billofmaterial.service.ProcurementService
import link.pihda.billofmaterial.service.TransactionService
import link.pihda.billofmaterial.util.TableUtil.setDoubleCellFactory
import link.pihda.billofmaterial.util.TableUtil.createRowWithClickHandler
import link.pihda.billofmaterial.util.GuiUtil.ChangeScene
import java.text.NumberFormat
import java.util.concurrent.atomic.AtomicInteger

class TransactionListViewController {
    private val transactionService = TransactionService()
    private val lastSelectedIndex = AtomicInteger(-1)
    private val procurementService = ProcurementService()
    lateinit var transactionTableView: TableView<Transaction>
    lateinit var transactionNameColumn: TableColumn<Transaction, String>
    lateinit var totalPriceColumn: TableColumn<Transaction, Double>
    lateinit var removeButton: Button
    lateinit var viewButton: Button
    lateinit var currencyColumn: TableColumn<Transaction, CurrencyCode>
    lateinit var procurementField: TextField
    lateinit var backButton: Button
    lateinit var totalLabel: Label
    private lateinit var procurement: Procurement

    companion object {
        fun getView(): FXMLLoader {
            return FXMLLoader(TransactionListViewController::class.java.getResource("TransactionListView.fxml"))
        }
    }

    fun handleViewTransaction(event: ActionEvent) {
        val selectedTransaction = transactionTableView.selectionModel.selectedItem
        if (selectedTransaction != null) {
            val itemListViewController = ChangeScene<ItemListViewController>(ItemListViewController.getView(), event)
            itemListViewController.init(procurement, selectedTransaction)
        }
    }

    fun handleNewTransaction(actionEvent: ActionEvent) {
        val transactionFormController = ChangeScene<TransactionFormController>(TransactionFormController.getView(), actionEvent)
        transactionFormController.init(procurement)
    }

    private fun loadTransactions() {
        val transactions: ObservableList<Transaction> = FXCollections.observableArrayList(procurement.transactions)
        transactionNameColumn.setCellValueFactory(PropertyValueFactory("transactionName"))
        totalPriceColumn.setCellValueFactory(PropertyValueFactory("totalPrice"))
        currencyColumn.setCellValueFactory(PropertyValueFactory("currencyCode"))
        setDoubleCellFactory(totalPriceColumn)
        transactionTableView.items = transactions
    }

    fun handleRemoveTransaction(actionEvent: ActionEvent) {
        val selectedTransaction = transactionTableView.selectionModel.selectedItem
        if (selectedTransaction != null) {
            transactionService.delete(selectedTransaction)
            transactionTableView.items.remove(selectedTransaction)
        } else {
            println("No item selected to remove.")
        }
    }

    fun changeProcurementName(actionEvent: ActionEvent) {
        val newProcurementName = procurementField.text
        if (newProcurementName != null && newProcurementName.trim().isNotEmpty()) {
            procurement.name = newProcurementName
            procurementService.update(procurement)
        } else {
            procurementField.text = procurement.name
        }
    }

    fun handleBack(actionEvent: ActionEvent) {
        val procurementListViewController = ChangeScene<ProcurementListViewController>(ProcurementListViewController.getView(), actionEvent)
        procurementListViewController.init()
    }

    fun init(procurement: Procurement) {
        transactionTableView.columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN
        removeButton.disableProperty().bind(transactionTableView.selectionModel.selectedItemProperty().isNull)
        viewButton.disableProperty().bind(transactionTableView.selectionModel.selectedItemProperty().isNull)
        transactionTableView.rowFactory = Callback { createRowWithClickHandler(transactionTableView, lastSelectedIndex) }

        this.procurement = procurement
        procurementField.text = procurement.name
        val format = NumberFormat.getNumberInstance()
        format.minimumFractionDigits = 2
        format.maximumFractionDigits = 2
        totalLabel.text = format.format(procurement.totalPrice)
        loadTransactions()
    }
}
