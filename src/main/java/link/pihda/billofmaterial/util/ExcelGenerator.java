/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

package link.pihda.billofmaterial.util;

import javafx.scene.control.Alert;
import link.pihda.billofmaterial.entity.Item;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static link.pihda.billofmaterial.util.PathUtil.GetUserDataPath;

public class ExcelGenerator {

    public static void generateExcelFromItems(List<Item> items) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Items");

            // creating the header
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Item");
            header.createCell(1).setCellValue("Description");
            header.createCell(2).setCellValue("Unit");
            header.createCell(3).setCellValue("Buy Link");
            header.createCell(4).setCellValue("Price");
            header.createCell(5).setCellValue("Quantity");
            header.createCell(6).setCellValue("Total Price");

            // populate data
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(item.getItem());
                row.createCell(1).setCellValue(item.getDescription());
                row.createCell(2).setCellValue(item.getUnit().toString());
                row.createCell(3).setCellValue(item.getBuyLink());
                row.createCell(4).setCellValue(item.getPrice());
                row.createCell(5).setCellValue(item.getQuantity());
                row.createCell(6).setCellValue(item.getTotalPrice());
            }

            // write to file
            try (FileOutputStream fileOut = new FileOutputStream(GetUserDataPath() + "Items.xlsx")) {
                workbook.write(fileOut);
                var alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Saved to: " + GetUserDataPath() + "Items.xlsx");
                alert.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




