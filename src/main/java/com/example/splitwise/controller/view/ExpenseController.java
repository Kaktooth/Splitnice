package com.example.splitwise.controller.view;

import com.example.splitwise.controller.rest.RestRequestService;
import com.example.splitwise.model.Currency;
import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.ExpenseBuilder;
import com.example.splitwise.model.expense.IndividualExpense;
import com.example.splitwise.model.expense.SplittingType;
import com.example.splitwise.utils.TimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/dashboard/expenses")
public class ExpenseController {

    private final RestRequestService restResponsesService;

    @Autowired
    public ExpenseController(RestRequestService restResponsesService) {
        this.restResponsesService = restResponsesService;
    }

    @GetMapping
    public String getExpensesAccount(Model model) {
        model.addAttribute("id", 1);
        model.addAttribute("expenses", null);
        model.addAttribute("split", SplittingType.values());
        model.addAttribute("currency", Currency.values());

        return "redirect:/dashboard/expenses/1?expenses";
    }

    @GetMapping("/{id}")
    public String getExpensesWithUserId(@PathVariable("id") int id, Model model) {
        ArrayList<LinkedHashMap<String, Object>> expenses = restResponsesService.findAccountExpenses(id);
        List<Expense> expenseList = new ArrayList<>();
        for (LinkedHashMap<String, Object> expense : expenses) {
            Expense expenseFromLinked = new ExpenseBuilder()
                .withId((Integer)expense.get("id"))
                .withAmount(new BigDecimal((Double) expense.get("amount")))
                .withCreationDate(OffsetDateTime.now())
                .withCurrency(Currency.valueOf((String)expense.get("currency")))
                .withCreatorId((Integer)expense.get("creatorId"))
                .buildIndividualExpense();
            expenseList.add(expenseFromLinked);
        }

        model.addAttribute("expenses", expenseList);
        return "dashboard";
    }
}

