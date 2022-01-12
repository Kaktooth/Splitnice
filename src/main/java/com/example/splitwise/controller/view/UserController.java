package com.example.splitwise.controller.view;

import com.example.splitwise.controller.rest.RestRequestService;
import com.example.splitwise.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
@RequestMapping(value = "/account")
public class UserController {

    private final RestRequestService restResponsesService;


    @Autowired
    public UserController(RestRequestService restResponsesService) {
        this.restResponsesService = restResponsesService;
    }

    @GetMapping
    public String getUserAccount(Model model) {
        Integer id = 1;
        model.addAttribute("id", id);
        model.addAttribute("edit", null);
        return "redirect:/account/" + id + "?edit=";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") int id,
                          @RequestParam(value = "edit") String edit,
                          Model model) {
        User user = restResponsesService.findUser(id);

        model.addAttribute("edit", edit);
        model.addAttribute("savedEdit", edit);
        model.addAttribute("userObject", user);
        return "/account";
    }

//    @GetMapping("/{id}/{edit}")
//    public String getUserAccountWithId(@PathVariable("id") int id, Model model) {
//        User user = restResponsesService.findUser(id);
//
//        model.addAttribute("userObject", user);
//        return "/account";
//    }
//
//    @PutMapping("/{id}/{edit}")
//    public String edit(@PathVariable("id") Integer id,
//                       @PathVariable("edit") String edit,
//                       Model model) {
//
//        User user = restResponsesService.findUser(id);
//
//        user = restResponsesService.editUserByParam(id, user);
//
//        model.addAttribute("userObject", user);
//        return "/account";
//    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Integer id,
                         @RequestParam(value = "edit") String edit,
                         @RequestParam(value = "email", required = false) String email,
                         @RequestParam(value = "new-password", required = false) String password,
                         @RequestParam(value = "phone", required = false) String phone,
                         Model model) {

        String currentParam = "";
        if (Objects.equals(edit, "email")) {
            currentParam = "email=" + email;

        } else if (Objects.equals(edit, "password")) {
            currentParam = "new-password=" + password;
        } else {
            currentParam = "phone=" + phone;
        }
        User editedUser = restResponsesService.editUser(
            id,
            (User) model.getAttribute("userObject"),
            edit,
            currentParam);


        model.addAttribute("userObject", editedUser);
        model.addAttribute("savedEdit", edit);
        return "/account";
    }
}
