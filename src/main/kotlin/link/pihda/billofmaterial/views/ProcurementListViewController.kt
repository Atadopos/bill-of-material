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
import link.pihda.billofmaterial.service.ProcurementService
import link.pihda.billofmaterial.util.GuiUtil.ChangeScene
import link.pihda.billofmaterial.util.TableUtil.createRowWithClickHandler
import link.pihda.billofmaterial.util.TableUtil.setDoubleCellFactory
import java.util.concurrent.atomic.AtomicInteger

class ProcurementListViewController {
    private val procurementService = ProcurementService()
    private val lastSelectedIndex = AtomicInteger(-1)
    lateinit var procurementTableView: TableView<Procurement>
    lateinit var procurementNameColumn: TableColumn<Procurement, String>
    lateinit var totalPriceColumn: TableColumn<Procurement, Double>
    lateinit var viewButton: Button
    lateinit var removeButton: Button
    lateinit var procurementField: TextField

    companion object {
        fun getView(): FXMLLoader {
            return FXMLLoader(ProcurementListViewController::class.java.getResource("ProcurementListView.fxml"))
        }
    }

    fun init() {
        procurementTableView.columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN
        viewButton.disableProperty().bind(procurementTableView.selectionModel.selectedItemProperty().isNull)
        removeButton.disableProperty().bind(procurementTableView.selectionModel.selectedItemProperty().isNull)
        procurementTableView.rowFactory = Callback { createRowWithClickHandler(procurementTableView, lastSelectedIndex) }
        loadProcurements()
    }

    fun viewProcurement(actionEvent: ActionEvent) {
        val selectedProcurement = procurementTableView.selectionModel.selectedItem
        if (selectedProcurement != null) {
            val transactionListViewController = ChangeScene<TransactionListViewController>(TransactionListViewController.getView(), actionEvent)
            transactionListViewController.init(selectedProcurement)
        }
    }

    fun handleRemoveProcurement(actionEvent: ActionEvent) {
        val selectedProcurement = procurementTableView.selectionModel.selectedItem
        if (selectedProcurement != null) {
            procurementService.delete(selectedProcurement)
            procurementTableView.items.remove(selectedProcurement)
        } else {
            println("No procurement selected to remove.")
        }
    }

    fun handleNewProcurement(actionEvent: ActionEvent) {
        val procurementFormController = ChangeScene<ProcurementFormController>(ProcurementFormController.getView(), actionEvent)
        procurementFormController.init()
    }

    private fun loadProcurements() {
        val procurements: ObservableList<Procurement> = FXCollections.observableArrayList(procurementService.getAll())
        procurementNameColumn.cellValueFactory = PropertyValueFactory("name")
        totalPriceColumn.cellValueFactory = PropertyValueFactory("totalPrice")
        setDoubleCellFactory(totalPriceColumn)
        procurementTableView.items = procurements
    }
}
