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
import link.pihda.billofmaterial.entity.Procurement
import link.pihda.billofmaterial.entity.Transaction
import org.apache.poi.common.usermodel.HyperlinkType
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFFont
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

    fun generateExcelFromTransaction(transaction: Transaction) {
        XSSFWorkbook().use { workbook ->
            val sheet: Sheet = workbook.createSheet("Transactions")

            // Initialize row count
            var rowCount = 0

            // Create custom styles
            val transactionStyle = workbook.createCellStyle()
            transactionStyle.fillForegroundColor = IndexedColors.GREY_25_PERCENT.getIndex()
            transactionStyle.fillPattern = FillPatternType.SOLID_FOREGROUND

            val itemStyle = workbook.createCellStyle()
            itemStyle.fillForegroundColor = IndexedColors.LIGHT_GREEN.getIndex()
            itemStyle.fillPattern = FillPatternType.SOLID_FOREGROUND

            val hyperlinkStyle = workbook.createCellStyle()
            val hlinkFont = workbook.createFont()
            hlinkFont.underline = XSSFFont.U_SINGLE
            hlinkFont.color = IndexedColors.BLUE.getIndex()
            hyperlinkStyle.setFont(hlinkFont)

            // Create a helper
            val helper = workbook.creationHelper

            // Iterate over transactions

            // Create transaction header
            val transactionHeader: Row = sheet.createRow(rowCount++)
            transactionHeader.createCell(0).setCellValue("Transaction ID: ${transaction.id}")
            transactionHeader.createCell(1).setCellValue("Transaction Name: ${transaction.transactionName}")
            transactionHeader.createCell(2).setCellValue("Taxes: ${transaction.taxes}")
            transactionHeader.createCell(3).setCellValue("Currency Code: ${transaction.currencyCode.name}")

            // Apply styles to transaction header
            for (i in 0..3) {
                transactionHeader.getCell(i).cellStyle = transactionStyle
            }

            // Create item header
            val itemHeader: Row = sheet.createRow(rowCount++)
            itemHeader.createCell(0).setCellValue("Item ID")
            itemHeader.createCell(1).setCellValue("Item Name")
            itemHeader.createCell(2).setCellValue("Quantity")
            itemHeader.createCell(3).setCellValue("Unit")
            itemHeader.createCell(4).setCellValue("Price")
            itemHeader.createCell(5).setCellValue("Total Price")
            itemHeader.createCell(6).setCellValue("Buy Link")

            // Apply styles to item header
            for (i in 0..6) {
                itemHeader.getCell(i).cellStyle = itemStyle
            }

            // Iterate over items
            var totalItems = 0.0
            for (item in transaction.items) {
                val itemRow: Row = sheet.createRow(rowCount++)
                itemRow.createCell(0).setCellValue(item.id.toString())
                itemRow.createCell(1).setCellValue(item.item)
                itemRow.createCell(2).setCellValue(item.quantity)
                itemRow.createCell(3).setCellValue(item.unit.name)
                itemRow.createCell(4).setCellValue(item.price)
                itemRow.createCell(5).setCellValue(item.totalPrice)

                val cell = itemRow.createCell(6)
                cell.setCellValue(item.buyLink)
                val hyperlink = helper.createHyperlink(HyperlinkType.URL)
                hyperlink.address = item.buyLink
                cell.hyperlink = hyperlink
                cell.cellStyle = hyperlinkStyle

                totalItems += item.totalPrice
            }

            // Add total line for items
            val totalLine: Row = sheet.createRow(rowCount++)
            totalLine.createCell(4).setCellValue("Total:")
            totalLine.createCell(5).setCellValue(totalItems)

            // Add Shipping Rate and Total Price for the transaction
            val shippingRateLine: Row = sheet.createRow(rowCount++)
            shippingRateLine.createCell(4).setCellValue("Shipping Rate:")
            shippingRateLine.createCell(5).setCellValue(transaction.shippingRate)

            val totalPriceLine: Row = sheet.createRow(rowCount++)
            totalPriceLine.createCell(4).setCellValue("Total Price:")
            totalPriceLine.createCell(5).setCellValue(transaction.totalPrice)

            // Add a gap between transactions
            rowCount++


            // write to file
            try {
                FileOutputStream("${PathUtil.getUserDataPath()}Transactions.xlsx").use { fileOut ->
                    workbook.write(fileOut)
                    val alert = Alert(Alert.AlertType.INFORMATION)
                    alert.contentText = "Saved to: ${PathUtil.getUserDataPath()}Transactions.xlsx"
                    alert.show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun generateExcelFromProcurement(procurement: Procurement) {
        XSSFWorkbook().use { workbook ->
            val sheet: Sheet = workbook.createSheet("Procurement")

            // Initialize row count
            var rowCount = 0

            // Create custom styles
            val transactionStyle = workbook.createCellStyle()
            transactionStyle.fillForegroundColor = IndexedColors.GREY_25_PERCENT.getIndex()
            transactionStyle.fillPattern = FillPatternType.SOLID_FOREGROUND

            val itemStyle = workbook.createCellStyle()
            itemStyle.fillForegroundColor = IndexedColors.LIGHT_GREEN.getIndex()
            itemStyle.fillPattern = FillPatternType.SOLID_FOREGROUND

            val hyperlinkStyle = workbook.createCellStyle()
            val hlinkFont = workbook.createFont()
            hlinkFont.underline = XSSFFont.U_SINGLE
            hlinkFont.color = IndexedColors.BLUE.getIndex()
            hyperlinkStyle.setFont(hlinkFont)

            // Create a helper
            val helper = workbook.creationHelper

            // Procurement header
            val procurementHeader: Row = sheet.createRow(rowCount++)
            procurementHeader.createCell(0).setCellValue("Procurement ID: ${procurement.id}")
            procurementHeader.createCell(1).setCellValue("Procurement Name: ${procurement.name}")

            // Iterate over transactions
            for (transaction in procurement.transactions) {
                // Create transaction header
                val transactionHeader: Row = sheet.createRow(rowCount++)
                transactionHeader.createCell(0).setCellValue("Transaction ID: ${transaction.id}")
                transactionHeader.createCell(1).setCellValue("Transaction Name: ${transaction.transactionName}")
                transactionHeader.createCell(2).setCellValue("Taxes: ${transaction.taxes}")
                transactionHeader.createCell(3).setCellValue("Currency Code: ${transaction.currencyCode.name}")

                // Apply styles to transaction header
                for (i in 0..3) {
                    transactionHeader.getCell(i).cellStyle = transactionStyle
                }

                // Create item header
                val itemHeader: Row = sheet.createRow(rowCount++)
                itemHeader.createCell(0).setCellValue("Item ID")
                itemHeader.createCell(1).setCellValue("Item Name")
                itemHeader.createCell(2).setCellValue("Quantity")
                itemHeader.createCell(3).setCellValue("Unit")
                itemHeader.createCell(4).setCellValue("Price")
                itemHeader.createCell(5).setCellValue("Total Price")
                itemHeader.createCell(6).setCellValue("Buy Link")

                // Apply styles to item header
                for (i in 0..6) {
                    itemHeader.getCell(i).cellStyle = itemStyle
                }

                // Iterate over items
                for (item in transaction.items) {
                    val itemRow: Row = sheet.createRow(rowCount++)
                    itemRow.createCell(0).setCellValue(item.id.toString())
                    itemRow.createCell(1).setCellValue(item.item)
                    itemRow.createCell(2).setCellValue(item.quantity)
                    itemRow.createCell(3).setCellValue(item.unit.name)
                    itemRow.createCell(4).setCellValue(item.price)
                    itemRow.createCell(5).setCellValue(item.totalPrice)

                    val cell = itemRow.createCell(6)
                    cell.setCellValue(item.buyLink)
                    val hyperlink = helper.createHyperlink(HyperlinkType.URL)
                    hyperlink.address = item.buyLink
                    cell.hyperlink = hyperlink
                    cell.cellStyle = hyperlinkStyle
                }

                // Add Shipping Rate and Total Price for the transaction
                val shippingRateLine: Row = sheet.createRow(rowCount++)
                shippingRateLine.createCell(4).setCellValue("Shipping Rate:")
                shippingRateLine.createCell(5).setCellValue(transaction.shippingRate)

                val transactionTotalPriceLine: Row = sheet.createRow(rowCount++)
                transactionTotalPriceLine.createCell(4).setCellValue("Total Price:")
                transactionTotalPriceLine.createCell(5).setCellValue(transaction.totalPrice)

                // Add a gap between transactions
                rowCount++
            }

            // Procurement Total Price
            val procurementTotalPriceLine: Row = sheet.createRow(rowCount++)
            procurementTotalPriceLine.createCell(4).setCellValue("Procurement Total Price:")
            procurementTotalPriceLine.createCell(5).setCellValue(procurement.totalPrice)

            // write to file
            try {
                FileOutputStream("${PathUtil.getUserDataPath()}Procurement_${procurement.id}.xlsx").use { fileOut ->
                    workbook.write(fileOut)
                    val alert = Alert(Alert.AlertType.INFORMATION)
                    alert.contentText = "Saved to: ${PathUtil.getUserDataPath()}Procurement_${procurement.id}.xlsx"
                    alert.show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}

