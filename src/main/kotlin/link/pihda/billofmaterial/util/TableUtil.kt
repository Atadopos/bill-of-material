/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */
package link.pihda.billofmaterial.util

import javafx.scene.control.TableCell
import javafx.scene.control.TableColumn
import javafx.scene.control.TableRow
import javafx.scene.control.TableView
import java.text.NumberFormat
import java.util.concurrent.atomic.AtomicInteger

object TableUtil {
    fun <T> setDoubleCellFactory(column: TableColumn<T, Double>) {
        val format = NumberFormat.getNumberInstance()
        format.minimumFractionDigits = 2
        format.maximumFractionDigits = 2

        column.setCellFactory { _ ->
            object : TableCell<T, Double>() {
                override fun updateItem(value: Double?, empty: Boolean) {
                    super.updateItem(value, empty)
                    text = if (value == null || empty) {
                        null
                    } else {
                        format.format(value)
                    }
                }
            }
        }
    }

    fun <T> createRowWithClickHandler(transactionTableView: TableView<T>, lastIndex: AtomicInteger): TableRow<T> {
        return TableRow<T>().apply {
            setOnMouseClicked {
                if (!isEmpty) {
                    val index = index
                    if (lastIndex.get() == index && transactionTableView.selectionModel.isSelected(index)) {
                        transactionTableView.selectionModel.clearSelection(index)
                        lastIndex.set(-1)
                    } else {
                        lastIndex.set(index)
                    }
                }
            }
        }
    }
}
