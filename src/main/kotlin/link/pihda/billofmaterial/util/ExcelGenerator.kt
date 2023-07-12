/*
 * Copyright (c) 2023.  Adib S. A.
 * Copyright (c) 2023.  PT Atadopos
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */
package link.pihda.billofmaterial.util

import javafx.scene.control.Alert
import link.pihda.billofmaterial.entity.Item
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileOutputStream
import java.io.IOException

object ExcelGenerator {

    fun generateExcelFromItems(items: List<Item>) {
        XSSFWorkbook().use { workbook ->
            val sheet: Sheet = workbook.createSheet("Items")

            // creating the header
            val header: Row = sheet.createRow(0)
            header.createCell(0).setCellValue("Item")
            header.createCell(1).setCellValue("Description")
            header.createCell(2).setCellValue("Unit")
            header.createCell(3).setCellValue("Buy Link")
            header.createCell(4).setCellValue("Price")
            header.createCell(5).setCellValue("Quantity")
            header.createCell(6).setCellValue("Total Price")

            // populate data
            for (i in items.indices) {
                val item = items[i]
                val row: Row = sheet.createRow(i + 1)
                row.createCell(0).setCellValue(item.item)
                row.createCell(1).setCellValue(item.description)
                row.createCell(2).setCellValue(item.unit.toString())
                row.createCell(3).setCellValue(item.buyLink)
                row.createCell(4).setCellValue(item.price)
                row.createCell(5).setCellValue(item.quantity)
                row.createCell(6).setCellValue(item.totalPrice)
            }

            // write to file
            try {
                FileOutputStream("${PathUtil.getUserDataPath()}Items.xlsx").use { fileOut ->
                    workbook.write(fileOut)
                    val alert = Alert(Alert.AlertType.INFORMATION)
                    alert.contentText = "Saved to: ${PathUtil.getUserDataPath()}Items.xlsx"
                    alert.show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}

