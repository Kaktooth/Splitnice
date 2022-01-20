package com.example.splitwise.controller.rest;

import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.service.ExpenseService;
import com.example.splitwise.utils.ExcelExpenseExporter;
import com.example.splitwise.utils.Exporter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/export")
public class RestExportController {

    private final ExpenseService expenseService;

    public RestExportController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<ByteArrayResource> export() throws IOException {

        List<Expense> expenseList = expenseService.getUserExpenses(SecurityContextHolder.getContext()
            .getAuthentication()
            .getName());

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
