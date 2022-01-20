package com.example.splitwise.utils;

import com.example.splitwise.model.expense.GroupExpense;
import com.example.splitwise.model.expense.IndividualExpense;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ExcelExpenseExporter<T> implements Exporter<T> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public ByteArrayResource export(List<T> data) {
        Workbook workbook = new HSSFWorkbook();

        Sheet sheet = workbook.createSheet("Expenses Data");

        Map<String, Object[]> exportData = new TreeMap<>();
        if (data.iterator().next() instanceof IndividualExpense) {
            exportData.put("1", new Object[]{"Id", "Amount", "Creation Date", "Currency", "Creator Id", "Target Id"});
            int counter = 2;
            for (T expenseData : data) {
                IndividualExpense expense = (IndividualExpense) expenseData;
                exportData.put(Integer.toString(counter), new Object[]{expense.getId(), expense.getAmount().toString(),
                    expense.getCreationDate().toString(), expense.getCurrency().toString(),
                    expense.getCreatorId(), expense.getTargetId()});
                counter = counter + 1;
            }

        } else {
            exportData.put("1", new Object[]{"Id", "Amount", "Creation Date", "Currency", "Creator Id", "Group Id"});
            int counter = 2;
            for (T expenseData : data) {
                GroupExpense expense = (GroupExpense) expenseData;
                exportData.put(Integer.toString(counter), new Object[]{expense.getId(), expense.getAmount().toString(),
                    expense.getCreationDate().toString(), expense.getCurrency().toString(),
                    expense.getCreatorId(), expense.getGroupId()});
                counter = counter + 1;
            }
        }
        logger.info("Export: exporting to excel format");
        Set<String> keyset = exportData.keySet();
        int rownum = 0;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = exportData.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String)
                    cell.setCellValue((String) obj);
                else if (obj instanceof Integer)
                    cell.setCellValue((Integer) obj);
                logger.info("Export: " + cell);
            }
        }

        try (ByteArrayOutputStream byteArray = new ByteArrayOutputStream()) {
            byte[] bytes;
            workbook.write(byteArray);

            bytes = byteArray.toByteArray();
            logger.info("Export: completed");
            return new ByteArrayResource(bytes);
        } catch (Exception e) {
            logger.info("Export: failed");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getExportedPath() {
        return new File("C:/exports/excel.xlsx").getAbsolutePath();
    }
}

