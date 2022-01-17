package com.example.splitwise.controller.view;

import com.example.splitwise.model.Currency;
import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.ExpenseBuilder;
import com.example.splitwise.utils.ExcelExpenseExporter;
import com.example.splitwise.utils.Exporter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/export")
public class ExportController {

    @GetMapping
    public ResponseEntity<ByteArrayResource> export() throws IOException {
        List<Expense> expenseList = new ArrayList<>();

//        Expense expense = new ExpenseBuilder()
//            .withId(1)
//            .withAmount(new BigDecimal("13.5"))
//            .withCreationDate(OffsetDateTime.now())
//            .withCurrency(Currency.USD)
//            .withCreatorId(1)
//            .buildIndividualExpense();
//        expenseList.add(expense);
//
//        Expense expense2 = new ExpenseBuilder()
//            .withId(2)
//            .withAmount(new BigDecimal("100"))
//            .withCreationDate(OffsetDateTime.now())
//            .withCurrency(Currency.EUR)
//            .withCreatorId(1)
//            .buildIndividualExpense();
//        expenseList.add(expense2);
//
//        Expense expense3 = new ExpenseBuilder()
//            .withId(3)
//            .withAmount(new BigDecimal("76.2"))
//            .withCreationDate(OffsetDateTime.now())
//            .withCurrency(Currency.USD)
//            .withCreatorId(2)
//            .buildIndividualExpense();
//        expenseList.add(expense3);
//
//        Expense expense4 = new ExpenseBuilder()
//            .withId(4)
//            .withAmount(new BigDecimal("14.5"))
//            .withCreationDate(OffsetDateTime.now())
//            .withCurrency(Currency.EUR)
//            .withCreatorId(2)
//            .buildIndividualExpense();
//        expenseList.add(expense4);
//
//        Expense expense5 = new ExpenseBuilder()
//            .withId(5)
//            .withAmount(new BigDecimal("176.2"))
//            .withCreationDate(OffsetDateTime.now())
//            .withCurrency(Currency.USD)
//            .withCreatorId(2)
//            .buildIndividualExpense();
//        expenseList.add(expense5);
//
//        Expense expense6 = new ExpenseBuilder()
//            .withId(6)
//            .withAmount(new BigDecimal("144.5"))
//            .withCreationDate(OffsetDateTime.now())
//            .withCurrency(Currency.EUR)
//            .withCreatorId(3)
//            .buildIndividualExpense();
//        expenseList.add(expense6);


        Expense expense = new ExpenseBuilder()
            .withId(1)
            .withAmount(new BigDecimal("13.5"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.USD)
            .withCreatorId(1)
            .withGroupId(1)
            .buildGroupExpense();
        expenseList.add(expense);

        Expense expense2 = new ExpenseBuilder()
            .withId(2)
            .withAmount(new BigDecimal("100"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.EUR)
            .withCreatorId(1)
            .withGroupId(2)
            .buildGroupExpense();
        expenseList.add(expense2);

        Exporter<Expense> exporter = new ExcelExpenseExporter<>();
        ByteArrayResource bytes = exporter.export(expenseList);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=expenses.xlsx");

        return ResponseEntity.ok()
            .headers(headers)
            .contentLength(bytes.contentLength())
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(bytes);
    }
}
