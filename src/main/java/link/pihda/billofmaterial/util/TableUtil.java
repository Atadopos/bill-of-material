/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

package link.pihda.billofmaterial.util;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import java.text.NumberFormat;
import java.util.concurrent.atomic.AtomicInteger;

public class TableUtil {
    public static <T> void setDoubleCellFactory(TableColumn<T, Double> column) {
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);

        column.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                if (value == null || empty) {
                    setText(null);
                } else {
                    setText(format.format(value));
                }
            }
        });
    }

    // Method to create table row with a click handler
    public static <T> TableRow<T> createRowWithClickHandler(TableView<T> transactionTableView, AtomicInteger lastIndex) {
        TableRow<T> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if (!row.isEmpty()) {
                int index = row.getIndex();
                if (lastIndex.get() == index && transactionTableView.getSelectionModel().isSelected(index)) {
                    transactionTableView.getSelectionModel().clearSelection(index);
                    lastIndex.set(-1);
                } else {
                    lastIndex.set(index);
                }
            }
        });
        return row;
    }
}
